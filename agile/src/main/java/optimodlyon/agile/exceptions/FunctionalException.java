package optimodlyon.agile.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "The request doesn't respect fonctionnal requisites (a round is too long for example)")
public class FunctionalException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FunctionalException() {
		super();
	}
	public FunctionalException(final String message) {
        super(message);
    }
}