package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Loy004 extends TestBase {

	static final User USER = LoyaltyCommons.USER_PRO_WITH_LOY_POINTS;
	
	private final PageMangoLikesYouSteps pageMangoLikesYouSteps = new PageMangoLikesYouSteps();
	
	public Loy004() throws Exception {
		super();

		dataTest.setUserConnected(LoyaltyCommons.USER_PRO_WITH_LOY_POINTS.getEmail());
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());
	}
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		LoyaltyCommons.clickMangoLikesYou();
		pageMangoLikesYouSteps.click(TabLink.HISTORIAL);
		pageMangoLikesYouSteps.clickAyuda();
	}

}
