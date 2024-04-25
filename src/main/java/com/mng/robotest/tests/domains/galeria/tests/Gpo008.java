package com.mng.robotest.tests.domains.galeria.tests;

import java.util.ArrayList;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleria;
import com.mng.robotest.tests.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.testslegacy.data.Color.*;
import static com.mng.robotest.testslegacy.data.PaisShop.MONTENEGRO;

public class Gpo008 extends TestBase {

	private final Pais montenegro = MONTENEGRO.getPais();
	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	private final DataForScrollStep dataScroll = new DataForScrollStep();
	
	public Gpo008() {
		dataTest.setUserRegistered(true);
		dataTest.setPais(montenegro);
		
		dataScroll.setOrdenacionExpected(RECOMENDADOS);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
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
		int position = (app==shop) ? 50 : 20; 
		galeriaSteps.selectArticuloEnPestanyaAndBack(position);
	}

	private void scrollToLastAndSelectArticle() throws Exception {
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		galeriaSteps.scrollFromFirstPage(dataScroll);
		int position = (app==shop) ? 50 : 30;
		galeriaSteps.selectArticulo(position);
		goBackToGalery();
		if (!channel.isDevice()) {
			selectArticleInOtherLabel();
		}
	}

	private void goBackToGalery() {
		back();
		galeriaSteps.checkArticleGaleriaLoaded();
	}

}
