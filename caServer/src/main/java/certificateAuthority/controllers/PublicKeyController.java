package certificateAuthority.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PublicKeyController {
	@GetMapping("/getPublicKey")
	    public PublicKey publicKey(@RequestParam(value="clientId") String clientId) {
		try {
			return new PublicKey(clientId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
