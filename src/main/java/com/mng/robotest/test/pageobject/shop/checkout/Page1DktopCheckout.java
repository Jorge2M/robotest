package com.mng.robotest.test.pageobject.shop.checkout;

import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Descuento;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class Page1DktopCheckout extends PageObjTM {
	
	private final SecStoreCredit secStoreCredit;
	private final SecTMango secTMango;
	private final SecBillpay secBillpay;
	private final SecEps secEps;
	private final ModalAvisoCambioPais modalAvisoCambioPais;
	
	private final Channel channel;
	private final AppEcom app;
	
	private static final String XPathConfPagoButtonDesktop = "//*[@id[contains(.,'btnCheckout')]]";
	private static final String XPathAlmacenInNoProEntorns = "//span[@class='labelTestShowAlmacenStrong']";
	private static final String XPathBlockCodigoPromo = "//div[@class[contains(.,'contenidoPromoCode')]]";
	private static final String XPathErrorPromo = "//*[@class='labelIntroduceError']";
	private static final String XPathLinkToViewBlockPromo = "SVBody:SVResumenPromociones:FExpandirVales";
	private static final String xpathInputPromo = "//input[@class[contains(.,'inputVale')]]";
	private static final String XPathButtonAplicarPromo = "//input[@id[contains(.,'PromocionesVales')] and @type='submit']";
	private static final String XPathLinkEliminarPromo = "//span[@class[contains(.,'promoCodeConfirmar')]]";
	private static final String XPathBotoneraEmplPromo = "//div[@class='botoneraEmpleado']";
	private static final String XPathButtonCancelarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew claro']";
	private static final String XPathButtonGuardarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew']";
	private static final String XPathInputApellidoPromoEmpl = "//input[@id[contains(.,'primerApellido')]]";
	private static final String XPathInputDNIPromoEmpl = "//input[@id[contains(.,'dniEmpleado')]]";
	private static final String XPathDiaNaciPromoEmpl = "//select[@id[contains(.,'naciDia')]]";
	private static final String XPathMesNaciPromoEmpl = "//select[@id[contains(.,'naciMes')]]";
	private static final String XPathAnyNaciPromoEmpl = "//select[@id[contains(.,'naciAny')]]";
	private static final String XPathAceptarPromoEmpl = "//div[@class[contains(.,'botoneraEmpleado')]]/span[@class='botonNew']";
	private static final String XPathDescuentoEmpleado = "//p[@class[contains(.,'descuento-aplicado')]]//span[@class='price-format']";

	private static final String XPathPrecioRelArticle = "//div[@class[contains(.,'precioNormal')]]";
	private static final String XPathPrecioNoTachadoRelArticle = XPathPrecioRelArticle + "//self::div[not(@class[contains(.,'tachado')])]";
	private static final String XPathPrecioSiTachadoRelArticle = XPathPrecioRelArticle + "//self::div[@class[contains(.,'tachado')]]";
	
	private static final String XPathCantidadArticulos = "//div[@class[contains(.,'cantidadRes')]]";
	private static final String XPathImporteTotal = "//*[@id='SVBody:SVResumenDesgloseImporteTotal:importeTotal']";
	private static final String XPathClickDespliegaPagos = "//p[@class[contains(.,'anadirPago')]]";
	private static final String XPathImporteTotalCompra = "//*[@id[contains(.,'importeTotalComprar')]]";
	private static final String XPathOtrasFormasPago = "//div[@class[contains(.,'formasPago')]]";
	private static final String XPathRedError = "//div[@class[contains(.,'errorbocatapago')]]";
	private static final String XPathNombreEnvio = "//div[@class[contains(.,'nombreEnvio')]]"; 
	private static final String XPathDirecionEnvio = "//div[@class[contains(.,'direccionEnvio')]]";
	private static final String XPathPoblacionEnvio = "//div[@class[contains(.,'poblacionEnvio')]]";
	private static final String XPathProvinciaEnvio = "//div[@class[contains(.,'provinciaEnvio')]]";
	private static final String XPathPrecioSubTotal = "//div[@class='subTotal']/div/div[2]/span[@class='precioNormal']";
	private static final String XPathPrecioTotal = "//span[@class[contains(.,'precioTotal')]]";
	private static final String XPathBloquesPagoPosibles = 
		"//div[@id='textoCondicionesTarjeta']//*[@id='CardName'] | " + 
		"//div[@id='textoCondicionesTarjeta' and @class='paypalInfo'] | " +
		"//div[@id='textoCondicionesTarjeta']//*[@id[contains(.,'yandex')]] | " +
		"//div[@class[contains(.,'billpayFormularioTarjeta')]] | " + 
		"//div[@id='avisoConfirmar']/div[@style='']/div[@class[contains(.,'tituloPago')]] | " +
		"//div[@class='mensajesContrarembolso'] | " +
		"//div[@class[contains(.,'cardContainerNotIntegrated')]] | " +
		"//div[@class[contains(.,'falconFormularioTarjeta')]] | " + 
		"//div[@class[contains(.,'formasPago')]]"; 
	
	private static final String tagMetodoPago = "@TagMetodoPago";
	private static final String XPathBlockTarjetaGuardadaPagoWithTag = "//div[@class[contains(.,'tarjetaGuardada')] and @data-analytics-value='" + tagMetodoPago + "']";
	private static final String XPathRadioTrjGuardada = "//input[@class[contains(.,'guardadaInput')]]";
	private static final String XPathCvcTrjGuardada = "//div[@class='storedCardForm']//input[@id='cvc']";
	
	private static final String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
	private static final String XPathLinkEditDirecEnvio = "//span[@class[contains(.,'cambiarDatosEnvio')]]";
	private static final String XPathFirstArticulo = "//div[@class[contains(.,'firstArticulo')]]";
	private static final String XPathInputVendedorVOTF = "//input[@class[contains(.,'codiDependenta')]]";
	private static final String XPathButtonAcceptVendedorVOTF = "//span[@id[contains(.,'CodigoDependienta')]]";
	
	private static final String XPathContentChequeRegalo = "//div[@class[contains(.,'contentsChequeRegalo')]]";
	private static final String XPathNombreChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[1]";
	private static final String XPathEmailChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[2]";
	private static final String XPathPrecioChequeRegalo = XPathContentChequeRegalo + "//div[@class='span2']";
	private static final String XPathMensajeChequeRegalo = XPathContentChequeRegalo + "//div[@class='span4']";
	
	private static final String TagMetodoPago = "@TagMetodoPago";
	private static final String XPathRadioPagoWithTag = "//div[@class[contains(.,'cuadroPago')]]/input[@value='" + TagMetodoPago + "']/../input[@type='radio']";
	private static final String TextKrediKarti = "KREDİ KARTI";
	private static final String XPathPestanyaKrediKarti = "//div[@class[contains(.,'pmGroupTitle')]]/span[text()='" + TextKrediKarti + "']";
	
	private static final String XPathMetodoPago = "//*[@class[contains(.,'cardBox')]]/div";
	
	private static final String tagReferencia = "@TagRef";
	private static final String XPathLineaArticuloWithTag = 
		"//div[@class[contains(.,'ref')] and text()[contains(.,'" + tagReferencia + "')]]/ancestor::div[@class[contains(.,'articuloResBody')]]";
	
	private static final String tagCodVendedor = "@TagCodVendedor";
	private static final String XPathCodVendedorVotfWithTag = "//form[@id[contains(.,'Dependienta')]]//span[text()[contains(.,'" + tagCodVendedor + "')]]";
	
	public Page1DktopCheckout(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
		this.secStoreCredit = new SecStoreCredit(driver);
		this.secTMango = new SecTMango(channel, app, driver);
		this.secBillpay = new SecBillpay(channel, driver);
		this.secEps = new SecEps(driver);
		this.modalAvisoCambioPais = new ModalAvisoCambioPais(driver);
	}
	
	public SecStoreCredit getSecStoreCredit() {
		return secStoreCredit;
	}

	public SecTMango getSecTMango() {
		return secTMango;
	}

	public SecBillpay getSecBillpay() {
		return secBillpay;
	}

	public SecEps getSecEps() {
		return secEps;
	}
	
	public ModalAvisoCambioPais getModalAvisoCambioPais() {
		return modalAvisoCambioPais;
	}
	
	private String getXPathLinArticle(String referencia) {
		return (XPathLineaArticuloWithTag.replace(tagReferencia, referencia));
	}
	
	private String getXPathBlockTarjetaGuardada(String metodoPago) {
		return (XPathBlockTarjetaGuardadaPagoWithTag.replace(tagMetodoPago, metodoPago.toLowerCase()));
	}
	
	private String getXPathRadioTarjetaGuardada(String metodoPago) {
		String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
		return (xpathMethod + XPathRadioTrjGuardada);
	}

	@SuppressWarnings("static-access")
	public boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int maxSeconds) {
		switch (pago.getTypePago()) {
		case TMango:
			return (secTMango.isVisibleUntil(maxSeconds));
		case Billpay:
			return (secBillpay.isVisibleUntil(maxSeconds));
		default:
			String nameExpected = pago.getNombreInCheckout(Channel.desktop, app).toLowerCase();
			return (
				state(Visible, By.xpath(XPathBloquesPagoPosibles), driver).wait(maxSeconds).check() &&
				driver.findElement(By.xpath(XPathBloquesPagoPosibles)).getAttribute("innerHTML").toLowerCase().contains(nameExpected)
					);
		}
	}

	private String getXPathClickMetodoPago(String metodoPago) {
		if (TextKrediKarti.compareTo(metodoPago)==0) {
			return XPathPestanyaKrediKarti;
		}
		
		//TODO eliminar cuando añadan el "KLARNA" al value del input
		if (metodoPago.compareTo("KLARNA")==0) {
			return "//div[@class[contains(.,'cuadroPago')]]/input[@value='klarna' and @type='radio']";
		}
		String metodoPagoClick = (new PageCheckoutWrapper(channel, app, driver)).getMethodInputValue(metodoPago);
		return (XPathRadioPagoWithTag.replace(TagMetodoPago, metodoPagoClick));
	}

	private String getXPathCodigoVendedorVOTF(String codigoVendedor) {
		return (XPathCodVendedorVotfWithTag.replace(tagCodVendedor, codigoVendedor));
	}
	
	public boolean isPageUntil(int secondsWait) {
		return (isBloqueImporteTotal(driver, secondsWait));
	}

	public boolean isBloqueImporteTotal(WebDriver driver, int maxSeconds) {
		return (state(Present, By.xpath(XPathImporteTotal)).wait(maxSeconds).check());
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathInputApellidoPromoEmpl)).wait(maxSeconds).check());
	}

	public void inputApellidoPromoEmpl(String apellido) {
		driver.findElement(By.xpath(XPathInputApellidoPromoEmpl)).sendKeys(apellido); 
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return (state(Present, By.xpath(XPathInputDNIPromoEmpl)).check());
	}

	public void inputDNIPromoEmpl(String dni) {
		sendKeysWithRetry(dni, By.xpath(XPathInputDNIPromoEmpl), 2, driver);
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathDiaNaciPromoEmpl)).check());
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathMesNaciPromoEmpl)).check());
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return (state(Present, By.xpath(XPathAnyNaciPromoEmpl)).check());
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
		selectDiaNacPromoEmpl(fechaTokenizada.nextToken());
		selectMesNacPromoEmpl(fechaTokenizada.nextToken());
		selectAnyNacPromoEmpl(fechaTokenizada.nextToken());
	}
	
	public void selectDiaNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathDiaNaciPromoEmpl))).selectByValue(value);
	}
	
	public void selectMesNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathMesNaciPromoEmpl))).selectByValue(value);
	}	
	
	public void selectAnyNacPromoEmpl(String value) {
		new Select(driver.findElement(By.xpath(XPathAnyNaciPromoEmpl))).selectByValue(value);
	}

	public void clickAplicarPromo() {
		click(By.xpath(XPathButtonAplicarPromo)).exec();
	}

	public void clickGuardarPromo() {
		click(By.xpath(XPathButtonGuardarPromo)).exec();
	}

	public void clickConfirmarPago() {
		click(By.xpath(XPathConfPagoButtonDesktop)).exec();
	}

	public boolean isPresentButtonConfPago() {
		return (state(Present, By.xpath(XPathConfPagoButtonDesktop)).check());
	}

	public boolean isVisibleBlockCodigoPromoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathBlockCodigoPromo)).wait(maxSeconds).check());
	}

	public void clickLinkToViewBlockPromo() {
		click(By.id(XPathLinkToViewBlockPromo)).exec();
	}

	public void showInputCodigoPromoAndAccept(String codigoPromo) throws Exception {
		if (!isVisibleBlockCodigoPromoUntil(0)) {
			clickLinkToViewBlockPromo();
		}
		if (isVisibleBlockCodigoPromoUntil(5)) {
			inputCodigoPromo(codigoPromo);
			clickAplicarPromo();
		}
	}

	public boolean isVisibleInputCodigoPromoUntil(int maxSeconds) throws Exception {
		return (state(Visible, By.xpath(xpathInputPromo)).wait(maxSeconds).check());
	}

	public void clickEliminarValeIfExists() {
		By byEliminar = By.xpath(XPathLinkEliminarPromo);
		if (state(Visible, byEliminar).check()) {
			click(byEliminar).exec();
		}
	}

	public void inputCodigoPromo(String codigoPromo) {
		sendKeysWithRetry(codigoPromo, By.xpath(xpathInputPromo), 2, driver);
	}

	private static final String xpathTextValeCampaign = "//span[@class='texto_banner_promociones']";
	public boolean checkTextValeCampaingIs(String textoCampaingVale) {
		if (state(Visible, By.xpath(xpathTextValeCampaign)).check()) {
			return (driver.findElement(By.xpath(xpathTextValeCampaign)).getText().toLowerCase().contains(textoCampaingVale.toLowerCase()));
		}
		return false;
	}

	public String getImporteDescuentoEmpleado() {
		return (driver.findElement(By.xpath(XPathDescuentoEmpleado)).getText());
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathDescuentoEmpleado)).wait(maxSeconds).check());
	}

	public boolean isMetodoPagoPresent(String metodoPagoClick) {
		String xpathClickPago = getXPathClickMetodoPago(metodoPagoClick);
		return (state(Present, By.xpath(xpathClickPago), driver).check());
	}

	public boolean isNumMetodosPagoOK(Pais pais, boolean isEmpl) {
		int numPagosPant = driver.findElements(By.xpath(XPathMetodoPago)).size();
		if (app!=AppEcom.votf) {
			int numPagosPais = pais.getListPagosForTest(app, isEmpl).size();
			return (numPagosPais == numPagosPant);
		}
		
		//En el caso de VOTF no existe ningún método de pago por pantalla (por defecto es el pago vía TPV)
		return (numPagosPant == 0);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		int numPagosPant = driver.findElements(By.xpath(XPathMetodoPago)).size();
		return (numPagosPant == numPagosExpected);
	}

	public boolean isPresentMetodosPago() {
		return (state(Present, By.xpath(XPathMetodoPago)).check());
	}

	/**
	 * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
	 */
	public void forceClickMetodoPagoAndWait(String metodoPago, Pais pais) throws Exception {
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
		despliegaMetodosPago();
		pageCheckoutWrapper.waitUntilNoDivLoading(2);
		moveToMetodosPago();
		clickMetodoPago(pais, metodoPago);
		pageCheckoutWrapper.waitUntilNoDivLoading(10);
	}

	
	public void clickDesplegablePagos() {
		driver.findElement(By.xpath(XPathClickDespliegaPagos)).click();
	}
	
	public void moveToMetodosPago() {
		moveToElement(By.xpath(XPathImporteTotalCompra), driver);
	}
	
	public void despliegaMetodosPago() throws Exception {
		if (areMetodosPagoPlegados()) {
		   clickDesplegablePagos();
		   metodosPagosInStateUntil(false, 5);
		}
	}

	public boolean areMetodosPagoPlegados() {
		return (
			state(Present, By.xpath(XPathOtrasFormasPago)).check() &&
			!state(Visible, By.xpath(XPathOtrasFormasPago)).check());
	}

	private void metodosPagosInStateUntil(boolean plegados, int seconds) throws Exception {
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

	public void clickMetodoPago(Pais pais, String metodoPago) {
		String xpathClickMetodoPago = getXPathClickMetodoPago(metodoPago);
		click(By.xpath(xpathClickMetodoPago))
			.type(TypeClick.webdriver)
			.waitLink(2)
			.waitLoadPage(1).exec();
	}

	public boolean isRedErrorVisible() {
		return (state(Visible, By.xpath(XPathRedError)).check());
	}

	public String getTextRedError() {
		return (driver.findElement(By.xpath(XPathRedError)).getText());
	}	
	
	public String getTextNombreEnvio() {
		return (driver.findElement(By.xpath(XPathNombreEnvio)).getText());
	}
	
	public String getTextDireccionEnvio() {
		return (driver.findElement(By.xpath(XPathDirecionEnvio)).getText());
	}
	
	public String getTextPoblacionEnvio() {
		return (driver.findElement(By.xpath(XPathPoblacionEnvio)).getText());
	}	

	public String getTextDireccionEnvioCompleta() {
		waitForPageLoaded(driver); //For avoid StaleElementReferenceException
		if (state(Present, By.xpath(XPathDirecionEnvio)).check()) {
			return (
				driver.findElement(By.xpath(XPathDirecionEnvio)).getText() + ", " +
				driver.findElement(By.xpath(XPathPoblacionEnvio)).getText() + ", " +
				driver.findElement(By.xpath(XPathProvinciaEnvio)).getText());
		}
		return "";
	}

	public String getPrecioTotalFromResumen() throws Exception {
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
		String precioTotal = pageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal);
		return (ImporteScreen.normalizeImportFromScreen(precioTotal));
	}

	public String getAlmacenFromNoProdEntorn() {
		if (state(Present, By.xpath(XPathAlmacenInNoProEntorns)).check()) {
			return (driver.findElement(By.xpath(XPathAlmacenInNoProEntorns)).getText());
		}
		return "";
	}

	public WebElement getLineaArticle(String referencia) {
		By byLinArticle = By.xpath(getXPathLinArticle(referencia));
		if (state(Present, byLinArticle).check()) {
			return (driver.findElement(byLinArticle));
		}
		return null;
	}

	public boolean validateArticlesAndImport(DataBag dataBag) throws Exception {
		for (ArticuloScreen articulo : dataBag.getListArticlesTypeViewInBolsa()) {
			WebElement lineaArticulo = getLineaArticle(articulo.getReferencia());
			if (lineaArticulo==null) {
				return false;
			}
			
			PreciosArticulo preciosArticuloScreen = getPreciosArticuloResumen(lineaArticulo);
			if (!validateArticleImport(preciosArticuloScreen, articulo.getPrecio())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean validateArticleImport(PreciosArticulo preciosScreen, String precioArticulo) {
		return (preciosScreen.definitivo == ImporteScreen.getFloatFromImporteMangoScreen(precioArticulo));
	}
	
	public boolean validateArticlesAndDiscount(DataBag dataBag, Descuento descuento) throws Exception {
		for (ArticuloScreen articulo : dataBag.getListArticlesTypeViewInBolsa()) {
			WebElement lineaArticulo = getLineaArticle(articulo.getReferencia());
			if (lineaArticulo==null) {
				return false;
			}
			
			PreciosArticulo preciosArticuloScreen = getPreciosArticuloResumen(lineaArticulo);
			PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
			if (articulo.getValePais()!=null) {
				if (!pageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuento)) {
					return false;
				}
			} else {
				Descuento descuentoZero = new Descuento(0);
				if (!pageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuentoZero)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public String getPrecioSubTotalFromResumen() throws Exception {
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app, driver);
		return pageCheckoutWrapper.formateaPrecioTotal(XPathPrecioSubTotal);
	}
	
	private PreciosArticulo getPreciosArticuloResumen(WebElement articuloWeb) {
		PreciosArticulo precios = new PreciosArticulo();
		List<WebElement> preciosNoTachados= articuloWeb.findElements(By.xpath("." + XPathPrecioNoTachadoRelArticle));
		List<WebElement> preciosSiTachados= articuloWeb.findElements(By.xpath("." + XPathPrecioSiTachadoRelArticle));
		int cantidad = Integer.valueOf(articuloWeb.findElement(By.xpath("." + XPathCantidadArticulos)).getText());
		for (WebElement precioNoTachado : preciosNoTachados) {
			precios.definitivo = getFloatFromImporteScreen(precioNoTachado) / cantidad; 
			if (precios.definitivo!=0) {
				precios.original = precios.definitivo;
				break;
			}
		}
		
		for (WebElement precioSiTachado : preciosSiTachados) {
			float precio = getFloatFromImporteScreen(precioSiTachado) / cantidad; 
			if (precio!=0) {
				precios.ultimaRebaja = precio;
				if (precios.original==0 || precios.original==precios.definitivo) {
					precios.original = getFloatFromImporteScreen(precioSiTachado) / cantidad;
				}
			}
		}
		
		return (precios);
	}
	
	private float getFloatFromImporteScreen(WebElement importeWeb) {
		String precioArticulo = "";
		
		// Iteramos sobre los elementos correspondientes a la parte entera y decimal
		ListIterator<WebElement> itArticuloEntero = null;
		ListIterator<WebElement> itArticuloDecimal = null;
		itArticuloEntero = importeWeb.findElements(By.className("entero")).listIterator();
		while (itArticuloEntero != null && itArticuloEntero.hasNext()) {
			WebElement articuloEntero = itArticuloEntero.next();
			precioArticulo += articuloEntero.getText();
		}

		itArticuloDecimal = importeWeb.findElements(By.className("decimal")).listIterator();
		while (itArticuloDecimal != null && itArticuloDecimal.hasNext()) {
			WebElement articuloDecimal = itArticuloDecimal.next();
			precioArticulo += articuloDecimal.getText();
		}

		return ImporteScreen.getFloatFromImporteMangoScreen(precioArticulo);
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
	
	public boolean isMarkedQuieroFactura() {
		WebElement radio = driver.findElement(By.xpath(XPathLinkSolicitarFactura));
		if (radio.getAttribute("checked")!=null && radio.getAttribute("checked").contains("true")) {
			return true;
		}
		return false;
	}
	
	public void clickEditDirecEnvio() throws Exception {
		waitForPageLoaded(driver); //For avoid StaleElementReferenceException
		driver.findElement(By.xpath(XPathLinkEditDirecEnvio)).click();
	}

	public boolean isArticulos() {
		return (state(Present, By.xpath(XPathFirstArticulo)).check());
	}

	/**
	 * Función que, partiendo de la página con los métodos de pago del checkout, realiza la confirmación del pago
	 * (Simplemente selecciona "Confirmar pago")
	 */
	public void confirmarPagoFromMetodos() throws Exception {
		clickConfirmarPago();
	}

	public boolean isVisibleErrorRojoInputPromoUntil(int maxSeconds) {
		return (
			state(Visible, By.xpath(XPathErrorPromo)).wait(maxSeconds).check() &&
			driver.findElement(By.xpath(XPathErrorPromo)).getAttribute("style").contains("color: red"));
	}

	public void inputVendedorVOTF(String codigoVendedor) {
		driver.findElement(By.xpath(XPathInputVendedorVOTF)).clear();
		driver.findElement(By.xpath(XPathInputVendedorVOTF)).sendKeys(codigoVendedor);
	}

	public void acceptInputVendedorVOTF() {
		click(By.xpath(XPathButtonAcceptVendedorVOTF)).exec();
	}

	public boolean isVisibleInputVendedorVOTF(int maxSeconds) {
		return (state(Visible, By.xpath(XPathInputVendedorVOTF)).wait(maxSeconds).check());
	}

	public boolean isVisibleCodigoVendedorVOTF(String codigoVendedor) {
		String xpathVendedor = getXPathCodigoVendedorVOTF(codigoVendedor);
		return (state(Visible, By.xpath(xpathVendedor)).check());
	}

	public boolean isDataChequeRegalo(ChequeRegalo chequeRegalo) {
		if (!driver.findElement(By.xpath(XPathNombreChequeRegalo)).getText().contains(chequeRegalo.getNombre()) ||
			!driver.findElement(By.xpath(XPathNombreChequeRegalo)).getText().contains(chequeRegalo.getApellidos()) ||
			!driver.findElement(By.xpath(XPathPrecioChequeRegalo)).getText().contains(chequeRegalo.getImporte().toString().replace("euro", "")) ||
			!driver.findElement(By.xpath(XPathMensajeChequeRegalo)).getText().contains(chequeRegalo.getMensaje())) {
			return false;
		}
		return true;
	}

}