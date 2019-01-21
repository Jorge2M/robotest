package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalAvisoCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

@SuppressWarnings("javadoc")
public class ModalAvisoCambioPaisStpV {

    public static datosStep clickConfirmar(Pais paisEnvio, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Seleccionar botón \"Confirmar cambio\"", 
            "Aparece el modal para la introducción de la dirección de facturación");
        try {
            ModalAvisoCambioPais.clickConfirmarCambio(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        int maxSecondsWait = 10;
        String descripValidac = 
            "1) Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) En la dirección de envió aparece el país " + paisEnvio.getNombre_pais();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalAvisoCambioPais.isInvisibleUntil(maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2) 
            if (!PageCheckoutWrapper.direcEnvioContainsPais(Channel.desktop, paisEnvio.getNombre_pais(), dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}
