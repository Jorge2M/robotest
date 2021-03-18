package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paysecureqiwi;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rst.PaysecureGateway;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePaysecureQiwi1rstStpV {

	private final PagePaysecureQiwi1rst pagePaysecureQiwi;
	private final WebDriver driver;
	
	public PagePaysecureQiwi1rstStpV(AppEcom app, WebDriver driver) {
		this.pagePaysecureQiwi = new PagePaysecureQiwi1rst(app, driver);
		this.driver = driver;
	}
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais, Channel channel) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página inicial de la pasarela PaySecure",
			pagePaysecureQiwi.isPage(), State.Warn);
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	validations.add(
			"Aparece el icono de Qiwi",
			pagePaysecureQiwi.isPresentIcon(PaysecureGateway.Qiwi), State.Warn);
	 	return validations;
	}

	@Step (
		description="Seleccionar la opción de Qiwi Kошелек", 
		expected="Aparece la página de introducción del número de teléfono")
	public void clickIconPasarelaQiwi(Channel channel) throws Exception {
		pagePaysecureQiwi.clickIcon(PaysecureGateway.Qiwi);
		PageQiwiInputTlfnStpV.validateIsPage(driver);
	}
}
