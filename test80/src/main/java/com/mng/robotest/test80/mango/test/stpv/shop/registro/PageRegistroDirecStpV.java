package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;

import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRegistroAddressData;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroDirec;

public class PageRegistroDirecStpV {
    
	@Validation
    public static ChecksResult isPageFromPais(Pais pais, DataFmwkTest dFTest) {
		ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 3;
    	validations.add(
    		"Aparece la página de introducción de datos de la dirección (la esperamos un máximo de " + maxSecondsWait + " segundos)",
    		PageRegistroAddressData.isPageUntil(dFTest.driver, maxSecondsWait), State.Warn);
    	validations.add(
    		"Si existe el desplebagle de países, en él aparece el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
    		!PageRegistroAddressData.existsDesplegablePaises(dFTest.driver) || 
    		PageRegistroAddressData.isOptionPaisSelected(dFTest.driver, pais.getCodigo_pais()), State.Warn);
    	return validations;
    }
    
	@Step (            
		description="Introducir los datos correctos para el país #{pais.getNombre_pais()}", 
        expected="No aparece ningún mensaje de error")
    public static void sendDataAccordingCountryToInputs(HashMap<String,String> dataRegistro, Pais pais, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        PageRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro, pais, channel, dFTest.driver);
        validateInputDataOk(dFTest);
    }
    
    @Validation(
    	description="No aparece ningún mensaje de error asociado a los campos de entrada",
    	level=State.Defect)
    public static boolean validateInputDataOk(DataFmwkTest dFTest) {
    	return (PageRegistroDirec.getNumberMsgInputInvalid(dFTest.driver) <= 0);
    }
    
    @Step (
    	description= "Seleccionar el botón \"<b>Finalizar</b>\"", 
        expected="Aparece la página final del proceso de registro")
    public static void clickFinalizarButton(DataFmwkTest dFTest) 
    throws Exception {
        PageRegistroDirec.clickFinalizarButton(dFTest.driver);
        int maxSecondsWait = 5;
        PageRegistroFinStpV.isPageUntil(maxSecondsWait, dFTest);
    }
}
