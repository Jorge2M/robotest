package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarna;

public class SecKlarnaStpV {
    
	@Step (
		description="Introducir Nº personal Klarna (#{numPersonalKlarna})", 
        expected="El dato se introduce correctamente")
    public static void inputNumPersonal(String numPersonalKlarna, Channel channel, WebDriver driver) {
		SecKlarna.waitAndinputNumPersonal(driver, 2, numPersonalKlarna, channel);
    }
    
	@Step (
		description="Click botón \"Search Address\"", 
        expected="Aparece la capa de \"Klarna Invoice\" con datos correctos")
    public static void searchAddress(Pago pago, WebDriver driver) throws Exception {
        SecKlarna.clickSearchAddress(driver);
        checkIsVisibleModalDirecciones(pago, driver);
    }
	
	@Validation
	private static ChecksTM checkIsVisibleModalDirecciones(Pago pago, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 3;
      	validations.add(
    		"Aparece el modal de las direcciones de Klarna (lo esperamos hasta " + maxSeconds + " segundos)",
    		SecKlarna.isModalDireccionesVisibleUntil(maxSeconds, driver), State.Defect);
      	validations.add(
    		"Aparecen los datos asociados al Nº persona: " + pago.getNomklarna() + " - " + pago.getDirecklarna() + " - " + pago.getProvinklarna(),
    		SecKlarna.getTextNombreAddress(driver).contains(pago.getNomklarna()) &&
            SecKlarna.getTextDireccionAddress(driver).contains(pago.getDirecklarna()) &&
            SecKlarna.getTextProvinciaAddress(driver).contains(pago.getProvinklarna()), 
    		State.Warn);
		return validations;
	}
    
	@Step (
		description="Seleccionar el botón \"Confirm Address\"", 
        expected="Se modifica la dirección correctamente")
    public static void confirmAddress(Pago pago, Channel channel, WebDriver driver) throws Exception {
        SecKlarna.clickConfirmAddress(driver, channel);
        checkIsInvisibleModalDirecciones(2, driver);
        
        //Esta validación sólo podemos realizarla en Desktop porque en el caso de móvil todavía no figura la dirección en pantalla 
        if (channel==Channel.desktop) {
        	checkShippingAddress(pago, driver);
        }        
    }
	
	@Validation (
		description="Desaparece el modal de las direcciones de Klarna (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean checkIsInvisibleModalDirecciones(int maxSeconds, WebDriver driver) {
	    return (SecKlarna.isModalDireccionesInvisibleUntil(maxSeconds, driver));
	}
	
	@Validation (
		description="Como Shipping Address figura la de Klarna: #{pago.getNomklarna()} - #{pago.getDirecklarna()} - #{pago.getProvinklarna()}",
		level=State.Warn)
	private static boolean checkShippingAddress(Pago pago, WebDriver driver) {
		return (
			Page1DktopCheckout.getTextNombreEnvio(driver).contains(pago.getNomklarna()) &&
            Page1DktopCheckout.getTextDireccionEnvio(driver).contains(pago.getDirecklarna()) &&
            Page1DktopCheckout.getTextPoblacionEnvio(driver).replace(" ", "").contains(pago.getProvinklarna().replace(" ", "")));
	}
}
