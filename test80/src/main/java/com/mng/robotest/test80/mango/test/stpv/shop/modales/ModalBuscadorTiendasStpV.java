package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {                                
            if (!ModalBuscadorTiendas.isVisible(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ModalBuscadorTiendas.isPresentAnyTiendaUntil(dFTest.driver, maxSecondsToWait)) {
                listVals.add(2, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
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
        datosStep.setStateIniValidations();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (ModalBuscadorTiendas.isVisible(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                        
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
