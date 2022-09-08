package com.mng.robotest.test.appshop.galeria;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria.From;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.steps.shop.galeria.DataForScrollStep;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class Gpo001 extends TestBase {

	@Override
	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
		String tipoPrendasGaleria = "camisa";
			
		access();
		clickMenu("camisas");
		
		List<Color> colorsToFilter = new ArrayList<>();
		colorsToFilter.add(Color.Blanco);
		//En outlet/movil tenemos el antiguo filtro que sólo permite seleccionar un color
		if (!(app==AppEcom.outlet && channel.isDevice())) {
			colorsToFilter.add(Color.Azul);
		}
		new SecFiltrosSteps().selectFiltroColoresStep(true, "Camisas", colorsToFilter);

		//Pruebas a nivel del cambio de galería de 2<->4 columnas
		PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
		if (channel==Channel.desktop && app!=AppEcom.outlet) {
			PageGaleriaDesktop pageGaleria = (PageGaleriaDesktop)PageGaleria.getNew(From.MENU, channel);
			ListDataArticleGalery listArticlesGaleria2Cols = pageGaleria.getListDataArticles();
			listArticlesGaleria2Cols = pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.CUATRO, listArticlesGaleria2Cols);
			pageGaleriaSteps.selectListadoXColumnasDesktop(NumColumnas.DOS, listArticlesGaleria2Cols);
		}
		
		DataForScrollStep dataScroll = new DataForScrollStep();
		dataScroll.numPageToScroll = PageGaleriaDesktop.MAX_PAGE_TO_SCROLL;
		dataScroll.ordenacionExpected = FilterOrdenacion.NOordenado;
		dataScroll.validateArticlesExpected = false;
		dataScroll.validaImgBroken = true;
		DataScroll datosScrollFinalGaleria = pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		
		if (channel.isDevice()) {
			pageGaleriaSteps.backTo1erArticleMobilStep();
		}
		int numArticulosPantalla = 
			pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioDesc, tipoPrendasGaleria, -1);
		
		dataScroll.validateArticlesExpected = true;
		dataScroll.numArticlesExpected = datosScrollFinalGaleria.articulosTotalesPagina;
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);	
		
		pageGaleriaSteps.seleccionaOrdenacionGaleria(FilterOrdenacion.PrecioAsc, tipoPrendasGaleria, numArticulosPantalla);
		pageGaleriaSteps.scrollFromFirstPage(dataScroll);
		pageGaleriaSteps.selecColorFromArtGaleriaStep(1, 2);
		pageGaleriaSteps.selecArticuloGaleriaStep(1);		
	}

}
