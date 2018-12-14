package optimodlyon.agile.exceptions;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Some files must be loaded first.")
public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnprocessableEntityException(final String message) {
        super(message);
    }

    public UnprocessableEntityException(final Throwable cause) {
        super(cause);
    }
}
