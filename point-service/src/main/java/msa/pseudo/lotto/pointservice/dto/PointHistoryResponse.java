package msa.pseudo.lotto.pointservice.dto;

import lombok.Data;
import msa.pseudo.lotto.pointservice.model.PointTransaction;
import msa.pseudo.lotto.pointservice.repository.PointTransactionRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PointHistoryResponse {
    private Boolean isFirst;
    private Boolean isLast;
    private Integer prevPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer totalPage;
    private List<Integer> pageList;
    private List<PointTransactionDto> pointTransactionList;

    public PointHistoryResponse(Page<PointTransaction> data) {
        this.isFirst = data.isFirst();
        this.isLast = data.isLast();
        this.currentPage = data.getNumber() + 1;
        this.prevPage = this.currentPage - 1;
        this.nextPage = this.currentPage + 1;
        this.totalPage = data.getTotalPages();
        int startPage = ((currentPage - 1) / 10) * 10 + 1;
        int endPage = Math.min(startPage + 9, totalPage);
        this.pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
        this.pointTransactionList = data.get().map(PointTransactionDto::new).collect(Collectors.toList());
    }
}
