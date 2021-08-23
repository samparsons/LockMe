package Exceptions;

public class UserExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4028184379972038129L;
	String message;
	public UserExistsException(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	
	
}
