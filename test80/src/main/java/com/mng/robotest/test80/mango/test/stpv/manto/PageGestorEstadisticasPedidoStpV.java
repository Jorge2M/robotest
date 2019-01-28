package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorEstadisticasPedido;

@SuppressWarnings("javadoc")
public class PageGestorEstadisticasPedidoStpV {



	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
				"1) Estamos en la página \"" + PageGestorEstadisticasPedido.titulo + " \"<br>" +
				"2) Aparece el input de fecha de inicio<br>" + 
				"3) Aparece el input de fecha fin";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorEstadisticasPedido.isPage(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorEstadisticasPedido.isVisibleStartDateInput(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
			//3)
			if (!PageGestorEstadisticasPedido.isVisibleEndDateInput(dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
	
	
	
	
	public static void searchZalandoOrdersInformation(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Seleccionamos \"Todos los zalandos\" y damos click a \"Mostrar Pedidos\"", 
				"Muestra la tabla de información correctamente");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorEstadisticasPedido.selectZalandoAndClickShowOrdersButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece la tabla de información<br>" +
				"2) Las columnas de comparación en verde contienen \"0 €\"<br>" +
				"3) Las columnas de comparación en rojo contienen \"0 %\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorEstadisticasPedido.isTablaInformacionVisible(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorEstadisticasPedido.isColumnaCompararVerdeZero(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
			//3)
			if (!PageGestorEstadisticasPedido.isColumnaCompararRojoZero(dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);
          

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	
	

	public static void compareLastDayInformation(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Seleccionamos el radio \"Día Anterior\" y damos click a \"Comparar\"", 
				"Se muestran las celdas rojas y verdes con valores correctos");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorEstadisticasPedido.selectDiaAnteriorAndClickCompararButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Las columnas de comparación en verde no contienen \"0 €\"<br>" +
				"2) Las columnas de comparación en rojo no contienen \"0 %\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorEstadisticasPedido.isColumnaCompararVerdeNoZero(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorEstadisticasPedido.isColumnaCompararRojaNoZero(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
            

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
		
	}
	
	
	


}
