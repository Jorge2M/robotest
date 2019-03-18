package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais.LayoutPago;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * PageObject asociado a la página-2 del checkout de móvil ("Datos de Pago" con los métodos de pago)
 * @author jorge.munoz
 */
public class Page2DatosPagoCheckoutMobil extends WebdrvWrapp {
	enum TypeActionLinkFP {PlegarPagos, DesplegarPagos}
	
    public static SecTMango secTMango;
    public static SecBillpay secBillpay;
    
    static String XPathLink2DatosPago = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step2']";
    static String XPathButtonFinalizarCompra = "//button[@id[contains(.,'complete-step2')]]";
    static String XPathRedError = "//div[@class[contains(.,'step-error')]]/p";
    
    static String tagMetodoPago = "@TagMetodoPago";
    static String XPathBlockTarjetaGuardadaPagoWithTag = "//div[@data-analytics-value='" + tagMetodoPago + "']";
    static String XPathRadioTrjGuardada = "//div[@clas[contains(.,'saved-card')]//div[@class[contains(.,'radio')]]";
    
    static String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
    static String XPathLinkFormasPago = "//div[@class[contains(.,'payment-method')]]//span[@class[contains(.,'others-title')]]"; 
    static String XPathLineaPagoLayoutLinea = "//div[@class[contains(.,'payment-method')] and @data-id]";
    static String XPathLineaPagoLayoutPestanya = "//ul[@class[contains(.,'nav-tabs')]]/li[@data-toggle='tab']";
    
    //secciones de pagos (que se pueden mostrar/ocultar) disponibles en países como México
    static String XPathSectionsPagosMobil = "//*[@class[contains(.,'group-card-js')]]"; 
    
    static String getXPathBlockTarjetaGuardada(String metodoPago) {
    	return (XPathBlockTarjetaGuardadaPagoWithTag.replace(tagMetodoPago, metodoPago));
    }
    
    static String getXPathRadioTarjetaGuardada(String metodoPago) {
    	String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
    	return (xpathMethod + XPathRadioTrjGuardada);
    }
    
    static String getXPathPagoLayoutLinea(String nombrePago) {
    	return (XPathLineaPagoLayoutLinea + "/div[@data-analytics-value='" + nombrePago.toLowerCase() + "']/..");
    }
    
    static String getXPathRadioPagoLayoutLinea(String nombrePago) {
    	return (getXPathPagoLayoutLinea(nombrePago) + "//div[@class[contains(.,'custom-radio')]]/div");
    }
    
    static String getXPathPagoLayoutPestanya(String nombrePago) {
    	return (XPathLineaPagoLayoutPestanya + "//self::*[text()[contains(.,'" + nombrePago.toLowerCase() + "')]]");
    }    
    
    static String getXPathTextUnderPagoLayoutLinea(String nombrePago) {
    	return (getXPathPagoLayoutLinea(nombrePago) + "//div[@class='variable-card-content']");
    }
    
    static String getXPathClickMetodoPagoGeneric(LayoutPago layoutPago) {
    	switch (layoutPago) {
    	case Linea:
    		return XPathLineaPagoLayoutLinea;
    	case Pestaña:
    	default:
    		return XPathLineaPagoLayoutPestanya;
    	}    	
    }
    
    static String getXPathClickMetodoPago(String nombrePago, LayoutPago layoutPago) {
    	switch (layoutPago) {
    	case Linea:
    		return (getXPathRadioPagoLayoutLinea(nombrePago));
    	case Pestaña:
    	default:
    		return (getXPathPagoLayoutPestanya(nombrePago));
    	}
    }
    
    /**
     * @return el XPath correspondiente al link para plegar/desplegar los métodos de pago
     */
    static String getXPathLinkFormasPagoFor(TypeActionLinkFP actionForLink) {
    	switch (actionForLink) {
    	case PlegarPagos:
            return XPathLinkFormasPago + "//self::*[@class[contains(.,'selected')]]";
    	case DesplegarPagos:
    	default:
    		return XPathLinkFormasPago + "//self::*[@class[not(contains(.,'selected'))]]";
    	}
    }
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return isClickableButtonFinalizarCompraUntil(maxSecondsToWait, driver);
    }
    
    /**
     * Selección del apartado "2. Datos de pago"
     */
    public static void clickLink2DatosPagoAndWait(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLink2DatosPago));
        isPageUntil(2, driver);
    }
    
    public static void clickLink2DatosPagoIfVisible(WebDriver driver) throws Exception {
        if (isElementVisible(driver, By.xpath(XPathLink2DatosPago)))
            clickLink2DatosPagoAndWait(driver);
    }
    
    public static void clickButtonFinalizarCompra(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonFinalizarCompra), TypeOfClick.javascript);    
    }
    
    public static boolean isClickableButtonFinalizarCompraUntil(int seconds, WebDriver driver) {
        return (isElementClickableUntil(driver, By.xpath(XPathButtonFinalizarCompra), seconds));
    }
    
    public static void waitAndClickFinalizarCompra(int maxSecondsToWait, WebDriver driver) throws Exception {
    	isClickableButtonFinalizarCompraUntil(maxSecondsToWait, driver);
    	clickButtonFinalizarCompra(driver);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isClickableButtonFinalizarCompraUntil(0, driver)) {
        	clickButtonFinalizarCompra(driver);  
        }
    }
    
    public static boolean isMetodoPagoPresent(String nombrePago, LayoutPago layoutPago, WebDriver driver) {
        String xpathClickPago = getXPathClickMetodoPago(nombrePago, layoutPago);
        if (isElementPresent(driver, By.xpath(xpathClickPago)))
            return true; 
        
        return false;
    }
    
    /**
     * @return si el número de métodos de pago visualizados en pantalla es el correcto
     */
    public static boolean isNumMetodosPagoOK(Pais pais, AppEcom app, boolean isEmpl, WebDriver driver) {
        int numPagosPant = driver.findElements(By.xpath(getXPathClickMetodoPagoGeneric(pais.getLayoutPago()))).size();
        
        //Se comprueba que el número de métodos de pago en pantalla coincida con los asociados al país
        int numPagosPais = pais.getListPagosTest(app, isEmpl).size();
        return (numPagosPais == numPagosPant);
    }
    
    public static boolean isNumpagos(int numPagosExpected, LayoutPago layoutPago, WebDriver driver) {
        int numPagosPant = driver.findElements(By.xpath(getXPathClickMetodoPagoGeneric(layoutPago))).size();
        return (numPagosPant == numPagosExpected);
    }
    
    public static boolean isPresentMetodosPago(LayoutPago layoutPago, WebDriver driver) {
        return (isElementPresent(driver, By.xpath(getXPathClickMetodoPagoGeneric(layoutPago))));
    }

    public static void goToPageFromCheckoutIfNeeded(WebDriver driver) throws Exception {
        int i=0;
        while (!isPageUntil(1, driver) && i<3) {
            i+=1;
            if (Page1EnvioCheckoutMobil.isPageUntil(0, driver))
                Page1EnvioCheckoutMobil.clickContinuarAndWaitPage2(driver);
            else
                clickLink2DatosPagoAndWait(driver);
        }
    }
    
    /**
     * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
     */
    public static void forceClickMetodoPagoAndWait(String nombrePago, Pais pais, WebDriver driver) throws Exception {
        goToPageFromCheckoutIfNeeded(driver);
        despliegaMetodosPago(driver);
        moveToFirstMetodoPagoLine(pais.getLayoutPago(), driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 2/*seconds*/);
        clickMetodoPago(pais, nombrePago, driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 10/*seconds*/);
        waitForPageLoaded(driver); //For avoid StaleElementReferenceException
    }
    
    public static void clickLinkFormasPagoFor(TypeActionLinkFP typeAction, WebDriver driver) {
        driver.findElement(By.xpath(getXPathLinkFormasPagoFor(typeAction))).click();
    }
    
    public static void moveToFirstMetodoPagoLine(LayoutPago layoutPago, WebDriver driver) {
    	String xpath1rstPago = getXPathClickMetodoPagoGeneric(layoutPago);
    	moveToElement(By.xpath(xpath1rstPago), driver);
    }
    
    /**
     * Si no lo están, despliega los métodos de pago de la página de checkout
     *      */
    public static void despliegaMetodosPago(WebDriver driver) throws Exception {
        if (areMetodosPagoPlegados(driver)) { 
        	clickLinkFormasPagoFor(TypeActionLinkFP.DesplegarPagos, driver);
        	metodosPagosInStateUntil(false/*plegados*/, 3/*seconds*/, driver);
        }
    }
    
    public static boolean areMetodosPagoPlegados(WebDriver driver) {
        String xpathOtrasFormasPagoPlegado = getXPathLinkFormasPagoFor(TypeActionLinkFP.DesplegarPagos); 
        return (isElementVisible(driver, By.xpath(xpathOtrasFormasPagoPlegado)));
    }
    
    /**
     * Esperamos un máximo de segundos hasta que los métodos de pago estén plegados/desplegados
     */
    public static void metodosPagosInStateUntil(boolean plegados, int seconds, WebDriver driver) throws Exception {
        boolean inStateOk = false;
        int i=0;
        do {
            boolean arePlegados = areMetodosPagoPlegados(driver);
            if (arePlegados==plegados) {
                inStateOk = true;
                break;
            }

            Thread.sleep(1000);
            i+=1;
        }
        while (!inStateOk && i<seconds);
    }    
    
    public static void clickMetodoPago(Pais pais, String nombrePago, WebDriver driver) throws Exception {
    	clickMetodoPago(pais.getLayoutPago(), nombrePago, driver);
    }

    private static void clickMetodoPago(LayoutPago layoutPago, String nombrePago, WebDriver driver) throws Exception {
    	switch (layoutPago) {
    	case Linea:
    		clickMetodoPagoLayoutLinea(nombrePago, driver);
    		break;
    	case Pestaña:
    	default:
    		clickMetodoPagoLayoutPestanya(nombrePago, driver);
    		break;
    	}
    }
    
    private static void clickMetodoPagoLayoutPestanya(String nombrePago, WebDriver driver) {
        String xpathClickMetodoPago = getXPathClickMetodoPago(nombrePago, LayoutPago.Pestaña);
        driver.findElement(By.xpath(xpathClickMetodoPago)).click();    	
    }

    /**
     * Selecciona un método de pago de tipo normal (no PSP)
     */
    private static void clickMetodoPagoLayoutLinea(String nombrePago, WebDriver driver) throws Exception {
        String xpathClickMetodoPago = getXPathClickMetodoPago(nombrePago, LayoutPago.Linea);
        moveToElement(By.xpath(xpathClickMetodoPago), driver);
        
        //El icono queda debajo del header "Checkout" y es posible scrollar un poco más porque no funciona el moveByOffset así que falla el click
        //No es lo correcto pero en esta situación no ha quedado otra que modificar el z-index de dicho header mediante JavaScript
        hideHtmlComponent(HtmlLocator.TagName, "header", driver);

        //Si el icono sigue sin estar visible y existen secciones plegadas que pueden estar ocultándolo (como p.e. en México) buscaremos el pago en dichas secciones
        if (!driver.findElement(By.xpath(xpathClickMetodoPago)).isDisplayed())
        	searchMetPagoLayoutLineaInSections(nombrePago, driver);
        
        clickAndWaitLoad(driver, By.xpath(xpathClickMetodoPago), TypeOfClick.javascript);
    }    

    
    /**
    * Nos dice si es visible el bloque correspondiente a un determinado pago externo que aparece al seleccionar el método de pago
    */
    @SuppressWarnings("static-access")
    public static boolean isVisibleTextoBajoPagoUntil(Pago pago, Channel channel, int maxSecondsToWait, WebDriver driver) {
        switch (pago.getTypePago()) {
        case TMango:
            return (secTMango.isVisibleUntil(Channel.movil_web, maxSecondsToWait, driver));
        case Billpay:
            return (secBillpay.isVisibleUntil(Channel.movil_web, maxSecondsToWait, driver));
        default:
        	String xpathTexto = getXPathTextUnderPagoLayoutLinea(pago.getNombre(channel));
            return (isElementVisibleUntil(driver, By.xpath(xpathTexto), maxSecondsToWait));
        }
    }
    
    /**
     * Revisa si el método de pago no está visible. En este caso mira si existen secciones plegadas que puedan estar ocultándolo  (como p.e. en México) 
     * y las va desplegando hasta que encuentra el método de pago   
     */
    private static void searchMetPagoLayoutLineaInSections(String nombrePago, WebDriver driver) throws Exception {
        String xpathClickMetodoPago = getXPathClickMetodoPago(nombrePago, LayoutPago.Linea);
        boolean methodDisplayed = driver.findElement(By.xpath(xpathClickMetodoPago)).isDisplayed();
        if (!methodDisplayed) {
            List<WebElement> listSecciones = driver.findElements(By.xpath(XPathSectionsPagosMobil));
            int i=0;
            while (!methodDisplayed && i<listSecciones.size()) {
                listSecciones.get(i).click();
                Thread.sleep(500);
                methodDisplayed = driver.findElement(By.xpath(xpathClickMetodoPago)).isDisplayed();
                i+=1; 
            }
        }
    }    
    
    public static boolean isRedErrorVisible(WebDriver driver) {
        if (isElementVisible(driver, By.xpath(XPathRedError)))
            return true;
         
        return false;
    }
    
    /**
     * @return el texto correspondiente al mensaje rojo de error en el pago
     */
    public static String getTextRedError(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathRedError)).getText());
    }
    
    public static boolean isVisibleRadioTrjGuardada(String metodoPago, WebDriver driver)  {
    	String xpathRadioTrjGuardada = getXPathRadioTarjetaGuardada(metodoPago);
    	return (WebdrvWrapp.isElementVisible(driver, By.xpath(xpathRadioTrjGuardada)));
    }
    
    public static void clickRadioTrjGuardada(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathRadioTrjGuardada));
    }    

    public static void clickSolicitarFactura(WebDriver driver) {
        driver.findElement(By.xpath(XPathLinkSolicitarFactura)).click();
    }    

    static String XPathArticleBolsa = "//div[@id[contains(.,'panelBolsa:iteradorEntrega')]]";
    public static boolean isArticulos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathArticleBolsa)));
    }
    
    public static void confirmarPagoFromMetodos(DataPedido dataPedido, WebDriver driver) throws Exception {
    	clickButtonFinalizarCompra(driver);
        PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, Channel.movil_web, driver);
    }
    
    public static boolean isMarkedQuieroFactura(WebDriver driver) {
        WebElement radio = driver.findElement(By.xpath(XPathLinkSolicitarFactura));
        if (radio.getAttribute("checked")!=null &&
            radio.getAttribute("checked").contains("true"))
            return true;
         
        return false;
    }
    
    static String XPathPrecioTotal = "//div[@class[contains(.,'summary-total-price')]]/p";
    static String XPathDescuento = "//div[@class[contains(.,'summary-subtotal-price')]]/p/span[@class='price-negative']/..";
    static String XPathDireccionEnvioText = "//p[@class='summary-info-subdesc']";

    public static void clickFinalizarCompraAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickButtonFinalizarCompra(driver);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isClickableButtonFinalizarCompraUntil(0, driver)) {
        	clickButtonFinalizarCompra(driver);  
        	if (isClickableButtonFinalizarCompraUntil(1, driver)) {
        		clickButtonFinalizarCompra(driver);  
        	}
        }
        
        PageRedirectPasarelaLoading.isPageNotVisibleUntil(maxSecondsToWait, driver);
    }
    
    public static String getPrecioTotalFromResumen(WebDriver driver) throws Exception {
        String precioTotal = "";
//        
//        //En el caso de móvil, si estamos en el Paso-2 "Datos del pago" iremos al Paso-3 de "Resumen" puesto que es allí donde se encuentra el importe total. 
//        //Finalmente volveremos al Paso-2
//        if (Page2DatosPagoCheckoutMobil.isClickableButtonVerResumenUntil(driver, 0/*seconds*/)) {
//            Page2DatosPagoCheckoutMobil.clickButtonVerResumen(driver);
//            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
//            if (precioTotal.indexOf("0")==0)
//                //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
//                precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
//            Page2DatosPagoCheckoutMobil.clickLink2DatosPagoAndWait(driver);
//        }
//        else {
            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
            if (precioTotal.indexOf("0")==0) {
                //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
                precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
            }
//        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }

    public static String getTextDireccionEnvioCompleta(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathDireccionEnvioText)))
            return (driver.findElement(By.xpath(XPathDireccionEnvioText)).getText());
        
        return "";
    }    
}