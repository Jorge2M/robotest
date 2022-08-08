package com.mng.robotest.domains.buscador.steps;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria.From;
import com.mng.robotest.test.pageobject.shop.navigations.ArticuloNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class SecBuscadorSteps extends StepBase {

	private final PageGaleria pageGaleria;
	private final WebDriver driver;
	
	public SecBuscadorSteps() {
		this.pageGaleria = PageGaleria.getNew(From.BUSCADOR, channel, app);
		this.driver = pageGaleria.driver;
	}
	
	private static final String TabHTML = "&emsp;";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TabHTML + "id: <b>#{product.getGarmentId()}</b><br>" + 
			TabHTML + "color: <b>#{product.getArticleWithMoreStock().getColorLabel()}</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(GarmentCatalog product, Pais pais) throws Exception {
		searchArticuloCommon(product, pais);
	}
	
	private static final String TagValuesFilters = "@TagValuesFilters";
	@Step (
		description=
			"Buscar el artículo<br>"+ 
			TabHTML + "id: <b>#{product.getGarmentId()}</b><br>" + 
			TabHTML + "color: <b>#{product.getArticleWithMoreStock().getColorLabel()}</b><br>" +
			TabHTML + "filters: <b>" + TagValuesFilters + "</b>", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(GarmentCatalog product, Pais pais, List<FilterType> infoFilters) throws Exception {
		String filterValues = infoFilters.stream().map(FilterType::name).collect(Collectors.joining(","));
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagValuesFilters, filterValues);
		searchArticuloCommon(product, pais);
	}
	
	private void searchArticuloCommon(GarmentCatalog product, Pais pais) throws Exception {
		ArticuloNavigations.buscarArticulo(product.getArticleWithMoreStock(), channel, app, driver);
		SeleniumUtils.waitForPageLoaded(driver);  
		PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(pais);
		pageFichaSteps.validateIsFichaAccordingTypeProduct(product);
	}

	@Step (
		description="Introducir la categoría de producto <b>#{categoriaABuscar} </b>(existe categoría: #{categoriaExiste})</b>", 
		expected="El resultado de la búsqueda es el correcto :-)")
	public void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste) throws Exception {
		SecCabecera.buscarTexto(categoriaABuscar, channel, app, driver);
		SeleniumUtils.waitForPageLoaded(driver);	
		if (categoriaExiste) { 
			areProducts(categoriaABuscar, 3);
		} else {
			areProducts(3);
		}

		GenericChecks.from(Arrays.asList( 
				GenericCheck.CookiesAllowed,
				GenericCheck.JSerrors, //
				GenericCheck.Analitica,//
				GenericCheck.TextsTraduced,
				GenericCheck.ImgsBroken)).checks(driver);
	}

	@Validation (
		description="Aparece como mínimo un producto de tipo #{categoriaABuscar}  (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean areProducts(String categoriaABuscar, int maxSeconds) {
		String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
		return "".compareTo(pageGaleria.getNombreArticuloWithText(producSin1erCaracter, maxSeconds))!=0;
	}
	
	@Validation (
		description="Aparece algún producto (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean areProducts(int maxSeconds) {
		return pageGaleria.isVisibleArticleUntil(1, maxSeconds);
	}
}
