package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDotpay1rst.isPresentEntradaPago(nombrePago, channel, dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web)
                    fmwkTest.addValidation(2, State.Info_NoHardcopy, listVals);
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);
                
            }
            //3)
            if (!PageDotpay1rst.isPresentCabeceraStep(nombrePago, channel, dFTest.driver)) 
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (channel==Channel.desktop) {
                if (!PageDotpay1rst.isPresentButtonPago(dFTest.driver)) 
                    fmwkTest.addValidation(4, State.Defect, listVals);
            }
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
