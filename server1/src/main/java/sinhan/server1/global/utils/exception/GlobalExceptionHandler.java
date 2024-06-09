package sinhan.server1.global.utils.exception;

import static sinhan.server1.global.utils.ApiUtils.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sinhan.server1.global.utils.ApiUtils.ApiResult;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler//(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult<String> handleValidationExceptions(AuthException errors) {
        String errorMessage = errors.getMessage();

        log.info("errorMessage.toString={}", errorMessage.toString());
        return error(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
