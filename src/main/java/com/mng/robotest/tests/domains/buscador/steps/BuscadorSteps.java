package com.mng.robotest.tests.domains.buscador.steps;

import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.pageobject.shop.navigations.ArticuloNavigations;

public class BuscadorSteps extends StepBase {

	private final PageGaleria pgGaleria = PageGaleria.make(channel);
	
	private static final String TAB_HTML = "&emsp;";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TAB_HTML + "id: <b>#{article.getArticleId()}</b><br>" + 
			TAB_HTML + "color: <b>#{article.getColor().getLabel()}</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(Article article) {
		searchArticuloCommon(article);
	}
	
	private static final String TAG_VALUES_FILTERS = "@TagValuesFilters";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TAB_HTML + "id: <b>#{article.getArticleId()}</b><br>" + 
			TAB_HTML + "color: <b>#{article.getColor().getLabel()}</b><br>" +
			TAB_HTML + "filters: <b>" + TAG_VALUES_FILTERS + "</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(Article article, List<FilterType> infoFilters) {
		String filterValues = infoFilters.stream().map(FilterType::name).collect(Collectors.joining(","));
		replaceStepDescription(TAG_VALUES_FILTERS, filterValues);
		searchArticuloCommon(article);
	}
	
	private void searchArticuloCommon(Article article) {
		new ArticuloNavigations().buscarArticulo(article);
		waitLoadPage();  
		new FichaSteps().checkIsFichaAccordingTypeProduct(article);
	}

	@Step (
		description="Introducir la categoría de producto <b>#{categoriaABuscar} </b>(existe categoría: #{categoriaExiste})</b>", 
		expected="El resultado de la búsqueda es el correcto :-)")
	public void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste) {
		SecCabecera.buscarTexto(categoriaABuscar, channel);
		waitLoadPage();	
		if (categoriaExiste) { 
			areProducts(categoriaABuscar, 3);
		} else {
			areProducts(3);
		}
		checksDefault();
		checksGeneric().imgsBroken();
	}

	@Validation (
		description="Aparece como mínimo un producto de tipo #{categoriaABuscar} " + SECONDS_WAIT)
	private boolean areProducts(String categoriaABuscar, int seconds) {
		String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
		return "".compareTo(pgGaleria.getNombreArticuloWithText(producSin1erCaracter, seconds))!=0;
	}
	
	@Validation (description="Aparece algún producto " + SECONDS_WAIT)
	private boolean areProducts(int seconds) {
		return pgGaleria.isVisibleArticleUntil(1, seconds);
	}
}
