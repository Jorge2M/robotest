package com.mng.robotest.test.appshop.galeria;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.steps.shop.galeria.DataForScrollStep;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Gpo004 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.userRegistered = false;
		new AccesoSteps().oneStep(false);
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCamisas);

		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(app!=AppEcom.outlet && channel.isDevice())) {
			colorsToFilter.add(Color.Negro);
			colorsToFilter.add(Color.Azul);
		}
		new SecFiltrosSteps().selectFiltroColoresStep(false, "Camisas", colorsToFilter);
			
		//Scrollar hasta la 3a página
		PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = 3;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		LocationArticle loc1rsArticle1rstPage = LocationArticle.getInstanceInPage(2, 1);
		pageGaleriaSteps.selectArticuloEnPestanyaAndBack(loc1rsArticle1rstPage, dataTest.pais);
		
		//Scrollar hasta el final de la Galería
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
			
		LocationArticle loc1rsArticleLastPage = LocationArticle.getInstanceInPage(datosScrollFinalGaleria.paginaFinal, 1);
		pageGaleriaSteps.selectArticulo(loc1rsArticleLastPage);
		AllPagesSteps.backNagegador(driver);

		//Scrollar hasta el final de la Galería (comprobaremos que el número de artículos es el mismo que en el anterior scroll hasta el final)
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		dataScroll.validaImgBroken = false;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);		
	}

}
