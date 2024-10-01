package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageHomeDonateLikesSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

public class Loy902 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	public Loy902() throws Exception {
		super();
		
		dataTest.setPais(PaisGetter.from(USA));
		dataTest.setUser(USER_USA);
	}
	
	@Override
	public void execute() throws Exception {
		new LoyTestCommons().accessFlowUSA();
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
		return LoyTestCommons.addLoyaltyPoints(USER_USA);
	}
	
	private void clickFirstDonateLikesButton() {
		new MangoLikesYouSteps().clickButtonDonarLikes();
	}
	
	private int clickDonateLikesButton() {
		return new PageHomeDonateLikesSteps().selectDonateButton(50, 100);
	}
	
	private void checkLikes(int likesDonated) {
		clickMangoLikesYou();
		new MangoLikesYouSteps().clickHistorial();
		new PageHistorialLikesSteps().isMovementOf(likesDonated, 3);
	}
	
}
