package com.mng.robotest.test.steps.manto;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.manto.PageConsultaIdEans;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageConsultaIdEansSteps {

	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Excel",
			PageConsultaIdEans.isVisibleDivBusquedaExcel(driver), State.Defect);
	 	checks.add(
			"Es visible el contenido de la pestaña Busqueda Rapida",
			PageConsultaIdEans.isVisibleDivBusquedaRapida(driver), State.Defect);
	 	checks.add(
			"Es visible el título de página correcto",
			PageConsultaIdEans.isVisibleTituloPagina(driver), State.Defect);
	 	return checks;
	}

	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos los datos de contacto", 
		expected="Deben mostrar la información de contacto",
		saveErrorData=SaveWhen.Never)
	public static void consultaDatosContacto(List<String> pedidosPrueba, WebDriver driver) {
		PageConsultaIdEans.inputPedidosAndClickBuscarDatos(pedidosPrueba, driver);
		checkAfterConsultContact(pedidosPrueba, driver);
	}
	
	@Validation
	private static ChecksTM checkAfterConsultContact(List<String> pedidosPrueba, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSeconds + " segundos)",
			PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds, driver), State.Defect);
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds, driver), State.Defect);
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.size(),
			PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);
		return checks;
	}

	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos los Identificadores que tiene",
		expected="Debe mostrar los identificadores del pedido",
		saveErrorData=SaveWhen.Never)
	public static void consultaIdentificadoresPedido(List<String> pedidosPrueba, WebDriver driver) {
		PageConsultaIdEans.inputPedidosAndClickBuscarIdentificadores(pedidosPrueba, driver);
		checkAfterConsultaIdentPedidos(pedidosPrueba, driver);
	}
	
	@Validation
	private static ChecksTM checkAfterConsultaIdentPedidos(List<String> pedidosPrueba, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
		checks.add(
			"El número de líneas de pedido es " + pedidosPrueba.size(),
			PageConsultaIdEans.getLineasPedido(driver)==pedidosPrueba.size(), State.Defect);
		checks.add(
			"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString(),
			PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);		
		return checks;
	}
	
	@Step (
		description="Introducimos datos de pedidos válidos #{pedidosPrueba} y consultamos el trackings",
		expected="Debe mostrar el tracking",
		saveErrorData=SaveWhen.Never)
	public static void consultaTrackings(List<String> pedidosPrueba, WebDriver driver) {
		PageConsultaIdEans.inputPedidosAndClickBuscarTrackings(pedidosPrueba, driver);
		checkIsTableTrackingsInformation(2, driver);
	}
	
	@Validation (
		description="Se muestra la tabla de información (la esperamos un máximo de #{maxSeconds} segundos)",
		level=State.Defect)
	private static boolean checkIsTableTrackingsInformation(int maxSeconds, WebDriver driver) {
		return (PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSeconds, driver));
	}

	@Step (
		description="Introducimos artículos válidos #{articulosPrueba} y consultamos el EAN",
		expected="Debe mostrar el EAN",
		saveErrorData=SaveWhen.Never)
	public static void consultaDatosEan(List<String> articulosPrueba, WebDriver driver) {
		PageConsultaIdEans.inputArticulosAndClickBuscarDatosEan(articulosPrueba, driver);
		checkAfterConsultEAN(articulosPrueba, driver);
	}
	
	@Validation
	private static ChecksTM checkAfterConsultEAN(List<String> articulosPrueba, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		checks.add(
			"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
		checks.add(
			"El número de líneas de artículos es " + articulosPrueba.size(),
			PageConsultaIdEans.getLineasPedido(driver)==articulosPrueba.size(), State.Defect);
		checks.add(
			"Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString(),
			PageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba, driver), State.Defect);
		return checks;
	}
}
