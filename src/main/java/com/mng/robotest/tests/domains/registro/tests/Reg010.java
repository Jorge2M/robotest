package com.mng.robotest.tests.domains.registro.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.steps.PageMLYUnirmeAlClubSteps;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;

public class Reg010 extends TestBase {

	private final DataNewRegister dataRegister = makeDataRegister();

	private DataNewRegister makeDataRegister() {
		var dataNewRegister = DataNewRegister.makeDefault(dataTest.getPais());
		dataNewRegister.setCheckPromotions(false);
		return dataNewRegister;
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickMangoLikesYou();
		unirmeAlClub();
		checkLogin();
	}
	
	private void accesoAndClickMangoLikesYou() throws Exception {
		access();
		new SecMenusUserSteps().clickMenuMangoLikesYou();
	}	
	
	private void unirmeAlClub() {
		var pgMLYUnirmeAlClubSteps = new PageMLYUnirmeAlClubSteps();
		pgMLYUnirmeAlClubSteps.selectUnirmeAlClub();
		pgMLYUnirmeAlClubSteps.registro(dataRegister);
	}
	
	private void checkLogin() {
		new SecMenusUserSteps().logoffLogin(dataRegister.getEmail(), dataRegister.getPassword());
	}
	
}
