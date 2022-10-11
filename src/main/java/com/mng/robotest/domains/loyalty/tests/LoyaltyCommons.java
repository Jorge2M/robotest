package com.mng.robotest.domains.loyalty.tests;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;

public class LoyaltyCommons {
	
	public static final User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483560048388114", "ES");

	private LoyaltyCommons() {}
	
	public static int clickMangoLikesYou(Channel channel, AppEcom app) throws Exception {
		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
		return secMenusUserSteps.clickMenuMangoLikesYou();
	}
	
	public static int addLoyaltyPoints(User user, Channel channel, AppEcom app) 
			throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMangoLikesYou(channel, app);
	}

}
