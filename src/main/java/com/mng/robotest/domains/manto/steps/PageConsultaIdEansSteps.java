package com.mng.robotest.domains.manto.steps;

import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageConsultaIdEans;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageConsultaIdEansSteps extends StepMantoBase {

	private final PageConsultaIdEans pageConsultaIdEans = new PageConsultaIdEans();
	
	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Excel",
			pageConsultaIdEans.isVisibleDivBusquedaExcel(), Defect);
	 	
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Rapida",
			pageConsultaIdEans.isVisibleDivBusquedaRapida(), Defect);
	 	
	 	checks.add(
			"Es visible el título de página correcto",
			pageConsultaIdEans.isVisibleTituloPagina(), Defect);
	 	
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
		var checks = ChecksTM.getNew();
		int seconds = 2;
		
		checks.add(String.format(
			"Se muestra la tabla de información (la esperamos un máximo de %s segundos)", seconds),
			pageConsultaIdEans.isVisibleTablaInformacionUntil(seconds), Defect);
		
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			pageConsultaIdEans.isVisibleTablaInformacionUntil(seconds), Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.size(),
			pageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba), Defect);
		
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
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + seconds + " segundos)",
			pageConsultaIdEans.isVisibleTablaInformacionUntil(seconds), Defect);
		
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			pageConsultaIdEans.getLineasPedido()==pedidosPrueba.size(), Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString(),
			pageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba), Defect);
		
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
		description="Se muestra la tabla de información (la esperamos un máximo de #{seconds} segundos)",
		level=Defect)
	private boolean checkIsTableTrackingsInformation(int seconds) {
		return pageConsultaIdEans.isVisibleTablaInformacionUntil(seconds);
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
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + seconds + " segundos)",
			pageConsultaIdEans.isVisibleTablaInformacionUntil(seconds), Defect);
		
		checks.add(
			"El número de líneas de artículos es " + articulosPrueba.size(),
			pageConsultaIdEans.getLineasPedido()==articulosPrueba.size(), Defect);
		
		checks.add(
			"Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString(),
			pageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba), Defect);
		
		return checks;
	}
}
