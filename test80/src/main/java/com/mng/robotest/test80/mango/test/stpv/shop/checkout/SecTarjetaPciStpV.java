package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

@SuppressWarnings("javadoc")
public class SecTarjetaPciStpV {
    
    public static void validateIsSectionOk(Pago pago, Pais pais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = "";
        int maxSecondsToWait = 5;
        if (channel==Channel.desktop && !pais.isPagoPSP())
            descripValidac+= 
            "1) Aparece el bloque correspondiente a la introducción de los datos del método de pago " + pago.getNombre(channel) + " (lo esperamos hasta " + maxSecondsToWait + " segundo)";
        
        descripValidac+= "<br>" +
            "2) Aparecen los 4 campos <b>Número, Titular, Mes, Año</b> para la introducción de los datos de la tarjeta";
        
        if (pago.getTypePago()!=TypePago.Bancontact)
            descripValidac+= "<br>" +
            "3) Aparece también el campo <b>CVC</b>";     
        
        if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0)
            descripValidac+= "<br>" +
            "4) Aparece también el campo <b>DNI(C.C)</b>";
            
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)                    
            if (channel==Channel.desktop && !pais.isPagoPSP()) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isVisiblePanelPagoUntil(pago.getNombre(channel), maxSecondsToWait, dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            
            //2)
            if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputNumberUntil(1/*maxSecondsToWait*/, dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputTitular(dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectMes(dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectAny(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            
            //3)
            if (pago.getTypePago()!=TypePago.Bancontact) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputCvc(dFTest.driver))
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
            
            //4)
            if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputDni(dFTest.driver))
                    fmwkTest.addValidation(4, State.Defect, listVals);
            }            
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
