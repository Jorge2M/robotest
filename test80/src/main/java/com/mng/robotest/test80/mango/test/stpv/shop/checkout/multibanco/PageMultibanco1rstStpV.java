package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMultibanco1rst.isPresentEntradaPago(nombrePago, channel, dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver))
                if (channel==Channel.movil_web)
                    fmwkTest.addValidation(2, State.Info, listVals);
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);            
            //3)
            if (!PageMultibanco1rst.isPresentCabeceraStep(dFTest.driver)) 
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4) 
            if (channel==Channel.desktop) {
                if (!PageMultibanco1rst.isPresentEmailUsr(emailUsr, dFTest.driver))
                    fmwkTest.addValidation(4, State.Warn, listVals);
            }
            //5)
            if (channel==Channel.desktop) {
                if (!PageMultibanco1rst.isPresentButtonPagoDesktop(dFTest.driver)) 
                    fmwkTest.addValidation(5, State.Defect, listVals);
            }
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
