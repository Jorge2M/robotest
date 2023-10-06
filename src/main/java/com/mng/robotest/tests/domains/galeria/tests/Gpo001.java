package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.galeria.pageobjects.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.NumColumnas.*;
import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria.From;
import com.mng.robotest.tests.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus;
import com.mng.robotest.testslegacy.data.Color;

public class Gpo001 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu(CAMISAS_SHE);
		filterGaleryByColor(CAMISAS_SHE, Color.BLANCO);
		if (channel==Channel.desktop) {
			changeTwoToFourColumns();
		}
		checkScroll();		
	}

	private void changeTwoToFourColumns() {
		var pageGaleria = (PageGaleriaDesktop)PageGaleria.getNew(From.MENU, channel, app, dataTest.getPais());
		var listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
		listArticlesGaleria2Cols = pageGaleriaSteps.selectListadoXColumnasDesktop(CUATRO, listArticlesGaleria2Cols);
		pageGaleriaSteps.selectListadoXColumnasDesktop(DOS, listArticlesGaleria2Cols);
	}

	private void checkScroll() throws Exception {
		String nameMenuCamisas = FactoryMenus.get(CAMISAS_SHE).getMenu();
		var dataScroll = new DataForScrollStep();
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		dataScroll.setOrdenacionExpected(NO_ORDENADO);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
		var datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		
		if (channel.isDevice()) {
			pageGaleriaSteps.backTo1erArticleMobilStep();
		}
		int numArticulosPantalla = 
			pageGaleriaSteps.seleccionaOrdenacionGaleria(PRECIO_DESC, nameMenuCamisas);
		
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(datosScrollFinalGaleria.getArticulosTotalesPagina());
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);	
		
		pageGaleriaSteps.seleccionaOrdenacionGaleria(PRECIO_ASC, nameMenuCamisas, numArticulosPantalla);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		pageGaleriaSteps.selecColorFromArtGaleriaStep(1, 2);
		pageGaleriaSteps.selecArticuloGaleriaStep(1);
	}
	
}
