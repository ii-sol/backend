package sinhan.server1.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ApiUtils {

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult(true, data, null);
    }

    public static ApiResult error(String message) {
        return new ApiResult(false, null, message);
    }

    @Getter
    @AllArgsConstructor
    public static class ApiResult<T> {

        private boolean success;
        private T response;
        private String error;
    }

}