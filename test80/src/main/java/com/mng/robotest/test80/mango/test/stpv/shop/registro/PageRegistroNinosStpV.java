package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroNinos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;


public class PageRegistroNinosStpV {
    
	@Validation
    public static ChecksResult validaIsPageWithNinos(int numNinos, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 5;
    	validations.add(
    		"Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
    		PageRegistroNinos.isPageUntil(driver, maxSecondsToWait), State.Defect);
    	validations.add(
    		"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			PageRegistroNinos.getNumInputsNameNino(driver)==numNinos, State.Defect);
    	return validations;        
    }
    
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
        expected="Aparece la página de introducción de datos de la dirección")
    public static void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais, WebDriver driver) 
    throws Exception {
        PageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2, driver);
        PageRegistroNinos.clickContinuar(driver);
        PageRegistroDirecStpV.isPageFromPais(pais, driver);
        
        //Validaciones estándar.
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
}
