package msa.pseudo.lotto.webclient.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class PointHistoryResponse {
    private Boolean isFirst;
    private Boolean isLast;
    private Integer prevPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer totalPage;
    private List<Integer> pageList;
    private List<PointTransactionDto> pointTransactionList;
}
