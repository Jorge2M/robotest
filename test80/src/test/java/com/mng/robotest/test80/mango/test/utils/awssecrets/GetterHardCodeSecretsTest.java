package com.mng.robotest.test80.mango.test.utils.awssecrets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets.SecretType;

public class GetterHardCodeSecretsTest {

	private static GetterHardCodeSecrets getterSecrets;
	
	@Before
	public void init() throws Exception {
		getterSecrets = new GetterHardCodeSecrets();
	}
	
	@Test
	public void testGetCredentialsShopPerformanceUser() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.SHOP_PERFORMANCE_USER);
		
		assertTrue(secret.getNif()==null);
		assertTrue(secret.getUser().compareTo("test.performance01@mango.com")==0);
		assertTrue(secret.getPassword().compareTo("11111")==0);
		assertTrue(secret.getType().compareTo("SHOP_PERFORMANCE_USER")==0);
	}
	
	@Test
	public void testGetCredentialsShopStandardUser() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.SHOP_STANDARD_USER);
		
		assertTrue(secret.getNif()==null);
		assertTrue(secret.getUser().compareTo("ticket_digital_es@mango.com")==0);
		assertTrue(secret.getPassword().compareTo("AAAAA")==0);
		assertTrue(secret.getType().compareTo("SHOP_STANDARD_USER")==0);
	}
	
	@Test
	public void testGetCredentialsEmployeeData() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.EMPLOYEE_DATA);
		
		assertTrue(secret.getPassword()==null);
		assertTrue(secret.getType().compareTo("EMPLOYEE_DATA")==0);
		assertTrue(secret.getNif().compareTo("55555")==0);
	}
	
	@Test
	public void testIsAvailable() throws Exception {
		assertTrue(getterSecrets.isAvailable());
	}

}
