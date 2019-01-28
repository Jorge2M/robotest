package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroIni;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.PageData;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

@SuppressWarnings("javadoc")
public class PageRegistroIniStpV {
    
    public static void validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
    	int maxSecondsWait = 5;
        String descripValidac =
            "1) Aparece la página inicial del proceso de registro (la esperamos hasta " + maxSecondsWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();                
            //1)
            if (!PageRegistroIni.isPageUntil(maxSecondsWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static HashMap<String,String> sendDataAccordingCountryToInputs(Pais pais, String emailNonExistent, boolean clickPubli, DataFmwkTest dFTest) 
    throws Exception {
        HashMap<String,String> dataSended = new HashMap<>();

        //Step. Introducir datos en el registro según el país
        DatosStep datosStep = new DatosStep       (
            "Introducir los datos correctos para el país " + pais.getNombre_pais() + " (Si aparece, seleccionar link de publicidad: <b>" + clickPubli + "</b>)", 
            "No aparece ningún mensaje de dato incorrecto");
        try {
            dataSended = PageRegistroIni.sendDataAccordingCountryToInputs(pais, emailNonExistent, clickPubli, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        String descripValidac = 
            "1) No aparece mensaje de error en los campos con datos correctos"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageRegistroIni.isVisibleAnyInputErrorMessage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);                    
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return dataSended;
    }
    
    public static DatosStep sendFixedDataToInputs(ListDataRegistro dataToSend, DataFmwkTest dFTest) {
        //Step. Introducir datos en el registro
        DatosStep datosStep = new DatosStep       (
            "Introducir los datos:<br>" + dataToSend.getFormattedHTMLData(PageData.pageInicial), 
            "En los datos incorrectos aparece error y en los correctos no");
        try {
        	
            PageRegistroIni.sendDataToInputs(dataToSend, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        String descripValidac = 
            "1) No aparece mensaje de error en los campos con datos correctos<br>" + 
            "2) Sí aparece mensaje de error en los campos con datos incorrectos:<br>";
        for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
            if (!dataInput.isValidPrevRegistro())
                descripValidac+=dataInput.getDataRegType() 
				                	+ " (<b>" + dataInput.getData() + "</b>). Error:\"" 
				                	+ PageRegistroIni.getXPathDataInput(dataInput.getDataRegType()).getMsgErrorPrevRegistro() + "\"<br>";  
        }
        
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1) y 2)
            for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
                if (dataInput.isValidPrevRegistro()) {
                    if (PageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType, dFTest.driver) > 0)
                        fmwkTest.addValidation(1, State.Warn, listVals);                    
                }
                else {
                    if (!PageRegistroIni.isVisibleMsgInputInvalid(dataInput.dataRegType, dFTest.driver))
                        fmwkTest.addValidation(2, State.Warn, listVals);                    
                }
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static DatosStep clickRegistrateButton(Pais paisRegistro, boolean usrExists, AppEcom app, HashMap<String,String> dataRegistro, DataFmwkTest dFTest) 
    throws Exception {
        //Step. 
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el botón \"<b>Regístrate</b>\"", 
            "");
        try {
            PageRegistroIni.clickButtonRegistrate(dFTest.driver);
            Thread.sleep(1000);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validacion
        validaIsInvisibleCapaLoading(datosStep, dFTest);
        
        //Validaciones para los casos de error
        if (usrExists ||
            PageRegistroIni.getNumInputsObligatoriosNoInformados(dFTest.driver) > 0)
            validaRegistroKO(paisRegistro, usrExists, datosStep, dFTest);        
        else
            PageRegistroSegundaStpV.validaIsPageRegistroOK(paisRegistro, app, dataRegistro, datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }
    
	public static void validaIsInvisibleCapaLoading(DatosStep datosStep, DataFmwkTest dFTest) {
		int maxSecondsToWait = 3;
        String descripValidac =
            "1) Desparece la capa de loading (lo esperamos hasta " + maxSecondsToWait + " segundos)";         
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();                
            //1)
            if (!PageRegistroIni.isCapaLoadingInvisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
    private static void validaRegistroKO(Pais pais, boolean usrExists, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones para el caso de usuario ya existente
        if (usrExists) {
            String descripValidac =
                "1) Aparece un error \"Email ya registrado\" (lo esperamos hasta 5 segundos)";         
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();                
                //1)
                if (!PageRegistroIni.isVisibleErrorUsrDuplicadoUntil(dFTest.driver, 5/*maxSecondsToWait*/))
                    fmwkTest.addValidation(1, State.Defect, listVals);
        
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        //Validaciones para el caso de campos obligatorions no informados
        int inputsObligNoInf = PageRegistroIni.getNumInputsObligatoriosNoInformados(dFTest.driver);
        if (inputsObligNoInf > 0) {
            int numInputsTypePassrod = PageRegistroIni.getNumberInputsTypePassword(dFTest.driver);
            int numErrCampObligatorio = PageRegistroIni.getNumberMsgCampoObligatorio(dFTest.driver);
            String descripValidac = 
                "1) Aparecen " + inputsObligNoInf + " errores de campo obligatorio<br>" +
                "2) Si existe, en el desplegable aparece seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if ((inputsObligNoInf + numInputsTypePassrod) < numErrCampObligatorio)
                    fmwkTest.addValidation(1, State.Warn, listVals);
                //2)
                if (PageRegistroIni.isVisibleSelectPais(dFTest.driver)) {
                    if (!PageRegistroIni.isSelectedOptionPais(dFTest.driver, pais.getCodigo_pais()))
                        fmwkTest.addValidation(2, State.Warn, listVals);
                }
    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
    }
    
    public static void validaRebajasJun2018(IdiomaPais idioma, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
        String descripValidac = 
            "<b style=\"color:blue\">Rebajas</b></br>" +
            "1) El mensaje de NewsLetter no aparece o si aparece no contiene \"" + percentageSymbol + "\""; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageRegistroIni.newsLetterTitleContains(percentageSymbol, dFTest.driver))
                fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }

	public static void validaIsRGPDVisible(DatosStep datosStep, DataCtxShop dCtxSh, DataFmwkTest dFTest) {
		//Validaciones
		if (dCtxSh.pais.getRgpd().equals("S")) {
			int maxSeconds = 1;
	        String descripValidac = 
	            "1) El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "3) <b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais " + 
	            	dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)"; 
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
		        if (!PageRegistroIni.isTextoRGPDVisible(dFTest.driver))	            	
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            //2)
	            if (!PageRegistroIni.isTextoLegalRGPDVisible(dFTest.driver))
	                fmwkTest.addValidation(2, State.Defect, listVals);
	            //3)
	            if (!PageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds, dFTest.driver))
	                fmwkTest.addValidation(3, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }   
		}
		
		else {
			int maxSeconds = 1;
			String descripValidac = 
	            "1) El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "3) <b>NO</b> es visible el checkbox para recibir promociones e información personalizada para el pais " + 
	            	dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)"; 
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                             
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (PageRegistroIni.isTextoRGPDVisible(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            //2)
	            if (PageRegistroIni.isTextoLegalRGPDVisible(dFTest.driver))
	                fmwkTest.addValidation(2, State.Defect, listVals);
	            //3)
	            if (PageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds, dFTest.driver))
	                fmwkTest.addValidation(3, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 
		}
	}    
}
