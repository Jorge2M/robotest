package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecSoyNuevo;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecSoyNuevo.ActionNewsL;

public class SecSoyNuevoStpV {
    
	@Step (
		description="Desmarcamos el check NewsLetter, introducmos el email y seleccionamos \"Continuar\"", 
        expected="Aparece la página de introducción de datos del usuario")
    public static void inputEmailAndContinue(String email, boolean emailExistsYet, AppEcom appE, boolean userRegistered, 
    											  Pais pais, Channel channel, WebDriver driver) throws Exception {
        SecSoyNuevo.setCheckPubliNewsletter(driver, ActionNewsL.deactivate, channel);
        SecSoyNuevo.inputEmail(email, driver);
        SecSoyNuevo.clickContinue(channel, driver);
        
        Page2IdentCheckoutStpV.validateIsPage(emailExistsYet, 2, driver);
        if (!userRegistered && appE != AppEcom.votf) {
        	Page2IdentCheckoutStpV.validaRGPDText(pais, driver);
        }
    }
    
    @SuppressWarnings("static-access")
    @Validation
	public static ChecksResult validaRGPDText(DataCtxShop dCtxSh, WebDriver driver) {  
    	ChecksResult validations = ChecksResult.getNew();
		if (dCtxSh.pais.getRgpd().equals("S")) {
		 	validations.add(
				"El texto de info de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(driver), State.Defect);
		 	validations.add(
				"El texto legal de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(driver), State.Defect);
		} else {
		 	validations.add(
				"El texto de info de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				!Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(driver), State.Defect);			
		 	validations.add(
				"El texto legal de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				!Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(driver), State.Defect);		
		}
		
		return validations;
	}
}
