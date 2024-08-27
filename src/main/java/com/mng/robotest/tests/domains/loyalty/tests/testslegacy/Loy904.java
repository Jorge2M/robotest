package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy904 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	private final MangoLikesYouSteps pageMangoLikesYouSteps = new MangoLikesYouSteps();
	
	public Loy904() throws Exception {
		super();
		
		dataTest.setPais(PaisGetter.from(USA));
		dataTest.setUser(USER_USA);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		LoyTestCommons.clickMangoLikesYou();
		pageMangoLikesYouSteps.click(TabLink.HISTORIAL);
		pageMangoLikesYouSteps.clickAyuda();
	}

}
