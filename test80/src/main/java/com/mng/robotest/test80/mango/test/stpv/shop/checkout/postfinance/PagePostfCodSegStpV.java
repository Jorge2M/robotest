package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PagePostfCodSegStpV {

    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);    
    
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
    public static void postfinanceValidate1rstPage(String nombrePago, String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	PagePostfCodSeg.waitLoadPage();
    	if (PagePostfCodSeg.isPasarelaTest(dFTest.driver)) {
            PagePostfCodSegStpV.validateIsPageTest(nombrePago, importeTotal, codPais, datosStep, dFTest);
    	}
        else {
            PagePostfCodSegStpV.validateIsPagePro(importeTotal, codPais, datosStep, dFTest);
        }
    }
    
    public static void validateIsPageTest(String nombrePago, String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String validacion4 = "4) NO existe el campo de introducción del código de seguridad"; 
        if (isPostfinanceEcard(nombrePago))
            validacion4 = "4) SÍ existe el campo de introducción del código de seguridad";
        
        String descripValidac = 
            "1) Aparece la pasarela de pagos de PostFinance E-Payment de Test<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece un iframe en el que figura el botón Aceptar<br>" + 
            validacion4; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PagePostfCodSeg.isPasarelaPostfinanceTest(dFTest.driver, nombrePago)) {
                listVals.add(1, State.Defect);
            }
            if (!PagePostfCodSeg.isPresentButtonAceptar(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            boolean existsCode = PagePostfCodSeg.isPresentInputCodSeg(dFTest.driver);
            if (isPostfinanceEcard(nombrePago)) {
                if (!existsCode) {
                    listVals.add(3, State.Defect);
                }
            }
            else {
                if (existsCode) {
                    listVals.add(3, State.Defect);
                }
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in validations of Payment {} for country {}", nombrePago, codPais, e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validateIsPagePro(String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece el botón Weiter (Aceptar)<br>";         
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PagePostfCodSeg.isPasarelaPostfinanceProUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PagePostfCodSeg.isPresentButtonWeiter(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in PostFinance validations for country {}", codPais, e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo, DataFmwkTest dFTest) 
    throws Exception {
        String strCodSeguridad = "";
        
        //El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
        if (isPostfinanceEcard(nombreMetodo))
            strCodSeguridad = "Introducir el código de seguridad " + codSeguridad;
             
        DatosStep datosStep = new DatosStep     (
            strCodSeguridad + "Seleccionar el botón Aceptar", 
            "Aparece una página de redirección");
        try {
            if (isPostfinanceEcard(nombreMetodo))
                PagePostfCodSeg.inputCodigoSeguridad(dFTest.driver, codSeguridad);
                    
            PagePostfCodSeg.clickAceptarButton(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally {
            dFTest.driver.switchTo().defaultContent(); 
            StepAspect.storeDataAfterStep(datosStep); 
        }
            
        //Validaciones
        PagePostfRedirectStpV.isPageAndFinallyDisappears(datosStep, dFTest);
        
        return datosStep;
    }
    
    /**
     * @return si se trata de "PostFinance Card" y no de "PostFinance e-finance"
     */
    private static boolean isPostfinanceEcard(String nombreMetodo) {
        return (nombreMetodo.toUpperCase().contains("CARD"));
    }
}
