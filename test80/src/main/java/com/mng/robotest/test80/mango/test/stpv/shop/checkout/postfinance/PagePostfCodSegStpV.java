package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePostfCodSegStpV {

    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);    
    
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
    public static void postfinanceValidate1rstPage(String nombrePago, String importeTotal, String codPais, WebDriver driver) {
    	PagePostfCodSeg.waitLoadPage();
    	if (PagePostfCodSeg.isPasarelaTest(driver)) {
            PagePostfCodSegStpV.validateIsPageTest(nombrePago, importeTotal, driver);
    	} else {
            PagePostfCodSegStpV.validateIsPagePro(importeTotal, codPais, driver);
        }
    }
    
    @Validation
    public static ChecksTM validateIsPageTest(String nombrePago, String importeTotal, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
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
    public static ChecksTM validateIsPagePro(String importeTotal, String codPais, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
    	int maxSeconds = 5;
	   	validations.add(
    		"Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + maxSeconds + " segundos)",
    		PagePostfCodSeg.isPasarelaPostfinanceProUntil(maxSeconds, driver), State.Defect);    	
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
    public static void inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo, WebDriver driver) {
    	try {
	        if (isPostfinanceEcard(nombreMetodo)) {
	            //El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
	        	StepTM step = TestMaker.getCurrentStepInExecution();
	        	step.setDescripcion("Introducir el código de seguridad " + codSeguridad + ". " + step.getDescripcion());
	        	PagePostfCodSeg.inputCodigoSeguridad(driver, codSeguridad);
	        }
	              
	        PagePostfCodSeg.clickAceptarButton(driver);
    	}
        finally {
            driver.switchTo().defaultContent(); 
        }
    	
        PagePostfRedirectStpV.isPageAndFinallyDisappears(driver);
    }
    
    /**
     * @return si se trata de "PostFinance Card" y no de "PostFinance e-finance"
     */
    private static boolean isPostfinanceEcard(String nombreMetodo) {
        return (nombreMetodo.toUpperCase().contains("CARD"));
    }
}
