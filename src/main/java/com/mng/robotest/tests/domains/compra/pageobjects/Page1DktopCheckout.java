package com.mng.robotest.tests.domains.compra.pageobjects;

import java.util.ListIterator;
import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.compra.payments.billpay.pageobjects.SecBillpay;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.SecEps;
import com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.SecTMango;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.data.Descuento;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class Page1DktopCheckout extends PageBase {
	
	private final SecDireccionEnvioDesktop secDireccionEnvio = new SecDireccionEnvioDesktop();
	private final SecTMango secTMango = new SecTMango();
	private final SecBillpay secBillpay = new SecBillpay();
	private final SecEps secEps = new SecEps();
	private final ModalAvisoCambioPais modalAvisoCambioPais = new ModalAvisoCambioPais();
	
	private static final String XP_CONF_PAGO_BUTTON_DESKTOP = "//*[@id[contains(.,'btnCheckout')]]";
	private static final String XP_ALMACEN_IN_NO_PRO_ENTORNOS = "//span[@class='labelTestShowAlmacenStrong']";
	private static final String XP_BLOCK_CODIGO_PROMO = "//div[@class[contains(.,'contenidoPromoCode')]]";
	private static final String XP_ERROR_PROMO = "//*[@class='labelIntroduceError']";
	private static final String XP_LINK_TO_VIEW_BLOCK_PROMO = "SVBody:SVResumenPromociones:FExpandirVales";
	private static final String XP_INPUT_PROMO = "//input[@class[contains(.,'inputVale')]]";
	private static final String XP_BUTTON_APLICAR_PROMO = "//input[@id[contains(.,'PromocionesVales')] and @type='submit']";
	private static final String XP_LINK_ELIMINAR_PROMO = "//span[@class[contains(.,'promoCodeConfirmar')]]";
	private static final String XP_BOTONERA_EMPL_PROMO = "//div[@class='botoneraEmpleado']";
	private static final String XP_BUTTON_GUARDAR_PROMO = XP_BOTONERA_EMPL_PROMO + "//span[@class='botonNew']";
	private static final String XP_INPUT_APELLIDO_PROMO_EMPL = "//input[@id[contains(.,'primerApellido')]]";
	private static final String XP_INPUT_DNI_PROMO_EMPL = "//input[@id[contains(.,'dniEmpleado')]]";
	private static final String XP_DIA_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciDia')]]";
	private static final String XP_MES_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciMes')]]";
	private static final String XP_ANY_NACI_PROMO_EMPL = "//select[@id[contains(.,'naciAny')]]";
	private static final String XP_DESCUENTO_EMPLEADO = "//p[@class[contains(.,'descuento-aplicado')]]//span[@class='price-format']";

	private static final String XP_PRECIO_REL_ARTICLE = "//div[@class[contains(.,'precioNormal')]]";
	private static final String XP_PRECIO_NO_TACHADO_REL_ARTICLE = XP_PRECIO_REL_ARTICLE + "//self::div[not(@class[contains(.,'tachado')])]";
	private static final String XP_PRECIOO_SI_TACHADO_REL_ARTICLE = XP_PRECIO_REL_ARTICLE + "//self::div[@class[contains(.,'tachado')]]";
	private static final String XP_CANTIDAD_ARTICULOS = "//div[@class[contains(.,'cantidadRes')]]";
	private static final String XP_IMPORTE_TOTAL = "//*[@id='SVBody:SVResumenDesgloseImporteTotal:importeTotal']";
	private static final String XP_CLICK_DESPLIEGA_PAGOS = "//p[@class[contains(.,'anadirPago')]]";
	private static final String XP_IMPORTE_TOTAL_COMPRA = "//*[@id[contains(.,'importeTotalComprar')]]";
	private static final String XP_OTRAS_FORMAS_PAGO = "//div[@class[contains(.,'formasPago')]]";
	private static final String XP_RED_ERROR = "//div[@class[contains(.,'errorbocatapago')]]";
	
	private static final String XP_DIRECCION_FACTURACION = "//*[@id[contains(.,'datosCliente')]]";
	//TODO React solicitar data-testid
	private static final String XP_LINK_DESCARTAR_FACTURA = XP_DIRECCION_FACTURACION + "//*[text()='Descartar factura']"; 
	
	private static final String XP_PRECIO_SUBTOTAL = "//*[@data-testid='subTotalOrder.price']";
	private static final String XP_PRECIO_TOTAL = "//*[@data-testid='totalPromotions.price']";
	private static final String XP_PRECIO_TOTAL_CROATIA_EUROS = "//*[@data-testid='subTotalOrder.additionalPrice.1']";
	
	private static final String XP_BLOQUES_PAGO_POSIBLES = 
		"//div[@id='textoCondicionesTarjeta']//*[@id='CardName'] | " + 
		"//div[@id='textoCondicionesTarjeta' and @class='paypalInfo'] | " +
		"//div[@id='textoCondicionesTarjeta']//*[@id[contains(.,'yandex')]] | " +
		"//div[@class[contains(.,'billpayFormularioTarjeta')]] | " + 
		"//div[@id='avisoConfirmar']/div[@style='']/div[@class[contains(.,'tituloPago')]] | " +
		"//div[@class='mensajesContrarembolso'] | " +
		"//div[@class[contains(.,'cardContainerNotIntegrated')]] | " +
		"//div[@class[contains(.,'falconFormularioTarjeta')]] | " + 
		XP_OTRAS_FORMAS_PAGO; 
	
	private static final String TAG_METODO_PAGO = "@TagMetodoPago";
	private static final String XP_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG = "//div[@class[contains(.,'tarjetaGuardada')] and @data-analytics-value='" + TAG_METODO_PAGO + "']";
	private static final String XP_RADIO_TRJ_GUARDADA = "//input[@class[contains(.,'guardadaInput')]]";
	private static final String XP_CVC_TRJ_GUARDADA = "//div[@class='storedCardForm']//input[@id='cvc']";
	
	private static final String XP_LINK_SOLICITAR_FACTURA = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
	private static final String XP_FIRST_ARTICULO = "//div[@class[contains(.,'firstArticulo')]]";
	private static final String XP_INPUT_VENDEDOR_VOTF = "//input[@class[contains(.,'codiDependenta')]]";
	private static final String XP_BUTTON_ACCEPT_VENDEDOR_VOTF = "//span[@id[contains(.,'CodigoDependienta')]]";
	
	private static final String XP_CONTENT_CHEQUE_REGALO = "//div[@class[contains(.,'contentsChequeRegalo')]]";
	private static final String XP_NOMBRE_CHEQUE_REGALO = "(" + XP_CONTENT_CHEQUE_REGALO + "//div[@class='span3'])[1]";
	private static final String XP_PRECIO_CHEQUE_REGALO = XP_CONTENT_CHEQUE_REGALO + "//div[@class='span2']";
	private static final String XP_MENSAJE_CHEQUE_REGALO = XP_CONTENT_CHEQUE_REGALO + "//div[@class='span4']";
	
	private static final String XP_RADIO_PAGO_WITH_TAG = "//*[@class[contains(.,'cuadroPago')]]//input[@value='" + TAG_METODO_PAGO + "']/../input[@type='radio']";
	private static final String TEXT_KREDI_KARTI = "KREDİ KARTI";
	private static final String XP_PESTANYA_KREDI_KARTI = "//div[@class[contains(.,'pmGroupTitle')]]/span[text()='" + TEXT_KREDI_KARTI + "']";
	
	private static final String XP_METODO_PAGO = "//*[@class[contains(.,'cardBox')]]";
	
	private static final String TAG_REFERENCIA = "@TagRef";
	private static final String XP_LINEA_ARTICULO_WITH_TAG = 
		"//div[@class[contains(.,'ref')] and text()[contains(.,'" + TAG_REFERENCIA + "')]]/ancestor::div[@class[contains(.,'articuloResBody')]]";
	
	private static final String TAG_COD_VENDEDOR = "@TagCodVendedor";
	private static final String XP_COD_VENDEDOR_VOTF_WITH_TAG = "//form[@id[contains(.,'Dependienta')]]//span[text()[contains(.,'" + TAG_COD_VENDEDOR + "')]]";
	private static final String XP_TEXT_VALE_CAMPAIGN = "//span[@class='texto_banner_promociones']";

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
		return (XP_LINEA_ARTICULO_WITH_TAG.replace(TAG_REFERENCIA, referencia));
	}
	
	private String getXPathBlockTarjetaGuardada(String metodoPago) {
		return (XP_BLOCK_TARJETA_GUARDADA_PAGO_WITH_TAG.replace(TAG_METODO_PAGO, metodoPago.toLowerCase()));
	}
	
	private String getXPathRadioTarjetaGuardada(String metodoPago) {
		String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
		return (xpathMethod + XP_RADIO_TRJ_GUARDADA);
	}

	public boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int seconds) {
		switch (pago.getTypePago()) {
		case TARJETA_MANGO:
			return (secTMango.isVisibleUntil(seconds));
		case BILLPAY:
			return (secBillpay.isVisibleUntil(seconds));
		default:
			String nameExpected = pago.getNombreInCheckout(channel, app).toLowerCase();
			return (
				state(Visible, XP_BLOQUES_PAGO_POSIBLES).wait(seconds).check() &&
				getElement(XP_BLOQUES_PAGO_POSIBLES).getAttribute("innerHTML").toLowerCase().contains(nameExpected));
		}
	}

	private String getXPathClickMetodoPago(String metodoPago) {
		if (TEXT_KREDI_KARTI.compareTo(metodoPago)==0) {
			return XP_PESTANYA_KREDI_KARTI;
		}
		String metodoPagoClick = new PageCheckoutWrapper().getMethodInputValue(metodoPago);
		return (XP_RADIO_PAGO_WITH_TAG.replace(TAG_METODO_PAGO, metodoPagoClick));
	}

	private String getXPathCodigoVendedorVOTF(String codigoVendedor) {
		return (XP_COD_VENDEDOR_VOTF_WITH_TAG.replace(TAG_COD_VENDEDOR, codigoVendedor));
	}
	
	public boolean isPageUntil(int secondsWait) {
		return (isBloqueImporteTotal(secondsWait));
	}

	public boolean isBloqueImporteTotal(int seconds) {
		return state(Present, XP_IMPORTE_TOTAL).wait(seconds).check();
	}

	public boolean isPresentInputApellidoPromoEmplUntil(int seconds) {
		return state(Present, XP_INPUT_APELLIDO_PROMO_EMPL).wait(seconds).check();
	}

	public void inputApellidoPromoEmpl(String apellido) {
		getElement(XP_INPUT_APELLIDO_PROMO_EMPL).sendKeys(apellido); 
	}

	public boolean isPresentInputDNIPromoEmpl() {
		return state(Present, XP_INPUT_DNI_PROMO_EMPL).check();
	}

	public void inputDNIPromoEmpl(String dni) {
		sendKeysWithRetry(dni, By.xpath(XP_INPUT_DNI_PROMO_EMPL), 2, driver);
	}

	public boolean isPresentDiaNaciPromoEmpl() {
		return state(Present, XP_DIA_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentMesNaciPromoEmpl() {
		return state(Present, XP_MES_NACI_PROMO_EMPL).check();
	}

	public boolean isPresentAnyNaciPromoEmpl() {
		return state(Present, XP_ANY_NACI_PROMO_EMPL).check();
	}

	/**
	 * @param fechaNaci en formato "dd-mm-aaaa"
	 */
	public void selectFechaNacPromoEmpl(String fechaNaci) {
		var fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
		selectDiaNacPromoEmpl(fechaTokenizada.nextToken());
		selectMesNacPromoEmpl(fechaTokenizada.nextToken());
		selectAnyNacPromoEmpl(fechaTokenizada.nextToken());
	}
	
	public void selectDiaNacPromoEmpl(String value) {
		new Select(getElement(XP_DIA_NACI_PROMO_EMPL)).selectByValue(value);
	}
	
	public void selectMesNacPromoEmpl(String value) {
		new Select(getElement(XP_MES_NACI_PROMO_EMPL)).selectByValue(value);
	}	
	
	public void selectAnyNacPromoEmpl(String value) {
		new Select(getElement(XP_ANY_NACI_PROMO_EMPL)).selectByValue(value);
	}
	public void clickAplicarPromo() {
		click(XP_BUTTON_APLICAR_PROMO).exec();
	}

	public void clickGuardarPromo() {
		click(XP_BUTTON_GUARDAR_PROMO).exec();
	}

	public void clickConfirmarPago() {
		click(XP_CONF_PAGO_BUTTON_DESKTOP).exec();
	}

	public boolean isPresentButtonConfPago() {
		return state(Present, XP_CONF_PAGO_BUTTON_DESKTOP).check();
	}

	public boolean isVisibleBlockCodigoPromoUntil(int seconds) {
		return state(Visible, XP_BLOCK_CODIGO_PROMO).wait(seconds).check();
	}

	public void clickLinkToViewBlockPromo() {
		click(By.id(XP_LINK_TO_VIEW_BLOCK_PROMO)).exec();
	}

	public void showInputCodigoPromoAndAccept(String codigoPromo) {
		if (!isVisibleBlockCodigoPromoUntil(0)) {
			clickLinkToViewBlockPromo();
		}
		if (isVisibleBlockCodigoPromoUntil(5)) {
			inputCodigoPromo(codigoPromo);
			clickAplicarPromo();
		}
	}
	public boolean isVisibleInputCodigoPromoUntil(int seconds) {
		return state(Visible, XP_INPUT_PROMO).wait(seconds).check();
	}

	public void clickEliminarValeIfExists() {
		if (state(Visible, XP_LINK_ELIMINAR_PROMO).check()) {
			click(XP_LINK_ELIMINAR_PROMO).exec();
		}
	}

	public void inputCodigoPromo(String codigoPromo) {
		sendKeysWithRetry(codigoPromo, By.xpath(XP_INPUT_PROMO), 2, driver);
	}


	public boolean checkTextValeCampaingIs(String textoCampaingVale) {
		if (state(Visible, XP_TEXT_VALE_CAMPAIGN).check()) {
			return getElement(XP_TEXT_VALE_CAMPAIGN).getText().toLowerCase().contains(textoCampaingVale.toLowerCase());
		}
		return false;
	}

	public String getImporteDescuentoEmpleado() {
		return getElement(XP_DESCUENTO_EMPLEADO).getText();
	}

	public boolean isVisibleDescuentoEmpleadoUntil(int seconds) {
		return state(Visible, XP_DESCUENTO_EMPLEADO).wait(seconds).check();
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

	public boolean isNumMetodosPagoOK(boolean isEmpl) {
		int numPagosPant = getElements(XP_METODO_PAGO).size();
		if (app!=AppEcom.votf) {
			int numPagosPais = dataTest.getPais().getListPagosForTest(app, isEmpl).size();
			return (numPagosPais == numPagosPant);
		}
		
		//En el caso de VOTF no existe ningún método de pago por pantalla (por defecto es el pago vía TPV)
		return (numPagosPant == 0);
	}
	
	public boolean isNumpagos(int numPagosExpected) {
		int numPagosPant = getElements(XP_METODO_PAGO).size();
		return (numPagosPant == numPagosExpected);
	}

	public boolean isPresentMetodosPago() {
		return state(Present, XP_METODO_PAGO).check();
	}

	/**
	 * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
	 */
	public void forceClickMetodoPagoAndWait(String metodoPago) {
		despliegaMetodosPago();
		var pageCheckoutWrapper = new PageCheckoutWrapper();
		pageCheckoutWrapper.waitUntilNoDivLoading(2);
		moveToMetodosPago();
		clickMetodoPago(metodoPago);
		pageCheckoutWrapper.waitUntilNoDivLoading(10);
	}

	
	public void clickDesplegablePagos() {
		getElement(XP_CLICK_DESPLIEGA_PAGOS).click();
	}
	
	public void moveToMetodosPago() {
		moveToElement(XP_IMPORTE_TOTAL_COMPRA);
	}
	
	public void despliegaMetodosPago() {
		if (areMetodosPagoPlegados()) {
		   clickDesplegablePagos();
		   metodosPagosInStateUntil(false, 5);
		}
	}

	public boolean areMetodosPagoPlegados() {
		return (
			state(Present, XP_OTRAS_FORMAS_PAGO).check() &&
			!state(Visible, XP_OTRAS_FORMAS_PAGO).check());
	}

	private void metodosPagosInStateUntil(boolean plegados, int seconds) {
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

	public void clickMetodoPago(String metodoPago) {
		click(getXPathClickMetodoPago(metodoPago))
			.type(TypeClick.webdriver)
			.waitLink(2)
			.waitLoadPage(1).exec();
	}

	public boolean isRedErrorVisible() {
		return state(Visible, XP_RED_ERROR).check();
	}

	public String getTextRedError() {
		return getElement(XP_RED_ERROR).getText();
	}	
	
	public String getPrecioTotalFromResumen(boolean normalize) {
		String precioTotal = new PageCheckoutWrapper().formateaPrecioTotal(XP_PRECIO_TOTAL);
		if (!normalize) {
			return precioTotal;
		}
		return (ImporteScreen.normalizeImportFromScreen(precioTotal));
	}
	
	public String getCroaciaPrecioTotalInEuros(boolean normalize) {
		String precioTotal = new PageCheckoutWrapper().formateaPrecioTotal(XP_PRECIO_TOTAL_CROATIA_EUROS);
		if (!normalize) {
			return precioTotal;
		}
		return (ImporteScreen.normalizeImportFromScreen(precioTotal));		
	}

	public String getAlmacenFromNoProdEntorn() {
		if (state(Present, XP_ALMACEN_IN_NO_PRO_ENTORNOS).check()) {
			return getElement(XP_ALMACEN_IN_NO_PRO_ENTORNOS).getText();
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

	public boolean validateArticlesAndImport() {
		for (ArticuloScreen articulo : dataTest.getDataBag().getListArticlesTypeViewInBolsa()) {
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
	
	public boolean validateArticlesAndDiscount(Descuento descuento) {
		for (ArticuloScreen articulo : dataTest.getDataBag().getListArticlesTypeViewInBolsa()) {
			var lineaArticulo = getLineaArticle(articulo.getReferencia());
			if (lineaArticulo==null) {
				return false;
			}
			
			var preciosArticuloScreen = getPreciosArticuloResumen(lineaArticulo);
			var pageCheckoutWrapper = new PageCheckoutWrapper();
			if (articulo.getValePais()!=null) {
				if (!pageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuento)) {
					return false;
				}
			} else {
				var descuentoZero = new Descuento(0);
				if (!pageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuentoZero)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public String getPrecioSubTotalFromResumen() {
		return new PageCheckoutWrapper().formateaPrecioTotal(XP_PRECIO_SUBTOTAL);
	}
	
	private PreciosArticulo getPreciosArticuloResumen(WebElement articuloWeb) {
		var precios = new PreciosArticulo();
		var preciosNoTachados= articuloWeb.findElements(By.xpath("." + XP_PRECIO_NO_TACHADO_REL_ARTICLE));
		var preciosSiTachados= articuloWeb.findElements(By.xpath("." + XP_PRECIOO_SI_TACHADO_REL_ARTICLE));
		int cantidad = Integer.parseInt(articuloWeb.findElement(By.xpath("." + XP_CANTIDAD_ARTICULOS)).getText());
		for (var precioNoTachado : preciosNoTachados) {
			precios.definitivo = getFloatFromImporteScreen(precioNoTachado) / cantidad; 
			if (precios.definitivo!=0) {
				precios.original = precios.definitivo;
				break;
			}
		}
		
		for (var precioSiTachado : preciosSiTachados) {
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
		click(XP_RADIO_TRJ_GUARDADA).exec();
	}

	public void inputCvcTrjGuardadaIfVisible(String cvc) {
		if (state(Visible, XP_CVC_TRJ_GUARDADA).check()) {
			var input = getElement(XP_CVC_TRJ_GUARDADA);
			input.clear();
			input.sendKeys(cvc);
		}
	}

	public void clickSolicitarFactura() {
		getElement(XP_LINK_SOLICITAR_FACTURA).click();
	}
	
	public boolean isVisibleDescartarFacturaLink() {
		return state(Visible, XP_LINK_DESCARTAR_FACTURA).wait(1).check();
	}
	
	public boolean isArticulos() {
		return state(Present, XP_FIRST_ARTICULO).check();
	}

	/**
	 * Función que, partiendo de la página con los métodos de pago del checkout, realiza la confirmación del pago
	 * (Simplemente selecciona "Confirmar pago")
	 */
	public void confirmarPagoFromMetodos() {
		clickConfirmarPago();
	}

	public boolean isVisibleErrorRojoInputPromoUntil(int seconds) {
		return (
			state(Visible, XP_ERROR_PROMO).wait(seconds).check() &&
			getElement(XP_ERROR_PROMO).getAttribute("style").contains("color: red"));
	}

	public void inputVendedorVOTF(String codigoVendedor) {
		getElement(XP_INPUT_VENDEDOR_VOTF).clear();
		getElement(XP_INPUT_VENDEDOR_VOTF).sendKeys(codigoVendedor);
	}

	public void acceptInputVendedorVOTF() {
		click(XP_BUTTON_ACCEPT_VENDEDOR_VOTF).exec();
	}

	public boolean isVisibleInputVendedorVOTF(int seconds) {
		return state(Visible, XP_INPUT_VENDEDOR_VOTF).wait(seconds).check();
	}


	public boolean isVisibleCodigoVendedorVOTF(String codigoVendedor) {
		String xpathVendedor = getXPathCodigoVendedorVOTF(codigoVendedor);
		return state(Visible, xpathVendedor).check();
	}

	public boolean isDataChequeRegalo(ChequeRegalo chequeRegalo) {
		if (!getElement(XP_NOMBRE_CHEQUE_REGALO).getText().contains(chequeRegalo.getNombre()) ||
			!getElement(XP_NOMBRE_CHEQUE_REGALO).getText().contains(chequeRegalo.getApellidos()) ||
			!getElement(XP_PRECIO_CHEQUE_REGALO).getText().contains(chequeRegalo.getImporte().toString().replace("EURO_", "")) ||
			!getElement(XP_MENSAJE_CHEQUE_REGALO).getText().contains(chequeRegalo.getMensaje())) {
			return false;
		}
		return true;
	}

}