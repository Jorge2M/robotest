package com.mng.robotest.test.utils.awssecrets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class GetterHardCodeSecretsTest {

	private static GetterHardCodeSecrets getterSecrets;
	
	@Before
	public void init() throws Exception {
		getterSecrets = new GetterHardCodeSecrets();
	}
	
	@Test
	public void testGetCredentialsShopPerformanceUser() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.SHOP_PERFORMANCE_USER);
		
		assertNull(secret.getNif());
		assertEquals("test.performance01@mango.com", secret.getUser());
		assertEquals("mango457", secret.getPassword());
		assertEquals("SHOP_PERFORMANCE_USER", secret.getType());
	}
	
	@Test
	public void testGetCredentialsShopStandardUser() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.SHOP_STANDARD_USER);
		
		assertNull(secret.getNif());
		assertEquals("ticket_digital_es@mango.com", secret.getUser());
		assertEquals("mango457", secret.getPassword());
		assertEquals("SHOP_STANDARD_USER", secret.getType());
	}
	
	@Test
	public void testGetCredentialsEmployeeData() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.EMPLOYEE_DATA);
		
		assertNull(secret.getPassword());
		assertEquals("EMPLOYEE_DATA", secret.getType());
		assertEquals("32070858A", secret.getNif());
	}
	
	@Test
	public void testIsAvailable() throws Exception {
		assertTrue(getterSecrets.isAvailable());
	}

}
