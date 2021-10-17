package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Pago;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * PageObject asociado a la página-2 del checkout de móvil ("Datos de Pago" con los métodos de pago)
 * @author jorge.munoz
 */
public class Page2DatosPagoCheckoutMobil extends PageObjTM {
	
	public enum StateMethod {unselected, selecting, selected}
	enum TypeActionLinkFP {PlegarPagos, DesplegarPagos}
	
	private final Channel channel;
	private final AppEcom app;
	
    private final SecTMango secTMango;
    private final SecBillpay secBillpay;
    
    private final static String XPathLink2DatosPago = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step2']";
    private final static String XPathButtonFinalizarCompra = 
    	"//button[(@id[contains(.,'complete-step2')] or @id[contains(.,'complete-iframe-step2')]) and not(@class[contains(.,' hidden')])]";
    private final static String XPathRedError = "//div[@class[contains(.,'step-error')]]/p";
    
    private final static String tagMetodoPago = "@TagMetodoPago";
    private final static String tagMetodoPagoLowerCase = "@LowerCaseTagMetodoPago";
    private final static String XPathBlockTarjetaGuardadaPagoWithTag = "//div[" + 
    	"@data-analytics-value='" + tagMetodoPago + "' or " + 
    	"@data-analytics-value='" + tagMetodoPagoLowerCase + "']";
    private final static String XPathRadioTrjGuardada = "//div[@data-custom-radio-id[contains(.,'-saved')]]";
    
    //Desconozco este XPath, de momento he puesto el de Desktop
    private final static String XPathCvcTrjGuardada = "//div[@class='storedCardForm']//input[@id='cvc']"; 
    
    private final static String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
    private final static String XPathLinkFormasPago = "//div[@class[contains(.,'payment-method')]]//span[@class[contains(.,'others-title')]]"; 
    private final static String XPathLineaPagoLayoutLinea = "//div[@class[contains(.,'payment-method')] and @data-id]";
    
    //secciones de pagos (que se pueden mostrar/ocultar) disponibles en países como México
    private final static String XPathSectionsPagosMobil = "//*[@class[contains(.,'group-card-js')]]"; 
    
    public Page2DatosPagoCheckoutMobil(Channel channel, AppEcom app, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    	this.app = app;
        this.secTMango = new SecTMango(channel, app, driver);
        this.secBillpay = new SecBillpay(channel, driver);
    }
    
    private String getXPathBlockTarjetaGuardada(String metodoPago) {
    	return (XPathBlockTarjetaGuardadaPagoWithTag
    			.replace(tagMetodoPago, metodoPago)
    			.replace(tagMetodoPagoLowerCase, metodoPago.toLowerCase()));
    }
    
    private String getXPathRadioTarjetaGuardada(String metodoPago) {
    	String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
    	return (xpathMethod + XPathRadioTrjGuardada);
    }
    
    private String getXPathPago(String nombrePago) {
    	return (XPathLineaPagoLayoutLinea + "/div[@data-analytics-value='" + nombrePago.toLowerCase() + "']/..");
    }
    
    private String getXPathRadioPago(String nombrePago) {
    	if (nombrePago.contains("mercadopago")) {
    		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
        	String methodRadioName = pageCheckoutWrapper.getMethodInputValue(nombrePago);
        	return ("//div[@data-custom-radio-id='" + methodRadioName + "']"); 
    	}
    	//TODO eliminar cuando añadan el valor klarna al atributo data-analytics-value
    	if (nombrePago.compareTo("KLARNA")==0) {
    		return "//div[@data-custom-radio-id='klarna']";
    	}
    	return (getXPathPago(nombrePago) + "//div[@data-custom-radio-id]");
    }
    
    private String getXPathTextUnderPago(String nombrePago) {
    	return (getXPathPago(nombrePago) + "//div[@class='variable-card-content']");
    }

    /**
     * @return el XPath correspondiente al link para plegar/desplegar los métodos de pago
     */
    private String getXPathLinkFormasPagoFor(TypeActionLinkFP actionForLink) {
    	switch (actionForLink) {
    	case PlegarPagos:
            return XPathLinkFormasPago + "//self::*[@class[contains(.,'selected')]]";
    	case DesplegarPagos:
    	default:
    		return XPathLinkFormasPago + "//self::*[@class[not(contains(.,'selected'))]]";
    	}
    }
    
    public boolean isPageUntil(int maxSecondsToWait) {
        return isClickableButtonFinalizarCompraUntil(maxSecondsToWait);
    }

	public void clickLink2DatosPagoAndWait() {
		click(By.xpath(XPathLink2DatosPago)).exec();
		isPageUntil(2);
	}

	public void clickLink2DatosPagoIfVisible() {
		if (state(Visible, By.xpath(XPathLink2DatosPago)).check()) {
			clickLink2DatosPagoAndWait();
		}
	}

	public void clickButtonFinalizarCompra() throws Exception {
		click(By.xpath(XPathButtonFinalizarCompra)).type(javascript).exec();
	}

	public boolean isClickableButtonFinalizarCompraUntil(int maxSeconds) {
		return(state(Clickable, By.xpath(XPathButtonFinalizarCompra)).wait(maxSeconds).check());
	}

    public void waitAndClickFinalizarCompra(int maxSecondsToWait) throws Exception {
    	isClickableButtonFinalizarCompraUntil(maxSecondsToWait);
    	clickButtonFinalizarCompra();
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isClickableButtonFinalizarCompraUntil(0)) {
        	clickButtonFinalizarCompra();  
        }
    }

	public boolean isMetodoPagoPresent(String nombrePago) {
		String xpathClickPago = getXPathRadioPago(nombrePago);
		return (state(Present, By.xpath(xpathClickPago)).check());
	}

    public StateMethod getStateMethod(String nombrePago) {
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
    
    public boolean isMethodInStateUntil(String nombrePago, StateMethod stateExpected, int maxSeconds) 
    throws Exception {
    	for (int i=0; i<maxSeconds; i++) {
    		StateMethod actualState = getStateMethod(nombrePago);
    		if (actualState==stateExpected) {
    			return true;
    		}
    		Thread.sleep(1000);
    	}
    	return false;
    }
    
    public boolean isNumMetodosPagoOK(Pais pais, boolean isEmpl) {
        int numPagosPant = driver.findElements(By.xpath(XPathLineaPagoLayoutLinea)).size();
        int numPagosPais = pais.getListPagosForTest(app, isEmpl).size();
        return (numPagosPais == numPagosPant);
    }
    
    public boolean isNumpagos(int numPagosExpected) {
        int numPagosPant = driver.findElements(By.xpath(XPathLineaPagoLayoutLinea)).size();
        return (numPagosPant == numPagosExpected);
    }

	public boolean isPresentMetodosPago() {
		return (state(Present, By.xpath(XPathLineaPagoLayoutLinea)).check());
	}

    public void goToPageFromCheckoutIfNeeded() throws Exception {
        int i=0;
        while (!isPageUntil(1) && i<3) {
            i+=1;
            Page1EnvioCheckoutMobil page1 = new Page1EnvioCheckoutMobil(driver);
            if (page1.isPageUntil(0)) {
                page1.clickContinuarAndWaitPage2(app);
            } else {
                clickLink2DatosPagoAndWait();
            }
        }
    }
    
    /**
     * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
     */
    public void forceClickMetodoPagoAndWait(String nombrePago, Pais pais) throws Exception {
        goToPageFromCheckoutIfNeeded();
        despliegaMetodosPago();
        moveToFirstMetodoPagoLine();
        
        PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
        pageCheckoutWrapper.waitUntilNoDivLoading(2);
        clickMetodoPagoAndWait(pais, nombrePago);
        pageCheckoutWrapper.waitUntilNoDivLoading(10);
        waitForPageLoaded(driver); //For avoid StaleElementReferenceException
    }

	public void clickLinkFormasPagoFor(TypeActionLinkFP typeAction) throws Exception {
		By formasPagosBy = By.xpath(getXPathLinkFormasPagoFor(typeAction));
		click(formasPagosBy).type(javascript).exec();
	}

    public void moveToFirstMetodoPagoLine() {
    	moveToElement(By.xpath(XPathLineaPagoLayoutLinea), driver);
    }
    
    public void despliegaMetodosPago() throws Exception {
        if (areMetodosPagoPlegados()) { 
        	clickLinkFormasPagoFor(TypeActionLinkFP.DesplegarPagos);
        	metodosPagosInStateUntil(false, 3);
        }
    }

	public boolean areMetodosPagoPlegados() {
		String xpathOtrasFormasPagoPlegado = getXPathLinkFormasPagoFor(TypeActionLinkFP.DesplegarPagos); 
		return (state(Visible, By.xpath(xpathOtrasFormasPagoPlegado)).check());
	}

    public void metodosPagosInStateUntil(boolean plegados, int seconds) throws Exception {
        boolean inStateOk = false;
        int i=0;
        do {
            boolean arePlegados = areMetodosPagoPlegados();
            if (arePlegados==plegados) {
                inStateOk = true;
                break;
            }

            Thread.sleep(1000);
            i+=1;
        }
        while (!inStateOk && i<seconds);
    }    
    
    public void clickMetodoPagoAndWait(Pais pais, String nombrePago) throws Exception {
    	clickMetodoPago(nombrePago);
    	isMethodInStateUntil(nombrePago, StateMethod.selected, 1);
    }

    private void clickMetodoPago(String nombrePago) throws Exception {
        String xpathClickMetodoPago = getXPathRadioPago(nombrePago);
        moveToElement(By.xpath(xpathClickMetodoPago), driver);
        
        //El icono queda debajo del header "Checkout" y es posible scrollar un poco más porque no funciona el moveByOffset así que falla el click
        //No es lo correcto pero en esta situación no ha quedado otra que modificar el z-index de dicho header mediante JavaScript
        hideHtmlComponent(HtmlLocator.TagName, "header", driver);

        //Si el icono sigue sin estar visible y existen secciones plegadas que pueden estar ocultándolo (como p.e. en México) buscaremos el pago en dichas secciones
        if (!driver.findElement(By.xpath(xpathClickMetodoPago)).isDisplayed()) {
        	searchMetPagoLayoutLineaInSections(nombrePago);
        }
        click(By.xpath(xpathClickMetodoPago)).exec();
    }

	/**
	 * Nos dice si es visible el bloque correspondiente a un determinado pago externo que aparece al seleccionar el método de pago
	 */
	@SuppressWarnings("static-access")
	public boolean isVisibleTextoBajoPagoUntil(Pago pago, int maxSeconds) {
		switch (pago.getTypePago()) {
		case TMango:
			return (secTMango.isVisibleUntil(maxSeconds));
		case Billpay:
			return (secBillpay.isVisibleUntil(maxSeconds));
		default:
			String xpathTexto = getXPathTextUnderPago(pago.getNombre(channel, app));
			return (state(Visible, By.xpath(xpathTexto)).wait(maxSeconds).check());
		}
	}

    /**
     * Revisa si el método de pago no está visible. En este caso mira si existen secciones plegadas que puedan estar ocultándolo  (como p.e. en México) 
     * y las va desplegando hasta que encuentra el método de pago   
     */
    private void searchMetPagoLayoutLineaInSections(String nombrePago) throws Exception {
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

	public boolean isRedErrorVisible() {
		return (state(Visible, By.xpath(XPathRedError)).check());
	}

	public String getTextRedError() {
		return (driver.findElement(By.xpath(XPathRedError)).getText());
	}

	public boolean isVisibleRadioTrjGuardada(String metodoPago)  {
		String xpathRadioTrjGuardada = getXPathRadioTarjetaGuardada(metodoPago);
		return (state(Visible, By.xpath(xpathRadioTrjGuardada)).check());
	}

	public void clickRadioTrjGuardada() {
		click(By.xpath(XPathRadioTrjGuardada)).exec();
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) {
		if (state(Visible, By.xpath(XPathCvcTrjGuardada)).check()) {
			WebElement input = driver.findElement(By.xpath(XPathCvcTrjGuardada));
			input.clear();
			input.sendKeys(cvc);
		}
	}

	public void clickSolicitarFactura() {
		driver.findElement(By.xpath(XPathLinkSolicitarFactura)).click();
	}

	static String XPathArticleBolsa = "//div[@id[contains(.,'panelBolsa:iteradorEntrega')]]";
	public boolean isArticulos() {
		return (state(Present, By.xpath(XPathArticleBolsa)).check());
	}

    public void confirmarPagoFromMetodos(DataPedido dataPedido) throws Exception {
    	clickButtonFinalizarCompra();
    }
    
    public boolean isMarkedQuieroFactura() {
        WebElement radio = driver.findElement(By.xpath(XPathLinkSolicitarFactura));
        return (
        	radio.getAttribute("checked")!=null &&
            radio.getAttribute("checked").contains("true"));
    }
    
    private final static String XPathPrecioTotal = "//div[@class[contains(.,'summary-total-price')]]/p";
    private final static String XPathDescuento = "//div[@class[contains(.,'summary-subtotal-price')]]/p/span[@class='price-negative']/..";
    private final static String XPathDireccionEnvioText = "//p[@class='address']";

    public void clickFinalizarCompraAndWait(int maxSecondsToWait) throws Exception {
        clickButtonFinalizarCompra();
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isClickableButtonFinalizarCompraUntil(0)) {
        	clickButtonFinalizarCompra();  
        	if (isClickableButtonFinalizarCompraUntil(1)) {
        		clickButtonFinalizarCompra();  
        	}
        }
        
        PageRedirectPasarelaLoading.isPageNotVisibleUntil(maxSecondsToWait, driver);
    }
    
    public String getPrecioTotalFromResumen() throws Exception {
        String precioTotal = "";
        PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
        precioTotal = pageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal);
//        if (precioTotal.indexOf("0")==0) {
//            //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
//            precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathDescuento, driver);
//        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }
    
    public String getPrecioTotalSinSaldoEnCuenta() throws Exception {
        String precioTotal = "";
        PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
        precioTotal = pageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal);
        if (precioTotal.indexOf("0")==0) {
            //Si el total es 0 podríamos estar en el caso de saldo en cuenta (el importe total = importe del descuento)
            precioTotal = pageCheckoutWrapper.formateaPrecioTotal(XPathDescuento);
        }
        
        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }

	public String getTextDireccionEnvioCompleta() {
		if (state(Present, By.xpath(XPathDireccionEnvioText)).check()) {
			return (driver.findElement(By.xpath(XPathDireccionEnvioText)).getText());
		}
		return "";
	}
}