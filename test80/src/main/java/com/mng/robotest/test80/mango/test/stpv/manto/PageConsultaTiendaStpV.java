package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaTienda;

@SuppressWarnings("javadoc")
public class PageConsultaTiendaStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Es visible el input para la introducción de la tienda";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageConsultaTienda.isVisibleInputTienda(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

	public static void consultaTiendaInexistente(String tiendaNoExistente, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
	            "Introducimos tienda \"" + tiendaNoExistente + "\"", 
	            "No debe ser válida");
	        datosStep.setGrab_ErrorPageIfProblem(false);
	        
	        try {
	            PageConsultaTienda.introducirTienda(tiendaNoExistente, dFTest.driver);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	        }
	        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	        
	        //Validaciones
	        String descripValidac = 
	            "1) Aparece el mensaje La tienda no existe.";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (!PageConsultaTienda.apareceMensajeTiendaNoExiste(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	                
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }  
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}


	
	public static void consultaTiendaExistente(String tiendaExistente, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
	            "Introducimos tienda \"" + tiendaExistente + "\"", 
	            "No debe ser válida");
	        datosStep.setGrab_ErrorPageIfProblem(false);
	        try {
	            PageConsultaTienda.introducirTienda(tiendaExistente, dFTest.driver);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	        }
	        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	        
	        //Validaciones
	        String descripValidac = 
	            "1) Aparece la información de la tienda<br>" + 
	            "2) No aparece el mensaje de tienda no existe";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (!PageConsultaTienda.apareceInformacionTienda(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            
	            //2)
	            if (PageConsultaTienda.apareceMensajeTiendaNoExiste(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	                
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }  
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
}
