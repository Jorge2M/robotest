package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB;

public class PageLoginVOTF extends WebdrvWrapp {

    private static final String XPathInputUsuario = "//input[@class='username']";
    private static final String XPathInputPassword = "//input[@class='pwd']";
    private static final String XPathButtonContinue = "//input[@class[contains(.,'button submit')]]";
    
    public static void goToFromUrlAndSetTestABs(String urlLogin, DataCtxShop dCtxSh, WebDriver driver) throws Exception {    
    	AccesoNavigations.goToInitURL(urlLogin, driver);
        waitForPageLoaded(driver);
        activateTestsABs(dCtxSh, driver);
    }
    
    private static void activateTestsABs(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	TestAB.currentTestABsToActivate(dCtxSh, driver);
    	driver.navigate().refresh();
    }
    
    public static void inputUsuario(String usuario, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputUsuario)).sendKeys(usuario);
    }
    
    public static void inputPassword(String password, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
    }

    public static void clickButtonContinue(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonContinue));
    }
}
