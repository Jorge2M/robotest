package com.mng.robotest.test.getdata.loyaltypoints;


import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.domains.loyalty.getdata.data.ResultAddPoints;
import com.mng.robotest.domains.loyalty.tests.Loy002;


public class ClientApiLoyaltyPointsDevIT {

	@Ignore
	@Test
	public void testGetIdConsumer() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		String idConsumer = client.getContactIdConsumer("test.performance10@mango.com");
		assertTrue(idConsumer.compareTo("6875476978994042958")==0);
	}
	
	//@Ignore
	@Test
	public void addLoyaltyPoints() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		ResultAddPoints result = client.addLoyaltyPoints(Loy002.USER, 100);
		assertTrue("success".compareTo(result.getStatus())==0);
	}
}
