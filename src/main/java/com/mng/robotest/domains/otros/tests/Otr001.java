package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Otr001 extends TestBase {

	private static final Pais FRANCIA = PaisGetter.from(PaisShop.FRANCE);
	private static final IdiomaPais FRANCIA_FRANCES = FRANCIA.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		access();
		new MenuSteps().checkURLRedirectParkasHeEspanya();
		
		dataTest.setPais(FRANCIA);
		dataTest.setIdioma(FRANCIA_FRANCES);
		new AccesoSteps().goToInitialURL();
		access();	  
		new MenuSteps().checkURLRedirectFicha();		
	}
	
}
