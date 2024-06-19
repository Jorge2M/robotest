package com.mng.robotest.tests.domains.galeria.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.testslegacy.data.PaisShop.LIECHTENSTEIN;

public class Gpo008 extends TestBase {

	private final Pais liechtenstein = LIECHTENSTEIN.getPais();
	private final GaleriaSteps galeriaSteps;
	
	public Gpo008() {
		dataTest.setPais(liechtenstein);
		galeriaSteps = new GaleriaSteps();
	}
	
	@Override
	public void execute() throws Exception {
		access();
		selectGalery();
		scrollToLastAndSelectArticle();
	}

	private void selectGalery() {
		if (app==shop) {
			clickMenu(CAMISAS_SHE);
		} else {
			clickMenu(ABRIGOS_SHE);
		}
	}

	private void selectArticleInOtherLabel() {
		galeriaSteps.selectArticuloEnPestanyaAndBack(20);
	}

	private void scrollToLastAndSelectArticle() throws Exception {
		int posArticleToSelect = 30;
		galeriaSteps.scrollToLast();
		galeriaSteps.selectArticulo(posArticleToSelect);
		goBackToGalery(posArticleToSelect);
		if (!channel.isDevice() &&
			!isPRO()) { //problem in Robotest that not sends specific user-agent in other label
			selectArticleInOtherLabel();
		}
	}

	private void goBackToGalery(int posArticle) {
		back();
		galeriaSteps.checkArticleGaleriaVisibleInScreen(posArticle);
	}

}
