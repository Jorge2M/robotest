package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeLikes extends PageObjTM {

	String idLoyaltySpace = "loyaltySpace";
	//String xpathPoints = "//*[@class[contains(.,'user-total-likes')] or @class[contains(.,'enough-likes')]]";
	String xpathPoints = "//div[@class='vmPyL']"; //TODO pendiente Víctor gestione el cambio de la página
	//String xpathBlockExchange = "//ul[@class='cards-list']/li";
	String xpathBlockExchange = "//div[@class='Em-Eu']";
	
	public enum ButtonUseLikes {
		CompraConDescuento("//div[@role='button' and text()='Compra con descuento']"),
		DonarMisLikes("//div[@role='button' and contains(text(), 'Donar mis Likes')]"),
		Conseguir("//div[@role='button' and contains(text(), 'Conseguir')]"),
		RegalarMisLikes("//div[@role='button' and text()[contains(.,'Regalar')]]");
		
		private By by;
		private ButtonUseLikes(String xpath) {
			this.by = By.xpath(xpath);
		}
		public By getBy() {
			return by;
		}
	}
	
	private PageHomeLikes(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomeLikes getNew(WebDriver driver) {
		return (new PageHomeLikes(driver));
	}
	
	public boolean checkIsPageUntil(int maxSeconds) {
		return (state(Visible, By.id(idLoyaltySpace)).wait(maxSeconds).check());
	}
	
	public int getPoints() {
		if (state(Present, By.xpath(xpathPoints)).check()) {
			String textPoints = driver.findElement(By.xpath(xpathPoints)).getText();
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
	
	public boolean areVisibleBlocksExchangeLikes() {
		return (state(Visible, By.xpath(xpathBlockExchange)).wait(2).check());
	}

	public void clickButton(ButtonUseLikes button) {
		click(button.getBy()).exec();
	}
}