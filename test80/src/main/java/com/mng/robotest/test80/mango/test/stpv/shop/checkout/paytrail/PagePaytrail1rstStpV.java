package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PagePaytrail1rst.isPresentEntradaPago(nombrePagoCabecera, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            }
            if (channel==Channel.desktop) {
                if (!PagePaytrail1rst.isVisibleSelectBancosUntil(maxSecondsToWait, dFTest.driver)) {
                    listVals.add(3, State.Warn);
                }
            }
            if (channel==Channel.desktop) {
                if (!PagePaytrail1rst.isPresentButtonPago(dFTest.driver)) {
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