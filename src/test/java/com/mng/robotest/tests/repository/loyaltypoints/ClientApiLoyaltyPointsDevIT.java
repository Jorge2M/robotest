package com.mng.robotest.tests.repository.loyaltypoints;


import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.mng.robotest.tests.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.tests.domains.loyalty.getdata.data.ResultAddPoints;
import com.mng.robotest.tests.domains.loyalty.tests.Loy002;

public class ClientApiLoyaltyPointsDevIT {

	@Ignore
	@Test
	public void testGetIdConsumer() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		String idConsumer = client.getContactIdConsumer("test.performance10@mango.com");
		assertEquals("6875476978994042958", idConsumer);
	}
	
	//@Ignore
	@Test
	public void addLoyaltyPoints() throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		ResultAddPoints result = client.addLoyaltyPoints(Loy002.USER, 100);
		assertEquals("success", result.getStatus());
	}
}
