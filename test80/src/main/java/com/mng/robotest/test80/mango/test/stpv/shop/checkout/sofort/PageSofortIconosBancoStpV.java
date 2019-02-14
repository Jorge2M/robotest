package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
	public static void validateIsPage(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
	    String descripValidac =
	        "1) Aparece la 1a página de Sofort (la esperamos hasta " + maxSecondsToWait + " segundos)";
	    datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        if (!PageSofort1rst.isPageVisibleUntil(maxSecondsToWait, channel, dFTest.driver)) {
	            listVals.add(1, State.Warn);
	        }

	        datosStep.setListResultValidations(listVals);
	    }
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
    public static DatosStep clickIconoSofort(Channel channel, DataFmwkTest dFTest) throws Exception { 
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el link hacia la siguiente página de Sofort", 
            "Aparece la página de selección del Banco");
        try {
            PageSofort1rst.clickGoToSofort(dFTest.driver, channel);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }

        //Validaciones
        PageSofort2onStpV.validaIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
