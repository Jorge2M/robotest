package com.mng.robotest.domains.loyalty.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;


public class PageHomeLikes extends PageObjTM {

	private static final String XPATH_WRAPP_PAGE = "//micro-frontend[@id='loyaltySpace']";
	private static final String XPATH_POINTS = XPATH_WRAPP_PAGE + "//div[@id='space-header']/div[3]"; 
	
	public enum ButtonUseLikes {
		COMPRA_CON_DESCUENTO("//button/span[text()='Comprar con descuento']"),
		DONAR_MIS_LIKES("//button/span[contains(text(), 'Donar Likes')]"),
		SABER_MAS("//button/span[contains(text(), 'Saber más')]"),
		LIKES_1200("//button/span[contains(text(), '1200 Likes')]"),
		REGALAR_MIS_LIKES("//button/span[text()[contains(.,'Regalar')]]");
		
		private By by;
		private ButtonUseLikes(String xpath) {
			this.by = By.xpath(xpath);
		}
		public By getBy() {
			return by;
		}
	}
	
	public boolean checkIsPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_WRAPP_PAGE)).wait(maxSeconds).check());
	}
	
	public int getPoints() {
		if (state(Present, By.xpath(XPATH_POINTS)).wait(2).check()) {
			String textPoints = driver.findElement(By.xpath(XPATH_POINTS)).getText();
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
		return (state(Visible, button.getBy()).wait(maxSeconds).check());
	}

	public void clickButton(ButtonUseLikes button) {
		click(button.getBy()).exec();
	}
}