package com.mng.robotest.test.steps.shop.pedidos;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageInputPedido;
import com.mng.robotest.test.datastored.DataPedido;

public class PageInputPedidoSteps extends StepBase {

	private final PageInputPedido pageInputPedido = new PageInputPedido();
	
	@Validation (
		description="La página contiene un campo para la introducción del Nº de pedido",
		level=State.Warn)
	public boolean validateIsPage() {
		return (pageInputPedido.isVisibleInputPedido());
	}
	
	@Step (
		description=
			"Buscar el pedido <b style=\"color:brown;\">#{dataPedido.getCodpedido()}</b> introduciendo email + nº pedido</b>",
		expected=
			"Apareca la página con los datos correctos del pedido")
	public void inputPedidoAndSubmit(DataPedido dataPedido) {
		String usuarioAcceso = dataPedido.getEmailCheckout();
		String codPedido = dataPedido.getCodpedido();
		pageInputPedido.inputEmailUsr(usuarioAcceso);
		pageInputPedido.inputPedido(codPedido);
		pageInputPedido.clickRecuperarDatos();

		PageDetallePedidoSteps pageDetPedidoSteps = new PageDetallePedidoSteps(channel);
		pageDetPedidoSteps.validateIsPageOk(dataPedido);
	}
}
