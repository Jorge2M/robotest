package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaIdEans;

public class PageConsultaIdEansStpV {

	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Es visible el contenido de la pestaña Busqueda Excel<br>",
			PageConsultaIdEans.isVisibleDivBusquedaExcel(driver), State.Defect);
	 	validations.add(
			"Es visible el contenido de la pestaña Busqueda Rapida<br>",
			PageConsultaIdEans.isVisibleDivBusquedaRapida(driver), State.Defect);
	 	validations.add(
			"Es visible el título de página correcto",
			PageConsultaIdEans.isVisibleTituloPagina(driver), State.Defect);
	 	return validations;
    }

    @Step (
    	description="Introducimos datos de pedido válido y consultamos los datos de contacto", 
	    expected="Deben mostrar la información de contacto",
	    saveErrorPage=SaveWhen.Never)
	public static void consultaDatosContacto(List<String> pedidosPrueba, WebDriver driver) {
	    PageConsultaIdEans.inputPedidosAndClickBuscarDatos(pedidosPrueba, driver);
	    checkAfterConsultContact(pedidosPrueba, driver);
	}
    
    @Validation
    private static ChecksResult checkAfterConsultContact(List<String> pedidosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsWait + " segundos)<br>",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de pedido es " + pedidosPrueba.size() + "<br>",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.size() + "</b>",
    		PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);
    	return validations;
    }

    @Step (
    	description="Introducimos datos de pedido válido y consultamos los Identificadores que tiene",
    	expected="Debe mostrar los identificadores del pedido",
    	saveErrorPage=SaveWhen.Never)
	public static void consultaIdentificadoresPedido(List<String> pedidosPrueba, WebDriver driver) {
        PageConsultaIdEans.inputPedidosAndClickBuscarIdentificadores(pedidosPrueba, driver);
        checkAfterConsultaIdentPedidos(pedidosPrueba, driver);
	}
    
    @Validation
    private static ChecksResult checkAfterConsultaIdentPedidos(List<String> pedidosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de pedido es " + pedidosPrueba.size() + "<br>",
    		PageConsultaIdEans.getLineasPedido(driver)==pedidosPrueba.size(), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString() + "</b>",
    		PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);		
    	return validations;
    }
	
	@Step (
		description="Introducimos datos de pedido válido y consultamos el trackings",
		expected="Debe mostrar el tracking",
		saveErrorPage=SaveWhen.Never)
	public static void consultaTrackings(List<String> pedidosPrueba, WebDriver driver) {
        PageConsultaIdEans.inputPedidosAndClickBuscarTrackings(pedidosPrueba, driver);
		checkIsTableTrackingsInformation(2, driver);
	}
	
	@Validation (
		description="Se muestra la tabla de información (la esperamos un máximo de #{maxSecondsWait} segundos)<br>",
		level=State.Defect)
	private static boolean checkIsTableTrackingsInformation(int maxSecondsWait, WebDriver driver) {
		return (PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver));
	}

	@Step (
		description="Introducimos artículos válidos y consultamos el EAN",
		expected="Debe mostrar el EAN",
		saveErrorPage=SaveWhen.Never)
	public static void consultaDatosEan(List<String> articulosPrueba, WebDriver driver) {
        PageConsultaIdEans.inputArticulosAndClickBuscarDatosEan(articulosPrueba, driver);
		checkAfterConsultEAN(articulosPrueba, driver);
	}
	
	@Validation
	private static ChecksResult checkAfterConsultEAN(List<String> articulosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de artículos es " + articulosPrueba.size() + "<br>",
    		PageConsultaIdEans.getLineasPedido(driver)==articulosPrueba.size(), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString() + "</b>",
    		PageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba, driver), State.Defect);
    	return validations;
	}
}
