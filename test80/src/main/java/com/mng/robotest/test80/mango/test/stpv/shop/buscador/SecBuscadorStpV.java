package com.mng.robotest.test80.mango.test.stpv.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.service.webdriver.pageobject.WebdrvWrapp;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorBusqueda;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria.From;
import com.mng.robotest.test80.mango.test.pageobject.shop.navigations.ArticuloNavigations;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;

public class SecBuscadorStpV {

	@Step (
		description="Buscar el artículo con id #{product.getGarmentId()} y color:#{product.getDefaultColor().getId()})", 
		expected="Aparece la ficha del producto")
	public static void searchArticulo(Garment product, DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		ArticuloNavigations.buscarArticulo(product.getArticleWithMoreStock(), dCtxSh.channel, dCtxSh.appE, driver);
		WebdrvWrapp.waitForPageLoaded(driver);  
		PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
		pageFichaStpV.validateIsFichaAccordingTypeProduct(product);
	}

	@Step (
		description="Introducir la categoría de producto <b>#{categoriaABuscar} </b>(existe categoría: #{categoriaExiste})</b>", 
		expected="El resultado de la búsqueda es el correcto :-)")
	public static void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste, AppEcom app, 
												 Channel channel, WebDriver driver) throws Exception {
		SecCabecera.buscarTexto(categoriaABuscar, channel, app, driver);
		PageGaleria pageGaleria = (PageGaleria)PageGaleria.getNew(From.buscador, channel, app, driver); 
		WebdrvWrapp.waitForPageLoaded(driver);    

		//Validaciones
		if (categoriaExiste) { 
			appearsProductsOfCategoria(categoriaABuscar, pageGaleria);
		} else {
			appearsSearchErrorPage(categoriaABuscar, driver);
		}

		//Validaciones estándar. 
		StdValidationFlags flagsVal = StdValidationFlags.newOne();
		flagsVal.validaSEO = false;
		flagsVal.validaJS = true;
		flagsVal.validaImgBroken = true;
		AllPagesStpV.validacionesEstandar(flagsVal, driver);
	}

	@Validation
	private static ChecksTM appearsProductsOfCategoria(String categoriaABuscar, PageGaleria pageGaleria) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsWait = 3;
		String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
		validations.add(
			"Aparece como mínimo un producto de tipo " + producSin1erCaracter + " (lo esperamos hasta " + maxSecondsWait + " segundos)",
			"".compareTo(pageGaleria.getNombreArticuloWithText(producSin1erCaracter, maxSecondsWait))!=0, State.Defect);
		return validations;
	}

	@Validation (
		description="Aparece la página de error en la búsqueda con el encabezado <b>#{categoriaABuscar}</b>",
		level=State.Warn)
	private static boolean appearsSearchErrorPage(String categoriaABuscar, WebDriver driver) {
		return (PageErrorBusqueda.isCabeceraResBusqueda(driver, categoriaABuscar));
	}
}
