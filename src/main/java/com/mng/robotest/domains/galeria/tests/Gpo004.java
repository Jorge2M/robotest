package com.mng.robotest.domains.galeria.tests;

import java.util.ArrayList;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.steps.DataForScrollStep;
import com.mng.robotest.domains.galeria.steps.LocationArticle;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.steps.shop.AllPagesSteps;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

public class Gpo004 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	private final DataForScrollStep dataScroll = new DataForScrollStep();
	
	public Gpo004() {
		dataScroll.setOrdenacionExpected(FilterOrdenacion.NO_ORDENADO);
		dataScroll.setValidateArticlesExpected(false);
		dataScroll.setValidaImgBroken(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		selectGaleryAndFilterByColor();
		scrollToThirdPage();
		if (!channel.isDevice()) {
			selectArticleInOtherLabel();
		}
		
		int articulosTotalesPagina = scrollToLastAndSelectArticle();
		goBackToGalery();
		scrollToLastAndCheck(articulosTotalesPagina);		
	}

	private void selectGaleryAndFilterByColor() {
		if (app==AppEcom.shop) {
			clickMenu(CAMISAS_SHE);
		} else {
			clickMenu(ABRIGOS_SHE);
		}

		var colorsToFilter = new ArrayList<Color>();
		colorsToFilter.add(Color.BLANCO);
		colorsToFilter.add(Color.NEGRO);
		colorsToFilter.add(Color.AZUL);			
		if (app==AppEcom.shop) {
			filterGaleryByColors(CAMISAS_SHE, colorsToFilter);
		} else {
			colorsToFilter.add(Color.MARRON);
			colorsToFilter.add(Color.BEIGE);
			filterGaleryByColors(ABRIGOS_SHE, colorsToFilter);
		}
	}

	private void scrollToThirdPage() throws Exception {
		dataScroll.setNumPageToScroll(3);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

	private void selectArticleInOtherLabel() {
		LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
		pageGaleriaSteps.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage);
	}

	private int scrollToLastAndSelectArticle() throws Exception {
		dataScroll.setNumPageToScroll(PageGaleria.MAX_PAGE_TO_SCROLL);
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.getPaginaFinal(), 1);
		pageGaleriaSteps.selectArticulo(loc1rsArticleLastPage);
		return datosScrollFinalGaleria.getArticulosTotalesPagina();
	}

	private void goBackToGalery() {
		new AllPagesSteps().backNagegador();
	}

	private void scrollToLastAndCheck(int articulosTotalesPagina) throws Exception {
		dataScroll.setValidateArticlesExpected(true);
		dataScroll.setNumArticlesExpected(articulosTotalesPagina);
		dataScroll.setValidaImgBroken(false);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

}
