package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;


public class LoyaltyCommons {
	
	public static final User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483560048388114", "ES");

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
