package msa.pseudo.lotto.pointservice.service;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.pointservice.dto.*;
import msa.pseudo.lotto.pointservice.model.PointTransaction;
import msa.pseudo.lotto.pointservice.model.TransactionType;
import msa.pseudo.lotto.pointservice.model.User;
import msa.pseudo.lotto.pointservice.repository.PointTransactionRepository;
import msa.pseudo.lotto.pointservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public Long getCurrentPoint(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow();
        return user.getPoint();
    }

    @Transactional
    public ChargeResponse charge(String userId, Long amount) {
        User user = userRepository.findWithLockByUserId(userId).orElseThrow();
        long prevPoint = user.getPoint();
        long currPoint = user.charge(amount);

        PointTransaction pointTransaction = new PointTransaction(user, TransactionType.CHARGE, amount);
        pointTransactionRepository.save(pointTransaction);

        return ChargeResponse.builder()
                .userId(userId)
                .amount(amount)
                .prevPoint(prevPoint)
                .currPoint(currPoint)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public WithdrawResponse withdraw(String userId, Long amount) {
        User user = userRepository.findWithLockByUserId(userId).orElseThrow();
        long prevPoint = user.getPoint();
        long currPoint = user.withdraw(amount);

        PointTransaction pointTransaction = new PointTransaction(user, TransactionType.WITHDRAW, -amount);
        pointTransactionRepository.save(pointTransaction);

        return WithdrawResponse.builder()
                .userId(userId)
                .amount(amount)
                .prevPoint(prevPoint)
                .currPoint(currPoint)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Transactional
    public PaymentResponse payment(PaymentRequest request) {
        User user = userRepository.findWithLockByUserId(request.getUserId()).orElseThrow();
        long prevPoint = user.getPoint();
        long currPoint = user.payment(request.getPrice());

        PointTransaction pointTransaction = new PointTransaction(user, request);
        pointTransaction = pointTransactionRepository.save(pointTransaction);

        return PaymentResponse.builder()
                .txId(request.getTxId())
                .userId(user.getUserId())
                .price(request.getPrice())
                .prevPoint(prevPoint)
                .currPoint(currPoint)
                .createdAt(pointTransaction.getCreatedAt())
                .build();
    }

    @Transactional
    public PaymentCancelResponse paymentCancel(PaymentCancelRequest request) {
        User user = userRepository.findWithLockByUserId(request.getUserId()).orElseThrow();
        PointTransaction paymentTransaction = pointTransactionRepository.findByTxId(request.getTxId());
        if (paymentTransaction == null || request.getPrice() != paymentTransaction.getAmount()) {
            throw new IllegalStateException();
        }
        long prevPoint = user.getPoint();
        long currPoint = user.paymentCancel(request.getPrice());

        PointTransaction paymentCancelTransaction = pointTransactionRepository.findByTxIdAndTransactionType(request.getTxId(), TransactionType.PAYMENT_CANCELLED);
        if (paymentCancelTransaction != null) {

        }

        PointTransaction pointTransaction = new PointTransaction(user, request);
        pointTransaction = pointTransactionRepository.save(pointTransaction);

        return PaymentCancelResponse.builder()
                .txId(request.getTxId())
                .userId(user.getUserId())
                .price(request.getPrice())
                .prevPoint(prevPoint)
                .currPoint(currPoint)
                .createdAt(pointTransaction.getCreatedAt())
                .build();
    }


    @Transactional(readOnly = true)
    public PointHistoryResponse getPointHistory(String userId, Pageable pageable) {
        Page<PointTransaction> pointTransactions = pointTransactionRepository.findByUserUserIdOrderByIdDesc(userId, pageable);
        return new PointHistoryResponse(pointTransactions);
    }

}
