package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.domains.loyalty.steps.PageHomeDonateLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Loy002 extends TestBase {

	public static final User USER = new User("test.performance21@mango.com", "6877377061230042978", "ES");
	
	private final PageMangoLikesYouSteps pageHomeLikesSteps = new PageMangoLikesYouSteps();
	private final PageHomeDonateLikesSteps pageHomeDonateLikesSteps = new PageHomeDonateLikesSteps();
	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	
	public Loy002() throws Exception {
		super();
		
		dataTest.setUserRegistered(true);
		if (isPRO()) {
			dataTest.setUserConnected(LoyaltyCommons.USER_PRO_WITH_LOY_POINTS.getEmail());
			dataTest.setPasswordUser(GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword());
		} else {
			dataTest.setUserConnected(USER.getEmail());
			dataTest.setPasswordUser(GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword());
		}

	}
	
	@Override
	public void execute() throws Exception {
		access();
		int loyaltyPointsIni = LoyaltyCommons.clickMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isPRO()) {
			loyaltyPointsIni = LoyaltyCommons.addLoyaltyPoints(USER);
		}
		pageHomeLikesSteps.clickButtonDonarLikes();
		
		if (!isPRO()) {
			ButtonLikes donateButton = ButtonLikes.BUTTON_100_LIKES;
			pageHomeDonateLikesSteps.selectDonateButton(donateButton);
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			
			secMenusUserSteps.checkLoyaltyPoints(
					loyaltyPointsIni, donateButton.getNumLikes(), loyaltyPointsFin);
		}
	}
}
