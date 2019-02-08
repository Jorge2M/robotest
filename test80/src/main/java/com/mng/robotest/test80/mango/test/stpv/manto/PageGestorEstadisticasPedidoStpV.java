package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
		datosStep.setStateIniValidations();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorEstadisticasPedido.isPage(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorEstadisticasPedido.isVisibleStartDateInput(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageGestorEstadisticasPedido.isVisibleEndDateInput(dFTest.driver)) {
				listVals.add(3, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
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
		datosStep.setStateIniValidations();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorEstadisticasPedido.isTablaInformacionVisible(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorEstadisticasPedido.isColumnaCompararVerdeZero(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageGestorEstadisticasPedido.isColumnaCompararRojoZero(dFTest.driver)) {
				listVals.add(3, State.Defect);
			}
          
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
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
		datosStep.setStateIniValidations();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorEstadisticasPedido.isColumnaCompararVerdeNoZero(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorEstadisticasPedido.isColumnaCompararRojaNoZero(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
            
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
