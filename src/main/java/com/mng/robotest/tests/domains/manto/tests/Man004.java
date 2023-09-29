package com.mng.robotest.tests.domains.manto.tests;

import com.mng.robotest.tests.domains.base.TestMantoBase;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.pedidos.PageGestorEstadisticasPedidoSteps;

public class Man004 extends TestMantoBase {

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
