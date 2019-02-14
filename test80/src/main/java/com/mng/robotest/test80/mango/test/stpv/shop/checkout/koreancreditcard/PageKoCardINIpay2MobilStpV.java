package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        datosStep.setNOKstateByDefault(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
	        if (!PageKoCardINIpay2Mobil.isPage(dFTest.driver)) {
	            listVals.add(1, State.Defect);
	        }
	        	
	        datosStep.setListResultValidations(listVals);
	    } 
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static void confirmMainPaymentCorea(DataFmwkTest dFTest) throws Exception {
	    //Step
	    DatosStep datosStep = new DatosStep     (
	        "Seleccionamos el botón para Continuar",
	        "Aparece la 3a y última página de INIpay");
	    datosStep.setNOKstateByDefault();
	    try {
	        clickAndWait(BodyPageKoCardINIpay2.nextButton, 2, dFTest.driver);
	        
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } 
	    finally { fmwkTest.grabStep(datosStep, dFTest); }
	
	    //Validation
	    PageKoCardINIpay3MobilStpV.validateIsPage(datosStep, dFTest);
	}
}
