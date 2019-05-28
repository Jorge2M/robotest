package com.mng.robotest.test80.mango.test.stpv.shop.buscador;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorBusqueda;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecBuscadorWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;

public class SecBuscadorStpV {
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    public static void searchArticuloAndValidateBasic(TypeArticleStock typeArticle, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	ArticleStock articulo = ManagerArticlesStock.getArticleStock(typeArticle, dCtxSh);
    	searchArticuloAndValidateBasic(articulo, dCtxSh, driver);
    }
    
    /**
     * Utiliza el buscador para localizar una referencia y aplica validaciones específicas según la disponibilidad: disponible, no_disponible, algún_color_no_disponible
     */
    @Step (
    	description=
    		"Buscar un producto de tipo: <b>#{articulo.getType()}</b> " +
    		"(#{articulo.getReference()} color:#{articulo.getColourCode()}, size:#{articulo.getSize()})", 
    	expected=
    		"Aparece una ficha de producto de typo <b>#{articulo.getType()}</b>")
    public static void searchArticuloAndValidateBasic(ArticleStock articulo, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecBuscadorWrapper.buscarArticulo(articulo, dCtxSh.channel, dCtxSh.appE, driver);
        WebdrvWrapp.waitForPageLoaded(driver);  
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpV.validateIsFichaAccordingTypeProduct(articulo);
    }
    
    @Step (
    	description="Introducir la categoría de producto <b>#{categoriaABuscar} </b>(existe categoría: #{categoriaExiste})</b>", 
        expected="El resultado de la búsqueda es el correcto :-)")
    public static void busquedaCategoriaProducto(String categoriaABuscar, boolean categoriaExiste, AppEcom app, 
    											 Channel channel, WebDriver driver) throws Exception {
    	PageGaleria pageGaleria = (PageGaleria)PageGaleria.getInstance(channel, app, driver); 
        SecBuscadorWrapper.buscarTexto(categoriaABuscar, channel, app, driver);
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
    private static ChecksResult appearsProductsOfCategoria(String categoriaABuscar, PageGaleria pageGaleria) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 3;
    	String producSin1erCaracter = categoriaABuscar.substring(1, categoriaABuscar.length()-1).toLowerCase();
    	validations.add(
    		"Aparece como mínimo un producto de tipo " + producSin1erCaracter + " (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		"".compareTo(pageGaleria.getArticuloWithText(producSin1erCaracter, maxSecondsWait))!=0, State.Defect);
//    	validations.add(
//    		"Aparece la categoría en el resultado de la búsqueda",
//    		pageGaleria.isCabeceraResBusqueda(producSin1erCaracter), State.Defect);
    	return validations;
    }
    
    @Validation (
    	description="Aparece la página de error en la búsqueda con el encabezado <b>#{categoriaABuscar}</b>",
    	level=State.Warn)
    private static boolean appearsSearchErrorPage(String categoriaABuscar, WebDriver driver) {
    	return (PageErrorBusqueda.isCabeceraResBusqueda(driver, categoriaABuscar));
    }
}
