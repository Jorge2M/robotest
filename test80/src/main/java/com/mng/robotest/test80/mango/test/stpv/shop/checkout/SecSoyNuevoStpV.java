package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecSoyNuevo;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecSoyNuevo.ActionNewsL;

@SuppressWarnings("javadoc")
public class SecSoyNuevoStpV {
    
    public static DatosStep inputEmailAndContinue(String email, boolean emailExistsYet, AppEcom appE, boolean userRegistered, 
    											  Pais pais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Desmarcamos el check NewsLetter, introducmos el email y seleccionamos \"Continuar\"", 
            "Aparece la página de introducción de datos del usuario");
        try {
            SecSoyNuevo.setCheckPubliNewsletter(dFTest.driver, ActionNewsL.deactivate, channel);
            SecSoyNuevo.inputEmail(email, dFTest.driver);
            SecSoyNuevo.clickContinue(channel, dFTest.driver);
                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        Page2IdentCheckoutStpV.validateIsPage(emailExistsYet, datosStep, dFTest);
        if (!userRegistered && appE != AppEcom.votf)
        	Page2IdentCheckoutStpV.validaRGPDText(datosStep, pais, dFTest);
        
        return datosStep;
    }
    
    
    @SuppressWarnings("static-access")
	public static void validaRGPDText(DatosStep datosStep, DataCtxShop dCtxSh, DataFmwkTest dFTest) {      
    	//Validaciones
		if (dCtxSh.pais.getRgpd().equals("S")) {
	        String descripValidac = 
	            "1) El texto de info de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
	        datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	        try {
	            if (!Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(dFTest.driver)) {
	                listVals.add(1, State.Defect);
	            }
	            if (!Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(dFTest.driver)) {
	                listVals.add(2, State.Defect);
	            }
	            
	            datosStep.setListResultValidations(listVals);
	        }
	        finally { listVals.checkAndStoreValidations(descripValidac); }   
		}
		
		else {
			String descripValidac = 
	            "1) El texto de info de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
	        datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	        try {
	            if (Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(dFTest.driver)) {
	                listVals.add(1, State.Defect);
	            }
	            if (Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(dFTest.driver)) {
	                listVals.add(2, State.Defect);
	            }
	            
	            datosStep.setListResultValidations(listVals);
	        }
	        finally { listVals.checkAndStoreValidations(descripValidac); } 
	    }
	}
}
