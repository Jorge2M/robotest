package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRegistroAddressData;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroDirec;

public class PageRegistroDirecStpV {
    
	@Validation
    public static ChecksTM isPageFromPais(Pais pais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
    	int maxSecondsWait = 3;
    	validations.add(
    		"Aparece la página de introducción de datos de la dirección (la esperamos un máximo de " + maxSecondsWait + " segundos)",
    		PageRegistroAddressData.isPageUntil(driver, maxSecondsWait), State.Warn);
    	validations.add(
    		"Si existe el desplebagle de países, en él aparece el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
    		!PageRegistroAddressData.existsDesplegablePaises(driver) || 
    		PageRegistroAddressData.isOptionPaisSelected(driver, pais.getCodigo_pais()), State.Warn);
    	return validations;
    }
    
	@Step (
		description="Introducir los datos correctos para el país #{pais.getNombre_pais()}", 
		expected="No aparece ningún mensaje de error")
	public static void sendDataAccordingCountryToInputs(Map<String,String> dataRegistro, Pais pais, Channel channel, WebDriver driver) 
	throws Exception {
		PageRegistroDirec.sendDataAccordingCountryToInputs(dataRegistro, pais, channel, driver);
		validateInputDataOk(driver);
	}

    @Validation(
    	description="No aparece ningún mensaje de error asociado a los campos de entrada",
    	level=State.Defect)
    public static boolean validateInputDataOk(WebDriver driver) {
    	return (PageRegistroDirec.getNumberMsgInputInvalid(driver) <= 0);
    }
    
    @Step (
    	description= "Seleccionar el botón \"<b>Finalizar</b>\"", 
        expected="Aparece la página final del proceso de registro")
    public static void clickFinalizarButton(WebDriver driver) 
    throws Exception {
        PageRegistroDirec.clickFinalizarButton(driver);
        int maxSecondsWait = 5;
        PageRegistroFinStpV.isPageUntil(maxSecondsWait, driver);
    }
}
