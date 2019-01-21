package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecBillpay;

@SuppressWarnings("javadoc")
public class SecBillpayStpV {
    
    public static void validateIsSectionOk(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) Aparecen 3 desplegables para la selección de la fecha de nacimiento<br>" +
            "2) Aparece el check de \"Acepto\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecBillpay.isPresentSelectBirthBirthYear(dFTest.driver) ||
                !SecBillpay.isPresentSelectBirthMonth(dFTest.driver) ||
                !SecBillpay.isPresentSelectBirthDay(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!SecBillpay.isPresentRadioAcepto(channel, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * @param fechaNac en formato "DD-MM-AAAA"
     */
    public static datosStep inputDiaNacAndCheckAcepto(String fechaNac, String nombrePago, Channel channel, DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep (
            "Informamos la fecha de nacimiento " + fechaNac + " y marcamos el check de \"Acepto\"", 
            "La fecha se informa correctamente");
        try {
            SecBillpay.putBirthday(dFTest.driver, fechaNac);
            SecBillpay.clickAcepto(dFTest.driver, channel);
                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        if (nombrePago.compareTo("Lastschrift")==0) {
            //Validaciones
            String descripValidac = 
                "1) Aparece el campo para la introducción del titular<br>" +
                "2) Aparece el campo para la introducción del IBAN<br>" +
                "3) Aparece el campo para la introducción del BIC";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!SecBillpay.isPresentInputTitular(dFTest.driver))
                    fmwkTest.addValidation(1, State.Defect, listVals);
                //2)
                if (!SecBillpay.isPresentInputIBAN(dFTest.driver))
                    fmwkTest.addValidation(2, State.Defect, listVals);
                //3)
                if (!SecBillpay.isPresentInputBIC(dFTest.driver))
                    fmwkTest.addValidation(3, State.Defect, listVals);

                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            catch (Exception e) {
                //
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        return datosStep;
    }
    
    public static datosStep inputDataInLastschrift(String iban, String bic, String titular, DataFmwkTest dFTest) { 
        datosStep datosStep = new datosStep (
            "Informamos el titular, IBAN (" + iban + ") y BIC (" + bic + ")", 
            "Los datos se informan correctamente");
        try {
            SecBillpay.sendDataInputIBAN(iban, dFTest.driver);
            SecBillpay.sendDataInputTitular(titular, dFTest.driver);
            SecBillpay.sendDataInputBIC(bic, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
