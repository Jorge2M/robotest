package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTallaTiendas;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;

@SuppressWarnings("javadoc")
public class ModalDetalleMisComprasStpV {
    
    /**
     * Cerrar el buscador de tiendas mediante selección de la aspa
     */
    public static void clickBuscarTiendaButton(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Damos click al botón de \"Buscar talla en tienda\"",
            "Aparece el modal de busqueda en tienda");
        try {
        	
        	ModalDetalleMisCompras.clickBuscarTallaTiendaButton(dFTest.driver);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                    
        //Validaciones.
        String descripValidac = 
            "1) Aparece el modal de búsqueda de talla en tienda";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalBuscadorTallaTiendas.isVisible(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            ModalBuscadorTallaTiendas.clickAspaForClose(dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);}
    }
}
