package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.utils.testab.TestABactive;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;

public class PageLoginVOTF {

    private static final String XPathInputUsuario = "//input[@class='username']";
    private static final String XPathInputPassword = "//input[@class='pwd']";
    private static final String XPathButtonContinue = "//input[@class[contains(.,'button submit')]]";
    
    public static void goToFromUrlAndSetTestABs(/*String urlLogin,*/ DataCtxShop dCtxSh, WebDriver driver) throws Exception {    
    	//AccesoNavigations.goToInitURL(/*urlLogin,*/ driver);
        waitForPageLoaded(driver);
        activateTestsABs(dCtxSh.channel, dCtxSh.appE, driver);
    }
    
    private static void activateTestsABs(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	TestABactive.currentTestABsToActivate(channel, app, driver);
    	driver.navigate().refresh();
    }
    
    public static void inputUsuario(String usuario, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputUsuario)).sendKeys(usuario);
    }
    
    public static void inputPassword(String password, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
    }

    public static void clickButtonContinue(WebDriver driver) {
    	click(By.xpath(XPathButtonContinue), driver).exec();
    }
}
