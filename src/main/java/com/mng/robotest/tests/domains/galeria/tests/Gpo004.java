package com.mng.robotest.tests.domains.galeria.tests;

import java.util.ArrayList;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.testslegacy.data.Color;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.testslegacy.data.Color.*;

public class Gpo004 extends TestBase {

	private final PageGaleriaSteps pgGaleriaSteps = new PageGaleriaSteps();
	private final DataForScrollStep dataScroll = new DataForScrollStep();
	
	public Gpo004() {
		dataScroll.setOrdenacionExpected(RECOMENDADOS);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
	}
	
	@Override
	public void execute() throws Exception {
		quickAccess();
		selectGaleryAndFilterByColor();
		scrollToSecondPage();
		if (!channel.isDevice()) {
			selectArticleInOtherLabel();
		}
		
		int articulosTotalesPagina = scrollToLastAndSelectArticle();
		goBackToGalery();
		scrollToLastAndCheck(articulosTotalesPagina);		
	}

	private void selectGaleryAndFilterByColor() {
		if (app==shop) {
			clickMenu(CAMISAS_SHE);
		} else {
			clickMenu(ABRIGOS_SHE);
		}

		var colorsToFilter = new ArrayList<Color>();
		colorsToFilter.add(BLANCO);
		colorsToFilter.add(NEGRO);
		colorsToFilter.add(AZUL);			
		if (app==shop) {
			filterGaleryByColors(CAMISAS_SHE, colorsToFilter);
		} else {
			filterGaleryByColors(ABRIGOS_SHE, colorsToFilter);
		}
	}

	private void scrollToSecondPage() throws Exception {
		dataScroll.setNumPageToScroll(2);
		pgGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

	private void selectArticleInOtherLabel() {
		pgGaleriaSteps.selectArticuloEnPestanyaAndBack(40);
	}

	private int scrollToLastAndSelectArticle() throws Exception {
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		var datosScrollFinalGaleria = pgGaleriaSteps.scrollFromFirstPage(dataScroll);
		pgGaleriaSteps.selectArticulo(45);
		return datosScrollFinalGaleria.getArticulosTotalesPagina();
	}

	private void goBackToGalery() {
		back();
	}

	private void scrollToLastAndCheck(int articulosTotalesPagina) throws Exception {
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(articulosTotalesPagina);
		dataScroll.setValidaImgBroken(false);
		pgGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

}
