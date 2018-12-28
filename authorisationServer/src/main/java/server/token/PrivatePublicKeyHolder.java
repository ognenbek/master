package server.token;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class PrivatePublicKeyHolder {
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	private static Certificate certificate;
	public PrivatePublicKeyHolder() {
		
	}
	public PrivatePublicKeyHolder(PrivateKey privateKey, PublicKey publicKey, Certificate certificate) {
		// TODO Auto-generated constructor stub
		this.publicKey = publicKey;
		this.privateKey =  privateKey;
		this.certificate = certificate;
	}
	public static PrivateKey getPrivateKey() {
		return privateKey;
	}
	public static void setPrivateKey(PrivateKey privateKey) {
		PrivatePublicKeyHolder.privateKey =  privateKey;
	}
	public static PublicKey getPublicKey() {
		return publicKey;
	}
	public static Certificate getCertificate() {
		return certificate;
	}
	public static void setCertificate(Certificate certificate) {
		PrivatePublicKeyHolder.certificate = certificate;
	}
	public static void setPublicKey(PublicKey publicKey) {
		PrivatePublicKeyHolder.publicKey =  publicKey;
	}
}
