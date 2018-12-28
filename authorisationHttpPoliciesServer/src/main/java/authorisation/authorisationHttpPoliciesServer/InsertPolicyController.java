package authorisation.authorisationHttpPoliciesServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jackson.JacskonToPolicyParser;

@RestController
public class InsertPolicyController {

/*
 * https://localhost:8084/postPolicy?policy=
 */
	@GetMapping("/postPolicy")
	public String insertPolicy(@RequestParam(value="policy") String policy)  {
		System.out.println(policy);
		JacskonToPolicyParser parser = new JacskonToPolicyParser();
		if(parser.parse(policy))
			return "Policy generated";
		else 
			return "Wrong input";
	}
}
