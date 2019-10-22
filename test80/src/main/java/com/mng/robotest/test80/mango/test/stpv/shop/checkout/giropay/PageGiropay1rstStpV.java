package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropay1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageGiropay1rstStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b>",
			PageGiropay1rst.isPresentIconoGiropay(channel, driver), State.Warn);	
	   	
	   	State stateVal = State.Warn;
	   	boolean avoidEvidences = false;
        if (channel==Channel.movil_web) {
        	stateVal = State.Info;
        	avoidEvidences = true;
        }
	   	validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal, avoidEvidences);
	   	validations.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageGiropay1rst.isPresentCabeceraStep(driver), State.Warn);	
        int maxSecondsWait = 2;
	   	if (channel==Channel.desktop) {
		   	validations.add(
		   		"Aparece un input para la introducción del Banco (lo esperamos hasta " + maxSecondsWait + " segundos)",
		   		PageGiropay1rst.isVisibleInputBankUntil(maxSecondsWait, driver), State.Warn);
		   	validations.add(
		   		"Figura un botón de pago",
		   		PageGiropay1rst.isPresentButtonPagoDesktop(driver), State.Defect);
	   	}
	   	
	   	return validations;
    }
    
	public static void inputBankFluxSteps(String bankToInput, Channel channel, WebDriver driver) throws Exception {
		inputBank(bankToInput, channel, driver);
		moveFocusToInputBanck(bankToInput, driver);
	}
	
	@Step (
		description="Introducimos el banco \"#{bankToInput}\" y pulsamos \"TAB\"", 
        expected="Aparece un desplegable con dicho banco")
    private static void inputBank(String bankToInput, Channel channel, WebDriver driver) throws Exception {
        PageGiropay1rst.inputBank(bankToInput, channel, driver);
        
        //Validaciones
        int maxSecondsWait = 2;
        isVisibleBankInList(bankToInput, maxSecondsWait, driver);
	}
	
	@Validation (
		description="Aparece la entrada <b>#{bankToInput}</b> del desplegable (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean isVisibleBankInList(String bankToInput, int maxSecondsWait, WebDriver driver) {
		return (PageGiropay1rst.isVisibleBankInListUntil(bankToInput, maxSecondsWait, driver));	
	}
	
	@Step (
		description="Movemos el foco del input de \"Bank\" mediante un  \"TAB\"", 
        expected="Aparece un desplegable con dicho banco")
	private static void moveFocusToInputBanck(String bankToInput, WebDriver driver) throws Exception {
		PageGiropay1rst.inputTabInBank(driver);
		
		//Validations
		int maxSecondsWait = 2;
		isVisibleInputBankInDesplegable(bankToInput, maxSecondsWait, driver);
	}
	
	@Validation (
		description="Acaba desapareciendo la entrada <b>#{bankToInput}</b> del desplegable (lo esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Warn)
	private static boolean isVisibleInputBankInDesplegable(String bankToInput, int maxSecondsWait, WebDriver driver) {
        return (PageGiropay1rst.isInvisibleBankInListUntil(bankToInput, maxSecondsWait, driver));
    }
    
	@Step (
		description="Pulsamos el botón para continuar con el Pago", 
        expected="Aparece la página de Test de introducción de datos de Giropay")
    public static void clickButtonContinuePay(Channel channel, WebDriver driver) throws Exception {
        PageGiropay1rst.clickButtonContinuePay(channel, driver);
        
        //Validación
        int maxSecondsWait = 2;
        PageGiropayInputDataTestStpV.validateIsPage(maxSecondsWait, driver);
    }
}
