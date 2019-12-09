package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * PageObject asociado a la página-2 del checkout de móvil ("Datos de Pago" con los métodos de pago)
 * @author jorge.munoz
 */
public class Page2DatosPagoCheckoutMobil extends WebdrvWrapp {
	
	public enum StateMethod {unselected, selecting, selected}
	enum TypeActionLinkFP {PlegarPagos, DesplegarPagos}
	
    public static SecTMango secTMango;
    public static SecBillpay secBillpay;
    
    static String XPathLink2DatosPago = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step2']";
    static String XPathButtonFinalizarCompra = 
    	"//button[(@id[contains(.,'complete-step2')] or @id[contains(.,'complete-iframe-step2')]) and not(@class[contains(.,' hidden')])]";
    static String XPathRedError = "//div[@class[contains(.,'step-error')]]/p";
    
    static String tagMetodoPago = "@TagMetodoPago";
    static String tagMetodoPagoLowerCase = "@LowerCaseTagMetodoPago";
    static String XPathBlockTarjetaGuardadaPagoWithTag = "//div[" + 
    	"@data-analytics-value='" + tagMetodoPago + "' or " + 
    	"@data-analytics-value='" + tagMetodoPagoLowerCase + "']";
    static String XPathRadioTrjGuardada = "//div[@data-custom-radio-id[contains(.,'-saved')]]";
    
    //Desconozco este XPath, de momento he puesto el de Desktop
    static String XPathCvcTrjGuardada = "//div[@class='storedCardForm']//input[@id='cvc']"; 
    
    static String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
    static String XPathLinkFormasPago = "//div[@class[contains(.,'payment-method')]]//span[@class[contains(.,'others-title')]]"; 
    static String XPathLineaPagoLayoutLinea = "//div[@class[contains(.,'payment-method')] and @data-id]";
    static String XPathLineaPagoLayoutPestanya = "//ul[@class[contains(.,'nav-tabs')]]/li[@data-toggle='tab']";
    
    //secciones de pagos (que se pueden mostrar/ocultar) disponibles en países como México
    static String XPathSectionsPagosMobil = "//*[@class[contains(.,'group-card-js')]]"; 
    
    static String getXPathBlockTarjetaGuardada(String metodoPago) {
    	return (XPathBlockTarjetaGuardadaPagoWithTag
    			.replace(tagMetodoPago, metodoPago)
    			.replace(tagMetodoPagoLowerCase, metodoPago.toLowerCase()));
    }
    
    static String getXPathRadioTarjetaGuardada(String metodoPago) {
    	String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
    	return (xpathMethod + XPathRadioTrjGuardada);
    }
    
    static String getXPathPago(String nombrePago) {
    	return (XPathLineaPagoLayoutLinea + "/div[@data-analytics-value='" + nombrePago.toLowerCase() + "']/..");
    }
    
    static String getXPathRadioPago(String nombrePago) {
    	if (nombrePago.contains("mercadopago")) {
        	String methodRadioName = PageCheckoutWrapper.getMethodInputValue(nombrePago, Channel.movil_web);
        	return ("//div[@data-custom-radio-id='" + methodRadioName + "']"); 
    	}
    	return (getXPathPago(nombrePago) + "//div[@data-custom-radio-id]");
    }
    
    static String getXPathTextUnderPago(String nombrePago) {
    	return (getXPathPago(nombrePago) + "//div[@class='variable-card-content']");
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
        if (isElementVisible(driver, By.xpath(XPathLink2DatosPago))) {
            clickLink2DatosPagoAndWait(driver);
        }
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
    
    public static boolean isMetodoPagoPresent(String nombrePago, WebDriver driver) {
        String xpathClickPago = getXPathRadioPago(nombrePago);
        return (isElementPresent(driver, By.xpath(xpathClickPago)));
    }

    public static StateMethod getStateMethod(String nombrePago, WebDriver driver) {
    	String xpathRadio = getXPathRadioPago(nombrePago);
    	WebElement radio = driver.findElement(By.xpath(xpathRadio));
    	String classRadio = radio.getAttribute("class");
    	if (classRadio.contains("checked")) {
    		return StateMethod.selected;
    	}
//    	if (classRadio.contains("reload")) {
//    		return StateMethod.selecting;
//    	}
    	return StateMethod.unselected;
    }
    
    public static boolean isMethodInStateUntil(
    		String nombrePago, StateMethod stateExpected, int maxSecondsWait, WebDriver driver) throws Exception {
    	for (int i=0; i<maxSecondsWait; i++) {
    		StateMethod actualState = getStateMethod(nombrePago, driver);
    		if (actualState==stateExpected) {
    			return true;
    		}
    		Thread.sleep(1000);
    	}
    	
    	return false;
    }
    
    /**
     * @return si el número de métodos de pago visualizados en pantalla es el correcto
     */
    public static boolean isNumMetodosPagoOK(Pais pais, AppEcom app, boolean isEmpl, WebDriver driver) {
        int numPagosPant = driver.findElements(By.xpath(XPathLineaPagoLayoutLinea)).size();
        int numPagosPais = pais.getListPagosForTest(app, isEmpl).size();
        return (numPagosPais == numPagosPant);
    }
    
    public static boolean isNumpagos(int numPagosExpected, WebDriver driver) {
        int numPagosPant = driver.findElements(By.xpath(XPathLineaPagoLayoutLinea)).size();
        return (numPagosPant == numPagosExpected);
    }
    
    public static boolean isPresentMetodosPago(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathLineaPagoLayoutLinea)));
    }

    public static void goToPageFromCheckoutIfNeeded(WebDriver driver) throws Exception {
        int i=0;
        while (!isPageUntil(1, driver) && i<3) {
            i+=1;
            if (Page1EnvioCheckoutMobil.isPageUntil(0, driver)) {
                Page1EnvioCheckoutMobil.clickContinuarAndWaitPage2(driver);
            } else {
                clickLink2DatosPagoAndWait(driver);
            }
        }
    }
    
    /**
     * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
     */
    public static void forceClickMetodoPagoAndWait(String nombrePago, Pais pais, WebDriver driver) throws Exception {
        goToPageFromCheckoutIfNeeded(driver);
        despliegaMetodosPago(driver);
        moveToFirstMetodoPagoLine(driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 2);
        clickMetodoPagoAndWait(pais, nombrePago, driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 10);
        waitForPageLoaded(driver); //For avoid StaleElementReferenceException
    }
    
    public static void clickLinkFormasPagoFor(TypeActionLinkFP typeAction, WebDriver driver) throws Exception {
    	By formasPagosBy = By.xpath(getXPathLinkFormasPagoFor(typeAction));
    	WebdrvWrapp.clickAndWaitLoad(driver, formasPagosBy, TypeOfClick.javascript);
    }
    
    public static void moveToFirstMetodoPagoLine(WebDriver driver) {
    	moveToElement(By.xpath(XPathLineaPagoLayoutLinea), driver);
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
    
    public static void clickMetodoPagoAndWait(Pais pais, String nombrePago, WebDriver driver) throws Exception {
    	clickMetodoPago(nombrePago, driver);
    	isMethodInStateUntil(nombrePago, StateMethod.selected, 1, driver);
    }

    private static void clickMetodoPago(String nombrePago, WebDriver driver) throws Exception {
        String xpathClickMetodoPago = getXPathRadioPago(nombrePago);
        moveToElement(By.xpath(xpathClickMetodoPago), driver);
        
        //El icono queda debajo del header "Checkout" y es posible scrollar un poco más porque no funciona el moveByOffset así que falla el click
        //No es lo correcto pero en esta situación no ha quedado otra que modificar el z-index de dicho header mediante JavaScript
        hideHtmlComponent(HtmlLocator.TagName, "header", driver);

        //Si el icono sigue sin estar visible y existen secciones plegadas que pueden estar ocultándolo (como p.e. en México) buscaremos el pago en dichas secciones
        if (!driver.findElement(By.xpath(xpathClickMetodoPago)).isDisplayed()) {
        	searchMetPagoLayoutLineaInSections(nombrePago, driver);
        }
        
        clickAndWaitLoad(driver, By.xpath(xpathClickMetodoPago));
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
        	String xpathTexto = getXPathTextUnderPago(pago.getNombre(channel));
            return (isElementVisibleUntil(driver, By.xpath(xpathTexto), maxSecondsToWait));
        }
    }
    
    /**
     * Revisa si el método de pago no está visible. En este caso mira si existen secciones plegadas que puedan estar ocultándolo  (como p.e. en México) 
     * y las va desplegando hasta que encuentra el método de pago   
     */
    private static void searchMetPagoLayoutLineaInSections(String nombrePago, WebDriver driver) throws Exception {
        String xpathClickMetodoPago = getXPathPago(nombrePago);
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
        return (isElementVisible(driver, By.xpath(XPathRedError)));
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

	public static void inputCvcTrjGuardadaIfVisible(String cvc, WebDriver driver) {
		if (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathCvcTrjGuardada))) {
			WebElement input = driver.findElement(By.xpath(XPathCvcTrjGuardada));
			input.clear();
			input.sendKeys(cvc);
		}
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
    }
    
    public static boolean isMarkedQuieroFactura(WebDriver driver) {
        WebElement radio = driver.findElement(By.xpath(XPathLinkSolicitarFactura));
        return (
        	radio.getAttribute("checked")!=null &&
            radio.getAttribute("checked").contains("true"));
    }
    
    static String XPathPrecioTotal = "//div[@class[contains(.,'summary-total-price')]]/p";
    static String XPathDescuento = "//div[@class[contains(.,'summary-subtotal-price')]]/p/span[@class='price-negative']/..";
    static String XPathDireccionEnvioText = "//p[@class='address']";

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
        precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
//        if (precioTotal.indexOf("0")==0) {
//            //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
//            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
//        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }
    
    public static String getPrecioTotalSinSaldoEnCuenta(WebDriver driver) throws Exception {
        String precioTotal = "";
        precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
        if (precioTotal.indexOf("0")==0) {
            //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }

    public static String getTextDireccionEnvioCompleta(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathDireccionEnvioText))) {
            return (driver.findElement(By.xpath(XPathDireccionEnvioText)).getText());
        }
        return "";
    }    
}