package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;


public class Reg004 extends TestBase {
	
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	private final PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
	private final PageRegistroDirecStepsOutlet pageRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pageRegistroFinSteps; 
	
	private final String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	private Map<String, String> dataRegister = new HashMap<>();

	public Reg004(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = false;
		
		pageRegistroFinSteps = new PageRegistroFinStepsOutlet(dataTest.idioma);
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClickRegistrate();
		registerAndGoShoppingNoPubli();		
	}

	private void accessAndClickRegistrate() throws Exception {
		new AccesoSteps().oneStep(dataTest, false);
		userMenusSteps.selectRegistrate(dataTest);
	}

	private void registerAndGoShoppingNoPubli() throws Exception {
		dataRegister = 
				pageRegistroIniSteps.sendDataAccordingCountryToInputs(dataTest.pais, emailNonExistent, false);
		
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, dataRegister);
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister, dataTest.pais);
		pageRegistroDirecSteps.clickFinalizarButton(dataTest.idioma);
		pageRegistroFinSteps.clickIrDeShoppingButton();
		userMenusSteps.checkVisibilityLinkMangoLikesYou();
	}
	
}
