package mqtt.resourceserver;

import static org.junit.Assert.assertEquals;

import java.security.Security;

import org.junit.Test;

import server.certificate.GenerateKeystore;

public class TokenValidationTest {

	@Test
	public void tokenValidation() {
		Security.addProvider(new org.bouncycastle.jce.provider
                .BouncyCastleProvider());
		System.setProperty("java.home", "c:\\Program Files\\Java\\jdk-9");
    	GenerateKeystore.create("resourcereceiver");
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJ4YWNtbCI6Ikg0c0lBQUFBQUFBQUFMVlR3VTdETUF5OTh4VlJPSzlkMlFFUnJaVmd1MVFhRWhvSWNVMVQwd1dseVlnZHV2NDlFV3lNU21Ob2g5MXN4Ky9GNzhtZUx1RTlBQkxidE1aaXpvTzN3a25VS0t4c0FRVXBzWkdxTldLU2pJVnlIZ1NxRmJSU2RQVW91K1pzNXRwS1c2am5vRFJxWjNQK0tnMENaMHVneVBYZ2pGWjlXUzgwMHU2cHVKamVFbmxkQlFKa00wblFPTi8vODdYY0lVWnFDeEJnUDdSM3RnVkxBMDcyRTVWMXpuOTFsVFZucFZVbTFGRGFKV0F3QjJkNmxpWUFtMHVTVC8wYWNyNGlXb3MwN2JvdTZTYUo4MDE2TlI1bjZjdjk0dkhMaWt0dDQwVGdlWEV6VFljc2tYWmZHU1I0c2d0WmRBRkQ5UWFLOWg1SXBRQnh0SzBmc2NFN0ErZlNqeEZvRzE3Y25WUCtIMHNnRmNXdE95TDh1K0hjMHBIaWFad21QOTFlWHZFSnVqN0k2NE1EQUFBPSIsImNsaWVudElkIjoiYW5kcm9pZCIsImlzcyI6ImF1dGhvcmlzYXRpb25zZXJ2ZXIiLCJleHAiOjE1NDM3MDQxNDExfQ.jvlNyF_Xki3OzhVgJt6ZmJ1TSS7YjoWBnM7f8ko8r8w";
		try {
			assertEquals(true, TokensHolder.checkTokenValidity(token));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void tokenValidationChangedCharacter() {
		Security.addProvider(new org.bouncycastle.jce.provider
                .BouncyCastleProvider());
		System.setProperty("java.home", "c:\\Program Files\\Java\\jdk-9");
    	GenerateKeystore.create("resourcereceiver");
		String token = "ayJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJ4YWNtbCI6Ikg0c0lBQUFBQUFBQUFMVlR3VTdETUF5OTh4VlJPSzlkMlFFUnJaVmd1MVFhRWhvSWNVMVQwd1dseVlnZHV2NDlFV3lNU21Ob2g5MXN4Ky9GNzhtZUx1RTlBQkxidE1aaXpvTzN3a25VS0t4c0FRVXBzWkdxTldLU2pJVnlIZ1NxRmJSU2RQVW91K1pzNXRwS1c2am5vRFJxWjNQK0tnMENaMHVneVBYZ2pGWjlXUzgwMHU2cHVKamVFbmxkQlFKa00wblFPTi8vODdYY0lVWnFDeEJnUDdSM3RnVkxBMDcyRTVWMXpuOTFsVFZucFZVbTFGRGFKV0F3QjJkNmxpWUFtMHVTVC8wYWNyNGlXb3MwN2JvdTZTYUo4MDE2TlI1bjZjdjk0dkhMaWt0dDQwVGdlWEV6VFljc2tYWmZHU1I0c2d0WmRBRkQ5UWFLOWg1SXBRQnh0SzBmc2NFN0ErZlNqeEZvRzE3Y25WUCtIMHNnRmNXdE95TDh1K0hjMHBIaWFad21QOTFlWHZFSnVqN0k2NE1EQUFBPSIsImNsaWVudElkIjoiYW5kcm9pZCIsImlzcyI6ImF1dGhvcmlzYXRpb25zZXJ2ZXIiLCJleHAiOjE1NDM4MDQ1MTR9.7AXPZBED2GGqmIPolU0NlFEoGOo8Cj2jutoEyELbFsm7LDF2bvui_3Wnxfv7uxYmb9xNF9-vqhgIP27V_iIzRA";
		try {
			assertEquals(false, TokensHolder.checkTokenValidity(token));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void expiredTokenValidation() {
		Security.addProvider(new org.bouncycastle.jce.provider
                .BouncyCastleProvider());
    	GenerateKeystore.create("resourcereceiver");
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJ4YWNtbCI6Ikg0c0lBQUFBQUFBQUFMVlR3VTdETUF5OTh4VlJPSzlkMlFFUnJaVmd1MVFhRWhvSWNVMVQwd1dseVlnZHV2NDlFV3lNU21Ob2g5MXN4Ky9GNzhtZUx1RTlBQkxidE1aaXpvTzN3a25VS0t4c0FRVXBzWkdxTldLU2pJVnlIZ1NxRmJSU2RQVW91K1pzNXRwS1c2am5vRFJxWjNQK0tnMENaMHVneVBYZ2pGWjlXUzgwMHU2cHVKamVFbmxkQlFKa00wblFPTi8vODdYY0lVWnFDeEJnUDdSM3RnVkxBMDcyRTVWMXpuOTFsVFZucFZVbTFGRGFKV0F3QjJkNmxpWUFtMHVTVC8wYWNyNGlXb3MwN2JvdTZTYUo4MDE2TlI1bjZjdjk0dkhMaWt0dDQwVGdlWEV6VFljc2tYWmZHU1I0c2d0WmRBRkQ5UWFLOWg1SXBRQnh0SzBmc2NFN0ErZlNqeEZvRzE3Y25WUCtIMHNnRmNXdE95TDh1K0hjMHBIaWFad21QOTFlWHZFSnVqN0k2NE1EQUFBPSIsImNsaWVudElkIjoiYW5kcm9pZCIsImlzcyI6ImF1dGhvcmlzYXRpb25zZXJ2ZXIiLCJleHAiOjE1NDM3MDQxNH0.bNOeYjuIUReM90CwnooVR6Q4jv4zS-ik3xXGZr6-Mak";
		try {
			assertEquals(false, TokensHolder.checkTokenValidity(token));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
