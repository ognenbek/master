package server.subscrcibers;

public class AuthorisatonReturnMessage {
	private  String message = "";
	private boolean success;
	private String index;
	
	
	public AuthorisatonReturnMessage(String message, boolean success, String index) {
		this.message = message;
		this.success = success;
		this.index = index;
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
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	
	
}
