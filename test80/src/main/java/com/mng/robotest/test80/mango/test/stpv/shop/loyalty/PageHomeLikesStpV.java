package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV.ChecksResultWithNumberPoints;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeLikes;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeLikes.ButtonUseLikes;

public class PageHomeLikesStpV {

	private final WebDriver driver;
	private final PageHomeLikes pageHomeLikes;
	
	private PageHomeLikesStpV(WebDriver driver) {
		this.driver = driver;
		this.pageHomeLikes = PageHomeLikes.getNew(driver);
	}
	
	public static PageHomeLikesStpV getNewInstance(WebDriver driver) {
		return (new PageHomeLikesStpV(driver));
	}
	
	@Validation
	public ChecksResultWithNumberPoints checkIsPageOk() {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		PageHomeLikes pageLikes = PageHomeLikes.getNew(driver);
		int maxSeconds = 5;
		checks.add(
			"Aparece la página de <b>Mango likes you</b> (esperamos hasta " + maxSeconds + " segundos)",
			pageLikes.checkIsPageUntil(4), State.Defect);
		
		int maxSecondsButton = 2;
		checks.add(
			"Es visible el botón \"Compra con descuento\" (esperamos hasta " + maxSecondsButton + " segundos)",
			pageLikes.isVisibleButton(ButtonUseLikes.CompraConDescuento, maxSecondsButton), State.Defect);
		
		checks.setNumberPoints(pageLikes.getPoints());
		checks.add(
			"El número de puntos Loyalty es > 0",
			checks.getNumberPoints() > 0, State.Warn);
		return checks;
	}
	
	@Step(
		description="Seleccionar el link \"Compra un descuento\"",
		expected="Aparece la página de \"Compra con descuento\"")
	public void clickOpcionCompraUnDescuento() throws Exception {
		pageHomeLikes.clickButton(ButtonUseLikes.CompraConDescuento);
		PageHomePurchaseWithDiscountStpV.getNew(driver)
			.checkHomePurchaseWithDiscountPageOk();
	}

	@Step(
		description="Seleccionar el 1er botón \"Donar mis Likes\"",
		expected="Aparece una página para donar mis Likes")
	public void clickButtonDonarLikes() {
		pageHomeLikes.clickButton(ButtonUseLikes.DonarMisLikes);
		PageHomeDonateLikesStpV.getNew(driver).checkIsPage(2);
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Conseguir por 1200 Likes\"",
		expected="Aparece una página para conseguir por 1200 Likes")
	public void clickButtonConseguirPor1200Likes() {
		pageHomeLikes.clickButton(ButtonUseLikes.Conseguir);
		PageHomeConseguirPor1200LikesStpV.getNew(driver).checkIsPage(2);
	}
	
	@Step(
		description="Seleccionar el 1er botón \"Regalar mis Likes\"",
		expected="Aparece la página para regalar mis Likes")
	public PageRegalarMisLikesStpV clickButtonRegalarMisLikes() {
		pageHomeLikes.clickButton(ButtonUseLikes.RegalarMisLikes);
		PageRegalarMisLikesStpV pageRegalarStpV = PageRegalarMisLikesStpV.getNew(driver);
		pageRegalarStpV.checkIsPage();
		return pageRegalarStpV;
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
