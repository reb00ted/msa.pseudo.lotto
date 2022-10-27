package msa.pseudo.lotto.pointservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor @Builder
public class PaymentResponse {

    private Long txId;
    private String userId;
    private Long price;
    private Long prevPoint;
    private Long currPoint;
    private LocalDateTime createdAt;

}
