package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageHomeConseguirPorLikesSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy903 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	public Loy903() throws Exception {
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
		return LoyTestCommons.addLoyaltyPoints(USER_USA);
	}
	
	private void clickConseguirPorLikesButton() {
		new MangoLikesYouSteps().clickExchangeLikesForExperience();
	}	
	
	private int selectConseguirButton() {
		return (new PageHomeConseguirPorLikesSteps().selectConseguirButton());
	}
	
	private void checkLikes(int likesUsed) {
		clickMangoLikesYou();
		new MangoLikesYouSteps().clickHistorial();
		new PageHistorialLikesSteps().isLastMovementOf(likesUsed);
	}

}
