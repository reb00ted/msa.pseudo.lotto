package msa.pseudo.lotto.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserInfo {
    private String userId;
    private Long point;
}
