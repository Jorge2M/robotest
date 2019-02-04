package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil.BodyPageKoCardINIpay2;

public class PageKoCardINIpay2MobilStpV extends ElementPageFunctions {
	
	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
	    //Validation
	    String descripValidac =
	        "1) Aparece la 2a página de INIpay (con el input del email)";
	    try {
	        List<SimpleValidation> listVals = new ArrayList<>();
	        //1)
	        if (!PageKoCardINIpay2Mobil.isPage(dFTest.driver))
	            fmwkTest.addValidation(1, State.Defect, listVals);
	        	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	    } 
	    finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	public static void confirmMainPaymentCorea(DataFmwkTest dFTest) throws Exception {
	    //Step
	    DatosStep datosStep = new DatosStep     (
	        "Seleccionamos el botón para Continuar",
	        "Aparece la 3a y última página de INIpay");
	    datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
	    try {
	        clickAndWait(BodyPageKoCardINIpay2.nextButton, 2, dFTest.driver);
	        
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } 
	    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	
	    //Validation
	    PageKoCardINIpay3MobilStpV.validateIsPage(datosStep, dFTest);
	}
}
