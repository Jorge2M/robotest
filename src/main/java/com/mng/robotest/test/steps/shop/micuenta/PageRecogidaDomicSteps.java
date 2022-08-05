package com.mng.robotest.test.steps.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageRecADomic;

public class PageRecogidaDomicSteps {

	@Validation
	public static ChecksTM vaidaIsPageSinDevoluciones (WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Recogida a Domicilio",
			PageRecADomic.isPage(driver), State.Defect);
		checks.add(
			"Aparece la tabla de devoluciones",
			PageRecADomic.isTableDevoluciones(driver), State.Defect);
		checks.add(
			"No aparece ningún pedido",
			 !PageRecADomic.hayPedidos(driver), State.Info);

		return checks;
	}
}
