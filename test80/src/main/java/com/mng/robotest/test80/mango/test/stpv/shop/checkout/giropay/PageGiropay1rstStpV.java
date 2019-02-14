package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropay1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageGiropay1rstStpV {
    
    public static void validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b><br>" +
            "2) Aparece el importe de la compra: " + importeTotal + "<br>" +
            "3) Aparece la cabecera indicando la 'etapa' del pago";
        if (channel==Channel.desktop)
            descripValidac+="<br>" +
            "4) Aparece un input para la introducción del Banco (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "5) Figura un botón de pago";
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGiropay1rst.isPresentIconoGiropay(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                if (channel==Channel.movil_web)
                    listVals.add(2, State.Info_NoHardcopy);
                else
                    listVals.add(2, State.Warn);            
            }
            if (!PageGiropay1rst.isPresentCabeceraStep(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (channel==Channel.desktop) {
                if (!PageGiropay1rst.isVisibleInputBankUntil(maxSecondsToWait, dFTest.driver)) {
                    listVals.add(4, State.Warn);
                }
            }
            if (channel==Channel.desktop) {
                if (!PageGiropay1rst.isPresentButtonPagoDesktop(dFTest.driver)) {
                    listVals.add(5, State.Defect); 
                }
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputBank(String bankToInput, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Introducimos el banco \"" + bankToInput + "\" y pulsamos \"TAB\"", 
            "Aparece un desplegable con dicho banco");
        try {
            PageGiropay1rst.inputBank(bankToInput, channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la entrada <b>" + bankToInput + "</b> del desplegable (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGiropay1rst.isVisibleBankInListUntil(bankToInput, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        //Step
        datosStep = new DatosStep     (
            "Movemos el foco del input de \"Bank\" mediante un  \"TAB\"", 
            "Aparece un desplegable con dicho banco");
        try {
            PageGiropay1rst.inputTabInBank(dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        maxSecondsToWait = 2;
        descripValidac = 
            "1) Acaba desapareciendo la entrada <b>" + bankToInput + "</b> del desplegable (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();   
        listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageGiropay1rst.isInvisibleBankInListUntil(bankToInput, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        return datosStep;
    }
    
    public static DatosStep clickButtonContinuePay(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Pulsamos el botón para continuar con el Pago", 
            "Aparece la página de Test de introducción de datos de Giropay");
        try {
            PageGiropay1rst.clickButtonContinuePay(channel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validación
        PageGiropayInputDataTestStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
}
