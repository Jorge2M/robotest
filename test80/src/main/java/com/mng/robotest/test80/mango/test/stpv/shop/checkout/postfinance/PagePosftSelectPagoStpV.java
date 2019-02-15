package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectPago;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePosftSelectPagoStpV {

	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	public static void validateIsPage(String nombrePago, String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
		int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Aparece la 1a pantalla para la selección del método <b>" + nombrePago + "</b> (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
            "2) En la página resultante figura el importe total de la compra (" + importeTotal + ")";
        datosStep.setNOKstateByDefault();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);   
        try {
            if (!PagePostfSelectPago.isPageUntil(nombrePago, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn); 
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem in validations of Payment {} for country {}", nombrePago, codPais, e);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }		
	}
	
	public static void clickIconoPago(String nombrePago, String importeTotal, String codigoPais, DataFmwkTest dFTest) 
	throws Exception {
		//Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el icono del pago <b>" + nombrePago + "</b>", 
            "Aparece una página de redirección");
        try {
        	PagePostfSelectPago.clickIconoPago(nombrePago, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
		
        //Validation
		PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codigoPais, datosStep, dFTest);
	}
	
}
