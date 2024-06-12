package sinhan.server1.global.utils.exception;

import static sinhan.server1.global.utils.ApiUtils.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sinhan.server1.global.utils.ApiUtils.ApiResult;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResult<String> handleValidationExceptions(AuthException error) {
        return error(error.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResult<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException error) {
        return error(error.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult<String> handleNoSuchElementException(NoSuchElementException error) {
        return error(error.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<String> handleSQLException(SQLException error) {
        return error(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        return error(error.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
