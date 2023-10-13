package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		accessAndLogin(true);
		clickMenu(CAMISAS_SHE);
		checkAvisame();
		selectTallaArticle();
	}

	private void checkAvisame() {
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		var modal = new ModalArticleNotAvailableSteps();
		modal.checkVisibleAvisame();
		modal.clickRecibirAviso();
		if (app!=AppEcom.outlet) {
			modal.clickButtonEntendido();
		}
	}
	
	private void selectTallaArticle() throws Exception {
		new PageGaleriaSteps().selectTallaAvailable();
	}

}
