package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataMango;

public class Reg004 extends TestBase {
	
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	private final PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
	private final PageRegistroDirecStepsOutlet pageRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pageRegistroFinSteps = new PageRegistroFinStepsOutlet(); 
	
	private final String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	private Map<String, String> dataRegister = new HashMap<>();

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
		dataRegister = pageRegistroIniSteps.sendDataAccordingCountryToInputs(emailNonExistent, false);
		pageRegistroIniSteps.clickRegistrateButton(dataRegister);
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister);
		pageRegistroDirecSteps.clickFinalizarButton();
		pageRegistroFinSteps.clickIrDeShoppingButton();
		userMenusSteps.checkVisibilityLinkMangoLikesYou();
	}
	
}
