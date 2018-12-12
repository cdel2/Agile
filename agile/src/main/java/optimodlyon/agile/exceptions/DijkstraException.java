package optimodlyon.agile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The user asked for something that is not computable for our algorithm (one way road for example)")
public class DijkstraException extends RuntimeException{
	public DijkstraException() {
		super();
	}
	public DijkstraException(final String message) {
        super(message);
    }
}
