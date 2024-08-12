package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.menus.entity.FactoryMenus.MenuItem.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		accessAndLogin(true);
		clickMenu(CAMISAS_SHE);
		checkAvisame();
		selectTallaArticle();
	}

	private void checkAvisame() {
		new GaleriaSteps().selectTallaNoDisponibleArticulo();
		var modal = new ModalArticleNotAvailableSteps();
		modal.checkVisibleAvisame();
		modal.clickRecibirAviso();
	}
	
	private void selectTallaArticle() throws Exception {
		new GaleriaSteps().selectTallaAvailable();
	}

}
