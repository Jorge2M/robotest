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
		assertEquals(secret.getUser(), "test.performance01@mango.com");
		assertEquals(secret.getPassword(), "mango457");
		assertEquals(secret.getType(), "SHOP_PERFORMANCE_USER");
	}
	
	@Test
	public void testGetCredentialsShopStandardUser() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.SHOP_STANDARD_USER);
		
		assertNull(secret.getNif());
		assertEquals(secret.getUser(), "ticket_digital_es@mango.com");
		assertEquals(secret.getPassword(), "mango457");
		assertEquals(secret.getType(), "SHOP_STANDARD_USER");
	}
	
	@Test
	public void testGetCredentialsEmployeeData() throws Exception {
		Secret secret = getterSecrets.getCredentials(SecretType.EMPLOYEE_DATA);
		
		assertNull(secret.getPassword());
		assertEquals(secret.getType(), "EMPLOYEE_DATA");
		assertEquals(secret.getNif(), "32070858A");
	}
	
	@Test
	public void testIsAvailable() throws Exception {
		assertTrue(getterSecrets.isAvailable());
	}

}
