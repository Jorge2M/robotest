package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.domains.loyalty.steps.PageHomeDonateLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Loy002 extends TestBase {

	public static final User USER = new User("test.performance21@mango.com", "6877377061230042978", "ES");
	final boolean isPro = LoyaltyCommons.isPro(app, driver);

	private final PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps();
	private final PageHomeDonateLikesSteps pageHomeDonateLikesSteps = new PageHomeDonateLikesSteps();
	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	
	public Loy002() throws Exception {
		super();
		
		dataTest.userRegistered = true;
		if (isPro) {
			dataTest.userConnected = LoyaltyCommons.USER_PRO_WITH_LOY_POINTS.getEmail();
			dataTest.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword();
		} else {
			dataTest.userConnected = USER.getEmail();
			dataTest.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		}

	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(dataTest, false);
		
		int loyaltyPointsIni = LoyaltyCommons.clickMangoLikesYou(channel, app);
		if (loyaltyPointsIni < 3000 && !isPro) {
			loyaltyPointsIni = LoyaltyCommons.addLoyaltyPoints(USER, channel, app);
		}
		pageHomeLikesSteps.clickButtonDonarLikes();
		
		if (!isPro) {
			ButtonLikes donateButton = ButtonLikes.BUTTON_100_LIKES;
			pageHomeDonateLikesSteps.selectDonateButton(donateButton);
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			
			secMenusUserSteps.checkLoyaltyPoints(
					loyaltyPointsIni, donateButton.getNumLikes(), loyaltyPointsFin);
		}
	}
}
