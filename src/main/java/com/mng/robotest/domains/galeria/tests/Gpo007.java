package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.domains.transversal.TestBase;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.setUserRegistered(true);
		access();
		clickMenu(CAMISAS_SHE);
		checkAvisame();		
	}

	private void checkAvisame() throws Exception {
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		new ModalArticleNotAvailableSteps().checkVisibleAvisame();
	}

}
