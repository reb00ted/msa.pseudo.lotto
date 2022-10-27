package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoNumberDto {
    private Integer number;
    private Boolean matched;
}
