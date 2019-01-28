package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

@SuppressWarnings("javadoc")
public class ModalBuscadorTiendasStpV {

    public static void validaBusquedaConResultados(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) La capa de búsqueda es visible<br>" +
            "2) Se ha localizado alguna tienda (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {                                
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalBuscadorTiendas.isVisible(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ModalBuscadorTiendas.isPresentAnyTiendaUntil(dFTest.driver, maxSecondsToWait))
                fmwkTest.addValidation(2, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);}
    }
    
    /**
     * Cerrar el buscador de tiendas mediante selección de la aspa
     */
    public static void close(DataFmwkTest dFTest) {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Cerramos la capa correspondiente al resultado del buscador", 
            "La capa correspondiente a la búsqueda desaparece");
        try {
            ModalBuscadorTiendas.clickAspaForClose(dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                    
        //Validaciones.
        String descripValidac = 
            "1) La capa correspondiente a la búsqueda desaparece";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (ModalBuscadorTiendas.isVisible(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);}
    }
}
