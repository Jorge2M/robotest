package com.mng.robotest.tests.domains.loyalty.steps;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps.ChecksResultWithNumberPoints;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes.*;

public class MangoLikesYouSteps extends StepBase {

	private final PageMangoLikesYou pgMangoLikesYou;
	
	public MangoLikesYouSteps() {
		this.pgMangoLikesYou = PageMangoLikesYou.make(dataTest.getPais()); 
	}
	
	@Validation
	public ChecksResultWithNumberPoints checkIsPage() {
		var checks = new ChecksResultWithNumberPoints();
		int seconds = 6;
		checks.add(
			"Aparece la página de <b>Mango likes you</b> " + getLitSecondsWait(seconds),
			pgMangoLikesYou.isPage(seconds));
		
		int secondsButton = 10;
		checks.add(
			"Es visible el botón \"Compra con descuento\" (esperamos hasta " + secondsButton + " segundos)",
			pgMangoLikesYou.isVisibleCompraConDescuentoExperience(secondsButton));
		
		checks.setNumberPoints(pgMangoLikesYou.getPoints());
		checks.add(
			"El número de puntos Loyalty es > 0",
			checks.getNumberPoints() > 0, WARN);
		return checks;
	}

	public void clickAyuda() {
		Pair<String, String> pair = clickAyudaStep();
		switchToParent(pair.getLeft(), pair.getRight());
	}	
	
	@Step(
		description="Seleccionar el link de <b>Ayuda</b>",
		expected="Aparece la página de ayuda específica de Mango Likes You")
	private Pair<String, String> clickAyudaStep() {
		String parentWindow = driver.getWindowHandle();
		pgMangoLikesYou.clickAyuda();
		String childWindow = switchToAnotherWindow(driver, parentWindow);
		checkPageAyudaMangoLikesYouVisible(2);
		return Pair.of(parentWindow, childWindow);
	}
	
	private void switchToParent(String parentWindow, String childWindow) {
		if (childWindow.compareTo(parentWindow)!=0) {
			driver.switchTo().window(childWindow);
			driver.close();
			driver.switchTo().window(parentWindow);
		}
	}
	
	@Validation
    public ChecksTM checkPageAyudaMangoLikesYouVisible(int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página específica de ayuda para MangoLikesYou "  + SECONDS_WAIT + 
		    "(problema <a href='https://jira.mango.com/browse/PIUR-4471'>PIUR4471</a>)",
		    pgMangoLikesYou.isPageAyudaMangoLikesYouVisible(seconds), WARN);
		
	    return checks;
    }	
	
	@Step(
		description="Seleccionar la opción <b>Historial</b>",
		expected="Aparece el historial de movimientos")
	public void clickHistorial() {
		pgMangoLikesYou.clickHistorial();
		new PageHistorialLikesSteps().checkHistorialVisible(5);
	}	
	
	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() {
		pgMangoLikesYou.clickCompraConDescuentoExperience();
		new PageHomePurchaseWithDiscountSteps()
			.checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() {
		pgMangoLikesYou.clickDonation();
		new PageHomeDonateLikesSteps().checkIsPage(5, BUTTON_50_LIKES, BUTTON_100_LIKES);
		checksDefault();
	}
	
	@Step(
		description="Seleccionar la primera experiencia",
		expected="Aparece una página para conseguir la experiencia")
	public void clickExchangeLikesForExperience() {
		pgMangoLikesYou.clickExchangeLikesForExperience();
		new PageHomeConseguirPorLikesSteps().checkIsPage(3);
		checksDefault();
	}
	
}
