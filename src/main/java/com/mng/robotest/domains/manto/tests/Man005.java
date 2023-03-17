package com.mng.robotest.domains.manto.tests;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.domains.manto.steps.pedidos.PageGestorEstadisticasPedidoSteps;

public class Man005 extends TestMantoBase {

	@Override
	public void execute() {
		accesoAlmacenEspanya();
		goToGestorEstadisticasPedido();		
		comparePedidosZalandoES();
	}
	
	private void goToGestorEstadisticasPedido() {
		new PageMenusMantoSteps().goToGestorEstadisticasPedido();
	}
	
	private void comparePedidosZalandoES() {
		var pageGestorEstadisticasPedidoSteps = new PageGestorEstadisticasPedidoSteps();
		pageGestorEstadisticasPedidoSteps.searchZalandoOrdersInformation();
		pageGestorEstadisticasPedidoSteps.compareLastDayInformation();
	}

}
