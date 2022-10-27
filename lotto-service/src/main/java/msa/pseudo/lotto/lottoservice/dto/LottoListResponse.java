package msa.pseudo.lotto.lottoservice.dto;

import lombok.Data;
import msa.pseudo.lotto.lottoservice.domain.Lotto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class LottoListResponse {
    private Boolean isFirst;
    private Boolean isLast;
    private Integer prevPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer totalPage;
    private List<Integer> pageList;
    private List<LottoResponse> lottoList;

    public LottoListResponse(Page<Lotto> data) {
        this.isFirst = data.isFirst();
        this.isLast = data.isLast();
        this.currentPage = data.getNumber() + 1;
        this.prevPage = currentPage - 1;
        this.nextPage = currentPage + 1;
        this.totalPage = data.getTotalPages();
        int startPage = ((currentPage - 1) / 10) * 10 + 1;
        int endPage = Math.min(startPage + 9, totalPage);
        this.pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
        this.lottoList = data.get().map(LottoResponse::new).collect(Collectors.toList());

    }
}
