package com.mng.robotest.domains.loyalty.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class PageHomeLikes extends PageBase {

	private static final String XPATH_WRAPP_PAGE = "//micro-frontend[@id='loyaltySpace']";
	private static final String XPATH_POINTS = XPATH_WRAPP_PAGE + "//div[@id='space-header']/div[3]"; 
	
	public enum ButtonUseLikes {
		COMPRA_CON_DESCUENTO("//button/span[text()='Comprar con descuento']"),
		DONAR_MIS_LIKES("//button/span[contains(text(), 'Donar Likes')]"),
		SABER_MAS("//button/span[contains(text(), 'Saber más')]"),
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
	
	public boolean checkIsPageUntil(int maxSeconds) {
		return state(Visible, XPATH_WRAPP_PAGE).wait(maxSeconds).check();
	}
	
	public int getPoints() {
		if (state(Present, XPATH_POINTS).wait(2).check()) {
			String textPoints = getElement(XPATH_POINTS).getText();
			Pattern pattern = Pattern.compile(" [0-9,.]+ ");
			Matcher matcher = pattern.matcher(textPoints);
			if (matcher.find()) {
				return Integer.valueOf(
					matcher.group(0).
					trim().
					replace(",", "").
					replace(".", ""));
			}
		}
		return 0;
	}

	public boolean isVisibleButton(ButtonUseLikes button, int maxSeconds) {
		return state(Visible, button.xpath()).wait(maxSeconds).check();
	}

	public void clickButton(ButtonUseLikes button) {
		click(button.xpath()).exec();
	}
}