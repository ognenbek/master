package authorisation.authorisationHttpPoliciesServer;

public class AuthorisatonReturnMessage {
	private  String message = "";
	private boolean success;
	
	
	public AuthorisatonReturnMessage(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
	public AuthorisatonReturnMessage() {
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message += message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}
