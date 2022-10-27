package msa.pseudo.lotto.statisticsservice.controller;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.statisticsservice.dto.StatisticsDto;
import msa.pseudo.lotto.statisticsservice.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/lucky")
    public ResponseEntity<StatisticsDto> lucky() {
        return ResponseEntity.ok(statisticsService.getLucky());
    }

    @GetMapping("/unlucky")
    public ResponseEntity<StatisticsDto> unlucky() {
        return ResponseEntity.ok(statisticsService.getUnlucky());
    }

}
