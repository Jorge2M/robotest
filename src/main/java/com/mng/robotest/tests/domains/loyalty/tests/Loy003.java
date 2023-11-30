package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageHomeConseguirPorLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy003 extends TestBase {

	static final User USER = new User("test.performance22@mango.com", "6876577027622042923", "ES");
	
	public Loy003() throws Exception {
		super();

		if (isPRO()) {
			dataTest.setUserConnected(LoyTestCommons.USER_PRO_WITH_LOY_POINTS.getEmail());
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
		accessAndLogin();
		int loyaltyPointsIni = clickMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isPRO()) {
			chargePoints();
		}
		clickConseguirPorLikesButton();
		
		if (!isPRO()) {
			int likesUsed = selectConseguirButton();
			checkLikes(likesUsed);
		}
	}
	
	private int clickMangoLikesYou() {
		return LoyTestCommons.clickMangoLikesYou();
	}
	
	private int chargePoints() {
		return LoyTestCommons.addLoyaltyPoints(USER);
	}
	
	private void clickConseguirPorLikesButton() {
		new PageMangoLikesYouSteps().clickConseguirPorLikesButton();
	}	
	
	private int selectConseguirButton() {
		return (new PageHomeConseguirPorLikesSteps().selectConseguirButton());
	}
	
	private void checkLikes(int likesUsed) {
		clickMangoLikesYou();
		new PageMangoLikesYouSteps().click(TabLink.HISTORIAL);
		new PageHistorialLikesSteps().isLastMovementOf(likesUsed);
	}

}
