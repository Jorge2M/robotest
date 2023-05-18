package com.mng.robotest.domains.galeria.tests;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria.From;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

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
		PageGaleriaDesktop pageGaleria = (PageGaleriaDesktop)PageGaleria.getNew(From.MENU, channel);
		ListDataArticleGalery listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
		listArticlesGaleria2Cols = pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO, listArticlesGaleria2Cols);
		pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.DOS, listArticlesGaleria2Cols);
	}

	private void checkScroll() throws Exception {
		String nameMenuCamisas = FactoryMenus.get(CAMISAS_SHE).getMenu();
		var dataScroll = new DataForScrollStep();
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		dataScroll.setOrdenacionExpected(FilterOrdenacion.NO_ORDENADO);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
		var datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		
		if (channel.isDevice()) {
			pageGaleriaSteps.backTo1erArticleMobilStep();
		}
		int numArticulosPantalla = 
			pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PRECIO_DESC, nameMenuCamisas);
		
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(datosScrollFinalGaleria.getArticulosTotalesPagina());
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);	
		
		pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PRECIO_ASC, nameMenuCamisas, numArticulosPantalla);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		pageGaleriaSteps.selecColorFromArtGaleriaStep(1, 2);
		pageGaleriaSteps.selecArticuloGaleriaStep(1);
	}
	
}
