package com.mng.robotest.tests.domains.registro.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.steps.PageMLYUnirmeAlClubSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;

public class Reg010 extends TestBase {

	private final DataNewRegister dataRegister = makeDataRegister();
	private final PageMLYUnirmeAlClubSteps pgMLYUnirmeAlClubSteps = new PageMLYUnirmeAlClubSteps();

	private DataNewRegister makeDataRegister() {
		var dataNewRegister = DataNewRegister.makeDefault(dataTest.getPais());
		dataNewRegister.setCheckPromotions(false);
		return dataNewRegister;
	}
	
	@Override
	public void execute() throws Exception {
		access();
		clickMangoLikesYou();
		clickQueSonLosLikes();
		unirmeAlClub();
		checkLogin();
	}
	
	private void clickMangoLikesYou() {
		new MenusUserSteps().clickMenuMangoLikesYou();
	}
	
	private void clickQueSonLosLikes() {
		pgMLYUnirmeAlClubSteps.selectQueSonLosLikes();
	}
	
	private void unirmeAlClub() {
		pgMLYUnirmeAlClubSteps.selectUnirmeAlClub();
		pgMLYUnirmeAlClubSteps.registro(dataRegister);
	}
	
	private void checkLogin() {
		new MenusUserSteps().logoffLogin(dataRegister.getEmail(), dataRegister.getPassword());
	}
	
}
