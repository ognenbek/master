package certificateAuthority.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CertificateController {
	@GetMapping("/certificate")
    public Certificate publicKey(@RequestParam(value="certificate") String certificate, @RequestParam(value="alias") String alias) {
		try {
			System.out.println("CERT: " + certificate);
			System.out.println("alias: " + alias);
			return new Certificate(certificate.replaceAll(" ", "+"), alias);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
