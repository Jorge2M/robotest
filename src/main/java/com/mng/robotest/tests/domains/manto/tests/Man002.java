package com.mng.robotest.tests.domains.manto.tests;

import com.mng.robotest.tests.domains.base.TestMantoBase;
import com.mng.robotest.tests.domains.manto.steps.PageGestionarClientesSteps;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;

public class Man002 extends TestMantoBase {

	@Override
	public void execute() {
		accesoAlmacenEspanya();
		var pedido = searchPedido();
		goToGestionarClientes();
		consultaCliente(pedido.getPago().getDni());
	}
	
	private void goToGestionarClientes() {
		new PageMenusMantoSteps().goToGestionarClientes();
	}
	
	private void consultaCliente(String dni) {
		var pageGestionarClientesSteps = new PageGestionarClientesSteps();
		pageGestionarClientesSteps.inputDniAndClickBuscar(dni);
		pageGestionarClientesSteps.clickDetallesButton(dni);		
	}

}
