package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageInputPedido;

public class PageInputPedidoStpV {
    
	@Validation (
		description="La página contiene un campo para la introducción del Nº de pedido",
		level=State.Warn)
    public static boolean validateIsPage(WebDriver driver) {
		return (PageInputPedido.isVisibleInputPedido(driver));
    }
    
	@Step (
		description="Buscar el pedido <b>#{dataPedido.getCodpedido()}</b> introduciendo email + nº pedido</b>", 
        expected="Apareca la página con los datos correctos del pedido")
    public static void inputPedidoAndSubmit(DataPedido dataPedido, WebDriver driver) 
    throws Exception {
        String usuarioAcceso = dataPedido.getEmailCheckout();
        String codPedido = dataPedido.getCodpedido();
        PageInputPedido.inputEmailUsr(usuarioAcceso, driver);
        PageInputPedido.inputPedido(codPedido, driver);
        PageInputPedido.clickRecuperarDatos(driver);
        
        //Validaciones
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
        pageDetPedidoStpV.validateIsPageOk(dataPedido, driver);
    }
}
