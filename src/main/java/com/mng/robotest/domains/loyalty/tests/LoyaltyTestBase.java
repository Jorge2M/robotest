package com.mng.robotest.domains.loyalty.tests;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;


public abstract class LoyaltyTestBase {

	public abstract void execute() throws Exception;
	
	static final String USER_PRO_WITH_LOY_POINTS = "ticket_digital_es@mango.com";
	
	final WebDriver driver;
	final boolean isPro;
	final Channel channel;
	final AppEcom app;
	
	public LoyaltyTestBase() throws Exception {
		this.driver = TestMaker.getDriverTestCase();
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = (Channel)inputParamsSuite.getChannel();
		this.isPro = isPro(app, driver);
	}
	
	private boolean isPro(AppEcom app, WebDriver driver) {
		return UtilsMangoTest.isEntornoPRO(app, driver);
	}
	
	int clickMangoLikesYou() throws Exception {
		SecMenusUserSteps secMenusUserStpV = new SecMenusUserSteps(channel, app, driver);
		return secMenusUserStpV.clickMenuMangoLikesYou();
	}
	
	int addLoyaltyPoints(User user) throws Exception {
		ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMangoLikesYou();
	}

}
