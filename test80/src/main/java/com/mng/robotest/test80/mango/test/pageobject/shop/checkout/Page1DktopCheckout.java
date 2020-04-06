package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.SecTMango;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class Page1DktopCheckout {
	
    public static SecStoreCredit secStoreCredit;
    public static SecTMango secTMango;
    public static SecBillpay secBillpay;
    public static SecKlarna secKlarna;
    public static SecEps secEps;
    public static ModalAvisoCambioPais modalAvisoCambioPais;
    
    final static String XPathConfPagoButtonDesktop = "//*[@id[contains(.,'btnCheckout')]]";
    final static String XPathAlmacenInNoProEntorns = "//span[@class='labelTestShowAlmacenStrong']";
    final static String XPathBlockCodigoPromo = "//div[@class[contains(.,'contenidoPromoCode')]]";
    final static String XPathErrorPromo = "//*[@class='labelIntroduceError']";
    final static String XPathLinkToViewBlockPromo = "SVBody:SVResumenPromociones:FExpandirVales";
    final static String xpathInputPromo = "//input[@class[contains(.,'inputVale')]]";
    final static String XPathButtonAplicarPromo = "//input[@id[contains(.,'PromocionesVales')] and @type='submit']";
    final static String XPathLinkEliminarPromo = "//span[@class[contains(.,'promoCodeConfirmar')]]";
    final static String XPathBotoneraEmplPromo = "//div[@class='botoneraEmpleado']";
    final static String XPathButtonCancelarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew claro']";
    final static String XPathButtonGuardarPromo = XPathBotoneraEmplPromo + "//span[@class='botonNew']";
    final static String XPathInputApellidoPromoEmpl = "//input[@id[contains(.,'primerApellido')]]";
    final static String XPathInputDNIPromoEmpl = "//input[@id[contains(.,'dniEmpleado')]]";
    final static String XPathDiaNaciPromoEmpl = "//select[@id[contains(.,'naciDia')]]";
    final static String XPathMesNaciPromoEmpl = "//select[@id[contains(.,'naciMes')]]";
    final static String XPathAnyNaciPromoEmpl = "//select[@id[contains(.,'naciAny')]]";
    final static String XPathAceptarPromoEmpl = "//div[@class[contains(.,'botoneraEmpleado')]]/span[@class='botonNew']";
    final static String XPathDescuentoEmpleado = "//p[@class[contains(.,'descuento-aplicado')]]//span[@class='price-format']";

    final static String XPathPrecioRelArticle = "//div[@class[contains(.,'precioNormal')]]";
	final static String XPathPrecioNoTachadoRelArticle = XPathPrecioRelArticle + "//self::div[not(@class[contains(.,'tachado')])]";
	final static String XPathPrecioSiTachadoRelArticle = XPathPrecioRelArticle + "//self::div[@class[contains(.,'tachado')]]";
	
    final static String XPathCantidadArticulos = "//div[@class[contains(.,'cantidadRes')]]";
    final static String XPathImporteTotal = "//*[@id='SVBody:SVResumenDesgloseImporteTotal:importeTotal']";
    final static String XPathClickDespliegaPagos = "//p[@class[contains(.,'anadirPago')]]";
    final static String XPathImporteTotalCompra = "//*[@id[contains(.,'importeTotalComprar')]]";
    final static String XPathOtrasFormasPago = "//div[@class[contains(.,'formasPago')]]";
    final static String XPathRedError = "//div[@class[contains(.,'errorbocatapago')]]";
    final static String XPathNombreEnvio = "//div[@class[contains(.,'nombreEnvio')]]"; 
    final static String XPathDirecionEnvio = "//div[@class[contains(.,'direccionEnvio')]]";
    final static String XPathPoblacionEnvio = "//div[@class[contains(.,'poblacionEnvio')]]";
    final static String XPathProvinciaEnvio = "//div[@class[contains(.,'provinciaEnvio')]]";
    final static String XPathPrecioSubTotal = "//div[@class='subTotal']/div/div[2]/span[@class='precioNormal']";
    final static String XPathPrecioTotal = "//span[@class[contains(.,'precioTotal')]]";
    final static String XPathBloquesPagoPosibles = 
    	"//div[@id='textoCondicionesTarjeta']//*[@id='CardName'] | " + 
        "//div[@id='textoCondicionesTarjeta' and @class='paypalInfo'] | " +
        "//div[@id='textoCondicionesTarjeta']//*[@id[contains(.,'yandex')]] | " +
        "//div[@class[contains(.,'billpayFormularioTarjeta')]] | " + 
        "//div[@id='avisoConfirmar']/div[@style='']/div[@class[contains(.,'tituloPago')]] | " +
        "//div[@class='mensajesContrarembolso'] | " +
        "//div[@class[contains(.,'cardContainerNotIntegrated')]] | " +
        "//div[@class[contains(.,'falconFormularioTarjeta')]]"; 
    
    final static String tagMetodoPago = "@TagMetodoPago";
    final static String XPathBlockTarjetaGuardadaPagoWithTag = "//div[@class[contains(.,'tarjetaGuardada')] and @data-analytics-value='" + tagMetodoPago + "']";
    final static String XPathRadioTrjGuardada = "//input[@class[contains(.,'guardadaInput')]]";
	final static String XPathCvcTrjGuardada = "//div[@class='storedCardForm']//input[@id='cvc']";
    
    final static String XPathLinkSolicitarFactura = "//input[@type='checkbox' and @id[contains(.,'chekFacturaE')]]";
    final static String XPathLinkEditDirecEnvio = "//span[@class[contains(.,'cambiarDatosEnvio')]]";
    final static String XPathFirstArticulo = "//div[@class[contains(.,'firstArticulo')]]";
    final static String XPathInputVendedorVOTF = "//input[@class[contains(.,'codiDependenta')]]";
    final static String XPathButtonAcceptVendedorVOTF = "//span[@id[contains(.,'CodigoDependienta')]]";
    
    final static String XPathContentChequeRegalo = "//div[@class[contains(.,'contentsChequeRegalo')]]";
    final static String XPathNombreChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[1]";
    final static String XPathEmailChequeRegalo = "(" + XPathContentChequeRegalo + "//div[@class='span3'])[2]";
    final static String XPathPrecioChequeRegalo = XPathContentChequeRegalo + "//div[@class='span2']";
    final static String XPathMensajeChequeRegalo = XPathContentChequeRegalo + "//div[@class='span4']";
    
    final static String TagMetodoPago = "@TagMetodoPago";
    final static String XPathRadioPagoWithTag = "//div[@class[contains(.,'cuadroPago')]]/input[@value='" + TagMetodoPago + "']/../input[@type='radio']";
    final static String TextKrediKarti = "KREDİ KARTI";
    final static String XPathPestanyaKrediKarti = "//div[@class[contains(.,'pmGroupTitle')]]/span[text()='" + TextKrediKarti + "']";
    
    final static String XPathMetodoPago = "//*[@class[contains(.,'cardBox')]]/div";
    
    final static String tagReferencia = "@TagRef";
    final static String XPathLineaArticuloWithTag = 
    	"//div[@class[contains(.,'ref')] and text()[contains(.,'" + tagReferencia + "')]]/ancestor::div[@class[contains(.,'articuloResBody')]]";
    
    final static String tagCodVendedor = "@TagCodVendedor";
    final static String XPathCodVendedorVotfWithTag = "//form[@id[contains(.,'Dependienta')]]//span[text()[contains(.,'" + tagCodVendedor + "')]]";
    
    static String getXPathLinArticle(String referencia) {
    	return (XPathLineaArticuloWithTag.replace(tagReferencia, referencia));
    }
    
    static String getXPathBlockTarjetaGuardada(String metodoPago) {
    	return (XPathBlockTarjetaGuardadaPagoWithTag.replace(tagMetodoPago, metodoPago.toLowerCase()));
    }
    
    static String getXPathRadioTarjetaGuardada(String metodoPago) {
    	String xpathMethod = getXPathBlockTarjetaGuardada(metodoPago);
    	return (xpathMethod + XPathRadioTrjGuardada);
    }

	@SuppressWarnings("static-access")
	public static boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, int maxSeconds, WebDriver driver) {
		switch (pago.getTypePago()) {
		case TMango:
			return (secTMango.isVisibleUntil(Channel.desktop, maxSeconds, driver));
		case Billpay:
			return (secBillpay.isVisibleUntil(Channel.desktop, maxSeconds, driver));
		case Klarna:
			return (secKlarna.isVisibleUntil(Channel.desktop, maxSeconds, driver));
		case KlarnaDeutsch:
			return (SecKlarnaDeutsch.isVisibleUntil(Channel.desktop, maxSeconds, driver));
		default:
			String nameExpected = pago.getNombreInCheckout(Channel.desktop).toLowerCase();
			return (
				state(Visible, By.xpath(XPathBloquesPagoPosibles), driver).wait(maxSeconds).check() &&
				driver.findElement(By.xpath(XPathBloquesPagoPosibles)).getAttribute("innerHTML").toLowerCase().contains(nameExpected)
					);
		}
	}

    private static String getXPathClickMetodoPago(String metodoPago) {
        if (TextKrediKarti.compareTo(metodoPago)==0) {
        	return XPathPestanyaKrediKarti;
        }
        String metodoPagoClick = PageCheckoutWrapper.getMethodInputValue(metodoPago, Channel.desktop);
        return (XPathRadioPagoWithTag.replace(TagMetodoPago, metodoPagoClick));
    }

    private static String getXPathCodigoVendedorVOTF(String codigoVendedor) {
        return (XPathCodVendedorVotfWithTag.replace(tagCodVendedor, codigoVendedor));
    }
    
    public static boolean isPageUntil(int secondsWait, WebDriver driver) {
        return (isBloqueImporteTotal(driver, secondsWait));
    }

	public static boolean isBloqueImporteTotal(WebDriver driver, int maxSeconds) {
		return (state(Present, By.xpath(XPathImporteTotal), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isPresentInputApellidoPromoEmplUntil(int maxSeconds, WebDriver driver) {
		return (state(Present, By.xpath(XPathInputApellidoPromoEmpl), driver)
				.wait(maxSeconds).check());
	}

	public static void inputApellidoPromoEmpl(String apellido, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputApellidoPromoEmpl)).sendKeys(apellido); 
	}

	public static boolean isPresentInputDNIPromoEmpl(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputDNIPromoEmpl), driver).check());
	}

	public static void inputDNIPromoEmpl(String dni, WebDriver driver) {
		sendKeysWithRetry(dni, By.xpath(XPathInputDNIPromoEmpl), 2, driver);
	}

	public static boolean isPresentDiaNaciPromoEmpl(WebDriver driver) {
		return (state(Present, By.xpath(XPathDiaNaciPromoEmpl), driver).check());
	}

	public static boolean isPresentMesNaciPromoEmpl(WebDriver driver) {
		return (state(Present, By.xpath(XPathMesNaciPromoEmpl), driver).check());
	}

	public static boolean isPresentAnyNaciPromoEmpl(WebDriver driver) {
		return (state(Present, By.xpath(XPathAnyNaciPromoEmpl), driver).check());
	}

    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public static void selectFechaNacPromoEmpl(String fechaNaci, WebDriver driver) {
        StringTokenizer fechaTokenizada = (new StringTokenizer(fechaNaci, "-"));
        selectDiaNacPromoEmpl(fechaTokenizada.nextToken(), driver);
        selectMesNacPromoEmpl(fechaTokenizada.nextToken(), driver);
        selectAnyNacPromoEmpl(fechaTokenizada.nextToken(), driver);
    }
    
    public static void selectDiaNacPromoEmpl(String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathDiaNaciPromoEmpl))).selectByValue(value);
    }
    
    public static void selectMesNacPromoEmpl(String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathMesNaciPromoEmpl))).selectByValue(value);
    }    
    
    public static void selectAnyNacPromoEmpl(String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathAnyNaciPromoEmpl))).selectByValue(value);
    }

	public static void clickAplicarPromo(WebDriver driver) {
		click(By.xpath(XPathButtonAplicarPromo), driver).exec();
	}

	public static void clickGuardarPromo(WebDriver driver) {
		click(By.xpath(XPathButtonGuardarPromo), driver).exec();
	}

	public static void clickConfirmarPago(WebDriver driver) {
		click(By.xpath(XPathConfPagoButtonDesktop), driver).exec();
	}

	public static boolean isPresentButtonConfPago(WebDriver driver) {
		return (state(Present, By.xpath(XPathConfPagoButtonDesktop), driver).check());
	}

	public static boolean isVisibleBlockCodigoPromoUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathBlockCodigoPromo), driver)
				.wait(maxSeconds).check());
	}

	public static void clickLinkToViewBlockPromo(WebDriver driver) {
		click(By.id(XPathLinkToViewBlockPromo), driver).exec();
	}

	public static void showInputCodigoPromoAndAccept(String codigoPromo, WebDriver driver) throws Exception {
		if (!isVisibleBlockCodigoPromoUntil(0, driver)) {
			clickLinkToViewBlockPromo(driver);
		}
		if (isVisibleBlockCodigoPromoUntil(5, driver)) {
			inputCodigoPromo(codigoPromo, driver);
			clickAplicarPromo(driver);
		}
	}

	public static boolean isVisibleInputCodigoPromoUntil(int maxSeconds, WebDriver driver) throws Exception {
		return (state(Visible, By.xpath(xpathInputPromo), driver)
				.wait(maxSeconds).check());
	}

	public static void clickEliminarValeIfExists(WebDriver driver) {
		By byEliminar = By.xpath(XPathLinkEliminarPromo);
		if (state(Visible, byEliminar, driver).check()) {
			click(byEliminar, driver).exec();
		}
	}

	public static void inputCodigoPromo(String codigoPromo, WebDriver driver) {
		sendKeysWithRetry(codigoPromo, By.xpath(xpathInputPromo), 2, driver);
	}

	final static String xpathTextValeCampaign = "//span[@class='texto_banner_promociones']";
	public static boolean checkTextValeCampaingIs(String textoCampaingVale, WebDriver driver) {
		if (state(Visible, By.xpath(xpathTextValeCampaign), driver).check()) {
			return (driver.findElement(By.xpath(xpathTextValeCampaign)).getText().toLowerCase().contains(textoCampaingVale.toLowerCase()));
		}
		
		return false;
	}

	public static String getImporteDescuentoEmpleado(WebDriver driver) {
		return (driver.findElement(By.xpath(XPathDescuentoEmpleado)).getText());
	}

	public static boolean isVisibleDescuentoEmpleadoUntil(WebDriver driver, int maxSeconds) {
		return (state(Visible, By.xpath(XPathDescuentoEmpleado), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isMetodoPagoPresent(String metodoPagoClick, WebDriver driver) {
		String xpathClickPago = getXPathClickMetodoPago(metodoPagoClick);
		return (state(Present, By.xpath(xpathClickPago), driver).check());
	}

    /**
     * @return si el número de métodos de pago visualizados en pantalla es el correcto
     */
    public static boolean isNumMetodosPagoOK(WebDriver driver, Pais pais, AppEcom app, boolean isEmpl) {
        int numPagosPant = driver.findElements(By.xpath(XPathMetodoPago)).size();
        if (app!=AppEcom.votf) {
            //Se comprueba que el número de métodos de pago en pantalla coincida con los asociados al país
            int numPagosPais = pais.getListPagosForTest(app, isEmpl).size();
            return (numPagosPais == numPagosPant);
        }
        
        //En el caso de VOTF no existe ningún método de pago por pantalla (por defecto es el pago vía TPV)
        return (numPagosPant == 0);
    }
    
    public static boolean isNumpagos(int numPagosExpected, WebDriver driver) {
        int numPagosPant = driver.findElements(By.xpath(XPathMetodoPago)).size();
        return (numPagosPant == numPagosExpected);
    }

	public static boolean isPresentMetodosPago(WebDriver driver) {
		return (state(Present, By.xpath(XPathMetodoPago), driver).check());
	}

    /**
     * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
     */
    public static void forceClickMetodoPagoAndWait(String metodoPago, Pais pais, WebDriver driver) throws Exception {
        despliegaMetodosPago(driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 2);
        moveToMetodosPago(driver);
        clickMetodoPago(pais, metodoPago, driver);
        PageCheckoutWrapper.waitUntilNoDivLoading(driver, 10);
    }

    
    public static void clickDesplegablePagos(WebDriver driver) {
        driver.findElement(By.xpath(XPathClickDespliegaPagos)).click();
    }
    
    public static void moveToMetodosPago(WebDriver driver) {
        moveToElement(By.xpath(XPathImporteTotalCompra), driver);
    }
    
    /**
     * Si no lo están, despliega los métodos de pago de la página de checkout
     */
    public static void despliegaMetodosPago(WebDriver driver) throws Exception {
        if (areMetodosPagoPlegados(driver)) {
           clickDesplegablePagos(driver);
           metodosPagosInStateUntil(driver, false/*plegados*/, 2/*seconds*/);
        }
    }

	public static boolean areMetodosPagoPlegados(WebDriver driver) {
		return (
			state(Present, By.xpath(XPathOtrasFormasPago), driver).check() &&
			!state(Visible, By.xpath(XPathOtrasFormasPago), driver).check());
	}

    /**
     * Esperamos un máximo de segundos hasta que los métodos de pago estén plegados/desplegados
     */
    private static void metodosPagosInStateUntil(WebDriver driver, boolean plegados, int seconds) throws Exception {
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

	public static void clickMetodoPago(Pais pais, String metodoPago, WebDriver driver) {
		String xpathClickMetodoPago = getXPathClickMetodoPago(metodoPago);
		click(By.xpath(xpathClickMetodoPago), driver)
			.type(TypeClick.webdriver).waitLink(2).waitLoadPage(1).exec();
	}

	public static boolean isRedErrorVisible(WebDriver driver) {
		return (state(Visible, By.xpath(XPathRedError), driver).check());
	}

    public static String getTextRedError(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathRedError)).getText());
    }    
    
    public static String getTextNombreEnvio(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathNombreEnvio)).getText());
    }
    
    public static String getTextDireccionEnvio(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathDirecionEnvio)).getText());
    }
    
    public static String getTextPoblacionEnvio(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathPoblacionEnvio)).getText());
    }    

	public static String getTextDireccionEnvioCompleta(WebDriver driver) {
		waitForPageLoaded(driver); //For avoid StaleElementReferenceException
		if (state(Present, By.xpath(XPathDirecionEnvio), driver).check()) {
			return (
				driver.findElement(By.xpath(XPathDirecionEnvio)).getText() + ", " +
				driver.findElement(By.xpath(XPathPoblacionEnvio)).getText() + ", " +
				driver.findElement(By.xpath(XPathProvinciaEnvio)).getText());
		}
		return "";
	}

	public static String getPrecioTotalFromResumen(WebDriver driver) throws Exception {
		String precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioTotal, driver);
		return (ImporteScreen.normalizeImportFromScreen(precioTotal));
	}

	/**
	 * @return el código de almacén que es posible obtener en el caso de la página de checkout de entornos no-productivos
	 */
	public static String getAlmacenFromNoProdEntorn(WebDriver driver) {
		if (state(Present, By.xpath(XPathAlmacenInNoProEntorns), driver).check()) {
			return (driver.findElement(By.xpath(XPathAlmacenInNoProEntorns)).getText());
		}
		return "";
	}

	public static WebElement getLineaArticle(String referencia, WebDriver driver) {
		By byLinArticle = By.xpath(getXPathLinArticle(referencia));
		if (state(Present, byLinArticle, driver).check()) {
			return (driver.findElement(byLinArticle));
		}
		return null;
	}

    public static boolean validateArticlesAndImport(DataBag dataBag, WebDriver driver) throws Exception {
    	for (ArticuloScreen articulo : dataBag.getListArticlesTypeViewInBolsa()) {
    		WebElement lineaArticulo = getLineaArticle(articulo.getReferencia(), driver);
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
    
    public static boolean validateArticleImport(PreciosArticulo preciosScreen, String precioArticulo) {
    	return (preciosScreen.definitivo == ImporteScreen.getFloatFromImporteMangoScreen(precioArticulo));
    }
    
    public static boolean validateArticlesAndDiscount(DataBag dataBag, Descuento descuento, WebDriver driver) throws Exception {
    	for (ArticuloScreen articulo : dataBag.getListArticlesTypeViewInBolsa()) {
    		WebElement lineaArticulo = getLineaArticle(articulo.getReferencia(), driver);
    		if (lineaArticulo==null) {
    			return false;
    		}
    		
    		PreciosArticulo preciosArticuloScreen = getPreciosArticuloResumen(lineaArticulo);
    		if (articulo.getValePais()!=null) {
	    		if (!PageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuento)) {
	    			return false;
	    		}
    		} else {
    			Descuento descuentoZero = new Descuento(0);
	    		if (!PageCheckoutWrapper.validateDiscountOk(preciosArticuloScreen, descuentoZero)) {
	    			return false;
	    		}
    		}
    	}
    	
    	return true;
    }
    
    public static String getPrecioSubTotalFromResumen(final WebDriver driver) throws Exception {
        String precioTotal = PageCheckoutWrapper.formateaPrecioTotal(XPathPrecioSubTotal, driver);
        return precioTotal;
    }
    
    /**
     * @return precios del artículo en la página de resumen
     */
    private static PreciosArticulo getPreciosArticuloResumen(WebElement articuloWeb) {
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
    
    private static float getFloatFromImporteScreen(WebElement importeWeb) {
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

	public static boolean isVisibleRadioTrjGuardada(String metodoPago, WebDriver driver)  {
		String xpathRadioTrjGuardada = getXPathRadioTarjetaGuardada(metodoPago);
		return (state(Visible, By.xpath(xpathRadioTrjGuardada), driver).check());
	}

	public static void clickRadioTrjGuardada(WebDriver driver) {
		click(By.xpath(XPathRadioTrjGuardada), driver).exec();
	}

	public static void inputCvcTrjGuardadaIfVisible(String cvc, WebDriver driver) {
		if (state(Visible, By.xpath(XPathCvcTrjGuardada), driver).check()) {
			WebElement input = driver.findElement(By.xpath(XPathCvcTrjGuardada));
			input.clear();
			input.sendKeys(cvc);
		}
	}

    public static void clickSolicitarFactura(WebDriver driver) {
        driver.findElement(By.xpath(XPathLinkSolicitarFactura)).click();
    }
    
    public static boolean isMarkedQuieroFactura(WebDriver driver) {
        WebElement radio = driver.findElement(By.xpath(XPathLinkSolicitarFactura));
        if (radio.getAttribute("checked")!=null && radio.getAttribute("checked").contains("true")) {
            return true;
        }
        return false;
    }
    
    public static void clickEditDirecEnvio(WebDriver driver) throws Exception {
    	waitForPageLoaded(driver); //For avoid StaleElementReferenceException
        driver.findElement(By.xpath(XPathLinkEditDirecEnvio)).click();
    }

	public static boolean isArticulos(WebDriver driver) {
		return (state(Present, By.xpath(XPathFirstArticulo), driver).check());
	}

	/**
	 * Función que, partiendo de la página con los métodos de pago del checkout, realiza la confirmación del pago
	 * (Simplemente selecciona "Confirmar pago")
	 */
	public static void confirmarPagoFromMetodos(WebDriver driver) throws Exception {
		clickConfirmarPago(driver);
	}

	public static boolean isVisibleErrorRojoInputPromoUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathErrorPromo), driver).wait(maxSeconds).check() &&
				driver.findElement(By.xpath(XPathErrorPromo)).getAttribute("style").contains("color: red"));
	}

	public static void inputVendedorVOTF(String codigoVendedor, WebDriver driver) {
		driver.findElement(By.xpath(XPathInputVendedorVOTF)).clear();
		driver.findElement(By.xpath(XPathInputVendedorVOTF)).sendKeys(codigoVendedor);
	}

	public static void acceptInputVendedorVOTF(WebDriver driver) {
		click(By.xpath(XPathButtonAcceptVendedorVOTF), driver).exec();
	}

	public static boolean isVisibleInputVendedorVOTF(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputVendedorVOTF), driver).check());
	}

	public static boolean isVisibleCodigoVendedorVOTF(String codigoVendedor, WebDriver driver) {
		String xpathVendedor = getXPathCodigoVendedorVOTF(codigoVendedor);
		return (state(Visible, By.xpath(xpathVendedor), driver).check());
	}

	public static boolean isDataChequeRegalo(ChequeRegalo chequeRegalo, WebDriver driver) {
		if (!driver.findElement(By.xpath(XPathNombreChequeRegalo)).getText().contains(chequeRegalo.getNombre()) ||
			!driver.findElement(By.xpath(XPathNombreChequeRegalo)).getText().contains(chequeRegalo.getApellidos()) ||
			!driver.findElement(By.xpath(XPathPrecioChequeRegalo)).getText().contains(chequeRegalo.getImporte().toString().replace("euro", "")) ||
			!driver.findElement(By.xpath(XPathMensajeChequeRegalo)).getText().contains(chequeRegalo.getMensaje())) {
			return false;
		}
		return true;
	}
}