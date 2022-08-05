package com.mng.robotest.test.steps.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.miscompras.PageInputPedido;

public class PageInputPedidoSteps {

	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	private final PageInputPedido pageInputPedido;
	
	private PageInputPedidoSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.channel = channel;
		this.app = app;
		this.driver = driver;
		this.pageInputPedido = new PageInputPedido(driver);
	}
	public static PageInputPedidoSteps getNew(Channel channel, AppEcom app, WebDriver driver) {
		return new PageInputPedidoSteps(channel, app, driver);
	}
	
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

		PageDetallePedidoSteps pageDetPedidoSteps = new PageDetallePedidoSteps(channel, app, driver);
		pageDetPedidoSteps.validateIsPageOk(dataPedido);
	}
}
