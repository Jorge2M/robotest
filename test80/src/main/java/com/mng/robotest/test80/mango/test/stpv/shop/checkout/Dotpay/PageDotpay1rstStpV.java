package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpay1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageDotpay1rstStpV {
    
    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "4) Figura un botón de pago";
        datosStep.setStateIniValidations();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageDotpay1rst.isPresentEntradaPago(nombrePago, channel, dFTest.driver)) {
                listVals.add(1,State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info_NoHardcopy);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            }
            if (!PageDotpay1rst.isPresentCabeceraStep(nombrePago, channel, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (channel==Channel.desktop) {
                if (!PageDotpay1rst.isPresentButtonPago(dFTest.driver)) {
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
    
    public static void clickToPay(String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el link hacia el Pago", 
            "Aparece la página de selección del canal de pago");
        try {
            PageDotpay1rst.clickToPay(channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageDotpayPaymentChannelStpV.validateIsPage(importeTotal, codPais, datosStep, dFTest);
    }
}
