package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalAvisoCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;


public class ModalAvisoCambioPaisStpV {

    public static DatosStep clickConfirmar(Pais paisEnvio, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar botón \"Confirmar cambio\"", 
            "Aparece el modal para la introducción de la dirección de facturación");
        try {
            ModalAvisoCambioPais.clickConfirmarCambio(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        int maxSecondsWait = 10;
        String descripValidac = 
            "1) Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) En la dirección de envió aparece el país " + paisEnvio.getNombre_pais();
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalAvisoCambioPais.isInvisibleUntil(maxSecondsWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageCheckoutWrapper.direcEnvioContainsPais(Channel.desktop, paisEnvio.getNombre_pais(), dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}
