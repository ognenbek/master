package server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class getClientRoleControler {

	/*
	 * path: https://localhost:8086/getClientRole?clientId=clientid&action=action
	 */
	@GetMapping("/getClientRole")
	public String publicKey(@RequestParam(value="clientId") String clientId, @RequestParam(value="action") String action) {
		return ClientRole.getRole(clientId, action);
	}
}
