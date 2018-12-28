package certificateAuthority.keystore;

import java.io.IOException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ExportCertificateToBSKeystore {
	public static void export() {
		Security.addProvider(new BouncyCastleProvider());
		String slash = "\\";
		String certificatePath = CreateKeystore.KEYSTOREPATH + slash + "ServerCaCerAndroid.cer";
		String keyStorePath = CreateKeystore.KEYSTOREPATH + slash + "androidkeystore.bks";
		String bouncyCastleLocation = "\"c:\\Program Files (x86)\\Java\\jdk-9\\lib\\security\\bcprov-jdk15on-160.jar\"";
		
		String cmd = "keytool -importcert -v -trustcacerts -file " + certificatePath + " -alias ca -keystore  " +  keyStorePath
				+ " -providerpath " + bouncyCastleLocation + " -storetype BKS -provider org.bouncycastle.jce.provider.BouncyCastleProvider -storepass " + CreateKeystore.getPassword() + " -noprompt";
		System.out.println(cmd);
		try {
			System.out.println("before");
			Runtime.getRuntime().exec(cmd);
			System.out.println("after");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
