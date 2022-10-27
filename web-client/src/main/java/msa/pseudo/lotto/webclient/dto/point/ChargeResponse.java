package msa.pseudo.lotto.webclient.dto.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChargeResponse {

    private String userId;
    private Long amount;
    private Long prevPoint;
    private Long currPoint;
    private LocalDateTime createdAt;
    private String message;

    public ChargeResponse(String message) {
        this.message = message;
    }
}