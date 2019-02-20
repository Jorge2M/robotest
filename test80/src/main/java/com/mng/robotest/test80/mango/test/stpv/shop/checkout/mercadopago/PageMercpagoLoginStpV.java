package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoLogin;

/**
 * Page-1: Identificación en Mercadopago
 * @author jorge.munoz
 *
 */
public class PageMercpagoLoginStpV {

	@Validation
    public static ListResultValidation validateIsPage(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Aparece la página de identificación de Mercadopago<br>",
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

        //Validaciones
        PageMercpagoDatosTrjStpV.validaIsPage(channel, driver);
    }
}
