package com.mng.robotest.test80.mango.test.stpv.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria.From;
import com.mng.robotest.test80.mango.test.pageobject.shop.navigations.ArticuloNavigations;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;

public class SecBuscadorStpV {

	private final PageGaleria pageGaleria;
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	
	public SecBuscadorStpV(AppEcom app, Channel channel, WebDriver driver) {
		this.pageGaleria = (PageGaleria)PageGaleria.getNew(From.buscador, channel, app, driver);
		this.driver = driver;
		this.channel = channel;
		this.app = app;
	}
	
	@Step (
		description="Buscar el artículo con id #{product.getGarmentId()} y color:#{product.getArticleWithMoreStock().getColorLabel()})", 
		expected="Aparece la ficha del producto")
	public void searchArticulo(Garment product, Pais pais) throws Exception {
		ArticuloNavigations.buscarArticulo(product.getArticleWithMoreStock(), channel, app, driver);
		SeleniumUtils.waitForPageLoaded(driver);  
		PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel, pais);
		pageFichaStpV.validateIsFichaAccordingTypeProduct(product);
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
//			if (app!=AppEcom.outlet) {
			areProducts(3);
//			} else {
//				appearsSearchErrorPage(categoriaABuscar, driver);
//			}
		}

		//Validaciones estándar. 
		StdValidationFlags flagsVal = StdValidationFlags.newOne();
		flagsVal.validaSEO = false;
		flagsVal.validaJS = true;
		flagsVal.validaImgBroken = true;
		AllPagesStpV.validacionesEstandar(flagsVal, driver);
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
	
//	@Validation (
//		description="Aparece la página de error en la búsqueda con el encabezado <b>#{categoriaABuscar}</b>",
//		level=State.Warn)
//	private static boolean appearsSearchErrorPage(String categoriaABuscar, WebDriver driver) {
//		return (PageErrorBusqueda.isCabeceraResBusqueda(driver, categoriaABuscar));
//	}
}
