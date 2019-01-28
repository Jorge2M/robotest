package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrail1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PagePaytrail1rstStpV {
    
    public static void validateIsPage(String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String nombrePagoCabecera = "Finnish E-Banking";
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePagoCabecera + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal;
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "3) Es visible el desplegable de bancos (lo esperamos hasta " + maxSecondsToWait + " seconds)<br>" +
            "4) Figura un botón de pago";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PagePaytrail1rst.isPresentEntradaPago(nombrePagoCabecera, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                if (channel==Channel.movil_web)
                    fmwkTest.addValidation(2, State.Info, listVals);
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (channel==Channel.desktop) {
                if (!PagePaytrail1rst.isVisibleSelectBancosUntil(maxSecondsToWait, dFTest.driver))
                    fmwkTest.addValidation(3, State.Warn, listVals);
            }
            //4)
            if (channel==Channel.desktop) {
                if (!PagePaytrail1rst.isPresentButtonPago(dFTest.driver)) 
                    fmwkTest.addValidation(4, State.Defect, listVals); 
            }
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep selectBancoAndContinue(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        String bancoNordea = "Nordea";
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el banco \"" + bancoNordea + "\" del desplegable y pulsar el botón \"Continue\"", 
            "Aparece la página de identificación de E-payment");
        try {
            PagePaytrail1rst.selectBanco(bancoNordea, channel, dFTest.driver);
            PagePaytrail1rst.clickButtonContinue(channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PagePaytrailEpaymentStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}