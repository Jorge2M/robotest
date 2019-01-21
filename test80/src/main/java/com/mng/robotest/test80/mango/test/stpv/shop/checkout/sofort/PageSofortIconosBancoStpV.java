package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort1rst;

/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PageSofortIconosBancoStpV {
	public static void validateIsPage(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
	    //Validaciones
        int maxSecondsToWait = 3;
	    String descripValidac =
	        "1) Aparece la 1a página de Sofort (la esperamos hasta " + maxSecondsToWait + " segundos)";
	    datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
	    try {
	        List<SimpleValidation> listVals = new ArrayList<>();
	        //1)
	        if (!PageSofort1rst.isPageVisibleUntil(maxSecondsToWait, channel, dFTest.driver)) {
	            fmwkTest.addValidation(1, State.Warn, listVals);
	        }

	        datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	    }
	    finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
    public static datosStep clickIconoSofort(Channel channel, DataFmwkTest dFTest) throws Exception { 
        datosStep datosStep = new datosStep     (
            "Seleccionar el link hacia la siguiente página de Sofort", 
            "Aparece la página de selección del Banco");
        try {
            PageSofort1rst.clickGoToSofort(dFTest.driver, channel);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        PageSofort2onStpV.validaIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
