package msa.pseudo.lotto.lottoservice.controller;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.lottoservice.common.NotFoundException;
import msa.pseudo.lotto.lottoservice.dto.LottoBuyRequest;
import msa.pseudo.lotto.lottoservice.dto.LottoBuyResponse;
import msa.pseudo.lotto.lottoservice.dto.LottoListResponse;
import msa.pseudo.lotto.lottoservice.dto.LottoRoundResponse;
import msa.pseudo.lotto.lottoservice.service.LottoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class LottoServiceController {
    
    private final LottoService lottoService;
    
    @PostMapping("/lotto")
    public ResponseEntity<LottoBuyResponse> lottoBuy(
            @RequestBody @Validated LottoBuyRequest lottoBuyRequest,
            BindingResult bindingResult
    ) {
        // saga pattern 구현 필요
        // 다른 마이크로서비스와 통신 필요
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LottoBuyResponse buy = lottoService.buy(lottoBuyRequest);
        return ResponseEntity.ok(buy);
    }

    @GetMapping("/lottoRound/current")
    public ResponseEntity<LottoRoundResponse> getCurrentLottoRound() {
        LottoRoundResponse currentRound = lottoService.getCurrentRound().orElseThrow(() -> new NotFoundException());
        System.out.println("GET /lottoRound");
        System.out.println(currentRound);
        return ResponseEntity.ok(currentRound);
    }

    // 현재 진행 중인 복권 회차 이전의 회차 정보들만 조회 가능
    @GetMapping("/lottoRound/{round}")
    public ResponseEntity<LottoRoundResponse> getFinishedLottoRound(
            @PathVariable(name = "round", required = false) Integer round
    ) {
        if (round == null || round < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(lottoService.getFinishedLottoRound(round));
    }


    @GetMapping("/lottoRound/last")
    public ResponseEntity<Integer> getLastFinishedRound() {
        return ResponseEntity.ok(lottoService.getLastRound().getRound());
    }

    @GetMapping("/lotto/list/{page}")
    public ResponseEntity<LottoListResponse> getLottoList(
            @PathVariable(value = "page", required = false) Integer page,
            @RequestHeader(value = "userId", required = false) String userId
    ) {
        if (page == null || page < 1 || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println(userId);
        return ResponseEntity.ok(lottoService.getLottoList(userId, PageRequest.of(page - 1, 10)));

    }
}
