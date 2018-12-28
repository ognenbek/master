package hashtools;

public class PasswordSaltKeeper {
	private String password;
	private byte[] salt;
	public PasswordSaltKeeper(String password, byte[] salt) {
		// TODO Auto-generated constructor stub
		this.password = password;
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public byte[] getSalt() {
		return salt;
	}
	
}
