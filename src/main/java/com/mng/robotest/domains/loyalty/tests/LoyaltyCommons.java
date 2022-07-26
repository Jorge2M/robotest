package com.mng.robotest.domains.loyalty.tests;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;

public class LoyaltyCommons {
	
	public final static User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483560048388114", "ES");

	private LoyaltyCommons() {}
	
	public static boolean isPro(AppEcom app, WebDriver driver) {
		return UtilsMangoTest.isEntornoPRO(app, driver);
	}
	
	public static int clickMangoLikesYou(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		SecMenusUserSteps secMenusUserStpV = new SecMenusUserSteps(channel, app, driver);
		return secMenusUserStpV.clickMenuMangoLikesYou();
	}
	
	public static int addLoyaltyPoints(User user, Channel channel, AppEcom app, WebDriver driver) 
			throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMangoLikesYou(channel, app, driver);
	}

}
