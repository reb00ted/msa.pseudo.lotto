package msa.pseudo.lotto.userserivce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import msa.pseudo.lotto.userserivce.model.User;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private Long id;
    private String userId;

    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
    }
}
