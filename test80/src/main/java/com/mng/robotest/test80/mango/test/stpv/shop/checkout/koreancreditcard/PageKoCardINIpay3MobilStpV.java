package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil.BodyPageKoCardINIpay3;

public class PageKoCardINIpay3MobilStpV extends ElementPageFunctions {
	
	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
	    //Validation
	    String descripValidac =
	        "1) Aparece la 3a página de INIpay";
        datosStep.setStateIniValidations(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        if (!PageKoCardINIpay3Mobil.isPage(dFTest.driver)) {
	        	listVals.add(1, State.Defect);
	        }
	        	
	        datosStep.setListResultValidations(listVals);
	    } 
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
	public static DatosStep clickNextButton(DataFmwkTest dFTest) throws Exception {
	    //Step
	    DatosStep datosStep = new DatosStep     (
	        "Seleccionamos el <b>Next Button</b>",
	        "Aparece la 4a y última página de INIpay con resultado OK");
	    datosStep.setStateIniValidations();
	    try {
	    	clickElementVisibleAndWaitLoad(BodyPageKoCardINIpay3.nextButton, 0, dFTest.driver);
	        
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } 
	    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	    
	    //Validations
	    PageKoCardINIpay4MobilStpV.validateIsPage(datosStep, dFTest);

	    return datosStep;
	}
}
