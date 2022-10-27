package msa.pseudo.lotto.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Data @NoArgsConstructor
public class ApiResult<T> {

    private T data;

    private String error;

    ApiResult(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> succeed(T data) {
        return new ApiResult<>(data, null);
    }

    public static ApiResult failed(Throwable throwable) {
        return failed(throwable.getMessage());
    }

    public static ApiResult failed(String message) {
        return new ApiResult<>(null, message);
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("data", data)
                .append("error", error)
                .toString();
    }
}