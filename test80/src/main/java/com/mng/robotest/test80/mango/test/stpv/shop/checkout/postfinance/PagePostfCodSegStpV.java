package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
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
    public static void postfinanceValidate1rstPage(String nombrePago, String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
    	PagePostfCodSeg.waitLoadPage(dFTest.driver);
    	if (PagePostfCodSeg.isPasarelaTest(dFTest.driver))
            PagePostfCodSegStpV.validateIsPageTest(nombrePago, importeTotal, codPais, datosStep, dFTest);
        else
            PagePostfCodSegStpV.validateIsPagePro(importeTotal, codPais, datosStep, dFTest);
    }
    
    public static void validateIsPageTest(String nombrePago, String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
        String validacion4 = "4) NO existe el campo de introducción del código de seguridad"; 
        if (isPostfinanceEcard(nombrePago))
            validacion4 = "4) SÍ existe el campo de introducción del código de seguridad";
        
        String descripValidac = 
            "1) Aparece la pasarela de pagos de PostFinance E-Payment de Test<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece un iframe en el que figura el botón Aceptar<br>" + 
            validacion4; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();                            
            //1)
            if (!PagePostfCodSeg.isPasarelaPostfinanceTest(dFTest.driver, nombrePago))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PagePostfCodSeg.isPresentButtonAceptar(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            boolean existsCode = PagePostfCodSeg.isPresentInputCodSeg(dFTest.driver);
            if (isPostfinanceEcard(nombrePago)) {
                if (!existsCode)
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
            else {
                if (existsCode)
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in validations of Payment {} for country {}", nombrePago, codPais, e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validateIsPagePro(String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>" +
            "3) Aparece el botón Weiter (Aceptar)<br>";         
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePostfCodSeg.isPasarelaPostfinanceProUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PagePostfCodSeg.isPresentButtonWeiter(dFTest.driver)) 
                fmwkTest.addValidation(3, State.Defect, listVals);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in PostFinance validations for country {}", codPais, e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo, DataFmwkTest dFTest) 
    throws Exception {
        String strCodSeguridad = "";
        
        //El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
        if (isPostfinanceEcard(nombreMetodo))
            strCodSeguridad = "Introducir el código de seguridad " + codSeguridad;
             
        datosStep datosStep = new datosStep     (
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
            datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); 
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
