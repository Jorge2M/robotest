package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.annotations.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaIdEans;

public class PageConsultaIdEansStpV {

	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Es visible el contenido de la pestaña Busqueda Excel",
			PageConsultaIdEans.isVisibleDivBusquedaExcel(driver), State.Defect);
	 	validations.add(
			"Es visible el contenido de la pestaña Busqueda Rapida",
			PageConsultaIdEans.isVisibleDivBusquedaRapida(driver), State.Defect);
	 	validations.add(
			"Es visible el título de página correcto",
			PageConsultaIdEans.isVisibleTituloPagina(driver), State.Defect);
	 	return validations;
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
    private static ChecksResult checkAfterConsultContact(List<String> pedidosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsWait + " segundos)",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de pedido es " + pedidosPrueba.size(),
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.size(),
    		PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);
    	return validations;
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
    private static ChecksResult checkAfterConsultaIdentPedidos(List<String> pedidosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de pedido es " + pedidosPrueba.size(),
    		PageConsultaIdEans.getLineasPedido(driver)==pedidosPrueba.size(), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString(),
    		PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, driver), State.Defect);		
    	return validations;
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
		description="Se muestra la tabla de información (la esperamos un máximo de #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean checkIsTableTrackingsInformation(int maxSecondsWait, WebDriver driver) {
		return (PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsWait, driver));
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
	private static ChecksResult checkAfterConsultEAN(List<String> articulosPrueba, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
    	validations.add(
    		"Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
    		PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, driver), State.Defect);
    	validations.add(
    		"El número de líneas de artículos es " + articulosPrueba.size(),
    		PageConsultaIdEans.getLineasPedido(driver)==articulosPrueba.size(), State.Defect);
    	validations.add(
    		"Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString(),
    		PageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba, driver), State.Defect);
    	return validations;
	}
}
