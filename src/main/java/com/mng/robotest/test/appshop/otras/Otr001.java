package com.mng.robotest.test.appshop.otras;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusDesktopSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Otr001 extends TestBase {

	private static final Pais ESPANA = PaisGetter.get(PaisShop.ESPANA);
	private static final Pais FRANCIA = PaisGetter.get(PaisShop.FRANCE);
	private static final IdiomaPais CASTELLANO = ESPANA.getListIdiomas().get(0);
	private static final IdiomaPais FRANCIA_FRANCES = FRANCIA.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		dataTest.pais = ESPANA;
		dataTest.idioma = CASTELLANO;
		access();
		
		SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps();
		secMenusDesktopSteps.checkURLRedirectParkasHeEspanya();
		
		dataTest.pais = FRANCIA;
		dataTest.idioma = FRANCIA_FRANCES;
		new AccesoSteps().goToInitialURL();
		access();	  
		new SecMenusDesktopSteps().checkURLRedirectFicha();		
	}
	
}