package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;

public class Page2IdentCheckoutStpV {
    
	@Validation
    public static ChecksResult validateIsPage(boolean emailYetExists, int maxSecondsWait, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página-2 de introducción de datos de la dirección del cliente (la esperamos hasta " + maxSecondsWait + ")<br>",
			Page2IdentCheckout.isPageUntil(maxSecondsWait, driver), State.Defect);
	 	validations.add(
			"Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña",
			Page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists, driver), State.Defect);
	 	return validations;
    }
    
	@Step (
		description="Introducimos los datos del cliente según el país", 
        expected="Se hace clickable el botón \"Continuar\"",
        saveImagePage=SaveWhen.Always)
    public static HashMap<String, String> inputDataPorDefecto(Pais pais, String emailUsr, boolean inputDireccCharNoLatinos, WebDriver driver) 
    throws Exception {
        HashMap<String, String> datosRegistro = 
            Page2IdentCheckout.inputDataPorDefectoSegunPais(pais, emailUsr, inputDireccCharNoLatinos, false, driver);
        TestCaseData.getDatosCurrentStep().addDescriptionText(". Utilizando los datos: "+ UtilsMangoTest.listaCamposHTML(datosRegistro)); 
        checkIsVisibleContiueButton(5, driver);
        return datosRegistro;
    }
	
	@Validation (
		description="Se hace clickable el botón \"Continuar\" (lo esperamos hasta #{maxSecondsWait})",
		level=State.Defect)
	private static boolean checkIsVisibleContiueButton(int maxSecondsWait, WebDriver driver) {
	    return (Page2IdentCheckout.isContinuarClickableUntil(maxSecondsWait, driver));
	}
    
	@Step (
		description="Seleccionamos el botón \"Continuar\"",
		expected="Aparece la página de Checkout",
		saveImagePage=SaveWhen.Always)
    public static void clickContinuar(boolean userRegistered, DataBag dataBag, Channel channel, WebDriver driver)
    throws Exception {
        int maxSecondsToWait = 20;
        Page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait, driver);   
        PageCheckoutWrapperStpV.validateIsFirstPage(userRegistered, dataBag, channel, driver);
    }
	
	@Step (
		description="Seleccionamos el botón \"Continuar\" (hay carácteres no-latinos introducidos en la dirección)",
        expected="Aparece un aviso indicando que en la dirección no pueden figurar carácteres no-latinos",
        saveImagePage=SaveWhen.Always)
    public static void clickContinuarAndExpectAvisoDirecWithNoLatinCharacters(WebDriver driver)
    throws Exception {
        int maxSecondsToWait = 2;
        Page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait, driver);      
        checkAvisoDireccionWithNoLatinCharacters(driver);
    }
            
    @Validation (
    	description="Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos",
    	level=State.Defect)
    private static boolean checkAvisoDireccionWithNoLatinCharacters(WebDriver driver) {
        return (Page2IdentCheckout.isDisplayedAvisoAduanas(driver));
    }
    
    @Validation
    public static ChecksResult validaRGPDText(Pais pais, WebDriver driver) {  
    	ChecksResult validations = ChecksResult.getNew();
		if (pais.getRgpd().equals("S")) {
		 	validations.add(
				"El texto legal de RGPD <b>SI</b> existe para el pais " + pais.getCodigo_pais() + "<br>",
				Page2IdentCheckout.isTextoLegalRGPDVisible(driver), State.Defect);
		}
		else {
		 	validations.add(
				"El texto legal de RGPD <b>NO</b> existe para el pais " + pais.getCodigo_pais() + "<br>",
				!Page2IdentCheckout.isTextoLegalRGPDVisible(driver), State.Defect);
		}
		
		return validations;
	}
}
