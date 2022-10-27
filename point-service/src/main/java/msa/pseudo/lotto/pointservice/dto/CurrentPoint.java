package msa.pseudo.lotto.pointservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CurrentPoint {
    private String userId;
    private Long point;
}
