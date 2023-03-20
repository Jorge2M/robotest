package com.mng.robotest.domains.manto.steps.pedidos;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageGestorEstadisticasPedido;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageGestorEstadisticasPedidoSteps extends StepMantoBase {

	private final PageGestorEstadisticasPedido pageGestorEstadisticasPedido = new PageGestorEstadisticasPedido();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestorEstadisticasPedido.TITULO,
			pageGestorEstadisticasPedido.isPage(), State.Defect);
	 	
	 	checks.add(
			"Aparece el input de fecha de inicio",
			pageGestorEstadisticasPedido.isVisibleStartDateInput(), State.Defect);
	 	
	 	checks.add(
			"Aparece el input de fecha fin",
			pageGestorEstadisticasPedido.isVisibleEndDateInput(), State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos \"Todos los zalandos\" y damos click a \"Mostrar Pedidos\"",
		expected="Muestra la tabla de información correctamente",
		saveErrorData=SaveWhen.Never)
	public void searchZalandoOrdersInformation() {
		pageGestorEstadisticasPedido.selectZalandoEs();
		pageGestorEstadisticasPedido.inputFechaInicioYesterday();
		pageGestorEstadisticasPedido.clickMostrarPedidosButton();
		checkAfterSelectMostrarPedidosZalandos();
	}
	
	@Validation
	private ChecksTM checkAfterSelectMostrarPedidosZalandos() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la tabla de información",
			pageGestorEstadisticasPedido.isTablaInformacionVisible(), State.Defect);
	 	
	 	checks.add(
			"Las columnas de comparación en verde contienen \"0 €\"",
			pageGestorEstadisticasPedido.isColumnaCompararVerdeZero(), State.Defect);
	 	
	 	checks.add(
			"Las columnas de comparación en rojo contienen \"0 %\"",
			pageGestorEstadisticasPedido.isColumnaCompararRojoZero(), State.Defect);
	 	
	 	return checks;
	}

	@Step (
		description="Seleccionamos el radio \"Día Anterior\" y damos click a \"Comparar\"",
		expected="Se muestran las celdas rojas y verdes con valores correctos",
		saveErrorData=SaveWhen.Never)
	public void compareLastDayInformation() {
		pageGestorEstadisticasPedido.selectDiaAnteriorAndClickCompararButton();
		checkAfterCompararDias();
	}
	
	@Validation
	private ChecksTM checkAfterCompararDias() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Las columnas de comparación en verde no contienen \"0 €\"",
			pageGestorEstadisticasPedido.isColumnaCompararVerdeNoZero(), State.Defect);
	 	
	 	checks.add(
			"Las columnas de comparación en rojo no contienen \"0 %\"",
			pageGestorEstadisticasPedido.isColumnaCompararRojaNoZero(), State.Defect);
	 	
		return checks;
	}
}