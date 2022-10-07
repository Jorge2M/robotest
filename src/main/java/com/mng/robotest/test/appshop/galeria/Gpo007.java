package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.galeria.ModalArticleNotAvailableSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.setUserRegistered(true);
		access();
		clickMenu(CAMISAS_SHE);
		
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = new ModalArticleNotAvailableSteps();
		modalArticleNotAvailableSteps.checkVisibleAvisame();		
	}

}
