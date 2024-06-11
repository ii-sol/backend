package sinhan.server1.global.utils.exception;

import java.sql.SQLException;

public class SqlException extends SQLException {

    private final String message;

    public SqlException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
