package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil.BodyPageKoCardINIpay4;

public class PageKoCardINIpay4MobilStpV extends ElementPageFunctions {
	
	public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
	    //Validation
	    String descripValidac =
	        "1) Está presente el texto de pago OK en Coreano <b>" + PageKoCardINIpay4Mobil.textoPagoConExitoKR + "</b>";
	    try {
	        List<SimpleValidation> listVals = new ArrayList<>();
	        //1)
	        if (!PageKoCardINIpay4Mobil.isVisibleMessagePaymentOk(dFTest.driver))
	        	fmwkTest.addValidation(1, State.Defect, listVals);
	        	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	    } 
	    finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	public static datosStep clickConfirmarButton(DataFmwkTest dFTest) throws Exception {
	    //Step
	    datosStep datosStep = new datosStep     (
	        "Seleccionar el botón para Confirmar", 
	        "Aparece la página de confirmación de KrediKarti");
	    datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);    
	    try {       
	    	clickElementVisibleAndWaitLoad(BodyPageKoCardINIpay4.nextButton, 0, dFTest.driver);
	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    }
	    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	
	    return datosStep;
	}
}
