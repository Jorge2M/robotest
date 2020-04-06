package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageInputPedido;

public class PageInputPedidoStpV {

	private final WebDriver driver;
	private final PageInputPedido pageInputPedido;
	
	private PageInputPedidoStpV(WebDriver driver) {
		this.driver = driver;
		this.pageInputPedido = new PageInputPedido(driver);
	}
	public static PageInputPedidoStpV getNew(WebDriver driver) {
		return new PageInputPedidoStpV(driver);
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

		PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
		pageDetPedidoStpV.validateIsPageOk(dataPedido);
	}
}
