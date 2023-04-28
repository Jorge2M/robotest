package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.steps.navigations.shop.GaleriaSteps;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

public class Gpo007 extends TestBase {

	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu(CAMISAS_SHE);
		checkAvisame();		
		selectTallaArticle();
	}

	private void checkAvisame() {
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		var modal = new ModalArticleNotAvailableSteps();
		modal.checkVisibleAvisame();
		String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
		modal.inputMailAndClickRecibirAviso(emailNotExistent);
		modal.clickButtonEntendido();
	}
	
	private void selectTallaArticle() throws Exception {
		new GaleriaSteps().selectTalla();
	}

}
