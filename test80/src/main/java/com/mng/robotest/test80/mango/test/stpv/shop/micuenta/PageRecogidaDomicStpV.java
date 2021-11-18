package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRecADomic;

public class PageRecogidaDomicStpV {

	@Validation
	public static ChecksTM vaidaIsPageSinDevoluciones (WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de Recogida a Domicilio",
			PageRecADomic.isPage(driver), State.Defect);
		validations.add(
			"Aparece la tabla de devoluciones",
			PageRecADomic.isTableDevoluciones(driver), State.Defect);
		validations.add(
			"No aparece ningún pedido",
			 !PageRecADomic.hayPedidos(driver), State.Info);

		return validations;
	}
}
