package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarna;

public class SecKlarnaStpV {
    
	private final SecKlarna secKlarna;
	private final Channel channel;
	private final AppEcom app;
	private final WebDriver driver;
	
	public SecKlarnaStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.secKlarna = new SecKlarna(channel, driver);
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	@Step (
		description="Introducir Nº personal Klarna (#{numPersonalKlarna})", 
        expected="El dato se introduce correctamente")
    public void inputNumPersonal(String numPersonalKlarna) {
		secKlarna.waitAndinputNumPersonal(2, numPersonalKlarna);
    }
    
	@Step (
		description="Click botón \"Search Address\"", 
        expected="Aparece la capa de \"Klarna Invoice\" con datos correctos")
    public void searchAddress(Pago pago) throws Exception {
        secKlarna.clickSearchAddress();
        checkIsVisibleModalDirecciones(pago);
    }
	
	@Validation
	private ChecksTM checkIsVisibleModalDirecciones(Pago pago) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 3;
      	validations.add(
    		"Aparece el modal de las direcciones de Klarna (lo esperamos hasta " + maxSeconds + " segundos)",
    		secKlarna.isModalDireccionesVisibleUntil(maxSeconds), State.Defect);
      	validations.add(
    		"Aparecen los datos asociados al Nº persona: " + pago.getNomklarna() + " - " + pago.getDirecklarna() + " - " + pago.getProvinklarna(),
    		secKlarna.getTextNombreAddress().contains(pago.getNomklarna()) &&
            secKlarna.getTextDireccionAddress().contains(pago.getDirecklarna()) &&
            secKlarna.getTextProvinciaAddress().contains(pago.getProvinklarna()), 
    		State.Warn);
		return validations;
	}
    
	@Step (
		description="Seleccionar el botón \"Confirm Address\"", 
        expected="Se modifica la dirección correctamente")
    public void confirmAddress(Pago pago) throws Exception {
        secKlarna.clickConfirmAddress();
        checkIsInvisibleModalDirecciones(2);
        
        //Esta validación sólo podemos realizarla en Desktop porque en el caso de móvil todavía no figura la dirección en pantalla 
        if (channel==Channel.desktop) {
        	checkShippingAddress(pago);
        }        
    }
	
	@Validation (
		description="Desaparece el modal de las direcciones de Klarna (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkIsInvisibleModalDirecciones(int maxSeconds) {
	    return (secKlarna.isModalDireccionesInvisibleUntil(maxSeconds));
	}
	
	@Validation (
		description="Como Shipping Address figura la de Klarna: #{pago.getNomklarna()} - #{pago.getDirecklarna()} - #{pago.getProvinklarna()}",
		level=State.Warn)
	private boolean checkShippingAddress(Pago pago) {
		Page1DktopCheckout page1DktopCheckout = new Page1DktopCheckout(channel, app, driver);
		return (
			page1DktopCheckout.getTextNombreEnvio().contains(pago.getNomklarna()) &&
			page1DktopCheckout.getTextDireccionEnvio().contains(pago.getDirecklarna()) &&
			page1DktopCheckout.getTextPoblacionEnvio().replace(" ", "").contains(pago.getProvinklarna().replace(" ", "")));
	}
}
