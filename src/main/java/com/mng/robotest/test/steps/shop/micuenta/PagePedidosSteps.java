package com.mng.robotest.test.steps.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageMispedidos;

import org.openqa.selenium.WebDriver;

public class PagePedidosSteps {

	@Validation
	public static ChecksTM validaIsPageSinPedidos (String usrRegistrado, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de \"Mis Pedidos\"",
			PageMispedidos.isPage(driver), State.Defect);
		checks.add(
			"La página contiene " + usrRegistrado ,
			PageMispedidos.elementContainsText(driver, usrRegistrado), State.Warn);
		checks.add(
			"La lista de pedidos está vacía",
			PageMispedidos.listaPedidosVacia(driver), State.Warn);
		return checks;
	}
}
