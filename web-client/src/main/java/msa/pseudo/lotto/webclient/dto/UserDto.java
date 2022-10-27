package msa.pseudo.lotto.webclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @Size(min = 4, max = 16)
    @NotNull
    String userId;

    @Size(min = 8, max = 24)
    @NotNull
    String password;


    public UserDto() {

    }

    @JsonIgnore
    public static UserDto EMPTY_OBJECT = new UserDto(null, null);


    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
