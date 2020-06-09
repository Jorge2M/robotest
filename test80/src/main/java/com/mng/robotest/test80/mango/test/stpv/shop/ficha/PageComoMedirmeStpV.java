package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageComoMedirme;

public class PageComoMedirmeStpV {

	private final PageComoMedirme pageComoMedirme;
	
	private PageComoMedirmeStpV(WebDriver driver) {
		this.pageComoMedirme = PageComoMedirme.getNew(driver);
	}
	
	public static PageComoMedirmeStpV getNew(WebDriver driver) {
		return new PageComoMedirmeStpV(driver);
	}
	
	@Validation (
		description="Aparece la página de <b>Cómo medirme</b> en una nueva pestaña",
		level=State.Warn)
	public boolean isPageInNewTab() throws Exception {
		return (pageComoMedirme.goToPageInNewTabCheckAndClose());
	}
}
