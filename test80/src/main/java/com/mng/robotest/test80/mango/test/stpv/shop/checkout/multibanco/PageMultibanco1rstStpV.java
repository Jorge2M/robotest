package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibanco1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageMultibanco1rstStpV {
    public static void validateIsPage(String nombrePago, String importeTotal, String emailUsr, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "4) Aparece un campo de introducción de email (informado con <b>" + emailUsr + "</b>)<br>" +
            "5) Figura un botón de pago";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMultibanco1rst.isPresentEntradaPago(nombrePago, channel, dFTest.driver)) {
                listVals.add(1,State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web) {
                    listVals.add(2, State.Info);
                }
                else {
                    listVals.add(2, State.Warn);
                }
            }
            if (!PageMultibanco1rst.isPresentCabeceraStep(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (channel==Channel.desktop) {
                if (!PageMultibanco1rst.isPresentEmailUsr(emailUsr, dFTest.driver)) {
                    listVals.add(4, State.Warn);
                }
            }
            if (channel==Channel.desktop) {
                if (!PageMultibanco1rst.isPresentButtonPagoDesktop(dFTest.driver)) {
                    listVals.add(5, State.Defect);
                }
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep continueToNextPage(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Pagar\"", 
            "Aparece la página de \"En progreso\"");
        try {
            PageMultibanco1rst.continueToNextPage(channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageMultibancoEnProgresoStpv.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
