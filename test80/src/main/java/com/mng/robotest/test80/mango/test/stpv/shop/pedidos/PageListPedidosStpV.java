package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;

public class PageListPedidosStpV {

	@Validation
    public static ChecksTM validateIsPage(String codigoPedido, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
      	validations.add(
      		"La página contiene el bloque correspondiente a la lista de pedidos",
      		PageListPedidos.isPage(driver), State.Defect);	
      	validations.add(
      		"Figura la línea correspondiente al pedido " + codigoPedido,
      		PageListPedidos.isVisibleCodPedido(codigoPedido, driver), State.Info);	
		return validations;
    }
    
	@Step (
		description="Seleccionar el pedido de la lista <b>#{codPedido}</b>", 
        expected="Apareca la página con los datos correctos del pedido")
    public static void selectPedido(String codPedido, WebDriver driver) {
		PageListPedidos.selectLineaPedido(codPedido, driver);
    }
}
