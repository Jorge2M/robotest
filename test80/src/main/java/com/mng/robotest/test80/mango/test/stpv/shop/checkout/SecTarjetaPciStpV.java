package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

@SuppressWarnings("javadoc")
public class SecTarjetaPciStpV {
    
    public static void validateIsSectionOk(Pago pago, Pais pais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
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
            
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {               
            if (channel==Channel.desktop && !pais.isPagoPSP()) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isVisiblePanelPagoUntil(pago.getNombre(channel), maxSecondsToWait, dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
            }
            if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputNumberUntil(1/*maxSecondsToWait*/, dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputTitular(dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectMes(dFTest.driver) ||
                !PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentSelectAny(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (pago.getTypePago()!=TypePago.Bancontact) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputCvc(dFTest.driver)) {
                    listVals.add(3, State.Defect);
                }
            }
            if (pago.getDni()!=null && "".compareTo(pago.getDni())!=0) {
                if (!PageCheckoutWrapper.getSecTarjetaPci(channel).isPresentInputDni(dFTest.driver)) {
                    listVals.add(4, State.Defect);
                }
            }            
                    
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
