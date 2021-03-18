package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageInputPedido;

public class PageInputPedidoStpV {

	private final WebDriver driver;
	private final Channel channel;
	private final PageInputPedido pageInputPedido;
	
	private PageInputPedidoStpV(Channel channel, WebDriver driver) {
		this.channel = channel;
		this.driver = driver;
		this.pageInputPedido = new PageInputPedido(driver);
	}
	public static PageInputPedidoStpV getNew(Channel channel, WebDriver driver) {
		return new PageInputPedidoStpV(channel, driver);
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

		PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(channel, driver);
		pageDetPedidoStpV.validateIsPageOk(dataPedido);
	}
}
