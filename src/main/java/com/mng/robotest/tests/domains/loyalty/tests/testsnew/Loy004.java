package com.mng.robotest.tests.domains.loyalty.tests.testsnew;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy004 extends TestBase {

	static final User USER = LoyTestCommons.USER_PRO_WITH_LOY_POINTS;
	
	private final MangoLikesYouSteps pageMangoLikesYouSteps;
	
	public Loy004() throws Exception {
		super();

		dataTest.setUserConnected(LoyTestCommons.USER_PRO_WITH_LOY_POINTS.getEmail());
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());
		
		pageMangoLikesYouSteps = new MangoLikesYouSteps();
	}
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		LoyTestCommons.clickMangoLikesYou();
		pageMangoLikesYouSteps.clickHistorial();
		pageMangoLikesYouSteps.clickAyuda();
	}

}
