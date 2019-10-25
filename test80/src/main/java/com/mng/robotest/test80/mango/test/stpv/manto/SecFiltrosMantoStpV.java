package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecFiltros;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la sección de filtros de Pedidos en Manto
 * @author jorge.munoz
 *
 */

public class SecFiltrosMantoStpV {

    public enum TypeSearch {BOLSA, PEDIDO} 
    
    final static String tagNombrePago = "@TagNombrePago";
    final static String tagLitTienda = "@TagLitTienda";
    @Step (
    	description=
    		"Buscamos a nivel de #{typeSearch} el pedido <b style=\"color:blue;\">#{dataPedido.getCodigoPedidoManto()}</b> con filtros: <br>" +
    	    "- Método pago: <b>" + tagNombrePago + "</b><br>" +
    	    "- Tienda: <b>" + tagLitTienda + "</b><br>" +
    	    "- País: <b>#{dataPedido.getNombrePais()}</b> (#{dataPedido.getCodigoPais()})",
    	expected="La búsqueda es correcta",
    	saveErrorData=SaveWhen.Never)
    public static void setFiltrosHoyYbuscar(DataPedido dataPedido, @SuppressWarnings("unused") TypeSearch typeSearch, WebDriver driver) 
    throws Exception {
    	StepTM step = TestMaker.getCurrentStepInExecution();
    	step.replaceInDescription(tagNombrePago, dataPedido.getPago().getNombre());
    	step.replaceInDescription(tagLitTienda, SecCabecera.getLitTienda(driver));
    	
    	if (dataPedido.getCodigoPedidoManto()!=null) {
            SecFiltros.setFiltroCodPedido(dataPedido.getCodigoPedidoManto(), driver);
    	}
    	SecFiltros.setFiltroCodPaisIfExists(driver, dataPedido.getCodigoPais());
        String fechaHoy = SecFiltros.getFechaHastaValue(driver);
        SecFiltros.setFiltroFDesde(fechaHoy, driver);
        SecFiltros.clickButtonBuscar(driver);
    }
    
    public static void setFiltrosHoyWithoutChequeRegaloYbuscar(DataPedido dataPedido, TypeSearch typeSearch, WebDriver driver) 
    throws Exception {
    	setFiltroForAvoidChequeRegalo(driver);
    	setFiltrosHoyYbuscar(dataPedido, typeSearch, driver);
    }
    
    @Step (
    	description="Introducimos un importe total de 19.99 euros para evitar obtener pedidos asociados a cheques regalo",
    	expected="El filtro se setea correctamente")
    public static void setFiltroForAvoidChequeRegalo(WebDriver driver) {
        SecFiltros.setFiltroImporteTotal("19.99", driver);
    }
}
