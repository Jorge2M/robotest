package com.mng.robotest.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.manto.PageGestorEstadisticasPedido;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageGestorEstadisticasPedidoStpV {

	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestorEstadisticasPedido.titulo,
			PageGestorEstadisticasPedido.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece el input de fecha de inicio",
			PageGestorEstadisticasPedido.isVisibleStartDateInput(driver), State.Defect);
	 	validations.add(
			"Aparece el input de fecha fin",
			PageGestorEstadisticasPedido.isVisibleEndDateInput(driver), State.Defect);
	 	return validations;
	}
	
	@Step (
		description="Seleccionamos \"Todos los zalandos\" y damos click a \"Mostrar Pedidos\"",
		expected="Muestra la tabla de información correctamente",
		saveErrorData=SaveWhen.Never)
	public static void searchZalandoOrdersInformation(WebDriver driver) throws Exception {
		PageGestorEstadisticasPedido.selectZalandoAndClickShowOrdersButton(driver);
		checkAfterSelectMostrarPedidosZalandos(driver);
	}
	
	@Validation
	private static ChecksTM checkAfterSelectMostrarPedidosZalandos(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la tabla de información",
			PageGestorEstadisticasPedido.isTablaInformacionVisible(driver), State.Defect);
	 	validations.add(
			"Las columnas de comparación en verde contienen \"0 €\"",
			PageGestorEstadisticasPedido.isColumnaCompararVerdeZero(driver), State.Defect);
	 	validations.add(
			"Las columnas de comparación en rojo contienen \"0 %\"",
			PageGestorEstadisticasPedido.isColumnaCompararRojoZero(driver), State.Defect);
	 	return validations;
	}

	@Step (
		description="Seleccionamos el radio \"Día Anterior\" y damos click a \"Comparar\"",
		expected="Se muestran las celdas rojas y verdes con valores correctos",
		saveErrorData=SaveWhen.Never)
	public static void compareLastDayInformation(WebDriver driver) throws Exception {
		PageGestorEstadisticasPedido.selectDiaAnteriorAndClickCompararButton(driver);
		checkAfterCompararDias(driver);
	}
	
	@Validation
	private static ChecksTM checkAfterCompararDias(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Las columnas de comparación en verde no contienen \"0 €\"",
			PageGestorEstadisticasPedido.isColumnaCompararVerdeNoZero(driver), State.Defect);
	 	validations.add(
			"Las columnas de comparación en rojo no contienen \"0 %\"",
			PageGestorEstadisticasPedido.isColumnaCompararRojaNoZero(driver), State.Defect);
		return validations;
	}
}
