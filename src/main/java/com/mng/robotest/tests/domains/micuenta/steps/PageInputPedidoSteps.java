package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageInputPedido;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageInputPedidoSteps extends StepBase {

	private final PageInputPedido pgInputPedido = new PageInputPedido();
	
	@Validation (
		description="La página contiene un campo para la introducción del Nº de pedido",
		level=WARN)
	public boolean validateIsPage() {
		return pgInputPedido.isVisibleInputPedido();
	}
	
	@Step (
		description=
			"Buscar el pedido <b style=\"color:brown;\">#{dataPedido.getCodpedido()}</b> introduciendo email + nº pedido</b>",
		expected=
			"Apareca la página con los datos correctos del pedido")
	public void inputPedidoAndSubmit(DataPedido dataPedido) {
		String usuarioAcceso = dataPedido.getEmailCheckout();
		String codPedido = dataPedido.getCodpedido();
		pgInputPedido.inputEmailUsr(usuarioAcceso);
		pgInputPedido.inputPedido(codPedido);
		pgInputPedido.clickRecuperarDatos();
		new PageDetallePedidoSteps().validateIsPageOk(dataPedido);
	}
	
}
