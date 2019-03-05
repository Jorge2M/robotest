package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroNinos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;


public class PageRegistroNinosStpV {
    
	@Validation
    public static ListResultValidation validaIsPageWithNinos(int numNinos, DataFmwkTest dFTest) {
		ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsToWait = 5;
    	validations.add(
    		"Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>",
    		PageRegistroNinos.isPageUntil(dFTest.driver, maxSecondsToWait), State.Defect);
    	validations.add(
    		"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			PageRegistroNinos.getNumInputsNameNino(dFTest.driver)==numNinos, State.Defect);
    	return validations;        
    }
    
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
        expected="Aparece la página de introducción de datos de la dirección")
    public static void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais, DataFmwkTest dFTest) 
    throws Exception {
        PageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2/*nTimes*/, dFTest.driver);
        PageRegistroNinos.clickContinuar(dFTest.driver);               
                
        //Validaciones
        PageRegistroDirecStpV.isPageFromPais(pais, dFTest);
        
        //Validaciones estándar.
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
    }
}
