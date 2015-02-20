public class MusixMatchException extends RuntimeException {
	private static final long serialVersionUID = -5966671078560103472L;
	private String message;
	
	public MusixMatchException (String message) {
		this.message = message;
	}
	
	public String getMessage () {
		return message;
	}

}
