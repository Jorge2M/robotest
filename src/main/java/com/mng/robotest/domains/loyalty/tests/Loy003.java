package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.steps.PageHomeConseguirPorLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Loy003 extends TestBase {

	static final User USER = new User("test.performance22@mango.com", "6876577027622042923", "ES");
	
	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	private final PageMangoLikesYouSteps pageHomeLikesSteps = new PageMangoLikesYouSteps();
	private final PageHomeConseguirPorLikesSteps pageHomeConseguirPorLikesSteps = new PageHomeConseguirPorLikesSteps();
	
	public Loy003() throws Exception {
		super();

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
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		int loyaltyPointsIni = LoyaltyCommons.clickMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isPRO()) {
			loyaltyPointsIni = LoyaltyCommons.addLoyaltyPoints(USER);
		}
		pageHomeLikesSteps.clickConseguirPorLikesButton();
		
		if (!isPRO()) {
			pageHomeConseguirPorLikesSteps.selectConseguirButton();
			
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			secMenusUserSteps.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
		}
	}

}
