package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeLikes;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeLikes.ButtonUseLikes;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps.ChecksResultWithNumberPoints;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;


public class PageHomeLikesSteps extends StepBase {

	private final PageHomeLikes pageHomeLikes = new PageHomeLikes();
	
	@Validation
	public ChecksResultWithNumberPoints checkIsPageOk() {
		ChecksResultWithNumberPoints checks = new ChecksResultWithNumberPoints();
		PageHomeLikes pageLikes = new PageHomeLikes();
		int seconds = 5;
		checks.add(
			"Aparece la página de <b>Mango likes you</b> (esperamos hasta " + seconds + " segundos)",
			pageLikes.checkIsPageUntil(4), State.Defect);
		
		int secondsButton = 10;
		checks.add(
			"Es visible el botón \"Compra con descuento\" (esperamos hasta " + secondsButton + " segundos)",
			pageLikes.isVisibleButton(ButtonUseLikes.COMPRA_CON_DESCUENTO, secondsButton), State.Defect);
		
		checks.setNumberPoints(pageLikes.getPoints());
		checks.add(
			"El número de puntos Loyalty es > 0",
			checks.getNumberPoints() > 0, State.Warn);
		return checks;
	}
	
	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() {
		pageHomeLikes.clickButton(ButtonUseLikes.COMPRA_CON_DESCUENTO);
		new PageHomePurchaseWithDiscountSteps()
			.checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() {
		pageHomeLikes.clickButton(ButtonUseLikes.DONAR_MIS_LIKES);
		new PageHomeDonateLikesSteps().checkIsPage(5);
		GenericChecks.checkDefault();
	}
	
	public void clickConseguirPorLikesButton() {
		if (pageHomeLikes.isVisibleButton(ButtonUseLikes.LIKES_1200, 0)) {
			click1200Likes();
		} else {
			clickSaberMas();
		}
		GenericChecks.checkDefault();
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Saber más Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	private void clickSaberMas() {
		pageHomeLikes.clickButton(ButtonUseLikes.SABER_MAS);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
		
	}
	
	@Step(
		description="Seleccionar el 1er botón \"1200 Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	private void click1200Likes() {
		pageHomeLikes.clickButton(ButtonUseLikes.LIKES_1200);
		new PageHomeConseguirPorLikesSteps().checkIsPage(2);
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Regalar mis Likes\"",
		expected="Aparece la página para regalar mis Likes")
	public PageRegalarMisLikesSteps clickButtonRegalarMisLikes() {
		pageHomeLikes.clickButton(ButtonUseLikes.REGALAR_MIS_LIKES);
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
		};
	}
}
