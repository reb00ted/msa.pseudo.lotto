package msa.pseudo.lotto.saga.scheduler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.saga.domain.Transaction;
import msa.pseudo.lotto.saga.repository.TransactionRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Recovery implements ApplicationRunner {

    private final TransactionRepository transactionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Transaction> incompletes = transactionRepository.findIncompletes();
        for (Transaction transaction : incompletes) {
            switch (transaction.getStatus()) {
                case PAYMENT_REQUEST:
                    // 포인트 출금 요청이 성공했는지 확인
                    // 출금 요청 실패시 트랜잭션 실패 처리
                    // 출금 요청 성공시 구매 요청
                case PAYMENT_COMPLETED:
            }
        }
    }

    @Transactional
    public Transaction failed(Transaction transaction) {
        transaction.failed();
        return transactionRepository.save(transaction);
    }
}
