package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageYandexMoneyResult {
	
	public static String msgTransferOk = "Обработка завершена. Запрос выполнен успешно. Зачисление перевода проведено успешно";
	static String XPathDivResultMsg = "//div[@class='docbook-para']";
	
	public static boolean isPage(WebDriver driver) {
		return (state(Visible, By.xpath(XPathDivResultMsg), driver).check());
	}
		
	public static boolean isVisibleMsgTransferOk(WebDriver driver) {
		WebElement divResult = driver.findElement(By.xpath(XPathDivResultMsg));
		return (state(Visible, By.xpath(XPathDivResultMsg), driver).check() &&
				divResult.getText().contains(msgTransferOk));
	}
}
