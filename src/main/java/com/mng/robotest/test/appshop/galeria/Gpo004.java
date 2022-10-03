package com.mng.robotest.test.appshop.galeria;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.Menu;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.steps.shop.galeria.DataForScrollStep;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class Gpo004 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	private final DataForScrollStep dataScroll = new DataForScrollStep();
	
	public Gpo004() {
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
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

	private void selectGaleryAndFilterByColor() throws Exception {
		if (app==AppEcom.shop) {
			new MenuSteps().click(new Menu.Builder("camisas").build());
			//clickMenu("camisas");
		} else {
			clickMenu("abrigos");
		}

		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		if (app==AppEcom.shop) {
			colorsToFilter.add(Color.Negro);
			colorsToFilter.add(Color.Azul);
			new SecFiltrosSteps().selectFiltroColoresStep(false, "Camisas", colorsToFilter);
		} else {
			new SecFiltrosSteps().selectFiltroColoresStep(false, "Abrigos", colorsToFilter);
		}
	}

	private void scrollToThirdPage() throws Exception {
		dataScroll.numPageToScroll = 3;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

	private void selectArticleInOtherLabel() throws Exception {
		LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
		pageGaleriaSteps.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage);
	}

	private int scrollToLastAndSelectArticle() throws Exception {
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.paginaFinal, 1);
		pageGaleriaSteps.selectArticulo(loc1rsArticleLastPage);
		return datosScrollFinalGaleria.articulosTotalesPagina;
	}

	private void goBackToGalery() throws Exception {
		new AllPagesSteps().backNagegador();
	}

	private void scrollToLastAndCheck(int articulosTotalesPagina) throws Exception {
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = articulosTotalesPagina;
		dataScroll.validaImgBroken = false;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
	}

}
