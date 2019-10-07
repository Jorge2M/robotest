package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePostfCodSegStpV {

    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);    
    
    /**
     * Validación a nivel de la pasarela
     * Validamos la 1a página de Postfinance (la que contiene el input del código de seguridad en el caso de "PostFinance Card")
     * 
     * Para CI y PRE la pasarela de pago es diferente a la de PRO
     *   PRE/CI-Postfinance Card-> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
     *   PRO-PostFinance Card ->   https://epayment.postfinance.ch/pfcd/authentication/arh/cardIdForm
     *   PRE-Postfinance -> https://e-payment.postfinance.ch/ncol/test/orderstandard.asp
     *   PRO-PostFinance -> https://epayment.postfinance.ch/pfef/authentication/v2
     * @throws Exception 
     */
    public static void postfinanceValidate1rstPage(String nombrePago, String importeTotal, String codPais, WebDriver driver) 
    throws Exception {
    	PagePostfCodSeg.waitLoadPage();
    	if (PagePostfCodSeg.isPasarelaTest(driver)) {
            PagePostfCodSegStpV.validateIsPageTest(nombrePago, importeTotal, driver);
    	} else {
            PagePostfCodSegStpV.validateIsPagePro(importeTotal, codPais, driver);
        }
    }
    
    @Validation
    public static ChecksResult validateIsPageTest(String nombrePago, String importeTotal, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
    		"Aparece la pasarela de pagos de PostFinance E-Payment de Test",
    		PagePostfCodSeg.isPasarelaPostfinanceTest(driver, nombrePago), State.Defect);
	   	validations.add(
    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
    		PagePostfCodSeg.isPresentButtonAceptar(driver), State.Defect);
	   	
	   	boolean existsCode = PagePostfCodSeg.isPresentInputCodSeg(driver);
	   	if (isPostfinanceEcard(nombrePago)) {
		   	validations.add(
	    		"SÍ existe el campo de introducción del código de seguridad",
	    		existsCode, State.Defect);
	   	} else {
		   	validations.add(
	    		"NO existe el campo de introducción del código de seguridad",
	    		!existsCode, State.Defect);
	   	}
	   	
	   	return validations;
    }
    
    @Validation
    public static ChecksResult validateIsPagePro(String importeTotal, String codPais, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 5;
	   	validations.add(
    		"Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + maxSecondsWait + " segundos)",
    		PagePostfCodSeg.isPasarelaPostfinanceProUntil(maxSecondsWait, driver), State.Defect);    	
	   	validations.add(
    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);    		  
	   	validations.add(
    		"Aparece el botón Weiter (Aceptar)",
    		PagePostfCodSeg.isPresentButtonWeiter(driver), State.Defect);
	   	return validations;
    }
    
    @Step (
    	description="Seleccionar el botón Aceptar", 
        expected="Aparece una página de redirección")
    public static void inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo, WebDriver driver) 
    throws Exception {
    	try {
	        if (isPostfinanceEcard(nombreMetodo)) {
	            //El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
	        	StepTestMaker StepTestMaker = TestCaseData.getDatosLastStep();
	        	StepTestMaker.setDescripcion("Introducir el código de seguridad " + codSeguridad + ". " + StepTestMaker.getDescripcion());
	        	PagePostfCodSeg.inputCodigoSeguridad(driver, codSeguridad);
	        }
	              
	        PagePostfCodSeg.clickAceptarButton(driver);
    	}
        finally {
            driver.switchTo().defaultContent(); 
        }
            
        //Validaciones
        PagePostfRedirectStpV.isPageAndFinallyDisappears(driver);
    }
    
    /**
     * @return si se trata de "PostFinance Card" y no de "PostFinance e-finance"
     */
    private static boolean isPostfinanceEcard(String nombreMetodo) {
        return (nombreMetodo.toUpperCase().contains("CARD"));
    }
}
