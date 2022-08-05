package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.mng.robotest.domains.registro.steps.PageRegistroDirecSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroFinSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroIniSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;


public class Reg003 extends TestBase {
	
	private final SecMenusUserSteps userMenusSteps = SecMenusUserSteps.getNew(channel, app, driver);
	private final PageRegistroIniSteps pageRegistroIniSteps = new PageRegistroIniSteps();
	private final PageRegistroDirecSteps pageRegistroDirecSteps = new PageRegistroDirecSteps();
	private final PageRegistroFinSteps pageRegistroFinSteps; 
	
	private final String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
	private Map<String, String> dataRegister = new HashMap<>();

	public Reg003(Pais pais, IdiomaPais idioma) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = false;
		
		pageRegistroFinSteps = new PageRegistroFinSteps(dataTest, driver); 
	}
	
	@Override
	public void execute() throws Exception {
		if (inputParamsSuite.getTypeAccess()==TypeAccess.Bat) {
			return; 
		}
		
		AccesoSteps.oneStep(dataTest, false, driver);
		userMenusSteps.selectRegistrate(dataTest);
		registerAndGoShoppingNoPubli();		
	}

	private void registerAndGoShoppingNoPubli() throws Exception {
		dataRegister = 
				pageRegistroIniSteps.sendDataAccordingCountryToInputs(dataTest.pais, emailNonExistent, false, channel);
		
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, app, dataRegister);
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister, dataTest.pais, channel);
		pageRegistroDirecSteps.clickFinalizarButton(dataTest);
		pageRegistroFinSteps.clickIrDeShoppingButton();
		userMenusSteps.checkVisibilityLinkMangoLikesYou();
	}
	
}
