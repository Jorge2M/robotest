package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;

public class PageListPedidosStpV {

	@Validation
    public static ChecksResult validateIsPage(String codigoPedido, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
      	validations.add(
      		"La página contiene el bloque correspondiente a la lista de pedidos<br>",
      		PageListPedidos.isPage(driver), State.Defect);	
      	validations.add(
      		"Figura la línea correspondiente al pedido " + codigoPedido,
      		PageListPedidos.isVisibleCodPedido(codigoPedido, driver), State.Info);	
		return validations;
    }
    
	@Step (
		description="Seleccionar el pedido de la lista <b>#{codPedido}</b>", 
        expected="Apareca la página con los datos correctos del pedido")
    public static void selectPedido(String codPedido, WebDriver driver) throws Exception {
		PageListPedidos.selectLineaPedido(codPedido, driver);
    }
}
