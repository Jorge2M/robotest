package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoConf;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;

@SuppressWarnings("javadoc")
public class PageMercpagoConfStpV {

    public static void validaIsPage(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Estamos en la página de confirmación del pago (la esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageMercpagoConf.isPageUntil(channel, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep clickPagar(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Pagar\"", 
            "Aparece la página de resultado");
        try {
            PageMercpagoConf.clickPagar(dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
            
        //Validaciones
        PageResultPagoStpV.validaIsPageUntil(30/*maxSecondsToWait*/, channel, datosStep, dFTest);
        
        return datosStep;
    }
}
