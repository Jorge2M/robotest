package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecKlarna;

@SuppressWarnings("javadoc")
public class SecKlarnaStpV {
    
    public static DatosStep inputNumPersonal(String numPersonalKlarna, Channel channel, DataFmwkTest dFTest) {
        DatosStep datosStep = new DatosStep (
            "Introducir Nº personal Klarna (" + numPersonalKlarna + ")", 
            "El dato se introduce correctamente");
        try {
            SecKlarna.waitAndinputNumPersonal(dFTest.driver, 2, numPersonalKlarna, channel);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
    
    public static DatosStep searchAddress(Pago pago, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Click botón \"Search Address\"", 
            "Aparece la capa de \"Klarna Invoice\" con datos correctos");
        try {
            SecKlarna.clickSearchAddress(dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece el modal de las direcciones de Klarna (lo esperamos hasta " + maxSecondsToWait+ " segundos)<br>" + 
            "2) Aparecen los datos asociados al Nº persona: " + pago.getNomklarna() + " - " + pago.getDirecklarna() + " - " + pago.getProvinklarna();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);          
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecKlarna.isModalDireccionesVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!SecKlarna.getTextNombreAddress(dFTest.driver).contains(pago.getNomklarna()) ||
                !SecKlarna.getTextDireccionAddress(dFTest.driver).contains(pago.getDirecklarna()) ||
                !SecKlarna.getTextProvinciaAddress(dFTest.driver).contains(pago.getProvinklarna()))
                fmwkTest.addValidation(2,State.Warn, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static DatosStep confirmAddress(Pago pago, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"Confirm Address\"", 
            "Se modifica la dirección correctamente");
        try {
            SecKlarna.clickConfirmAddress(dFTest.driver, channel);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Desaparece el modal de las direcciones de Klarna (lo esperamos hasta " + maxSecondsToWait + " segundos)"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);          
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecKlarna.isModalDireccionesInvisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1,State.Warn, listVals);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Esta validación sólo podemos realizarla en Desktop porque en el caso de móvil todavía no figura la dirección en pantalla 
        if (channel==Channel.desktop) {
            //VAlidaciones
            descripValidac = 
                "1) Como Shipping Address figura la de Klarna: " + pago.getNomklarna() + " - " + pago.getDirecklarna() + " - " + pago.getProvinklarna();
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);          
            try { 
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!Page1DktopCheckout.getTextNombreEnvio(dFTest.driver).contains(pago.getNomklarna()) ||
                    !Page1DktopCheckout.getTextDireccionEnvio(dFTest.driver).contains(pago.getDirecklarna()) ||
                    !Page1DktopCheckout.getTextPoblacionEnvio(dFTest.driver).replace(" ", "").contains(pago.getProvinklarna().replace(" ", "")))
                    fmwkTest.addValidation(1,State.Warn, listVals);
                                                                 
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }                            
        }        
        
        return datosStep;
    }
}
