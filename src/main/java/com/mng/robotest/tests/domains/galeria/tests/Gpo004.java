package com.mng.robotest.tests.domains.galeria.tests;

import java.util.ArrayList;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis;
import com.mng.robotest.tests.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.testslegacy.data.Color;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.testslegacy.data.Color.*;

public class Gpo004 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	private final DataForScrollStep dataScroll = new DataForScrollStep();
	
	public Gpo004() {
		dataScroll.setOrdenacionExpected(RECOMENDADOS);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		selectGaleryAndFilterByColor();
//		scrollToSecondPage();
//		if (!channel.isDevice()) {
//			selectArticleInOtherLabel();
//		}
		
		scrollToLastAndSelectArticle();
//		int articulosTotalesPagina = scrollToLastAndSelectArticle();
//		goBackToGalery();
//		scrollToLastAndCheck(articulosTotalesPagina);		
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
			colorsToFilter.add(GRIS);
			filterGaleryByColors(ABRIGOS_SHE, colorsToFilter);
		}
	}

	private void scrollToSecondPage() throws Exception {
		dataScroll.setNumPageToScroll(2);
		galeriaSteps.scrollFromFirstPageNoGenesis(dataScroll);
	}

	private void selectArticleInOtherLabel() {
		int position = (app==shop) ? 50 : 20; 
		galeriaSteps.selectArticuloEnPestanyaAndBack(position);
	}

	private void scrollToLastAndSelectArticle() throws Exception {
		dataScroll.setNumPageToScroll(PageGaleriaNoGenesis.MAX_PAGE_TO_SCROLL);
		galeriaSteps.scrollFromFirstPageNoGenesis(dataScroll);
		int position = (app==shop) ? 50 : 30;
		galeriaSteps.selectArticulo(position);
		goBackToGalery(position);
		if (!channel.isDevice()) {
			selectArticleInOtherLabel();
		}
	}

	private void goBackToGalery(int posArticleToSelect) {
		back();
		galeriaSteps.checkArticleGaleriaLoaded(posArticleToSelect);
	}

	private void scrollToLastAndCheck(int articulosTotalesPagina) throws Exception {
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(articulosTotalesPagina);
		dataScroll.setValidaImgBroken(false);
		galeriaSteps.scrollFromFirstPageNoGenesis(dataScroll);
	}

}
