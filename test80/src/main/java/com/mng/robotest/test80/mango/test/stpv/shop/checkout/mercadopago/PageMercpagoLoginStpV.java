package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoLogin;

/**
 * Page-1: Identificación en Mercadopago
 * @author jorge.munoz
 *
 */
public class PageMercpagoLoginStpV {

	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece la página de identificación de Mercadopago",
    		PageMercpagoLogin.isPage(driver), State.Defect);
    	validations.add(
    		"En la página figuran los campos de identificación (email + password)",
    		PageMercpagoLogin.isInputUserVisible(driver) &&
            PageMercpagoLogin.isInputPasswordVisible(driver), State.Defect);
    	return validations;
    }
    
	@Step (
		description="Introducir el usuario/password de mercadopago (#{pago.getUseremail()} / #{pago.getPasswordemail()}) + click botón \"Ingresar\"",
		expected=
			"Aparece alguna de las páginas:<br>" +
	        " - Elección medio pago<br>" +
	        " - Introducción CVC")
    public static void loginMercadopago(Pago pago, Channel channel, WebDriver driver) 
    throws Exception {
        PageMercpagoLogin.sendInputUser(driver, pago.getUseremail());
        PageMercpagoLogin.sendInputPassword(driver, pago.getPasswordemail());
        PageMercpagoLogin.clickBotonContinuar(driver);
        
        PageMercpagoDatosTrjStpV pageMercpagoDatosTrjStpV = PageMercpagoDatosTrjStpV.newInstance(channel, driver);
        pageMercpagoDatosTrjStpV.validaIsPage(0);
    }
}
