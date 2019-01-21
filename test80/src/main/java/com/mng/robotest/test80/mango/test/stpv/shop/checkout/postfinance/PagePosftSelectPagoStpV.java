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
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectPago;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePosftSelectPagoStpV {

	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	public static void validateIsPage(String nombrePago, String importeTotal, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
		int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la 1a pantalla para la selección del método <b>" + nombrePago + "</b> (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();                            
            //1)
            if (!PagePostfSelectPago.isPageUntil(nombrePago, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals); 
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in validations of Payment {} for country {}", nombrePago, codPais, e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }		
	}
	
	public static void clickIconoPago(String nombrePago, String importeTotal, String codigoPais, DataFmwkTest dFTest) 
	throws Exception {
		//Step
        datosStep datosStep = new datosStep     (
            "Seleccionar el icono del pago <b>" + nombrePago + "</b>", 
            "Aparece una página de redirección");
        try {
        	PagePostfSelectPago.clickIconoPago(nombrePago, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
        //Validation
		PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codigoPais, datosStep, dFTest);
	}
	
}
