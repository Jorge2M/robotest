package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;

@SuppressWarnings("javadoc")
public class Page2IdentCheckoutStpV {
    
    public static void validateIsPage(boolean emailYetExists, datosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la página-2 de introducción de datos de la dirección del cliente (la esperamos hasta " + maxSecondsToWait + ")<br>" +
            "2) Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page2IdentCheckout.isPageUntil(maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!Page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static HashMap<String, String> inputDataPorDefecto(Pais pais, String emailUsr, boolean inputDireccCharNoLatinos, DataFmwkTest dFTest) 
    throws Exception {
        HashMap<String, String> datosRegistro = null;
        
        //Step
        datosStep datosStep = new datosStep (
            "Introdumios los datos del cliente según el país", 
            "Se hace clickable el botón \"Continuar\"");
        datosStep.setGrabImage(true);
        try {
            datosRegistro = Page2IdentCheckout.inputDataPorDefectoSegunPais(pais, emailUsr, inputDireccCharNoLatinos, false, dFTest.driver);
            datosStep.setDescripcion(datosStep.getDescripcion() + ". Utilizando los datos: "+ UtilsMangoTest.listaCamposHTML(datosRegistro));

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) Se hace clickable el botón \"Continuar\" (lo esperamos hasta " + maxSecondsToWait + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page2IdentCheckout.isContinuarClickableUntil(maxSecondsToWait, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        return datosRegistro;
    }
    
    /**
     * @param validaDirecCharNoLatinos indica si se ha de validar que en la dirección no pueden figurar carácteres no latinos
     */
    public static datosStep clickContinuar(boolean userRegistered, boolean validaDirecCharNoLatinos, DataBag dataBag, Channel channel, AppEcom app, DataFmwkTest dFTest)
    throws Exception {
        
        String descripIniTest = "";
        String descripResult = "Aparece la página de Checkout";
        int maxSecondsToWait = 20;
        if (validaDirecCharNoLatinos) {
            descripIniTest = "(Hay carácteres no-latinos introducidos en la dirección). ";
            descripResult = "Aparece un aviso indicando que en la dirección no pueden figurar carácteres no-latinos";
            maxSecondsToWait = 2;
        }
        
        //Step
        datosStep datosStep = new datosStep (
            descripIniTest + "Seleccionamos el botón \"Continuar\"", 
            descripResult /*Resultado esperado*/);
        datosStep.setGrabImage(true);
        try {
            Page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validaciones
        if (validaDirecCharNoLatinos) {
            String descripValidac = 
                "1) Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
            try { 
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!Page2IdentCheckout.isDisplayedAvisoAduanas(dFTest.driver)) 
                    fmwkTest.addValidation(1, State.Defect, listVals);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        else 
            PageCheckoutWrapperStpV.validateIsFirstPage(userRegistered, dataBag, channel, app, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validaRGPDText(datosStep datosStep, Pais pais, DataFmwkTest dFTest) {      
    	//Validaciones
		if (pais.getRgpd().equals("S")) {
	        String descripValidac = 
	            "1) El texto legal de RGPD <b>SI</b> existe para el pais " + pais.getCodigo_pais() + "<br>";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (!Page2IdentCheckout.isTextoLegalRGPDVisible(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }   
		}
		
		else {
			String descripValidac = 
	            "1) El texto legal de RGPD <b>NO</b> existe para el pais " + pais.getCodigo_pais() + "<br>";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                             
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (Page2IdentCheckout.isTextoLegalRGPDVisible(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 
	    }
	}
    
}
