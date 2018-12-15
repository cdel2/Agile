package optimodlyon.agile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "You cannot redo or undo.")
public class UndoRedoException extends RuntimeException{
		public UndoRedoException() {
			super();
		}
		public UndoRedoException(final String message) {
	        super(message);
	    }
}
