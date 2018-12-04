package optimodlyon.agile.exceptions;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Certains fichiers doivent d'abord être chargés.")
public class UnprocessableEntity extends RuntimeException {
    public UnprocessableEntity() {
        super();
    }

    public UnprocessableEntity(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnprocessableEntity(final String message) {
        super(message);
    }

    public UnprocessableEntity(final Throwable cause) {
        super(cause);
    }
}
