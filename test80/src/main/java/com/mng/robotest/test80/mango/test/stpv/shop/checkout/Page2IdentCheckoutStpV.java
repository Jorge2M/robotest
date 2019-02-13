package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.WhenSave;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;

@SuppressWarnings("javadoc")
public class Page2IdentCheckoutStpV {
    
    public static void validateIsPage(boolean emailYetExists, DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece la página-2 de introducción de datos de la dirección del cliente (la esperamos hasta " + maxSecondsToWait + ")<br>" +
            "2) Es <b>" + !emailYetExists + "</b> que aparece el input para la introducción de la contraseña";
        datosStep.setNOKstateByDefault();         
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!Page2IdentCheckout.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!Page2IdentCheckout.isInputPasswordAccordingEmail(emailYetExists, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static HashMap<String, String> inputDataPorDefecto(Pais pais, String emailUsr, boolean inputDireccCharNoLatinos, DataFmwkTest dFTest) 
    throws Exception {
        HashMap<String, String> datosRegistro = null;
        
        //Step
        DatosStep datosStep = new DatosStep (
            "Introdumios los datos del cliente según el país", 
            "Se hace clickable el botón \"Continuar\"");
        datosStep.setSaveImagePage(WhenSave.Always);
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
        datosStep.setNOKstateByDefault();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!Page2IdentCheckout.isContinuarClickableUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        return datosRegistro;
    }
    
    /**
     * @param validaDirecCharNoLatinos indica si se ha de validar que en la dirección no pueden figurar carácteres no latinos
     */
    public static DatosStep clickContinuar(boolean userRegistered, boolean validaDirecCharNoLatinos, DataBag dataBag, Channel channel, AppEcom app, DataFmwkTest dFTest)
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
        DatosStep datosStep = new DatosStep (
            descripIniTest + "Seleccionamos el botón \"Continuar\"", 
            descripResult /*Resultado esperado*/);
        datosStep.setSaveImagePage(WhenSave.Always);
        try {
            Page2IdentCheckout.clickBotonContinuarAndWait(maxSecondsToWait, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validaciones
        if (validaDirecCharNoLatinos) {
            String descripValidac = 
                "1) Aparece el aviso a nivel de aduanas que indica que la dirección contiene carácteres no-latinos";
            datosStep.setNOKstateByDefault();    
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);        
            try { 
                if (!Page2IdentCheckout.isDisplayedAvisoAduanas(dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
                
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        else 
            PageCheckoutWrapperStpV.validateIsFirstPage(userRegistered, dataBag, channel, app, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validaRGPDText(DatosStep datosStep, Pais pais, DataFmwkTest dFTest) {      
    	//Validaciones
		if (pais.getRgpd().equals("S")) {
	        String descripValidac = 
	            "1) El texto legal de RGPD <b>SI</b> existe para el pais " + pais.getCodigo_pais() + "<br>";
	        datosStep.setNOKstateByDefault();    
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	        try {
	            if (!Page2IdentCheckout.isTextoLegalRGPDVisible(dFTest.driver)) {
	                listVals.add(1, State.Defect);
	            }
	            
	            datosStep.setListResultValidations(listVals);
	        }
	        finally { listVals.checkAndStoreValidations(descripValidac); }   
		}
		
		else {
			String descripValidac = 
	            "1) El texto legal de RGPD <b>NO</b> existe para el pais " + pais.getCodigo_pais() + "<br>";
	        datosStep.setNOKstateByDefault();    
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	        try {
	            if (Page2IdentCheckout.isTextoLegalRGPDVisible(dFTest.driver)) {
	                listVals.add(1, State.Defect);
	            }
	            
	            datosStep.setListResultValidations(listVals);
	        }
	        finally { listVals.checkAndStoreValidations(descripValidac); } 
	    }
	}
    
}
