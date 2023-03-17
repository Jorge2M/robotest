package com.mng.robotest.domains.manto.tests;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.steps.PageGestionarClientesSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;

public class Man003 extends TestMantoBase {

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
		pageGestionarClientesSteps.clickThirdButton();
		pageGestionarClientesSteps.clickThirdButton();
		pageGestionarClientesSteps.clickDetallesButton(dni);
	}

}
