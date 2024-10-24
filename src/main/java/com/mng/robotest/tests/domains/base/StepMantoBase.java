package com.mng.robotest.tests.domains.base;

import java.time.LocalDate;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.manto.steps.PageLoginMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PagePedidosMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PageSelTdaMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.SecFiltrosMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public abstract class StepMantoBase extends PageBase {

	protected static final String CODIGO_ESPANYA = "001";
	protected static final String ALMACEN_ESPANYA = "001";
	protected static final String SECONDS_WAIT = "(esperamos hasta #{seconds} segundos)";
	
	protected String getLitSecondsWait(int seconds) {
		return "(esperamos hasta " + seconds + " segundos)";
	}
	
	protected static final Pais ESPANYA = new Pais();
	static {
		ESPANYA.setCodigoPais(CODIGO_ESPANYA);
		ESPANYA.setNombrePais("España");
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
        new PageMenusMantoSteps().goToPedidosStep();
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
		var pgPedidosMantoSteps = new PagePedidosMantoSteps();
		pgPedidosMantoSteps.setPedidoUsuarioRegistrado(pedidoData);
		pgPedidosMantoSteps.setDataPedidoStep(pedidoData);
		pgPedidosMantoSteps.setDataCliente(pedidoData);
		pgPedidosMantoSteps.setTiendaFisicaListaPedidos(pedidoData);
	}
	
	protected StepTM getCurrentStep() {
		return TestMaker.getCurrentStepInExecution();
	}
	protected String getStepDescription() {
		return getCurrentStep().getDescripcion();
	}
	protected void setStepDescription(String description) {
		getCurrentStep().setDescripcion(description);
	}
	protected void replaceStepDescription(String oldChar, String newChar) {
		getCurrentStep().replaceInDescription(oldChar, newChar);
	}
	protected void replaceStepExpected(String oldChar, String newChar) {
		getCurrentStep().replaceInExpected(oldChar, newChar);
	}
	
}
