package com.mng.robotest.domains.base;

import java.time.LocalDate;

import com.mng.robotest.domains.manto.steps.PageLoginMantoSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.domains.manto.steps.PagePedidosMantoSteps;
import com.mng.robotest.domains.manto.steps.PageSelTdaMantoSteps;
import com.mng.robotest.domains.manto.steps.SecFiltrosMantoSteps;
import com.mng.robotest.domains.manto.steps.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPedido;

public abstract class StepMantoBase extends PageBase {

	protected static final String CODIGO_ESPANYA = "001";
	protected static final String ALMACEN_ESPANYA = "001";
	
	protected static final Pais ESPANYA = new Pais();
	static {
		ESPANYA.setCodigo_pais(CODIGO_ESPANYA);
		ESPANYA.setNombre_pais("España");
	}
	
	protected void accesoAlmacenEspanya() {
		new PageLoginMantoSteps().login();
		new PageSelTdaMantoSteps().selectTienda(ALMACEN_ESPANYA, CODIGO_ESPANYA);
	}
	
	protected DataPedido searchPedido() {
		goToPedidos();
		return makeDataPedido();
	}
	
	private void goToPedidos() {
        new PageMenusMantoSteps().goToPedidos();
	}
	
	private DataPedido makeDataPedido() {
		var pedidoData = new DataPedido(ESPANYA, null);
		pedidoData.setCodigopais(CODIGO_ESPANYA);
		Pago dataPagoPrueba = new Pago();
		dataPagoPrueba.setNombre("");
		pedidoData.setPago(dataPagoPrueba);
		
		filterPedidosSevenDaysAgo(pedidoData);
		setDataPedido(pedidoData);		
		return pedidoData;
	}
	
	private void filterPedidosSevenDaysAgo(DataPedido pedidoData) {
		LocalDate dateSevenDaysAgo = LocalDate.now().minusDays(7);
		new SecFiltrosMantoSteps().setFiltrosWithoutChequeRegaloYbuscar(
				pedidoData, TypeSearch.PEDIDO, dateSevenDaysAgo, LocalDate.now());
	}
	
	private void setDataPedido(DataPedido pedidoData) {
		PagePedidosMantoSteps pagePedidosMantoSteps = new PagePedidosMantoSteps();
		pagePedidosMantoSteps.setPedidoUsuarioRegistrado(pedidoData);
		pagePedidosMantoSteps.setDataPedido(pedidoData);
		pagePedidosMantoSteps.setDataCliente(pedidoData);
		pagePedidosMantoSteps.setTiendaFisicaListaPedidos(pedidoData);
	}
	
}
