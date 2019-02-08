package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil.BodyPageKoCardINIpay4;

public class PageKoCardINIpay4MobilStpV extends ElementPageFunctions {
	
	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
	    //Validation
	    String descripValidac =
	        "1) Está presente el texto de pago OK en Coreano <b>" + PageKoCardINIpay4Mobil.textoPagoConExitoKR + "</b>";
        datosStep.setStateIniValidations(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        if (!PageKoCardINIpay4Mobil.isVisibleMessagePaymentOk(dFTest.driver)) {
	        	listVals.add(1, State.Defect);
	        }
	        	
	        datosStep.setListResultValidations(listVals);
	    } 
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static DatosStep clickConfirmarButton(DataFmwkTest dFTest) throws Exception {
	    //Step
	    DatosStep datosStep = new DatosStep     (
	        "Seleccionar el botón para Confirmar", 
	        "Aparece la página de confirmación de KrediKarti");
	    datosStep.setStateIniValidations();    
	    try {       
	    	clickElementVisibleAndWaitLoad(BodyPageKoCardINIpay4.nextButton, 0, dFTest.driver);
	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    }
	    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	
	    return datosStep;
	}
}
