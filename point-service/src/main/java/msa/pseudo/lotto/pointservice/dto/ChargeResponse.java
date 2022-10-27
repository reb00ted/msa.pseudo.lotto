package msa.pseudo.lotto.pointservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @Builder
@AllArgsConstructor
public class ChargeResponse {

    private String userId;
    private Long amount;
    private Long prevPoint;
    private Long currPoint;
    private LocalDateTime createdAt;

}
