package com.mng.robotest.tests.domains.loyalty.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.PRESENT;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.utils.UtilsLoyaltyPage;

public class PageMangoLikesYouOld extends PageBase implements PageMangoLikesYou {

	private static final String XP_WRAPP_PAGE = "//micro-frontend[@id='loyaltySpace']";
	private static final String XP_POINTS = XP_WRAPP_PAGE + "//div[@id='space-header']/div[3]";
	
	//TODO solicitar data-testid en lugar de React
	private static final String XP_LINK_AYUDA = "//div[text()='Ayuda' or text()='Help']";
	
	//TODO solicitar data-testid en lugar de React (Kiritaki)
	public enum TabLink {
		VENTAJAS("//button[text()='Ventajas' or text()='Benefits']"),
		CONSEGUIR_LIKES("//button[text()='Conseguir Likes' or text()='Get Likes']"),
		HISTORIAL("//button[text()='Historial' or text()='History']");
		
		private String xpath;
		private TabLink(String xpath) {
			this.xpath = xpath;
		}
		public String xpath() {
			return xpath;
		}
	}
	
	private enum ButtonUseLikes {
		COMPRA_CON_DESCUENTO("//button/span[text()='Comprar con descuento' or text()='Shop with a discount']"),
		DONAR_MIS_LIKES("//button/span[text()[contains(.,'Donar Likes')] or text()[contains(.,'Donate Likes')]]"),
		ENTRADA_CINE("//img[@src[contains(.,'card_cine')]]/..//button/span"),
		LIKES_1200("//button/span[text()[contains(.,'1200 Likes')] or text()[contains(.,'1,500 Likes')]]");
		
		private String xpath;
		private ButtonUseLikes(String xpath) {
			this.xpath = xpath;
		}
		public String xpath() {
			return xpath;
		}
	}

	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_WRAPP_PAGE).wait(seconds).check();
	}
	
	@Override
	public int getPoints() {
		if (state(PRESENT, XP_POINTS).wait(2).check()) {
			String textPoints = getElement(XP_POINTS).getText();
			return UtilsLoyaltyPage.getPointsFromLiteral(textPoints);
		}
		return 0;
	}
	
	@Override
	public void clickHistorial() {
		click(TabLink.HISTORIAL);
	}
	
	@Override
	public void closeHistorial() {
	}

	@Override
	public boolean isVisibleCompraConDescuentoExperience(int seconds) {
		return isVisibleButton(ButtonUseLikes.COMPRA_CON_DESCUENTO, seconds);
	}
	
	@Override
	public void clickCompraConDescuentoExperience() {
		
	}
	
	@Override
	public void clickDonation() {
		clickButton(ButtonUseLikes.DONAR_MIS_LIKES);
	}
	
	@Override
	public void clickExchangeLikesForExperience() {
		if (isVisibleButton(ButtonUseLikes.LIKES_1200, 0)) {
			clickButton(ButtonUseLikes.LIKES_1200);
		} else {
			clickButton(ButtonUseLikes.ENTRADA_CINE);
		}
	}
	
	@Override
	public void clickExchangeLikesForExperience(int posExperience) {
		clickExchangeLikesForExperience();
	}
	
	@Override
	public void clickAyuda() {
		click(XP_LINK_AYUDA).exec();
	}
	
	@Override
	public boolean isPageAyudaMangoLikesYouVisible(int seconds) {
		return state(VISIBLE, "//h1[text()='Club Mango Likes you']").wait(seconds).check();
	}	

	private void click(TabLink tabLink) {
		click(tabLink.xpath()).exec();
	}

	private boolean isVisibleButton(ButtonUseLikes button, int seconds) {
		return state(VISIBLE, button.xpath()).wait(seconds).check();
	}

	private void clickButton(ButtonUseLikes button) {
		click(button.xpath()).exec();
	}

}
