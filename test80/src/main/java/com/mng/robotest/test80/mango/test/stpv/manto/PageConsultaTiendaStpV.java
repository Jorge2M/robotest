package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaTienda;

@SuppressWarnings("javadoc")
public class PageConsultaTiendaStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Es visible el input para la introducción de la tienda";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaTienda.isVisibleInputTienda(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

	public static void consultaTiendaInexistente(String tiendaNoExistente, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
	            "Introducimos tienda \"" + tiendaNoExistente + "\"", 
	            "No debe ser válida");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            PageConsultaTienda.introducirTienda(tiendaNoExistente, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece el mensaje La tienda no existe.";
        datosStep.setNOKstateByDefault();     
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaTienda.apareceMensajeTiendaNoExiste(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static void consultaTiendaExistente(String tiendaExistente, DataFmwkTest dFTest) {
		//Step
		DatosStep datosStep = new DatosStep       (
            "Introducimos tienda \"" + tiendaExistente + "\"", 
            "No debe ser válida");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            PageConsultaTienda.introducirTienda(tiendaExistente, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validation
        String descripValidac = 
            "1) Aparece la información de la tienda<br>" + 
            "2) No aparece el mensaje de tienda no existe";
        datosStep.setNOKstateByDefault();        
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaTienda.apareceInformacionTienda(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageConsultaTienda.apareceMensajeTiendaNoExiste(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
                
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
}
