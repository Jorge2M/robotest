package com.mng.robotest.tests.domains.compra.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.payments.billpay.pageobjects.SecBillpay;
import com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.SecTMango;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

public class Page2DatosPagoCheckoutMobil extends PageBase {
	
	public enum StateMethod {UNSELECTED, SELECTING, SELECTED}
	enum TypeActionLinkFP {PLEGAR_PAGOS, DESPLEGAR_PAGOS}
	
	private final SecTMango secTMango = new SecTMango();
	private final SecBillpay secBillpay = new SecBillpay();
	
	private static final String XP_LINK2_DATOS_PAGO = "//h2[@class[contains(.,'xwing-toggle')] and @data-toggle='step2']";
	private static final String XP_BUTTON_FINALIZAR_COMPRA = 
		"//button[(@id[contains(.,'complete-step2')] or @id[contains(.,'complete-iframe-step2')]) and not(@class[contains(.,' hidden')])]";
	private static final String XP_RED_ERROR = "//div[@class[contains(.,'step-error')]]/p";
	
	private static final String TAG_METODO_PAGO = "@TagMetodoPago";
	private static final String TAG_METODO_PAGO_LOWER_CASE = "@LowerCaseTagMetodoPago";
	private static final String XP_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG = "//div[" + 
		"@data-analytics-value='" + TAG_METODO_PAGO + "' or " + 
		"@data-analytics-value='" + TAG_METODO_PAGO_LOWER_CASE + "']";
	private static final String XP_RADIO_TRJ_GUARDADA = "//div[@data-custom-radio-id[contains(.,'-saved')]]";
	
	//Desconozco este XPath, de momento he puesto el de Desktop
	private static final String XP_CVC_TRJ_GUARDADA = "//div[@class='storedCardForm']//input[@id='cvc']"; 
	
	private static final String XP_LINK_SOLICITAR_FACTURA = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
	private static final String XP_LINK_FORMAS_PAGO = "//div[@class[contains(.,'payment-method')]]//span[@class[contains(.,'others-title')]]"; 
	private static final String XP_LINEA_PAGO_LAYOUT_LINEA = "//div[@class[contains(.,'payment-method')] and @data-id]";
	
	//secciones de pagos (que se pueden mostrar/ocultar) disponibles en países como México
	private static final String XP_SECTIONS_PAGOS_MOBIL = "//*[@class[contains(.,'group-card-js')]]";
	private static final String XP_ARTICLE_BOLSA = "//div[@id[contains(.,'panelBolsa:iteradorEntrega')]]";
	private static final String XP_PRECIO_TOTAL = "//*[@data-testid='summaryTotalPrice.price']";
	private static final String XP_PRECIO_TOTAL_CROATIA_EUROS = "//*[@data-testid='summaryTotalPrice.additionalPrice.1']";
	private static final String XP_DIRECCION_ENVIO_TEXT = "//p[@class='address']";
	
	private String getXPathBlockTarjetaGuardada(String metodoPago) {
		return (XP_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG
				.replace(TAG_METODO_PAGO, metodoPago)
				.replace(TAG_METODO_PAGO_LOWER_CASE, metodoPago.toLowerCase()));
	}
	
	private String getXPathRadioTarjetaGuardada(String metodoPago) {
		String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
		return (xpathMethod + XP_RADIO_TRJ_GUARDADA);
	}
	
	private String getXPathPago(String nombrePago) {
		return (XP_LINEA_PAGO_LAYOUT_LINEA + "/div[@data-analytics-value='" + nombrePago.toLowerCase() + "']/..");
	}
	
	private String getXPathRadioPago(String nombrePago) {
		if (nombrePago.contains("mercadopago")) {
			String methodRadioName = new PageCheckoutWrapper().getMethodInputValue(nombrePago);
			return ("//div[@data-custom-radio-id='" + methodRadioName + "']"); 
		}
		//TODO eliminar cuando añadan el valor klarna al atributo data-analytics-value
		if (nombrePago.compareTo("KLARNA")==0) {
			return "//div[@data-custom-radio-id='klarna']";
		}
		return (getXPathPago(nombrePago) + "//*[@data-custom-radio-id]");
	}
	
	private String getXPathTextUnderPago(String nombrePago) {
		return (getXPathPago(nombrePago) + "//div[@class='variable-card-content']");
	}

	/**
	 * @return el XPath correspondiente al link para plegar/desplegar los métodos de pago
	 */
	private String getXPathLinkFormasPagoFor(TypeActionLinkFP actionForLink) {
		switch (actionForLink) {
		case PLEGAR_PAGOS:
			return XP_LINK_FORMAS_PAGO + "//self::*[@class[contains(.,'selected')]]";
		case DESPLEGAR_PAGOS:
		default:
			return XP_LINK_FORMAS_PAGO + "//self::*[@class[not(contains(.,'selected'))]]";
		}
	}
	
	public boolean isPageUntil(int seconds) {
		return isClickableButtonFinalizarCompraUntil(seconds);
	}

	public void clickLink2DatosPagoAndWait() {
		click(XP_LINK2_DATOS_PAGO).exec();
		isPageUntil(2);
	}

	public void clickLink2DatosPagoIfVisible() {
		if (state(Visible, XP_LINK2_DATOS_PAGO).check()) {
			clickLink2DatosPagoAndWait();
		}
	}

	public void clickButtonFinalizarCompra() {
		click(XP_BUTTON_FINALIZAR_COMPRA).type(javascript).exec();
	}

	public boolean isClickableButtonFinalizarCompraUntil(int seconds) {
		return state(Clickable, XP_BUTTON_FINALIZAR_COMPRA).wait(seconds).check();
	}

	public void waitAndClickFinalizarCompra(int seconds) {
		isClickableButtonFinalizarCompraUntil(seconds);
		clickButtonFinalizarCompra();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isClickableButtonFinalizarCompraUntil(0)) {
			clickButtonFinalizarCompra();  
		}
	}

	public boolean isMetodoPagoPresent(String nombrePago) {
		return state(Present, getXPathRadioPago(nombrePago)).check();
	}

	public StateMethod getStateMethod(String nombrePago) {
		WebElement radio = getElement(getXPathRadioPago(nombrePago));
		String classRadio = radio.getAttribute("class");
		if (classRadio.contains("checked")) {
			return StateMethod.SELECTED;
		}
		return StateMethod.UNSELECTED;
	}
	
	public boolean isMethodInStateUntil(String nombrePago, StateMethod stateExpected, int seconds) {
		for (int i=0; i<seconds; i++) {
			StateMethod actualState = getStateMethod(nombrePago);
			if (actualState==stateExpected) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	public boolean isNumMetodosPagoOK(boolean isEmpl) {
		int numPagosPant = getElements(XP_LINEA_PAGO_LAYOUT_LINEA).size();
		int numPagosPais = dataTest.getPais().getListPagosForTest(app, isEmpl).size();
		return (numPagosPais == numPagosPant);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		int numPagosPant = getElements(XP_LINEA_PAGO_LAYOUT_LINEA).size();
		return (numPagosPant == numPagosExpected);
	}

	public boolean isPresentMetodosPago() {
		return state(Present, XP_LINEA_PAGO_LAYOUT_LINEA).check();
	}

	public void goToPageFromCheckoutIfNeeded() {
		int i=0;
		while (!isPageUntil(1) && i<3) {
			i+=1;
			Page1EnvioCheckoutMobil page1 = new Page1EnvioCheckoutMobil();
			if (page1.isPageUntil(0)) {
				page1.clickContinuarAndWaitPage2();
			} else {
				clickLink2DatosPagoAndWait();
			}
		}
	}
	
	/**
	 * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
	 */
	public void forceClickMetodoPagoAndWait(String nombrePago) {
		goToPageFromCheckoutIfNeeded();
		despliegaMetodosPago();
		moveToFirstMetodoPagoLine();
		
		var pageCheckoutWrapper = new PageCheckoutWrapper();
		pageCheckoutWrapper.waitUntilNoDivLoading(2);
		clickMetodoPagoAndWait(nombrePago);
		pageCheckoutWrapper.waitUntilNoDivLoading(10);
		waitLoadPage(); //For avoid StaleElementReferenceException
	}

	public void clickLinkFormasPagoFor(TypeActionLinkFP typeAction) {
		click(getXPathLinkFormasPagoFor(typeAction)).type(javascript).exec();
	}

	public void moveToFirstMetodoPagoLine() {
		moveToElement(XP_LINEA_PAGO_LAYOUT_LINEA);
	}
	
	public void despliegaMetodosPago() {
		if (areMetodosPagoPlegados()) { 
			clickLinkFormasPagoFor(TypeActionLinkFP.DESPLEGAR_PAGOS);
			metodosPagosInStateUntil(false, 3);
		}
	}

	public boolean areMetodosPagoPlegados() {
		String xpathOtrasFormasPagoPlegado = getXPathLinkFormasPagoFor(TypeActionLinkFP.DESPLEGAR_PAGOS); 
		return state(Visible, xpathOtrasFormasPagoPlegado).check();
	}

	public void metodosPagosInStateUntil(boolean plegados, int seconds) {
		int i=0;
		do {
			boolean arePlegados = areMetodosPagoPlegados();
			if (arePlegados==plegados) {
				break;
			}
			waitMillis(1000);
			i+=1;
		}
		while (i<seconds);
	}	
	
	public void clickMetodoPagoAndWait(String nombrePago) {
		clickMetodoPago(nombrePago);
		isMethodInStateUntil(nombrePago, StateMethod.SELECTED, 1);
	}

	private void clickMetodoPago(String nombrePago) {
		String xpathClickMetodoPago = getXPathRadioPago(nombrePago);
		moveToElement(xpathClickMetodoPago);
		
		//El icono queda debajo del header "Checkout" y es posible scrollar un poco más porque no funciona el moveByOffset así que falla el click
		//No es lo correcto pero en esta situación no ha quedado otra que modificar el z-index de dicho header mediante JavaScript
		hideHtmlComponent(HtmlLocator.TagName, "header", driver);

		//Si el icono sigue sin estar visible y existen secciones plegadas que pueden estar ocultándolo (como p.e. en México) buscaremos el pago en dichas secciones
		if (!getElement(xpathClickMetodoPago).isDisplayed()) {
			searchMetPagoLayoutLineaInSections(nombrePago);
		}
		click(xpathClickMetodoPago).exec();
	}

	public boolean isVisibleTextoBajoPagoUntil(Pago pago, int seconds) {
		switch (pago.getTypePago()) {
		case TARJETA_MANGO:
			return (secTMango.isVisibleUntil(seconds));
		case BILLPAY:
			return (secBillpay.isVisibleUntil(seconds));
		default:
			String xpathTexto = getXPathTextUnderPago(pago.getNombre(channel, app));
			return state(Visible, xpathTexto).wait(seconds).check();
		}
	}

	/**
	 * Revisa si el método de pago no está visible. En este caso mira si existen secciones plegadas que puedan estar ocultándolo  (como p.e. en México) 
	 * y las va desplegando hasta que encuentra el método de pago   
	 */
	private void searchMetPagoLayoutLineaInSections(String nombrePago) {
		String xpathClickMetodoPago = getXPathPago(nombrePago);
		boolean methodDisplayed = getElement(xpathClickMetodoPago).isDisplayed();
		if (!methodDisplayed) {
			List<WebElement> listSecciones = getElements(XP_SECTIONS_PAGOS_MOBIL);
			int i=0;
			while (!methodDisplayed && i<listSecciones.size()) {
				listSecciones.get(i).click();
				waitMillis(500);
				methodDisplayed = getElement(xpathClickMetodoPago).isDisplayed();
				i+=1; 
			}
		}
	}	

	public boolean isRedErrorVisible() {
		return state(Visible, XP_RED_ERROR).check();
	}

	public String getTextRedError() {
		return getElement(XP_RED_ERROR).getText();
	}

	public boolean isVisibleRadioTrjGuardada(String metodoPago)  {
		String xpathRadioTrjGuardada = getXPathRadioTarjetaGuardada(metodoPago);
		return state(Visible, xpathRadioTrjGuardada).check();
	}

	public void clickRadioTrjGuardada() {
		click(XP_RADIO_TRJ_GUARDADA).exec();
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) {
		if (state(Visible, XP_CVC_TRJ_GUARDADA).check()) {
			WebElement input = getElement(XP_CVC_TRJ_GUARDADA);
			input.clear();
			input.sendKeys(cvc);
		}
	}

	public void clickSolicitarFactura() {
		getElement(XP_LINK_SOLICITAR_FACTURA).click();
	}


	public boolean isArticulos() {
		return state(Present, XP_ARTICLE_BOLSA).check();
	}

	public void confirmarPagoFromMetodos() {
		clickButtonFinalizarCompra();
	}
	
	public boolean isMarkedQuieroFactura() {
		WebElement radio = getElement(XP_LINK_SOLICITAR_FACTURA);
		return (
			radio.getAttribute("checked")!=null &&
			radio.getAttribute("checked").contains("true"));
	}
	

	public void clickFinalizarCompraAndWait(int seconds) {
		clickButtonFinalizarCompra();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isClickableButtonFinalizarCompraUntil(0)) {
			clickButtonFinalizarCompra();  
			if (isClickableButtonFinalizarCompraUntil(1)) {
				clickButtonFinalizarCompra();  
			}
		}
		new PageRedirectPasarelaLoading().isPageNotVisibleUntil(seconds);
	}
	
	public String getPrecioTotalFromResumen(boolean normalize) {
		String precioTotal = new PageCheckoutWrapper().formateaPrecioTotal(XP_PRECIO_TOTAL);
		if (!normalize) {
			return precioTotal;
		}
		return ImporteScreen.normalizeImportFromScreen(precioTotal);
	}
	
	public String getCroaciaPrecioTotalInEuros(boolean normalize) {
		String precioTotal = new PageCheckoutWrapper()
				.formateaPrecioTotal(XP_PRECIO_TOTAL_CROATIA_EUROS);
		if (!normalize) {
			return precioTotal;
		}
		return ImporteScreen.normalizeImportFromScreen(precioTotal);		
	}
	
	public String getTextDireccionEnvioCompleta() {
		if (state(Present, XP_DIRECCION_ENVIO_TEXT).check()) {
			return (getElement(XP_DIRECCION_ENVIO_TEXT)).getText();
		}
		return "";
	}
}