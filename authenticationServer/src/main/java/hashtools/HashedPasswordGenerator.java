package hashtools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import server.database.DatabaseStatements;

public class HashedPasswordGenerator {
	private static Random random = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	
	public static PasswordSaltKeeper getHashedPassword(String password) {
        byte[] salt = getNextSalt();
        String generatedPassword = hashPassword(password, salt);
        
        PasswordSaltKeeper passSalt = new PasswordSaltKeeper(generatedPassword, salt);
        
        return passSalt;
	}
	public static String reproduceAdminPassword(String username, String password) {
		byte[] salt = DatabaseStatements.getAdminSalt(username);
		return hashPassword(password, salt);
	}
	public static String reproduceClientPassword(String clientId, String password) {
		byte[] salt = DatabaseStatements.getClientSalt(clientId);
		if(salt!=null)
			return hashPassword(password, salt);
		return null;
	}
	private static String hashPassword(String password, byte[] salt) {
		String generatedPassword = null;
		try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(password.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		return generatedPassword;
	}
	private static byte[] getNextSalt() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}
}
