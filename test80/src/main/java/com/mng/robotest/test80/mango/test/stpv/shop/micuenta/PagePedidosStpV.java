package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMispedidos;
import org.openqa.selenium.WebDriver;

public class PagePedidosStpV {

	@Validation
	public static ChecksTM validaIsPageSinPedidos (String usrRegistrado, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de \"Mis Pedidos\"",
			PageMispedidos.isPage(driver), State.Defect);
		validations.add(
			"La página contiene " + usrRegistrado ,
			PageMispedidos.elementContainsText(driver, usrRegistrado), State.Warn);
		validations.add(
			"La lista de pedidos está vacía",
			PageMispedidos.listaPedidosVacia(driver), State.Warn);
		return validations;
	}
}
