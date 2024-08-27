package com.mng.robotest.tests.domains.loyalty.tests.testsnew;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageHomeDonateLikesSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;

import static com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes.*;
import static com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType.*;

public class Loy002 extends TestBase {

	public static final User USER = new User("test.performance21@mango.com", "6877377061230042978", "ES");
	
	public Loy002() throws Exception {
		super();
		
		if (isPRO()) {
			dataTest.setUserConnected(LoyTestCommons.USER_PRO_WITH_LOY_POINTS.getEmail());
			dataTest.setPasswordUser(GetterSecrets.factory()
					.getCredentials(SHOP_STANDARD_USER)
					.getPassword());
		} else {
			dataTest.setUserConnected(USER.getEmail());
			dataTest.setPasswordUser(GetterSecrets.factory()
					.getCredentials(SHOP_PERFORMANCE_USER)
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
		clickFirstDonateLikesButton();
		
		if (!isPRO()) {
			int likesDonated = clickDonateLikesButton(); 
			checkLikes(likesDonated);
		}
	}
	
	private int clickMangoLikesYou() {
		return LoyTestCommons.clickMangoLikesYou();
	}
	
	private int chargePoints() {
		return LoyTestCommons.addLoyaltyPoints(USER);
	}
	
	private void clickFirstDonateLikesButton() {
		new MangoLikesYouSteps().clickButtonDonarLikes();
	}
	
	private int clickDonateLikesButton() {
		return new PageHomeDonateLikesSteps().selectDonateButton(
				BUTTON_50_LIKES, 
				BUTTON_100_LIKES);
	}
	
	private void checkLikes(int likesDonated) {
		clickMangoLikesYou();
		new MangoLikesYouSteps().click(TabLink.HISTORIAL);
		new PageHistorialLikesSteps().isLastMovementOf(likesDonated);
	}
	
}
