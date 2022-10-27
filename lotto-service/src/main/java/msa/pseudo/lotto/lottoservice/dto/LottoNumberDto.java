package msa.pseudo.lotto.lottoservice.dto;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.domain.LottoNumber;

@Data
public class LottoNumberDto {
    private Integer number;
    private Boolean matched;

    public LottoNumberDto(LottoNumber lottoNumber) {
        this.number = lottoNumber.getId().getNumber();
        this.matched = lottoNumber.getMatched();
    }
}
