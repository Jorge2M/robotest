package com.mng.robotest.test80.mango.test.getdata.loyaltypoints;


import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.mng.robotest.test80.mango.test.appshop.Loyalty.UserTest;
import com.mng.robotest.test80.mango.test.getdata.loyaltypoints.data.ResultAddPoints;

public class ClientApiLoyaltyPointsDevTest {

	@Ignore
	@Test
	public void testGetIdConsumer() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		String idConsumer = client.getContactIdConsumer("test.performance10@mango.com");
		assertTrue(idConsumer.compareTo("6875476978994042958")==0);
	}
	
	@Ignore
	@Test
	public void addLoyaltyPoints() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		ResultAddPoints result = client.addLoyaltyPoints(UserTest.loy002, 100);
		assertTrue("success".compareTo(result.getStatus())==0);
	}
}
