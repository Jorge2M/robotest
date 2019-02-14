package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
        finally { fmwkTest.grabStep(datosStep, dFTest); }
                    
        //Validaciones.
        String descripValidac = 
            "1) Aparece el modal de búsqueda de talla en tienda";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalBuscadorTallaTiendas.isVisible(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            ModalBuscadorTallaTiendas.clickAspaForClose(dFTest.driver);
                        
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
