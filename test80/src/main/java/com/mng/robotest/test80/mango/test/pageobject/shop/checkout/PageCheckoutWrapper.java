package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Descuento;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci.SecTarjetaPci;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings({"static-access"})
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "CHECKOUT"
 * @author jorge.munoz 
 */
public class PageCheckoutWrapper {
 
    public static Page1DktopCheckout page1DktopCheckout;
    public static Page1EnvioCheckoutMobil page1MobilCheckout;
    public static Page2DatosPagoCheckoutMobil page2MobilCheckout;
    public static ModalAvisoCambioPais modalAvisoCambioPais;
    private SecTarjetaPci secTarjetaPci;
    
    //Abarca cualquier div de loading
    static String XPathDivLoading = "//div[@class[contains(.,'panel_loading')] or @class[contains(.,'container-full-centered-loading')] or @class[contains(.,'loading-panel')]]";
    
    public SecTarjetaPci getSecTarjetaPci(Channel channel, WebDriver driver) {
    	if (this.secTarjetaPci==null) {
    		this.secTarjetaPci = SecTarjetaPci.makeSecTarjetaPci(channel, driver);
    	}
    	
    	return this.secTarjetaPci;
    }

    public static boolean isFirstPageUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.isVisibleLink1EnvioUntil(driver, maxSecondsToWait));
        }
        
        return (page1DktopCheckout.isPageUntil(maxSecondsToWait, driver));    
    }
    
    public void inputNumberPci(String numtarj, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).inputNumber(numtarj);
    }
    
    public void inputTitularPci(String titular, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).inputTitular(titular);
    }
    
    public void selectMesByVisibleTextPci(String mesCaducidad, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).selectMesByVisibleText(mesCaducidad);
    }
    
    public void selectAnyByVisibleTextPci(String anyCaducidad, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).selectAnyByVisibleText(anyCaducidad);
    }    
    
    public void inputCvcPci(String cvc, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).inputCvc(cvc);
    }
    
    //Específico para método de Pago Codensa (Colombia)
    public void inputDniPci(String dni, Channel channel, WebDriver driver) {
    	getSecTarjetaPci(channel, driver).inputDni(dni);
    }
    
    public static void inputCodigoPromoAndAccept(String codigoPromo, Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.inputCodigoPromoAndAccept(codigoPromo, driver);
        } else {
            page1DktopCheckout.showInputCodigoPromoAndAccept(codigoPromo, driver);
        }
    }
    
    public static void clickEliminarValeIfExists(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.clickEliminarValeIfExists(driver);
        } else {
            page1DktopCheckout.clickEliminarValeIfExists(driver);
        }
    }
    
    public static boolean isPresentInputApellidoPromoEmplUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.isPresentInputApellidoPromoEmplUntil(maxSecondsToWait, driver));
        }
        return (page1DktopCheckout.isPresentInputApellidoPromoEmplUntil(maxSecondsToWait, driver));
    }
    
    public static boolean isPresentInputDNIPromoEmpl(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.isPresentInputDNIPromoEmpl(driver));
        }
        return (page1DktopCheckout.isPresentInputDNIPromoEmpl(driver));
    }    
    
    public static boolean isPresentDiaNaciPromoEmpl(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.isPresentDiaNaciPromoEmpl(driver));
        }
        return (page1DktopCheckout.isPresentDiaNaciPromoEmpl(driver));
    }    
    
    public static void inputDNIPromoEmpl(String dni, Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.inputDNIPromoEmpl(dni, driver);
        } else {
            page1DktopCheckout.inputDNIPromoEmpl(dni, driver);
        }
    }
    
    public static void inputApellidoPromoEmpl(String apellido, Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.inputApellidoPromoEmpl(apellido, driver);
        } else {
            page1DktopCheckout.inputApellidoPromoEmpl(apellido, driver);
        }
    }    

	final static String XpathButtonForApplyLoyaltyPoints = "//button[@class[contains(.,'redeem-likes')] and @type='button']";
	public static boolean isVisibleButtonForApplyLoyaltyPoints(WebDriver driver) {
		return (state(Visible, By.xpath(XpathButtonForApplyLoyaltyPoints), driver).check());
	}

	public static float applyAndGetLoyaltyPoints(WebDriver driver) {
		By byApplyButton = By.xpath(XpathButtonForApplyLoyaltyPoints);
		WebElement buttonLoyalty = SeleniumUtils.getElementsVisible(driver, byApplyButton).get(0);
		String textButtonApply = buttonLoyalty.getAttribute("innerHTML");
		String importeButton = ImporteScreen.normalizeImportFromScreen(textButtonApply);
		click(By.xpath(XpathButtonForApplyLoyaltyPoints), driver).exec();
		PageCheckoutWrapper.isNoDivLoadingUntil(1, driver);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importeButton));
	}

	final static String XPathDiscountLoyaltyAppliedMobil = "//span[@class='redeem-likes__discount']";
	public static float getDiscountLoyaltyAppliedMobil(WebDriver driver) {
		By byDiscountApplied = By.xpath(XPathDiscountLoyaltyAppliedMobil);
		if (state(Visible, byDiscountApplied, driver).check()) {
			String discountApplied = driver.findElement(byDiscountApplied).getAttribute("innerHTML");
			return (ImporteScreen.getFloatFromImporteMangoScreen(discountApplied));
		}	
		return 0;
	}

    /**
     * @param fechaNaci en formato "dd-mm-aaaa"
     */
    public static void selectFechaNacPromoEmpl(String fechaNaci, Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.selectFechaNacPromoEmpl(fechaNaci, driver);
        } else {
            page1DktopCheckout.selectFechaNacPromoEmpl(fechaNaci, driver);
        }
    }
    
    public static void clickGuardarPromo(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.clickAceptarPromo(driver);
        } else {
            page1DktopCheckout.clickGuardarPromo(driver);
        }
    }
    
    public static void clickButtonAceptarPromoEmpl(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.clickButtonAceptarPromoEmpl(driver);
        } else {
            page1DktopCheckout.clickGuardarPromo(driver);
        }
    }
    
    public static String getImporteDescuentoEmpleado(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.getImporteDescuentoEmpleado(driver));
        }
        return (page1DktopCheckout.getImporteDescuentoEmpleado(driver));
    }
    
    public static boolean isVisibleDescuentoEmpleadoUntil(Channel channel, WebDriver driver, int secondsToWait) {
        if (channel==Channel.movil_web) {
            return (page1MobilCheckout.isVisibleDescuentoEmpleadoUntil(driver, secondsToWait));
        }
        return (page1DktopCheckout.isVisibleDescuentoEmpleadoUntil(driver, secondsToWait)); 
    }

    /**
     * @return si el número de métodos de pago visualizados en pantalla es el correcto
     */
    public static boolean isNumMetodosPagoOK(Pais pais, AppEcom app, Channel channel, boolean isEmpl, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return page2MobilCheckout.isNumMetodosPagoOK(pais, app, isEmpl, driver);
        }
        return page1DktopCheckout.isNumMetodosPagoOK(driver, pais, app, isEmpl);
    }
    
    public static boolean isNumpagos(int numPagosExpected, Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            page2MobilCheckout.isNumpagos(numPagosExpected, driver);
        }
        return page1DktopCheckout.isNumpagos(numPagosExpected, driver);
    }
    
    public static boolean isPresentMetodosPago(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return page2MobilCheckout.isPresentMetodosPago(driver);
        }
        return page1DktopCheckout.isPresentMetodosPago(driver);
    }    
    
    public static boolean isMetodoPagoPresent(String metodoPago, Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return page2MobilCheckout.isMetodoPagoPresent(metodoPago, driver);
        }
        return page1DktopCheckout.isMetodoPagoPresent(metodoPago, driver);
    }
    
    public static void despliegaMetodosPago(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page2MobilCheckout.despliegaMetodosPago(driver);
        } else {
            page1DktopCheckout.despliegaMetodosPago(driver);
        }
    }

    public static String getPrecioTotalFromResumen(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            return (page2MobilCheckout.getPrecioTotalFromResumen(driver));
        }
        return (page1DktopCheckout.getPrecioTotalFromResumen(driver));
    }
    
    public static String getPrecioTotalSinSaldoEnCuenta(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            return (page2MobilCheckout.getPrecioTotalSinSaldoEnCuenta(driver));
        }
        return (page1DktopCheckout.getPrecioTotalFromResumen(driver));
    }
    
    public static String getAlmacenFromNoProdEntorn(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            return "";
        }
        return (page1DktopCheckout.getAlmacenFromNoProdEntorn(driver)); 
    }

	public static boolean waitUntilNoDivLoading(WebDriver driver, int seconds) {
		return (state(Invisible, By.xpath(XPathDivLoading), driver)
				.wait(seconds).check());
	}

	public static boolean isNoDivLoadingUntil(int seconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathDivLoading), driver)
				.wait(seconds).check());
	}

    /**
     * @return el valor del input value del método de pago (en casos concretos no se corresponde con el nombre del método de pago) 
     */
    public static String getMethodInputValue(String metodoPago, Channel channel) {
        if (metodoPago.contains("TRANSFERENCIA BANCARIA")) {
            return ("TRANSFERENCIA");
        }
        if (metodoPago.contains("наличными через терминал")) {
            return ("YANDEX OFFLINE");
        }
//        if (channel==Channel.desktop) {
//            if (metodoPago.contains("ОПЛАТА ПРИ ПОЛУЧЕНИИ")) {
//                return ("ContraReembolsoSTD");
//            }
//        }

        if (metodoPago.contains("PŁATNOŚĆ PRZY ODBIORZE") ||
            metodoPago.contains("PLATBA NA DOBÍRKU") ||
            metodoPago.contains("UTÁNVÉTELES FIZETÉS") ||
            metodoPago.contains("PLATA PRIN RAMBURS")) {
            return ("ContraReembolsoSTD");
        }
        
        if (metodoPago.contains("mercadopago_transferencia")) {
        	return ("mercadopago");
        }
        
        return metodoPago;
    }
    
    /**
     * Realizamos las acciones necesarias para forzar el click sobre un método de pago y esperamos a que desaparezcan las capas de loading
     */
    public static void forceClickMetodoPagoAndWait(String metodoPago, Pais pais, Channel channel, WebDriver driver) 
    throws Exception {
        if (channel==Channel.movil_web) {
            page2MobilCheckout.forceClickMetodoPagoAndWait(metodoPago, pais, driver);
        } else {
            page1DktopCheckout.forceClickMetodoPagoAndWait(metodoPago, pais, driver);
        }
    }
    
    public static boolean isAvailableTrjGuardada(String metodoPago, Channel channel, WebDriver driver) {
    	if (channel==Channel.movil_web) {
    		return (page2MobilCheckout.isVisibleRadioTrjGuardada(metodoPago, driver));
    	} else {
    		return (page1DktopCheckout.isVisibleRadioTrjGuardada(metodoPago, driver));
    	}
    }

	public static void clickRadioTrjGuardada(Channel channel, WebDriver driver) throws Exception {
		if (channel==Channel.movil_web) {
			page2MobilCheckout.clickRadioTrjGuardada(driver);
		} else {
			page1DktopCheckout.clickRadioTrjGuardada(driver);
		}
	}

	public static void inputCvcTrjGuardadaIfVisible(String cvc, Channel channel, WebDriver driver) { 
		if (channel==Channel.movil_web) {
			page2MobilCheckout.inputCvcTrjGuardadaIfVisible(cvc, driver);
		} else {
			page1DktopCheckout.inputCvcTrjGuardadaIfVisible(cvc, driver);
		}
	}
    
    public static void clickSolicitarFactura(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            page2MobilCheckout.clickSolicitarFactura(driver);
        } else {
            page1DktopCheckout.clickSolicitarFactura(driver);
        }
    }    
    
    public static boolean isMarkedQuieroFacturaDesktop(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page2MobilCheckout.isMarkedQuieroFactura(driver));
        }
        return (page1DktopCheckout.isMarkedQuieroFactura(driver));
    }
    
    public static void clickEditDirecEnvio(Channel channel, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            page1MobilCheckout.clickEditDirecEnvio(driver);
        } else {
            page1DktopCheckout.clickEditDirecEnvio(driver);
        }
    }
    
    public static boolean isArticulos(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page2MobilCheckout.isArticulos(driver));
        }
        return (page1DktopCheckout.isArticulos(driver));
    }
    
	public static float getImportSubtotalDesktop(WebDriver driver) throws Exception {
		String textImporte = Page1DktopCheckout.getPrecioSubTotalFromResumen(driver);
		return (ImporteScreen.getFloatFromImporteMangoScreen(textImporte));
	}
    
    public static void confirmarPagoFromMetodos(Channel channel, DataPedido dataPedido, WebDriver driver) throws Exception {
        PageCheckoutWrapper.getDataPedidoFromCheckout(dataPedido, channel, driver);
        if (channel==Channel.movil_web) {
            page2MobilCheckout.confirmarPagoFromMetodos(dataPedido, driver);
        } else {
            page1DktopCheckout.confirmarPagoFromMetodos(driver);
        }
    }
    
    public static boolean isVisibleBloquePagoNoTRJIntegradaUntil(Pago pago, Channel channel, int maxSecondsToWait, WebDriver driver) {
        if (channel==Channel.movil_web) {
            return (page2MobilCheckout.isVisibleTextoBajoPagoUntil(pago, channel, maxSecondsToWait, driver));
        }
        return (page1DktopCheckout.isVisibleBloquePagoNoTRJIntegradaUntil(pago, maxSecondsToWait, driver));
    }
    
    public static String getTextDireccionEnvioCompleta(Channel channel, WebDriver driver) {
        if (channel==Channel.movil_web) {
            if (Page1EnvioCheckoutMobil.isPageUntil(0, driver)) {
                return (page1MobilCheckout.getTextDireccionEnvioCompleta(driver));
            }
            return (page2MobilCheckout.getTextDireccionEnvioCompleta(driver));
        }
        
        return (page1DktopCheckout.getTextDireccionEnvioCompleta(driver));
    }
    
    public static void getDataPedidoFromCheckout(DataPedido dataPedido, Channel channel, WebDriver driver) throws Exception {
    	String direcEnvio = PageCheckoutWrapper.getTextDireccionEnvioCompleta(channel, driver);
    	if ("".compareTo(direcEnvio)!=0) {
    		dataPedido.setDireccionEnvio(direcEnvio);
    	}
    	dataPedido.setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver));
    	dataPedido.setCodigoAlmacen(getAlmacenFromNoProdEntorn(channel, driver));
    }
    
    public static String getDireccionEnvioCompleta(WebDriver driver, Channel channel) throws Exception {
        if (channel==Channel.movil_web) {
            return (Page1EnvioCheckoutMobil.getTextDireccionEnvioCompleta(driver));
        }
        return (Page1DktopCheckout.getTextDireccionEnvioCompleta(driver));
    }

    public static boolean direcEnvioContainsPais(String nombrePais, Channel channel, WebDriver driver) throws Exception {
        String direccionEnvio = getDireccionEnvioCompleta(driver, channel);
        return (direccionEnvio.toLowerCase().contains(nombrePais.toLowerCase()));
    }
    
    public static boolean direcEnvioContainsPais(Channel channel, String nombrePais, WebDriver driver) throws Exception {
        String direccionEnvio = getTextDireccionEnvioCompleta(channel, driver);
        return (direccionEnvio.toLowerCase().contains(nombrePais.toLowerCase()));
    }
    
    public static boolean isPresentBlockMetodo(TipoTransporte tipoTransporte, Channel channel, WebDriver driver) {
        switch (channel) {
        case desktop:
            return SecMetodoEnvioDesktop.isPresentBlockMetodo(tipoTransporte, driver);
        case movil_web:
        default:
            return Page1EnvioCheckoutMobil.isPresentBlockMetodo(tipoTransporte, driver);
        }
    }
    
    public static boolean validateDiscountOk(PreciosArticulo preciosArtScreen, Descuento descuento) {
    	switch (descuento.getDiscountOver()) {
    	case OriginalPrice:
    		return (validateDiscountOverOriginalPrice(preciosArtScreen, descuento.getPercentageDesc()));
    	case LastPriceOrSale:
    	default:
    		return (validateDiscountOverLastPriceOrSale(preciosArtScreen, descuento.getPercentageDesc()));
    	}
    }
    
    private static boolean validateDiscountOverOriginalPrice(PreciosArticulo preciosArtScreen, int porcMinDiscount) {
    	float porcMinDiscountLessOne = porcMinDiscount - 1; //Restamos 1 por un tema de precisión
    	float importeDescMinTeorico = preciosArtScreen.original * (1 - (porcMinDiscountLessOne/100));
    	return (preciosArtScreen.definitivo <= importeDescMinTeorico);
    }
    
    private static boolean validateDiscountOverLastPriceOrSale(PreciosArticulo preciosArtScreen, int porcMinDiscount) {
    	float porcMinDiscountLessOne = porcMinDiscount - 1; //Restamos 1 por un tema de precisión
    	float importeDescMinTeorico = preciosArtScreen.ultimaRebaja * (1 - (porcMinDiscountLessOne/100));
    	return (preciosArtScreen.definitivo <= importeDescMinTeorico);
    }
    
     /**
     * Función que vuelve a la página del checkout con los métodos de pago indicada en urlPagCheckout  
     * @param urlPagResumen URL correspondiente a la página de checkout
     */
    public static void backPageMetodosPagos(String urlPagCheckout, Channel channel, WebDriver driver) throws Exception {
        if (driver.getCurrentUrl().compareTo(urlPagCheckout)!=0) {
            driver.get(urlPagCheckout);
            acceptAlertIfExists(driver);
        }

        //En el caso de móvil existen 3 páginas de checkout y no tenemos claro si estamos en la de los métodos de pago 
        //así que si existe, clickamos el link a la página-2 del checkout con los métodos de pago        
        if (channel==Channel.movil_web) {
            Page2DatosPagoCheckoutMobil.clickLink2DatosPagoIfVisible(driver);
        }
    }
    
    /**
     * @return el importe normalizado a un String con un importe correctamente formateado
     */
    static final String XPathEnterosRelativeImporte = "//*[@class[contains(.,'ntero')]]";
    static final String XPathDecimalesRelativeImporte = "//*[@class[contains(.,'ecimal')]]";
    public static String formateaPrecioTotal(String xpathImporteCheckout, WebDriver driver) throws Exception {
    	for (int i=0; i<2; i++) {
    		try {
    			String precio = formateaPrecioTotalNoStaleSafe(xpathImporteCheckout, driver);
    			return precio;
    		}
    		catch (StaleElementReferenceException e) {
    			//
    		}
    	}
    	
    	return "";
    }
    
    private static String formateaPrecioTotalNoStaleSafe(String xpathImporteCheckout, WebDriver driver) throws Exception {
    	waitForPageLoaded(driver);
        String precioTotal = "";
        String xpathEnteros = xpathImporteCheckout + XPathEnterosRelativeImporte;
        List<WebElement> listEnteros = getElementsVisible(driver, By.xpath(xpathEnteros));
        for (WebElement entero : listEnteros) {
        	precioTotal+=entero.getAttribute("innerHTML");
        }
        
        String xpathDecimales = xpathImporteCheckout + XPathDecimalesRelativeImporte;
        List<WebElement> listDecimales = getElementsVisible(driver, By.xpath(xpathDecimales));
        for (WebElement decimal : listDecimales) {
        	precioTotal+=decimal.getAttribute("innerHTML");
        }
        
        return (precioTotal);
    }
    
    public static void selectBancoEPS(String nombreBanco, WebDriver driver) {
		SecEps.selectBanco(nombreBanco, driver);
	}

	public static boolean isBancoSeleccionado(String nombreBanco, WebDriver driver) {
		return SecEps.isBancoSeleccionado(nombreBanco, driver);
	}
}