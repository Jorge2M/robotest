package com.mng.robotest.test.pageobject.shop.checkout;

import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;
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

public class Page1DktopCheckout extends PageBase {
	
	private final SecDireccionEnvioDesktop secDireccionEnvio = new SecDireccionEnvioDesktop();
	private final SecStoreCredit secStoreCredit = new SecStoreCredit();
	private final SecTMango secTMango = new SecTMango();
	private final SecBillpay secBillpay = new SecBillpay();
	private final SecEps secEps = new SecEps();
	private final ModalAvisoCambioPais modalAvisoCambioPais = new ModalAvisoCambioPais();
	
	private static final String XPATH_CONF_PAGO_BUTTON_DESKTOP = "//*[@id[contains(.,'btnCheckout')]]";
	private static final String XPATH_ALMACEN_IN_NO_PRO_ENTORNOS = "//span[@class='labelTestShowAlmacenStrong']";
	private static final String XPATH_BLOCK_CODIGO_PROMO = "//div[@class[contains(.,'contenidoPromoCode')]]";
	private static final String XPATH_ERROR_PROMO = "//*[@class='labelIntroduceError']";
	private static final String XPATH_LINK_TO_VIEW_BLOCK_PROMO = "SVBody:SVResumenPromociones:FExpandirVales";
	private static final String XPATH_INPUT_PROMO = "//input[@class[contains(.,'inputVale')]]";
	private static final String XPATH_BUTTON_APLICAR_PROMO = "//input[@id[contains(.,'PromocionesVales')] and @type='submit']";
	private static final String XPATH_LINK_ELIMINAR_PROMO = "//span[@class[contains(.,'promoCodeConfirmar')]]";
	private static final String XPATH_BOTONERA_EMPL_PROMO = "//div[@class='botoneraEmpleado']";
	private static final String XPATH_BUTTON_GUARDAR_PROMO = XPATH_BOTONERA_EMPL_PROMO + "//span[@class='botonNew']";
	private static final String XPATH_INPUT_APELLIDO_PROMO_EMPL = "//input[@id[contains(.,'primerApellido')]]";
	private static final String XPATH_INPUT_DNI_PROMO_EMPL = "//input[@id[contains(.,'dniEmpleado')]]";
	private static final String XPATH_DIA_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciDia')]]";
	private static final String XPATH_MES_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciMes')]]";
	private static final String XPATH_ANY_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciAny')]]";
	private static final String XPATH_DESCUENTO_EMPLEADO = "//p[@class[contains(.,'descuento-aplicado')]]//span[@class='price-format']";

	private static final String XPATH_PRECIO_REL_ARTICLE = "//div[@class[contains(.,'precioNormal')]]";
	private static final String XPATH_PRECIO_NO_TACHADO_REL_ARTICLE = XPATH_PRECIO_REL_ARTICLE + "//self::div[not(@class[contains(.,'tachado')])]";
	private static final String XPATH_PRECIOO_SI_TACHADO_REL_ARTICLE = XPATH_PRECIO_REL_ARTICLE + "//self::div[@class[contains(.,'tachado')]]";
	private static final String XPATH_CANTIDAD_ARTICULOS = "//div[@class[contains(.,'cantidadRes')]]";
	private static final String XPATH_IMPORTE_TOTAL = "//*[@id='SVBody:SVResumenDesgloseImporteTotal:importeTotal']";
	private static final String XPATH_CLICK_DESPLIEGA_PAGOS = "//p[@class[contains(.,'anadirPago')]]";
	private static final String XPATH_IMPORTE_TOTAL_COMPRA = "//*[@id[contains(.,'importeTotalComprar')]]";
	private static final String XPATH_OTRAS_FORMAS_PAGO = "//div[@class[contains(.,'formasPago')]]";
	private static final String XPATH_RED_ERROR = "//div[@class[contains(.,'errorbocatapago')]]";
	
//	private static final String XPATH_PRECIO_SUBTOTAL = "//div[@class='subTotal']/div/div[2]/span[@class='precioNormal']";
//	private static final String XPATH_PRECIO_TOTAL = "//span[@class[contains(.,'precioTotal')]]";
	private static final String XPATH_PRECIO_SUBTOTAL = "//*[@data-testid='subTotalOrder.price']";
	private static final String XPATH_PRECIO_TOTAL = "//*[@data-testid='totalPromotions.price']";
	
	private static final String XPATH_BLOQUES_PAGO_POSIBLES = 
		"//div[@id='textoCondicionesTarjeta']//*[@id='CardName'] | " + 
		"//div[@id='textoCondicionesTarjeta' and @class='paypalInfo'] | " +
		"//div[@id='textoCondicionesTarjeta']//*[@id[contains(.,'yandex')]] | " +
		"//div[@class[contains(.,'billpayFormularioTarjeta')]] | " + 
		"//div[@id='avisoConfirmar']/div[@style='']/div[@class[contains(.,'tituloPago')]] | " +
		"//div[@class='mensajesContrarembolso'] | " +
		"//div[@class[contains(.,'cardContainerNotIntegrated')]] | " +
		"//div[@class[contains(.,'falconFormularioTarjeta')]] | " + 
		"//div[@class[contains(.,'formasPago')]]"; 
	
	private static final String TAG_METODO_PAGO = "@TagMetodoPago";
	private static final String XPATH_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG = "//div[@class[contains(.,'tarjetaGuardada')] and @data-analytics-value='" + TAG_METODO_PAGO + "']";
	private static final String XPATH_RADIO_TRJ_GUARDADA = "//input[@class[contains(.,'guardadaInput')]]";
	private static final String XPATH_CVC_TRJ_GUARDADA = "//div[@class='storedCardForm']//input[@id='cvc']";
	
	private static final String XPATH_LINK_SOLICITAR_FACTURA = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
	private static final String XPATH_FIRST_ARTICULO = "//div[@class[contains(.,'firstArticulo')]]";
	private static final String XPATH_INPUT_VENDEDOR_VOTF = "//input[@class[contains(.,'codiDependenta')]]";
	private static final String XPATH_BUTTON_ACCEPT_VENDEDOR_VOTF = "//span[@id[contains(.,'CodigoDependienta')]]";
	
	private static final String XPATH_CONTENT_CHEQUE_REGALO = "//div[@class[contains(.,'contentsChequeRegalo')]]";
	private static final String XPATH_NOMBRE_CHEQUE_REGALO = "(" + XPATH_CONTENT_CHEQUE_REGALO + "//div[@class='span3'])[1]";
	private static final String XPATH_PRECIO_CHEQUE_REGALO = XPATH_CONTENT_CHEQUE_REGALO + "//div[@class='span2']";
	private static final String XPATH_MENSAJE_CHEQUE_REGALO = XPATH_CONTENT_CHEQUE_REGALO + "//div[@class='span4']";
	
	private static final String XPATH_RADIO_PAGO_WITH_TAG = "//div[@class[contains(.,'cuadroPago')]]/input[@value='" + TAG_METODO_PAGO + "']/../input[@type='radio']";
	private static final String TEXT_KREDI_KARTI = "KREDİ KARTI";
	private static final String XPATH_PESTANYA_KREDI_KARTI = "//div[@class[contains(.,'pmGroupTitle')]]/span[text()='" + TEXT_KREDI_KARTI + "']";
	
	private static final String XPATH_METODO_PAGO = "//*[@class[contains(.,'cardBox')]]/div";
	
	private static final String TAG_REFERENCIA = "@TagRef";
	private static final String XPATH_LINEA_ARTICULO_WITH_TAG = 
		"//div[@class[contains(.,'ref')] and text()[contains(.,'" + TAG_REFERENCIA + "')]]/ancestor::div[@class[contains(.,'articuloResBody')]]";
	
	private static final String TAG_COD_VENDEDOR = "@TagCodVendedor";
	private static final String XPATH_COD_VENDEDOR_VOTF_WITH_TAG = "//form[@id[contains(.,'Dependienta')]]//span[text()[contains(.,'" + TAG_COD_VENDEDOR + "')]]";
	private static final String XPATH_TEXT_VALE_CAMPAIGN = "//span[@class='texto_banner_promociones']";
	
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
		return (XPATH_LINEA_ARTICULO_WITH_TAG.replace(TAG_REFERENCIA, referencia));
	}
	
	private String getXPathBlockTarjetaGuardada(String metodoPago) {
		return (XPATH_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG.replace(TAG_METODO_PAGO, metodoPago.toLowerCase()));
	}
	
	private String getXPathRadioTarjetaGuardada(String metodoPago) {
		String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
		return (xpathMethod + XPATH_RADIO_TRJ_GUARDADA);
	}

	public boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int maxSeconds) {
		switch (pago.getTypePago()) {
		case TMango:
			return (secTMango.isVisibleUntil(maxSeconds));
		case Billpay:
			return (secBillpay.isVisibleUntil(maxSeconds));
		default:
			String nameExpected = pago.getNombreInCheckout(channel, app).toLowerCase();
			return (
				state(Visible, XPATH_BLOQUES_PAGO_POSIBLES).wait(maxSeconds).check() &&
				getElement(XPATH_BLOQUES_PAGO_POSIBLES).getAttribute("innerHTML").toLowerCase().contains(nameExpected));
		}
	}

	private String getXPathClickMetodoPago(String metodoPago) {
		if (TEXT_KREDI_KARTI.compareTo(metodoPago)==0) {
			return XPATH_PESTANYA_KREDI_KARTI;
		}
		
		//TODO eliminar cuando añadan el "KLARNA" al value del input
		if (metodoPago.compareTo("KLARNA")==0) {
			return "//div[@class[contains(.,'cuadroPago')]]/input[@value='klarna' and @type='radio']";
		}
		String metodoPagoClick = new PageCheckoutWrapper().getMethodInputValue(metodoPago);
		return (XPATH_RADIO_PAGO_WITH_TAG.replace(TAG_METODO_PAGO, metodoPagoClick));
	}

	private String getXPathCodigoVendedorVOTF(String codigoVendedor) {
		return (XPATH_COD_VENDEDOR_VOTF_WITH_TAG.replace(TAG_COD_VENDEDOR, codigoVendedor));
	}
	
	public boolean isPageUntil(int secondsWait) {
		return (isBloqueImporteTotal(secondsWait));
	}

	public boolean isBloqueImporteTotal(int maxSeconds) {
		return state(Present, XPATH_IMPORTE_TOTAL).wait(maxSeconds).check();
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int maxSeconds) {
		return state(Present, XPATH_INPUT_APELLIDO_PROMO_EMPL).wait(maxSeconds).check();
	}

	public void inputApellidoPromoEmpl(String apellido) {
		getElement(XPATH_INPUT_APELLIDO_PROMO_EMPL).sendKeys(apellido); 
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return state(Present, XPATH_INPUT_DNI_PROMO_EMPL).check();
	}

	public void inputDNIPromoEmpl(String dni) {
		sendKeysWithRetry(dni, By.xpath(XPATH_INPUT_DNI_PROMO_EMPL), 2, driver);
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return state(Present, XPATH_DIA_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return state(Present, XPATH_MES_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return state(Present, XPATH_ANY_NACI_PROMO_EMPL).check();
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
		new Select(getElement(XPATH_DIA_NACI_PROMO_EMPL)).selectByValue(value);
	}
	
	public void selectMesNacPromoEmpl(String value) {
		new Select(getElement(XPATH_MES_NACI_PROMO_EMPL)).selectByValue(value);
	}	
	
	public void selectAnyNacPromoEmpl(String value) {
		new Select(getElement(XPATH_ANY_NACI_PROMO_EMPL)).selectByValue(value);
	}

	public void clickAplicarPromo() {
		click(XPATH_BUTTON_APLICAR_PROMO).exec();
	}

	public void clickGuardarPromo() {
		click(XPATH_BUTTON_GUARDAR_PROMO).exec();
	}

	public void clickConfirmarPago() {
		click(XPATH_CONF_PAGO_BUTTON_DESKTOP).exec();
	}

	public boolean isPresentButtonConfPago() {
		return state(Present, XPATH_CONF_PAGO_BUTTON_DESKTOP).check();
	}

	public boolean isVisibleBlockCodigoPromoUntil(int maxSeconds) {
		return state(Visible, XPATH_BLOCK_CODIGO_PROMO).wait(maxSeconds).check();
	}

	public void clickLinkToViewBlockPromo() {
		click(By.id(XPATH_LINK_TO_VIEW_BLOCK_PROMO)).exec();
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
		return state(Visible, XPATH_INPUT_PROMO).wait(maxSeconds).check();
	}

	public void clickEliminarValeIfExists() {
		if (state(Visible, XPATH_LINK_ELIMINAR_PROMO).check()) {
			click(XPATH_LINK_ELIMINAR_PROMO).exec();
		}
	}

	public void inputCodigoPromo(String codigoPromo) {
		sendKeysWithRetry(codigoPromo, By.xpath(XPATH_INPUT_PROMO), 2, driver);
	}


	public boolean checkTextValeCampaingIs(String textoCampaingVale) {
		if (state(Visible, XPATH_TEXT_VALE_CAMPAIGN).check()) {
			return getElement(XPATH_TEXT_VALE_CAMPAIGN).getText().toLowerCase().contains(textoCampaingVale.toLowerCase());
		}
		return false;
	}

	public String getImporteDescuentoEmpleado() {
		return getElement(XPATH_DESCUENTO_EMPLEADO).getText();
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int maxSeconds) {
		return state(Visible, XPATH_DESCUENTO_EMPLEADO).wait(maxSeconds).check();
	}
	
	public void clickEditDirecEnvio() {
		secDireccionEnvio.clickEditDireccion();
	}
	
	public String getTextDireccionEnvioCompleta() {
		return secDireccionEnvio.getTextDireccionEnvio();
	}

	public boolean isMetodoPagoPresent(String metodoPagoClick) {
		String xpathClickPago = getXPathClickMetodoPago(metodoPagoClick);
		return state(Present, xpathClickPago).check();
	}

	public boolean isNumMetodosPagoOK(Pais pais, boolean isEmpl) {
		int numPagosPant = getElements(XPATH_METODO_PAGO).size();
		if (app!=AppEcom.votf) {
			int numPagosPais = pais.getListPagosForTest(app, isEmpl).size();
			return (numPagosPais == numPagosPant);
		}
		
		//En el caso de VOTF no existe ningún método de pago por pantalla (por defecto es el pago vía TPV)
		return (numPagosPant == 0);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		int numPagosPant = getElements(XPATH_METODO_PAGO).size();
		return (numPagosPant == numPagosExpected);
	}

	public boolean isPresentMetodosPago() {
		return state(Present, XPATH_METODO_PAGO).check();
	}

	/**
	 * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
	 */
	public void forceClickMetodoPagoAndWait(String metodoPago, Pais pais) throws Exception {
		despliegaMetodosPago();
		PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper();
		pageCheckoutWrapper.waitUntilNoDivLoading(2);
		moveToMetodosPago();
		clickMetodoPago(pais, metodoPago);
		pageCheckoutWrapper.waitUntilNoDivLoading(10);
	}

	
	public void clickDesplegablePagos() {
		getElement(XPATH_CLICK_DESPLIEGA_PAGOS).click();
	}
	
	public void moveToMetodosPago() {
		moveToElement(XPATH_IMPORTE_TOTAL_COMPRA);
	}
	
	public void despliegaMetodosPago() throws Exception {
		if (areMetodosPagoPlegados()) {
		   clickDesplegablePagos();
		   metodosPagosInStateUntil(false, 5);
		}
	}

	public boolean areMetodosPagoPlegados() {
		return (
			state(Present, XPATH_OTRAS_FORMAS_PAGO).check() &&
			!state(Visible, XPATH_OTRAS_FORMAS_PAGO).check());
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
		click(xpathClickMetodoPago)
			.type(TypeClick.webdriver)
			.waitLink(2)
			.waitLoadPage(1).exec();
	}

	public boolean isRedErrorVisible() {
		return state(Visible, XPATH_RED_ERROR).check();
	}

	public String getTextRedError() {
		return getElement(XPATH_RED_ERROR).getText();
	}	
	
	public String getPrecioTotalFromResumen() throws Exception {
		String precioTotal = new PageCheckoutWrapper().formateaPrecioTotal(XPATH_PRECIO_TOTAL);
		return (ImporteScreen.normalizeImportFromScreen(precioTotal));
	}

	public String getAlmacenFromNoProdEntorn() {
		if (state(Present, XPATH_ALMACEN_IN_NO_PRO_ENTORNOS).check()) {
			return getElement(XPATH_ALMACEN_IN_NO_PRO_ENTORNOS).getText();
		}
		return "";
	}

	public WebElement getLineaArticle(String referencia) {
		String xpathLinArticle = getXPathLinArticle(referencia);
		if (state(Present, xpathLinArticle).check()) {
			return getElement(xpathLinArticle);
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
			PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper();
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
		return new PageCheckoutWrapper().formateaPrecioTotal(XPATH_PRECIO_SUBTOTAL);
	}
	
	private PreciosArticulo getPreciosArticuloResumen(WebElement articuloWeb) {
		PreciosArticulo precios = new PreciosArticulo();
		List<WebElement> preciosNoTachados= articuloWeb.findElements(By.xpath("." + XPATH_PRECIO_NO_TACHADO_REL_ARTICLE));
		List<WebElement> preciosSiTachados= articuloWeb.findElements(By.xpath("." + XPATH_PRECIOO_SI_TACHADO_REL_ARTICLE));
		int cantidad = Integer.valueOf(articuloWeb.findElement(By.xpath("." + XPATH_CANTIDAD_ARTICULOS)).getText());
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
		return state(Visible, xpathRadioTrjGuardada).check();
	}

	public void clickRadioTrjGuardada() {
		click(XPATH_RADIO_TRJ_GUARDADA).exec();
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) {
		if (state(Visible, XPATH_CVC_TRJ_GUARDADA).check()) {
			WebElement input = getElement(XPATH_CVC_TRJ_GUARDADA);
			input.clear();
			input.sendKeys(cvc);
		}
	}

	public void clickSolicitarFactura() {
		getElement(XPATH_LINK_SOLICITAR_FACTURA).click();
	}
	
	public boolean isMarkedQuieroFactura() {
		WebElement radio = getElement(XPATH_LINK_SOLICITAR_FACTURA);
		if (radio.getAttribute("checked")!=null && radio.getAttribute("checked").contains("true")) {
			return true;
		}
		return false;
	}
	
	public boolean isArticulos() {
		return state(Present, XPATH_FIRST_ARTICULO).check();
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
			state(Visible, XPATH_ERROR_PROMO).wait(maxSeconds).check() &&
			getElement(XPATH_ERROR_PROMO).getAttribute("style").contains("color: red"));
	}

	public void inputVendedorVOTF(String codigoVendedor) {
		getElement(XPATH_INPUT_VENDEDOR_VOTF).clear();
		getElement(XPATH_INPUT_VENDEDOR_VOTF).sendKeys(codigoVendedor);
	}

	public void acceptInputVendedorVOTF() {
		click(XPATH_BUTTON_ACCEPT_VENDEDOR_VOTF).exec();
	}

	public boolean isVisibleInputVendedorVOTF(int maxSeconds) {
		return state(Visible, XPATH_INPUT_VENDEDOR_VOTF).wait(maxSeconds).check();
	}

	public boolean isVisibleCodigoVendedorVOTF(String codigoVendedor) {
		String xpathVendedor = getXPathCodigoVendedorVOTF(codigoVendedor);
		return state(Visible, xpathVendedor).check();
	}

	public boolean isDataChequeRegalo(ChequeRegalo chequeRegalo) {
		if (!getElement(XPATH_NOMBRE_CHEQUE_REGALO).getText().contains(chequeRegalo.getNombre()) ||
			!getElement(XPATH_NOMBRE_CHEQUE_REGALO).getText().contains(chequeRegalo.getApellidos()) ||
			!getElement(XPATH_PRECIO_CHEQUE_REGALO).getText().contains(chequeRegalo.getImporte().toString().replace("EURO_", "")) ||
			!getElement(XPATH_MENSAJE_CHEQUE_REGALO).getText().contains(chequeRegalo.getMensaje())) {
			return false;
		}
		return true;
	}

}