package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class ModalBuscadorTiendasStpV {

	@Validation
    public static ListResultValidation validaBusquedaConResultados(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"La capa de búsqueda es visible<br>",
			ModalBuscadorTiendas.isVisible(driver), State.Warn);
	 	validations.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + maxSecondsWait + " segundos)",
			ModalBuscadorTiendas.isPresentAnyTiendaUntil(driver, maxSecondsWait), State.Warn);
		return validations;
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
        finally { StepAspect.storeDataAfterStep(datosStep); }
                    
        //Validaciones.
        String descripValidac = 
            "1) La capa correspondiente a la búsqueda desaparece";
        datosStep.setNOKstateByDefault();
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
