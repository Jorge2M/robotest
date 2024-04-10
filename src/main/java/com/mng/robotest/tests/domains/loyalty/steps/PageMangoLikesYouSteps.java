package com.mng.robotest.tests.domains.loyalty.steps;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps.ChecksResultWithNumberPoints;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes.*;
import static com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.ButtonUseLikes.*;

public class PageMangoLikesYouSteps extends StepBase {

	private final PageMangoLikesYou pgMangoLikesYou = new PageMangoLikesYou();
	
	@Validation
	public ChecksResultWithNumberPoints checkIsPage() {
		var checks = new ChecksResultWithNumberPoints();
		int seconds = 5;
		checks.add(
			"Aparece la página de <b>Mango likes you</b> " + getLitSecondsWait(seconds),
			pgMangoLikesYou.isPage(4));
		
		int secondsButton = 10;
		checks.add(
			"Es visible el botón \"Compra con descuento\" (esperamos hasta " + secondsButton + " segundos)",
			pgMangoLikesYou.isVisibleButton(COMPRA_CON_DESCUENTO, secondsButton));
		
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
		description="Seleccionar la pestaña <b>#{tabLink.name()}</b>",
		expected="Aparece la página asociada a dicha pestaña")
	public void click(TabLink tabLink) {
		pgMangoLikesYou.click(tabLink);
		if (tabLink==TabLink.HISTORIAL) {
			new PageHistorialLikesSteps().checkHistorialVisible(5);
		}
	}
	
	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() {
		pgMangoLikesYou.clickButton(COMPRA_CON_DESCUENTO);
		new PageHomePurchaseWithDiscountSteps()
			.checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() {
		pgMangoLikesYou.clickButton(DONAR_MIS_LIKES);
		new PageHomeDonateLikesSteps().checkIsPage(5, BUTTON_50_LIKES, BUTTON_100_LIKES);
		checksDefault();
	}
	
	public void clickConseguirPorLikesButton() {
		if (pgMangoLikesYou.isVisibleButton(LIKES_1200, 0)) {
			click1200Likes();
		} else {
			clickSaberMas();
		}
		checksDefault();
	}
	
	@Step(
		description="Seleccionar el 1er botón para la compra de una entrada de cine",
		expected="Aparece una página para conseguir la entrada de cine")
	private void clickSaberMas() {
		pgMangoLikesYou.clickButton(ENTRADA_CINE);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
		
	}
	
	@Step(
		description="Seleccionar el 1er botón \"1200 Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	private void click1200Likes() {
		pgMangoLikesYou.clickButton(LIKES_1200);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Regalar mis Likes\"",
		expected="Aparece la página para regalar mis Likes")
	public PageRegalarMisLikesSteps clickButtonRegalarMisLikes() {
		pgMangoLikesYou.clickButton(REGALAR_MIS_LIKES);
		var pageRegalarSteps = new PageRegalarMisLikesSteps();
		pageRegalarSteps.checkIsPage();
		return pageRegalarSteps;
	}
	
	@Validation (
		description=
			"- El usuario <b>#{dataPoints.getClienteEmisor()}</b> regala <b>#{dataPoints.getPointsRegalados()}</b> puntos a <b>#{dataPoints.getClienteReceptor()}</b><br>" +
			"- El emisor tenía inicialmente #{dataPoints.getIniPointsEmisor()} y el receptor #{dataPoints.getIniPointsReceptor()}<br>" +
			"- Validamos que el emisor tenga finalmente <b>#{dataPoints.getFinPointsEmisorExpected()}</b> y el receptor <b>#{dataPoints.getFinPointsReceptorExpected()}</b><br>" +
			"- Info: lo que se ve realmente es que el emisor acaba teniendo #{dataPoints.getFinPointsEmisorReal()} y el receptor #{dataPoints.getFinPointsReceptorReal()}")
	public static boolean checkRegalarPointsOk(DataRegaloPuntos dataPoints) {
		return (
			dataPoints.getFinPointsEmisorExpected()==dataPoints.getFinPointsEmisorReal() &&
			dataPoints.getFinPointsReceptorExpected()==dataPoints.getFinPointsReceptorReal());
	}
	
	public static class DataRegaloPuntos {
		private String clienteEmisor;
		private String clienteReceptor;
		private int pointsRegalados;
		private int iniPointsEmisor; 
		private int iniPointsReceptor;
		private int finPointsEmisorExpected;
		private int finPointsReceptorExpected;
		private int finPointsEmisorReal;
		private int finPointsReceptorReal;
		
		public DataRegaloPuntos() {}

		public String getClienteEmisor() {
			return clienteEmisor;
		}
		public void setClienteEmisor(String clienteEmisor) {
			this.clienteEmisor = clienteEmisor;
		}
		public String getClienteReceptor() {
			return clienteReceptor;
		}
		public void setClienteReceptor(String clienteReceptor) {
			this.clienteReceptor = clienteReceptor;
		}
		public int getPointsRegalados() {
			return pointsRegalados;
		}
		public void setPointsRegalados(int pointsRegalados) {
			this.pointsRegalados = pointsRegalados;
		}
		public int getIniPointsEmisor() {
			return iniPointsEmisor;
		}
		public void setIniPointsEmisor(int iniPointsEmisor) {
			this.iniPointsEmisor = iniPointsEmisor;
		}
		public int getIniPointsReceptor() {
			return iniPointsReceptor;
		}
		public void setIniPointsReceptor(int iniPointsReceptor) {
			this.iniPointsReceptor = iniPointsReceptor;
		}
		public int getFinPointsEmisorExpected() {
			return finPointsEmisorExpected;
		}
		public void setFinPointsEmisorExpected(int finPointsEmisorExpected) {
			this.finPointsEmisorExpected = finPointsEmisorExpected;
		}
		public int getFinPointsReceptorExpected() {
			return finPointsReceptorExpected;
		}
		public void setFinPointsReceptorExpected(int finPointsReceptorExpected) {
			this.finPointsReceptorExpected = finPointsReceptorExpected;
		}
		public int getFinPointsEmisorReal() {
			return finPointsEmisorReal;
		}
		public void setFinPointsEmisorReal(int finPointsEmisorReal) {
			this.finPointsEmisorReal = finPointsEmisorReal;
		}
		public int getFinPointsReceptorReal() {
			return finPointsReceptorReal;
		}
		public void setFinPointsReceptorReal(int finPointsReceptorReal) {
			this.finPointsReceptorReal = finPointsReceptorReal;
		}
	}
}
