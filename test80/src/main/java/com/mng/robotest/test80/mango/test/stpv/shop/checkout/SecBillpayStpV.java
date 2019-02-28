package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecBillpay;

public class SecBillpayStpV {
    
	@Validation
    public static ListResultValidation validateIsSectionOk(Channel channel, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"Aparecen 3 desplegables para la selección de la fecha de nacimiento<br>",
			SecBillpay.isPresentSelectBirthBirthYear(driver) &&
            SecBillpay.isPresentSelectBirthMonth(driver) &&
            SecBillpay.isPresentSelectBirthDay(driver), 
            State.Defect); 
	 	validations.add(
			"Aparece el check de \"Acepto\"",
			SecBillpay.isPresentRadioAcepto(channel, driver), State.Defect); 
	 	return validations;
    }
    
    /**
     * @param fechaNac en formato "DD-MM-AAAA"
     */
    public static DatosStep inputDiaNacAndCheckAcepto(String fechaNac, String nombrePago, Channel channel, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep (
            "Informamos la fecha de nacimiento " + fechaNac + " y marcamos el check de \"Acepto\"", 
            "La fecha se informa correctamente");
        try {
            SecBillpay.putBirthday(dFTest.driver, fechaNac);
            SecBillpay.clickAcepto(dFTest.driver, channel);
                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        if (nombrePago.compareTo("Lastschrift")==0) {
            //Validaciones
            String descripValidac = 
                "1) Aparece el campo para la introducción del titular<br>" +
                "2) Aparece el campo para la introducción del IBAN<br>" +
                "3) Aparece el campo para la introducción del BIC";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!SecBillpay.isPresentInputTitular(dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
                if (!SecBillpay.isPresentInputIBAN(dFTest.driver)) {
                    listVals.add(2, State.Defect);
                }
                if (!SecBillpay.isPresentInputBIC(dFTest.driver)) {
                    listVals.add(3, State.Defect);
                }

                datosStep.setListResultValidations(listVals);
            }
            catch (Exception e) {
                //
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        
        return datosStep;
    }
    
    public static DatosStep inputDataInLastschrift(String iban, String bic, String titular, DataFmwkTest dFTest) { 
        DatosStep datosStep = new DatosStep (
            "Informamos el titular, IBAN (" + iban + ") y BIC (" + bic + ")", 
            "Los datos se informan correctamente");
        try {
            SecBillpay.sendDataInputIBAN(iban, dFTest.driver);
            SecBillpay.sendDataInputTitular(titular, dFTest.driver);
            SecBillpay.sendDataInputBIC(bic, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        return datosStep;
    }
}
