package com.mng.robotest.test.steps.manto;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.manto.PageConsultaIdEans;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;


public class PageConsultaIdEansSteps extends StepBase {

	private final PageConsultaIdEans pageConsultaIdEans = new PageConsultaIdEans();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Excel",
			pageConsultaIdEans.isVisibleDivBusquedaExcel(), State.Defect);
	 	
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Rapida",
			pageConsultaIdEans.isVisibleDivBusquedaRapida(), State.Defect);
	 	
	 	checks.add(
			"Es visible el título de página correcto",
			pageConsultaIdEans.isVisibleTituloPagina(), State.Defect);
	 	
	 	return checks;
	}

	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos los datos de contacto", 
		expected="Deben mostrar la información de contacto",
		saveErrorData=SaveWhen.Never)
	public void consultaDatosContacto(List<String> pedidosPrueba) {
		pageConsultaIdEans.inputPedidosAndClickBuscarDatos(pedidosPrueba);
		checkAfterConsultContact(pedidosPrueba);
	}
	
	@Validation
	private ChecksTM checkAfterConsultContact(List<String> pedidosPrueba) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSeconds + " segundos)",
			pageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds), State.Defect);
		
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			pageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds), State.Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.size(),
			pageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba), State.Defect);
		
		return checks;
	}

	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos los Identificadores que tiene",
		expected="Debe mostrar los identificadores del pedido",
		saveErrorData=SaveWhen.Never)
	public void consultaIdentificadoresPedido(List<String> pedidosPrueba) {
		pageConsultaIdEans.inputPedidosAndClickBuscarIdentificadores(pedidosPrueba);
		checkAfterConsultaIdentPedidos(pedidosPrueba);
	}
	
	@Validation
	private ChecksTM checkAfterConsultaIdentPedidos(List<String> pedidosPrueba) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			pageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait), State.Defect);
		
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			pageConsultaIdEans.getLineasPedido()==pedidosPrueba.size(), State.Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString(),
			pageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba), State.Defect);
		
		return checks;
	}
	
	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos el trackings",
		expected="Debe mostrar el tracking",
		saveErrorData=SaveWhen.Never)
	public void consultaTrackings(List<String> pedidosPrueba) {
		pageConsultaIdEans.inputPedidosAndClickBuscarTrackings(pedidosPrueba);
		checkIsTableTrackingsInformation(2);
	}
	
	@Validation (
		description="Se muestra la tabla de información (la esperamos un máximo de #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkIsTableTrackingsInformation(int maxSeconds) {
		return pageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds);
	}

	@Step (
		description="Introducimos artículos válidos #{articulosPrueba} y consultamos el EAN",
		expected="Debe mostrar el EAN",
		saveErrorData=SaveWhen.Never)
	public void consultaDatosEan(List<String> articulosPrueba) {
		pageConsultaIdEans.inputArticulosAndClickBuscarDatosEan(articulosPrueba);
		checkAfterConsultEAN(articulosPrueba);
	}
	
	@Validation
	private ChecksTM checkAfterConsultEAN(List<String> articulosPrueba) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			pageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait), State.Defect);
		
		checks.add(
			"El número de líneas de artículos es " + articulosPrueba.size(),
			pageConsultaIdEans.getLineasPedido()==articulosPrueba.size(), State.Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString(),
			pageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba), State.Defect);
		
		return checks;
	}
}
