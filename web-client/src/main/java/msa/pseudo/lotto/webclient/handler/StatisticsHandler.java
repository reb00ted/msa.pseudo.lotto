package msa.pseudo.lotto.webclient.handler;

import lombok.RequiredArgsConstructor;
import msa.pseudo.lotto.webclient.dto.StatisticsDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatisticsHandler {

    private final WebClient webClient;


}
