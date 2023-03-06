package com.mng.robotest.domains.loyalty.steps;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.loyalty.pageobjects.PageMangoLikesYou;
import com.mng.robotest.domains.loyalty.pageobjects.PageMangoLikesYou.ButtonUseLikes;
import com.mng.robotest.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps.ChecksResultWithNumberPoints;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;


public class PageMangoLikesYouSteps extends StepBase {

	private final PageMangoLikesYou pageMangoLikesYou = new PageMangoLikesYou();
	
	@Validation
	public ChecksResultWithNumberPoints checkIsPageOk() {
		var checks = new ChecksResultWithNumberPoints();
		int seconds = 5;
		checks.add(
			"Aparece la página de <b>Mango likes you</b> (esperamos hasta " + seconds + " segundos)",
			pageMangoLikesYou.checkIsPageUntil(4), State.Defect);
		
		int secondsButton = 10;
		checks.add(
			"Es visible el botón \"Compra con descuento\" (esperamos hasta " + secondsButton + " segundos)",
			pageMangoLikesYou.isVisibleButton(ButtonUseLikes.COMPRA_CON_DESCUENTO, secondsButton), State.Defect);
		
		checks.setNumberPoints(pageMangoLikesYou.getPoints());
		checks.add(
			"El número de puntos Loyalty es > 0",
			checks.getNumberPoints() > 0, State.Warn);
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
		pageMangoLikesYou.clickAyuda();
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
	
	@Validation (
		description="Aparece la página específica de ayuda para MangoLikesYou (la esperamos #{seconds} segundos)",
		level=State.Defect)
	public boolean checkPageAyudaMangoLikesYouVisible(int seconds) {
		return pageMangoLikesYou.isPageAyudaMangoLikesYouVisible(seconds);
	}	
	
	@Step(
		description="Seleccionar la pestaña <b>#{tabLink.name()}</b>",
		expected="Aparece la página asociada a dicha pestaña")
	public void click(TabLink tabLink) {
		pageMangoLikesYou.click(tabLink);
		if (tabLink==TabLink.HISTORIAL) {
			new PageHistorialLikesSteps().checkHistorialVisible(5);
		}
	}
	
	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() {
		pageMangoLikesYou.clickButton(ButtonUseLikes.COMPRA_CON_DESCUENTO);
		new PageHomePurchaseWithDiscountSteps()
			.checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() {
		pageMangoLikesYou.clickButton(ButtonUseLikes.DONAR_MIS_LIKES);
		new PageHomeDonateLikesSteps().checkIsPage(5);
		GenericChecks.checkDefault();
	}
	
	public void clickConseguirPorLikesButton() {
		if (pageMangoLikesYou.isVisibleButton(ButtonUseLikes.LIKES_1200, 0)) {
			click1200Likes();
		} else {
			clickSaberMas();
		}
		GenericChecks.checkDefault();
	}
	
	@Step(
		description="Seleccionar el 1er botón para la compra de una entrada de cine",
		expected="Aparece una página para conseguir la entrada de cine")
	private void clickSaberMas() {
		pageMangoLikesYou.clickButton(ButtonUseLikes.ENTRADA_CINE);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
		
	}
	
	@Step(
		description="Seleccionar el 1er botón \"1200 Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	private void click1200Likes() {
		pageMangoLikesYou.clickButton(ButtonUseLikes.LIKES_1200);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Regalar mis Likes\"",
		expected="Aparece la página para regalar mis Likes")
	public PageRegalarMisLikesSteps clickButtonRegalarMisLikes() {
		pageMangoLikesYou.clickButton(ButtonUseLikes.REGALAR_MIS_LIKES);
		PageRegalarMisLikesSteps pageRegalarSteps = new PageRegalarMisLikesSteps();
		pageRegalarSteps.checkIsPage();
		return pageRegalarSteps;
	}
	
	@Validation (
		description=
			"- El usuario <b>#{dataPoints.getClienteEmisor()}</b> regala <b>#{dataPoints.getPointsRegalados()}</b> puntos a <b>#{dataPoints.getClienteReceptor()}</b><br>" +
			"- El emisor tenía inicialmente #{dataPoints.getIniPointsEmisor()} y el receptor #{dataPoints.getIniPointsReceptor()}<br>" +
			"- Validamos que el emisor tenga finalmente <b>#{dataPoints.getFinPointsEmisorExpected()}</b> y el receptor <b>#{dataPoints.getFinPointsReceptorExpected()}</b><br>" +
			"- Info: lo que se ve realmente es que el emisor acaba teniendo #{dataPoints.getFinPointsEmisorReal()} y el receptor #{dataPoints.getFinPointsReceptorReal()}",
		level=State.Defect)
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