package msa.pseudo.lotto.webclient.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class LottoListResponse {
    private Boolean isFirst;
    private Boolean isLast;
    private Integer prevPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer totalPage;
    private List<Integer> pageList;
    private List<LottoResponse> lottoList;
}
