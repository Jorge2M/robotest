package com.mng.robotest.test.stpv.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;

public class PagePostfRedirectStpV {

	private PagePostfRedirect pagePostfRedirect;
	
	public PagePostfRedirectStpV(WebDriver driver) {
		pagePostfRedirect = new PagePostfRedirect(driver);
	}
	
	@Validation
	public ChecksTM isPageAndFinallyDisappears() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 10;
		validations.add(
			"Aparece una página de redirección con un botón OK",
			pagePostfRedirect.isPresentButtonOk(), State.Defect);
		validations.add(
			"La página de redirección acaba desapareciendo (esperamos hasta " + maxSeconds + " segundos)",
			pagePostfRedirect.isInvisibleButtonOkUntil(maxSeconds), State.Defect);
		return validations;
	}
}
