package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2DatosPagoCheckoutMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.ModalDroppointsStpV;

/**
 * @author jorge.munoz
/* Pasos/Validaciones correspondientes a la página-1 del checkout (1. Envío) en móvil-web
 */

public class Page1EnvioCheckoutMobilStpV {

    public static ModalDroppointsStpV modalDroppoints;
    
    @Validation
    public static ChecksResult validateIsPage(boolean userLogged, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página correspondiente al paso-1",
			WebdrvWrapp.isElementPresent(driver, By.xpath("//h2[@data-toggle='step1']")), State.Warn);
	 	validations.add(
			"Aparece el botón de introducción del código promocional",
			Page1EnvioCheckoutMobil.isVisibleInputCodigoPromoUntil(0, driver), State.Defect);
	 	if (!userLogged) {
		 	validations.add(
				"Aparece seleccionado el método de envío \"Estándar\"",
				WebdrvWrapp.isElementPresent(driver, By.xpath("//div[@data-analytics-id='standard' and @class[contains(.,'checked')]]")), 
				State.Warn);
	 	}
	 	
	 	return validations;
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b> (previamente, si no lo estamos, nos posicionamos en el apartado \"1. Envio\")", 
        expected="Se selecciona el método de envío correctamente")
    public static void selectMetodoEnvio(TipoTransporte tipoTransporte, @SuppressWarnings("unused") String nombrePago, 
    									 DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        Page1EnvioCheckoutMobil.selectMetodoAfterPositioningIn1Envio(tipoTransporte, driver);
        if (!tipoTransporte.isEntregaDomicilio()) {
        	if (ModalDroppoints.isErrorMessageVisibleUntil(driver)) {
        		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
        	}
        }

        //Validaciones
        validaBlockSelected(tipoTransporte, 3, driver);
        if (tipoTransporte.isEntregaDomicilio()) {
            modalDroppoints.validaIsNotVisible(Channel.movil_web, driver);
        } else {
            modalDroppoints.validaIsVisible(Channel.movil_web, driver);
        }
    }    
    
    @Validation (
    	description="Queda seleccionado el bloque correspondiete a <b>#{tipoTransporte}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    public static boolean validaBlockSelected(TipoTransporte tipoTransporte, int maxSecondsWait, WebDriver driver) 
    throws Exception {
        return (Page1EnvioCheckoutMobil.isBlockSelectedUntil(tipoTransporte, maxSecondsWait, driver));
    }
    
    @Step (
    	description="Seleccionar el botón \"Continuar\"", 
        expected="Aparece la página asociada al Paso-2")
    public static void clickContinuarToMetodosPago(DataCtxShop dCtxSh, boolean saldoEnCuenta, WebDriver driver) 
    throws Exception {
    	Page1EnvioCheckoutMobil.clickContinuar(driver);
        PageCheckoutWrapperStpV.validateLoadingDisappears(10, driver);
        checkAppearsStep2(driver);
        if (!saldoEnCuenta) {
        	checkAppearsPageWithPaymentMethods(dCtxSh.pais, dCtxSh.channel, driver);
        }
    }
    
    @Validation (
    	description="Aparece la página asociada al Paso-2",
    	level=State.Defect)
    private static boolean checkAppearsStep2(WebDriver driver) {
    	return (Page2DatosPagoCheckoutMobil.isPageUntil(1, driver));
    }
    
    @Validation (
    	description="Están presentes los métodos de pago",
    	level=State.Defect)
    private static boolean checkAppearsPageWithPaymentMethods(Pais pais, Channel channel, WebDriver driver) {
        return (PageCheckoutWrapper.isPresentMetodosPago(channel, driver));
    }
    
    @Validation
    public static ChecksResult validaResultImputPromoEmpl(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 2;
	 	validations.add(
			"Aparece el descuento total aplicado al empleado (en menos de " + maxSecondsWait + " segundos)",
			Page1EnvioCheckoutMobil.isVisibleDescuentoEmpleadoUntil(driver, maxSecondsWait), State.Warn);
	 	validations.add(
			"Aparece un descuento de empleado mayor que 0",
			Page1EnvioCheckoutMobil.validateDiscountEmpleadoNotNull(driver), State.Warn);
	 	return validations;
    }
    
    @Step (
    	description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
        expected="La franja horaria se selecciona correctamente")
    public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
    	Page1EnvioCheckoutMobil.selectFranjaHorariaUrgente(posicion, driver);
    }
}
