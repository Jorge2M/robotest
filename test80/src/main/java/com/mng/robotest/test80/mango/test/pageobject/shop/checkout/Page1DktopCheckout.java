package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

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
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class Page1DktopCheckout extends PageObjTM {
	
    private final SecStoreCredit secStoreCredit;
    private final SecTMango secTMango;
    private final SecBillpay secBillpay;
    private final SecEps secEps;
    private final ModalAvisoCambioPais modalAvisoCambioPais;
    
    private final Channel channel;
    private final AppEcom app;
    
    private final static String XPathConfPagoButtonDesktop = "//*[@id[contains(.,'btnCheckout')]]";
    private final static String XPathAlmacenInNoProEntorns = "//span[@class='labelTestShowAlmacenStrong']";
    private final static String XPathBlockCodigoPromo = "//div[@class[contains(.,'contenidoPromoCode')]]";
    private final static String XPathErrorPromo = "//*[@class='labelIntroduceError']";
    private final static String XPathLinkToViewBlockPromo = "SVBody:SVResumenPromociones:FExpandirVales";
    private final static String xpathInputPromo = "//input[@class[contains(.,'inputVale')]]";
    private final static String XPathButtonAplicarPromo = "//input[@id[contains(.,'PromocionesVales')] and @type='submit']";
    private final static String XPathLinkEliminarPromo = "//span[@class[contains(.,'promoCodeConfirmar')]]";
    private final static String XPathBotoneraEmplPromo = "//div[@class='botoneraEmpleado']";
    private final static String XPathButtonCancelarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew claro']";
    private final static String XPathButtonGuardarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew']";
    private final static String XPathInputApellidoPromoEmpl = "//input[@id[contains(.,'primerApellido')]]";
    private final static String XPathInputDNIPromoEmpl = "//input[@id[contains(.,'dniEmpleado')]]";
    private final static String XPathDiaNaciPromoEmpl = "//select[@id[contains(.,'naciDia')]]";
    private final static String XPathMesNaciPromoEmpl = "//select[@id[contains(.,'naciMes')]]";
    private final static String XPathAnyNaciPromoEmpl = "//select[@id[contains(.,'naciAny')]]";
    private final static String XPathAceptarPromoEmpl = "//div[@class[contains(.,'botoneraEmpleado')]]/span[@class='botonNew']";
    private final static String XPathDescuentoEmpleado = "//p[@class[contains(.,'descuento-aplicado')]]//span[@class='price-format']";

    private final static String XPathPrecioRelArticle = "//div[@class[contains(.,'precioNormal')]]";
    private final static String XPathPrecioNoTachadoRelArticle = XPathPrecioRelArticle + "//self::div[not(@class[contains(.,'tachado')])]";
    private final static String XPathPrecioSiTachadoRelArticle = XPathPrecioRelArticle + "//self::div[@class[contains(.,'tachado')]]";
	
    private final static String XPathCantidadArticulos = "//div[@class[contains(.,'cantidadRes')]]";
    private final static String XPathImporteTotal = "//*[@id='SVBody:SVResumenDesgloseImporteTotal:importeTotal']";
    private final static String XPathClickDespliegaPagos = "//p[@class[contains(.,'anadirPago')]]";
    private final static String XPathImporteTotalCompra = "//*[@id[contains(.,'importeTotalComprar')]]";
    private final static String XPathOtrasFormasPago = "//div[@class[contains(.,'formasPago')]]";
    private final static String XPathRedError = "//div[@class[contains(.,'errorbocatapago')]]";
    private final static String XPathNombreEnvio = "//div[@class[contains(.,'nombreEnvio')]]"; 
    private final static String XPathDirecionEnvio = "//div[@class[contains(.,'direccionEnvio')]]";
    private final static String XPathPoblacionEnvio = "//div[@class[contains(.,'poblacionEnvio')]]";
    private final static String XPathProvinciaEnvio = "//div[@class[contains(.,'provinciaEnvio')]]";
    private final static String XPathPrecioSubTotal = "//div[@class='subTotal']/div/div[2]/span[@class='precioNormal']";
    private final static String XPathPrecioTotal = "//span[@class[contains(.,'precioTotal')]]";
    private final static String XPathBloquesPagoPosibles = 
    	"//div[@id='textoCondicionesTarjeta']//*[@id='CardName'] | " + 
        "//div[@id='textoCondicionesTarjeta' and @class='paypalInfo'] | " +
        "//div[@id='textoCondicionesTarjeta']//*[@id[contains(.,'yandex')]] | " +
        "//div[@class[contains(.,'billpayFormularioTarjeta')]] | " + 
        "//div[@id='avisoConfirmar']/div[@style='']/div[@class[contains(.,'tituloPago')]] | " +
        "//div[@class='mensajesContrarembolso'] | " +
        "//div[@class[contains(.,'cardContainerNotIntegrated')]] | " +
        "//div[@class[contains(.,'falconFormularioTarjeta')]] | " + 
        "//div[@class[contains(.,'formasPago')]]"; 
    
    private final static String tagMetodoPago = "@TagMetodoPago";
    private final static String XPathBlockTarjetaGuardadaPagoWithTag = "//div[@class[contains(.,'tarjetaGuardada')] and @data-analytics-value='" + tagMetodoPago + "']";
    private final static String XPathRadioTrjGuardada = "//input[@class[contains(.,'guardadaInput')]]";
    private final static String XPathCvcTrjGuardada = "//div[@class='storedCardForm']//input[@id='cvc']";
    
    private final static String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
    private final static String XPathLinkEditDirecEnvio = "//span[@class[contains(.,'cambiarDatosEnvio')]]";
    private final static String XPathFirstArticulo = "//div[@class[contains(.,'firstArticulo')]]";
    private final static String XPathInputVendedorVOTF = "//input[@class[contains(.,'codiDependenta')]]";
    private final static String XPathButtonAcceptVendedorVOTF = "//span[@id[contains(.,'CodigoDependienta')]]";
    
    private final static String XPathContentChequeRegalo = "//div[@class[contains(.,'contentsChequeRegalo')]]";
    private final static String XPathNombreChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[1]";
    private final static String XPathEmailChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[2]";
    private final static String XPathPrecioChequeRegalo = XPathContentChequeRegalo + "//div[@class='span2']";
    private final static String XPathMensajeChequeRegalo = XPathContentChequeRegalo + "//div[@class='span4']";
    
    private final static String TagMetodoPago = "@TagMetodoPago";
    private final static String XPathRadioPagoWithTag = "//div[@class[contains(.,'cuadroPago')]]/input[@value='" + TagMetodoPago + "']/../input[@type='radio']";
    private final static String TextKrediKarti = "KREDİ KARTI";
    private final static String XPathPestanyaKrediKarti = "//div[@class[contains(.,'pmGroupTitle')]]/span[text()='" + TextKrediKarti + "']";
    
    private final static String XPathMetodoPago = "//*[@class[contains(.,'cardBox')]]/div";
    
    private final static String tagReferencia = "@TagRef";
    private final static String XPathLineaArticuloWithTag = 
    	"//div[@class[contains(.,'ref')] and text()[contains(.,'" + tagReferencia + "')]]/ancestor::div[@class[contains(.,'articuloResBody')]]";
    
    private final static String tagCodVendedor = "@TagCodVendedor";
    private final static String XPathCodVendedorVotfWithTag = "//form[@id[contains(.,'Dependienta')]]//span[text()[contains(.,'" + tagCodVendedor + "')]]";
    
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

	private final static String xpathTextValeCampaign = "//span[@class='texto_banner_promociones']";
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
           metodosPagosInStateUntil(false, 2);
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

	public boolean isVisibleInputVendedorVOTF() {
		return (state(Visible, By.xpath(XPathInputVendedorVOTF)).check());
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