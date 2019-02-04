package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
	    try {
	        List<SimpleValidation> listVals = new ArrayList<>();
	        //1)
	        if (!PageKoCardINIpay3Mobil.isPage(dFTest.driver))
	        	fmwkTest.addValidation(1, State.Defect, listVals);
	        	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	    } 
	    finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
	public static DatosStep clickNextButton(DataFmwkTest dFTest) throws Exception {
	    //Step
	    DatosStep datosStep = new DatosStep     (
	        "Seleccionamos el <b>Next Button</b>",
	        "Aparece la 4a y última página de INIpay con resultado OK");
	    datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
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
