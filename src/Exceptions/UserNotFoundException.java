package Exceptions;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4028184379972038129L;
	String message;
	public UserNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	
	
}
