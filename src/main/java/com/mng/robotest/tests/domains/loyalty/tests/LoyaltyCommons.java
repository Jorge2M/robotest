package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;


public class LoyaltyCommons {
	
	public static final User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483570048367458", "ES");

	private LoyaltyCommons() {}
	
	public static int clickMangoLikesYou() {
		return new SecMenusUserSteps().clickMenuMangoLikesYou();
	}
	
	public static int addLoyaltyPoints(User user) {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMangoLikesYou();
	}

}
