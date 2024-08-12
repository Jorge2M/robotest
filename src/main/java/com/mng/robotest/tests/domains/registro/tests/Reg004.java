package com.mng.robotest.tests.domains.registro.tests;

import java.util.Map;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOld;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.DataMango;

public class Reg004 extends TestBase {
	
	private final MenusUserSteps userMenusSteps = new MenusUserSteps();
	private final PageRegistroIniStepsOld pageRegistroIniSteps = new PageRegistroIniStepsOld();
	private final PageRegistroDirecStepsOutlet pageRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pageRegistroFinSteps = new PageRegistroFinStepsOutlet(); 
	
	private final String emailNonExistent = DataMango.getEmailNonExistentTimestamp();

	public Reg004(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataTest.setUserRegistered(false);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClickRegistrate();
		registerAndGoShoppingNoPubli();		
	}

	private void accessAndClickRegistrate() throws Exception {
		access();
		userMenusSteps.selectRegistrate();
	}

	private void registerAndGoShoppingNoPubli() {
		Map<String, String> dataRegister = 
				pageRegistroIniSteps.sendDataAccordingCountryToInputs(emailNonExistent, false);
		
		pageRegistroIniSteps.clickRegistrateButton(dataRegister);
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister);
		pageRegistroDirecSteps.clickFinalizarButton();
		pageRegistroFinSteps.clickIrDeShoppingButton();
		userMenusSteps.checkVisibilityLinkMangoLikesYou();
	}
	
}
