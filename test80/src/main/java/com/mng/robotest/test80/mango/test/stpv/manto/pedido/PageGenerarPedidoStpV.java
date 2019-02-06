package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido;
import static com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageGenerarPedido.GestionPostCompra.*;


public class PageGenerarPedidoStpV {

	public static void validateIsPage(String idPedido, DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
			"1) Aparece la página de generación asociada al pedido <b>" + idPedido + "</b>";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (!PageGenerarPedido.isPage(idPedido, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validations
		String descripValidac = 
			"1) Aparece el mensaje de <b>Fichero creado correctamente</b>";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			// 1)
			if (!PageGenerarPedido.isElementInState(MessageOkFicheroCreado, StateElem.Visible, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
	}
}
