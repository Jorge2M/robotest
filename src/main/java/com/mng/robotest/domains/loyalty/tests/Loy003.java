package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.steps.PageHomeConseguirPorLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Loy003 extends TestBase {

	static final User USER = new User("test.performance22@mango.com", "6876577027622042923", "ES");
	final boolean isPro = LoyaltyCommons.isPro();
	
	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	private final PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps();
	private final PageHomeConseguirPorLikesSteps pageHomeConseguirPorLikesSteps = new PageHomeConseguirPorLikesSteps();
	
	public Loy003() throws Exception {
		super();

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
		dataTest.userRegistered=true;
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(false);
		
		int loyaltyPointsIni = LoyaltyCommons.clickMangoLikesYou(channel, app);
		if (loyaltyPointsIni < 3000 && !isPro) {
			loyaltyPointsIni = LoyaltyCommons.addLoyaltyPoints(USER, channel, app);
		}
		pageHomeLikesSteps.clickConseguirPorLikesButton();
		
		if (!isPro) {
			pageHomeConseguirPorLikesSteps.selectConseguirButton();
			
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			secMenusUserSteps.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
		}
	}

}
