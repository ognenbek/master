package mqtt.brokerinitialisation;

import java.io.IOException;

import server.certificate.GenerateKeystore;

public class CopyCACertificateToMosquitto {
	public static void copy(String mosquittoCertificatesPath, String certificateLocation) {
		String certificatePath =  GenerateKeystore.KEYSTOREPATH + "\\" + certificateLocation;
		internalcopy(mosquittoCertificatesPath, certificatePath);
	}
	public static void copy(String mosquittoCertificatesPath) {
		String certificatePath = GenerateKeystore.KEYSTOREPATH + "\\ServerCaCer.crt";
		internalcopy(mosquittoCertificatesPath, certificatePath);
	}
	private static void internalcopy(String mosquittoCertificatesPath, String certificatePath) {
		String cmd = "cmd /C copy " + certificatePath + " " + mosquittoCertificatesPath;
		System.out.println(cmd);
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			System.out.println("finished");
			while(process.waitFor()!=0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
