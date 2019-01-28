package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalSuscripcion;

@SuppressWarnings("javadoc")
public class ModalSuscripcionStpV {

	 /**
     * Validaciones del código fuente de la página para ver si están presentes o no los textos legales de RGPD
     */
    public static DatosStep validaRGPDModal(DataCtxShop dCtxSh, DataFmwkTest dFTest) {
    	DatosStep datosStep = new DatosStep (
                "Comprobar que se incluyen o no los textos legales de RGPD en el modal de suscripcion (el modal está en el HTML de la página no visible)<br>",
                "Los textos existen en el código fuente dependiendo del pais");
        datosStep.setGrabNettrafic(dFTest.ctx);
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
            
    	//Validaciones
		if (dCtxSh.pais.getRgpd().equals("S")) {
	        String descripValidac = 
	            "1) El texto de info de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (!ModalSuscripcion.isTextoRGPDPresent(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            //2)
	            if (!ModalSuscripcion.isTextoLegalRGPDPresent(dFTest.driver))
	                fmwkTest.addValidation(2, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }   
		}
		
		else {
			String descripValidac = 
	            "1) El texto de info de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>" + 
	            "2) El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais " + dCtxSh.pais.getCodigo_pais() + "<br>";
	        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                             
	        try {
	            List<SimpleValidation> listVals = new ArrayList<>();
	            //1)
	            if (ModalSuscripcion.isTextoRGPDPresent(dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
	            //2)
	            if (ModalSuscripcion.isTextoLegalRGPDPresent(dFTest.driver))
	                fmwkTest.addValidation(2, State.Defect, listVals);
	            
	            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	        }
	        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); } 
		}
		return datosStep;
    }
    
}
