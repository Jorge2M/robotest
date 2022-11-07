package com.mng.robotest.domains.buscador.steps;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria.From;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.navigations.ArticuloNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class SecBuscadorSteps extends StepBase {

	private final PageGaleria pageGaleria = PageGaleria.getNew(From.BUSCADOR, channel);
	
	private static final String TabHTML = "&emsp;";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TabHTML + "id: <b>#{article.getArticleId()}</b><br>" + 
			TabHTML + "color: <b>#{article.getColor().getLabel()}</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(Article article) {
		searchArticuloCommon(article);
	}
	
	private static final String TagValuesFilters = "@TagValuesFilters";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TabHTML + "id: <b>#{article.getArticleId()}</b><br>" + 
			TabHTML + "color: <b>#{article.getColor().getLabel()}</b><br>" +
			TabHTML + "filters: <b>" + TagValuesFilters + "</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(Article article, List<FilterType> infoFilters) {
		String filterValues = infoFilters.stream().map(FilterType::name).collect(Collectors.joining(","));
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagValuesFilters, filterValues);
		searchArticuloCommon(article);
	}
	
	private void searchArticuloCommon(Article article) {
		ArticuloNavigations.buscarArticulo(article, channel, app);
		PageObjTM.waitForPageLoaded(driver);  
		PageFichaSteps pageFichaSteps = new PageFichaSteps();
		pageFichaSteps.checkIsFichaAccordingTypeProduct(article);
	}

	@Step (
		description="Introducir la categoría de producto <b>#{categoriaABuscar} </b>(existe categoría: #{categoriaExiste})</b>", 
		expected="El resultado de la búsqueda es el correcto :-)")
	public void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste) {
		SecCabecera.buscarTexto(categoriaABuscar, channel, app);
		PageObjTM.waitForPageLoaded(driver);	
		if (categoriaExiste) { 
			areProducts(categoriaABuscar, 3);
		} else {
			areProducts(3);
		}
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.ImgsBroken)).checks();
	}

	@Validation (
		description="Aparece como mínimo un producto de tipo #{categoriaABuscar}  (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean areProducts(String categoriaABuscar, int seconds) {
		String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
		return "".compareTo(pageGaleria.getNombreArticuloWithText(producSin1erCaracter, seconds))!=0;
	}
	
	@Validation (
		description="Aparece algún producto (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean areProducts(int seconds) {
		return pageGaleria.isVisibleArticleUntil(1, seconds);
	}
}
