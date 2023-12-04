package com.mng.robotest.tests.domains.manto.steps.pedidos;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageGestorEstadisticasPedido;

import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageGestorEstadisticasPedidoSteps extends StepMantoBase {

	private final PageGestorEstadisticasPedido pgGestorEstadisticasPedido = new PageGestorEstadisticasPedido();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestorEstadisticasPedido.TITULO,
			pgGestorEstadisticasPedido.isPage());
	 	
	 	checks.add(
			"Aparece el input de fecha de inicio",
			pgGestorEstadisticasPedido.isVisibleStartDateInput());
	 	
	 	checks.add(
			"Aparece el input de fecha fin",
			pgGestorEstadisticasPedido.isVisibleEndDateInput());
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos \"Todos los zalandos\" y damos click a \"Mostrar Pedidos\"",
		expected="Muestra la tabla de información correctamente",
		saveErrorData=NEVER)
	public void searchZalandoOrdersInformation() {
		pgGestorEstadisticasPedido.selectZalandoEs();
		pgGestorEstadisticasPedido.inputFechaInicioYesterday();
		pgGestorEstadisticasPedido.clickMostrarPedidosButton();
		checkAfterSelectMostrarPedidosZalandos();
	}
	
	@Validation
	private ChecksTM checkAfterSelectMostrarPedidosZalandos() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la tabla de información",
			pgGestorEstadisticasPedido.isTablaInformacionVisible());
	 	
	 	checks.add(
			"Las columnas de comparación en verde contienen \"0 €\"",
			pgGestorEstadisticasPedido.isColumnaCompararVerdeZero());
	 	
	 	checks.add(
			"Las columnas de comparación en rojo contienen \"0 %\"",
			pgGestorEstadisticasPedido.isColumnaCompararRojoZero());
	 	
	 	return checks;
	}

	@Step (
		description="Seleccionamos el radio \"Día Anterior\" y damos click a \"Comparar\"",
		expected="Se muestran las celdas rojas y verdes con valores correctos",
		saveErrorData=NEVER)
	public void compareLastDayInformation() {
		pgGestorEstadisticasPedido.selectDiaAnteriorAndClickCompararButton();
		checkAfterCompararDias();
	}
	
	@Validation
	private ChecksTM checkAfterCompararDias() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Las columnas de comparación en verde no contienen \"0 €\"",
			pgGestorEstadisticasPedido.isColumnaCompararVerdeNoZero());
	 	
	 	checks.add(
			"Las columnas de comparación en rojo no contienen \"0 %\"",
			pgGestorEstadisticasPedido.isColumnaCompararRojaNoZero());
	 	
		return checks;
	}
}
