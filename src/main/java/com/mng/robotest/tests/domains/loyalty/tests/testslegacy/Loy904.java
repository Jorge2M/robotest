package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy904 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	private final MangoLikesYouSteps pageMangoLikesYouSteps;
	
	public Loy904() throws Exception {
		super();
		dataTest.setPais(PaisGetter.from(USA));
		dataTest.setUser(USER_USA);
		pageMangoLikesYouSteps = new MangoLikesYouSteps();		
	}
	
	@Override
	public void execute() throws Exception {
		new LoyTestCommons().accessFlowUSA();
		LoyTestCommons.clickMangoLikesYou();
		pageMangoLikesYouSteps.clickHistorial();
		pageMangoLikesYouSteps.clickAyuda();
	}

}
