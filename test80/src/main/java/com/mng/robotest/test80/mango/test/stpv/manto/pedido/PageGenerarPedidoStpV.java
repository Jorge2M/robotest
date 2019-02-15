package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido;
import static com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.GestionPostCompra.*;


public class PageGenerarPedidoStpV {

	public static void validateIsPage(String idPedido, DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
			"1) Aparece la página de generación asociada al pedido <b>" + idPedido + "</b>";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			// 1)
			if (!PageGenerarPedido.isPage(idPedido, dFTest.driver))
				listVals.add(1, State.Defect);

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
	public static DatosStep changePedidoToEstado(EstadoPedido newState, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el estado <b>" + newState + "</b> y pulsamos el botón <b>Generar Fichero</b>", 
            "Aparece una página de la pasarela de resultado OK");
        try {
            PageGenerarPedido.selectEstado(newState, dFTest.driver);
            PageGenerarPedido.clickAndWait(GenerarFicheroButton, dFTest.driver);
                               
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validations
		String descripValidac = 
			"1) Aparece el mensaje de <b>Fichero creado correctamente</b>";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			// 1)
			if (!PageGenerarPedido.isElementInState(MessageOkFicheroCreado, StateElem.Visible, dFTest.driver))
				listVals.add(1, State.Defect);

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
	}
}
