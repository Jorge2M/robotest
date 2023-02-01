package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;

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
		selectTallaArticle();
	}

	private void checkAvisame() {
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		var modal = new ModalArticleNotAvailableSteps();
		modal.checkVisibleAvisame();
		modal.clickAspaForClose();
	}
	
	private void selectTallaArticle() throws Exception {
		new GaleriaNavigationsSteps().selectTalla();
	}

}
