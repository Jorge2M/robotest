package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDesktop.NumColumnas.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleria.From;
import com.mng.robotest.tests.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.menus.beans.FactoryMenus;
import com.mng.robotest.testslegacy.data.Color;

public class Gpo001 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu(CAMISAS_SHE);
		filterGaleryByColor(CAMISAS_SHE, Color.BLANCO);
		if (isDesktop()) {
			changeTwoToFourColumns();
		}
		checkScroll();		
	}

	private void changeTwoToFourColumns() {
		var pageGaleria = (PageGaleriaDesktop)PageGaleria.make(From.MENU, channel, app, dataTest.getPais());
		var listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
		listArticlesGaleria2Cols = galeriaSteps.selectListadoXColumnasDesktop(CUATRO, listArticlesGaleria2Cols);
		galeriaSteps.selectListadoXColumnasDesktop(DOS, listArticlesGaleria2Cols);
	}

	private void checkScroll() throws Exception {
		String nameMenuCamisas = FactoryMenus.get(CAMISAS_SHE).getMenu();
		var dataScroll = new DataForScrollStep();
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		dataScroll.setOrdenacionExpected(RECOMENDADOS);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
		var datosScrollFinalGaleria = galeriaSteps.scrollFromFirstPage(dataScroll);
		
		if (channel.isDevice()) {
			galeriaSteps.backTo1erArticleMobilStep();
		}
		int numArticulosPantalla = 
			galeriaSteps.seleccionaOrdenacionGaleria(PRECIO_DESC, nameMenuCamisas);
		
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(datosScrollFinalGaleria.getArticulosTotalesPagina());
		galeriaSteps.scrollFromFirstPage(dataScroll);	
		
		galeriaSteps.seleccionaOrdenacionGaleria(PRECIO_ASC, nameMenuCamisas, numArticulosPantalla);
		galeriaSteps.scrollFromFirstPage(dataScroll);
		galeriaSteps.selecColorFromArtGaleriaStep(1, 2);
		galeriaSteps.selecArticuloGaleriaStep(1);
	}
	
}
