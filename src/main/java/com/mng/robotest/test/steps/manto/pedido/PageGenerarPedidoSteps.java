package com.mng.robotest.test.steps.manto.pedido;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.manto.pedido.PageGenerarPedido;
import com.mng.robotest.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido;

import static com.mng.robotest.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido.*;


public class PageGenerarPedidoSteps extends StepBase {

	private final PageGenerarPedido pageGenerarPedido = new PageGenerarPedido();
	
	@Validation (
		description="Aparece la página de generación asociada al pedido <b>#{idPedido}</b>",
		level=State.Defect)
	public boolean validateIsPage(String idPedido) {
		return pageGenerarPedido.isPage(idPedido);
	}
	
	public void anulaPedido() {
		EstadoPedido estadoPedido = pageGenerarPedido.getEstadoPedido();
		if (estadoPedido==EstadoPedido.PENDIENTE) {
			changePedidoToEstado(RECHAZADO);
		} else {
			changePedidoToEstado(ANULADO);
		}
	}
	
	@Step (
		description="Seleccionamos el estado <b>#{newState}</b> y pulsamos el botón <b>Generar Fichero</b>", 
		expected="Aparece una página de la pasarela de resultado OK",
		saveErrorData=SaveWhen.Never)
	public void changePedidoToEstado(EstadoPedido newState) {
		pageGenerarPedido.selectEstado(newState);
		pageGenerarPedido.clickInformarBancoEnCasoCancelacionAlGenerarPedido();
		pageGenerarPedido.clickGenerarFicheroPedido();
		checkMsgFileCreatedCorrectly();
	}
	
	@Validation (
		description="Aparece el mensaje de <b>Fichero creado correctamente</b>",
		level=State.Warn)
	private boolean checkMsgFileCreatedCorrectly() {
		return pageGenerarPedido.isVisibleMessageFileCreated();
	}
}
