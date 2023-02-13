package shopbaeFood.exception;

public class CheckOtpException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2299273268833688099L;
	private int code;
	private String message;

	public CheckOtpException() {
	}

	public CheckOtpException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
