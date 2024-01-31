package com.mng.robotest.tests.domains.footer.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.changecountry.steps.ModalChangeCountrySteps;
import com.mng.robotest.tests.domains.footer.pageobjects.FactoryPageFromFooter;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecFooterSteps extends StepBase {
	
	private final SecFooter secFooter = new SecFooter();
	
	@Validation 
	public ChecksTM checkLinksFooter() { 
		var checks = ChecksTM.getNew();
		for (var footerLink : FooterLink.getFooterLinksFiltered(app, channel)) {
			checks.add(
				"Aparecen el link <b>" + footerLink + "</b> en el footer",
				secFooter.checkFooter(footerLink));
		}
		return checks;
	}

	public void clickLinkFooter(FooterLink typeFooter) { 
		clickLinkFooter(typeFooter, false);
	}
	
	@Step (
		description="Seleccionar el link del footer <b>#{typeFooter}</b>", 
		expected="Se redirige a la pantalla adecuada")
	public void clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd) { 
		String windowFatherHandle = secFooter.clickLinkAndGetWindowFatherHandle(typeFooter);
		checkPageCorrectAfterSelectLinkFooter(windowFatherHandle, typeFooter, closeAtEnd);
		checksDefault();
	}
	 
	@Validation
	private ChecksTM checkPageCorrectAfterSelectLinkFooter(String windowFatherHandle, FooterLink typeFooter, boolean closeAtEnd) {
		var checks = ChecksTM.getNew();
		var pageObject = FactoryPageFromFooter.make(typeFooter);
		String windowActualHandle = driver.getWindowHandle();
		boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
		int seconds = 5;
		try {
			checks.add(
				"Aparece la página <b>" + pageObject.getName() + "</b> " + getLitSecondsWait(seconds),
				pageObject.isPageCorrectUntil(seconds), WARN);
			if (typeFooter.pageInNewTab()) {
				checks.add(
					"Aparece la página en una ventana aparte",
					newWindowInNewTab, WARN);		
			}
		}
		finally {
			if (typeFooter.pageInNewTab() &&
				closeAtEnd && newWindowInNewTab) {
				driver.close();
				driver.switchTo().window(windowFatherHandle);
			}
		}		
		
		return checks;
	}

	 @Step (
		description="Se selecciona el link para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	 public void clickChangeCountry() {
		 secFooter.clickLinkCambioPais();
		 new ModalChangeCountrySteps().checkIsVisible(5);
	 }
	 
	 public void clickFooterSubscriptionInput() {
		 clickFooterSubscriptionInput(true);
	 }
	 
	 @Step (
		description="Hacer click en el cuadro de suscripción del footer",
		expected="Aparecen los textos legales de RGPD")
	public void clickFooterSubscriptionInput(boolean clickRegister) {
		if (!clickRegister) {
			SecCabecera.make().clickLogoMango();
		}
		secFooter.clickFooterSuscripcion();
	}
	 
}
