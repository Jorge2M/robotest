package com.mng.robotest.test80.mango.test.stpv.shop.identificacion;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageRecuperaPasswd;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageRecuperaPasswdStpV {
    
	@Validation
    public static ChecksResult isPage(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsToWait = 2;
    	validations.add(
    		"Aparece la pantalla de recuperación de la contraseña (la esperamos hasta " + maxSecondsToWait + " segundos)",
    		PageRecuperaPasswd.isPageUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
    		"Aparece el campo para la introducción del correo",
    		PageRecuperaPasswd.isPresentInputCorreo(driver), State.Defect);
    	return validations;
    }
    
    @Step (
		description="Introducir el email <b>#{email}</b> y pulsar el botón \"Enviar\"", 
        expected="Aparece la página de cambio de contraseña")
    public static void inputMailAndClickEnviar(String email, WebDriver driver) throws Exception {
        PageRecuperaPasswd.inputEmail(email, driver);
        PageRecuperaPasswd.clickEnviar(driver);
        
        //Validaciones
        isPageCambioPassword(driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = false;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation
    private static ChecksResult isPageCambioPassword(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
    	validations.add(
        	"Aparece el mensaje de \"Revisa tu email\" (lo esperamos hasta " + maxSecondsToWait + " segundos)",
        	PageRecuperaPasswd.isVisibleRevisaTuEmailUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
        	"Aparece el botón \"Ir de Shopping\"",
        	PageRecuperaPasswd.isVisibleButtonIrDeShopping(driver), State.Defect);
    	return validations;
    }
}
