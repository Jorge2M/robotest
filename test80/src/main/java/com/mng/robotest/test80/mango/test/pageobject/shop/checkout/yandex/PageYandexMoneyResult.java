package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageYandexMoneyResult extends WebdrvWrapp {
    
    public static String msgTransferOk = "Обработка завершена. Запрос выполнен успешно. Зачисление перевода проведено успешно";
    static String XPathDivResultMsg = "//div[@class='docbook-para']";
    
    public static boolean isPage(WebDriver webdriver) {
        return (isElementVisible(webdriver, By.xpath(XPathDivResultMsg)));
    }
        
    public static boolean isVisibleMsgTransferOk(WebDriver driver) {
        WebElement divResult = driver.findElement(By.xpath(XPathDivResultMsg));
        return (isElementVisible(driver, By.xpath(XPathDivResultMsg)) &&
                divResult.getText().contains(msgTransferOk));
    }
}
