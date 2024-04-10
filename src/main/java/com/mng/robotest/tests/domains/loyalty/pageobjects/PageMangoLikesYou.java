package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.utils.UtilsLoyaltyPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMangoLikesYou extends PageBase {

	private static final String XP_WRAPP_PAGE = "//micro-frontend[@id='loyaltySpace']";
	private static final String XP_POINTS = XP_WRAPP_PAGE + "//div[@id='space-header']/div[3]";
	
	//TODO solicitar data-testid en lugar de React
	private static final String XP_LINK_AYUDA = "//div[text()='Ayuda']";
	
	//TODO solicitar data-testid en lugar de React (Kiritaki)
	public enum TabLink {
		VENTAJAS("//button[text()='Ventajas']"),
		CONSEGUIR_LIKES("//button[text()='Conseguir Likes']"),
		HISTORIAL("//button[text()='Historial']");
		
		private String xpath;
		private TabLink(String xpath) {
			this.xpath = xpath;
		}
		public String xpath() {
			return xpath;
		}
	}
	
	public enum ButtonUseLikes {
		COMPRA_CON_DESCUENTO("//button/span[text()='Comprar con descuento']"),
		DONAR_MIS_LIKES("//button/span[contains(text(), 'Donar Likes')]"),
		ENTRADA_CINE("//img[@src[contains(.,'card_cine')]]/..//button/span"),
		LIKES_1200("//button/span[contains(text(), '1200 Likes')]"),
		REGALAR_MIS_LIKES("//button/span[text()[contains(.,'Regalar')]]");
		
		private String xpath;
		private ButtonUseLikes(String xpath) {
			this.xpath = xpath;
		}
		public String xpath() {
			return xpath;
		}
	}
	
	public void click(TabLink tabLink) {
		click(tabLink.xpath()).exec();
	}
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_WRAPP_PAGE).wait(seconds).check();
	}
	
	public int getPoints() {
		if (state(PRESENT, XP_POINTS).wait(2).check()) {
			String textPoints = getElement(XP_POINTS).getText();
			return UtilsLoyaltyPage.getPointsFromLiteral(textPoints);
		}
		return 0;
	}

	public boolean isVisibleButton(ButtonUseLikes button, int seconds) {
		return state(VISIBLE, button.xpath()).wait(seconds).check();
	}

	public void clickButton(ButtonUseLikes button) {
		click(button.xpath()).exec();
	}
	
	public void clickAyuda() {
		click(XP_LINK_AYUDA).exec();
	}
	
	public boolean isPageAyudaMangoLikesYouVisible(int seconds) {
		return state(VISIBLE, "//h1[text()='Club Mango Likes you']").wait(seconds).check();
	}
	
}