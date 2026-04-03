package exceptions;

public class InvalidUserInputException extends CustomException{
	private static final long serialVersionUID = 1L;

	public InvalidUserInputException(String message, String metodo) {
		super(message, metodo);
	}

}
