package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;


public class PageAccesoMisCompras extends WebdrvWrapp implements PageFromFooter {

    public enum TypeBlock {SiRegistrado, NoRegistrado}
    static String XPathContainerBlocks = "//div[@class[contains(.,'mypurchases')]]//div[@class='shopping-container']";
    static String XPathLinkSiRegistrado = "//div[@id='menuRegistered']";
    static String XPathLinkNoRegistrado = "//div[@id='menuNoRegistered']";
    static String XPathBlockSiRegistrado = "//div[@id='loginContainer']";
    static String XPathBlockNoRegistrado = "//div[@id='consultOrder']";
    static String XPathInputUserBlockSi = XPathBlockSiRegistrado + "//input[@id[contains(.,'userMail')]]";
    static String XpathInputPasswordBlockSi = XPathBlockSiRegistrado + "//input[@id[contains(.,'chkPwd')]]";
    static String XPathInputUserBlockNo = XPathBlockNoRegistrado + "//input[@id[contains(.,'emailValueConsultOrder')]]";
    static String XPathInputNumPedidoBlockNo = XPathBlockNoRegistrado + "//input[@id[contains(.,'orderValueConsultOrder')]]";
    static String XPathButtonEntrarBlockSi = XPathBlockSiRegistrado + "//div[@id='buttonLogin']/input";
    static String XPathButtonBuscarPedidoBlockNo = XPathBlockNoRegistrado + "//div[@id='buttonConsultOrder']";
    
    
    public static String getXPathBlock(TypeBlock typeBlock) {
        switch (typeBlock) {
        case SiRegistrado:
            return XPathBlockSiRegistrado;
        case NoRegistrado:
        default:
            return XPathBlockNoRegistrado;
        }
    }
    
    public static String getXPathLinkBlock(TypeBlock typeBlock) {
        switch (typeBlock) {
        case SiRegistrado:
            return XPathLinkSiRegistrado;
        case NoRegistrado:
        default:
            return XPathLinkNoRegistrado;
        }
    }    
    
    public static String getXPathIsPage() {
        return XPathContainerBlocks;
    }
    
	@Override
	public String getName() {
		return "Mis Compras";
	}
    
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(getXPathIsPage()), maxSecondsWait));
	}
	
    public static boolean isPage(WebDriver driver) {
       return (isElementPresent(driver, By.xpath(getXPathIsPage())));
    }
    
    public static boolean isPresentBlock(TypeBlock typeBlock, WebDriver driver) {
        String xpathBlock = getXPathBlock(typeBlock);
        return (isElementPresent(driver, By.xpath(xpathBlock)));
    }
    
    public static boolean isVisibleBlockUntil(TypeBlock typeBlock, int maxSecondsToWait, WebDriver driver) {
        String xpathBlock = getXPathBlock(typeBlock);
        return (isElementVisibleUntil(driver, By.xpath(xpathBlock), maxSecondsToWait));
    }    
    
    public static void clickBlock(TypeBlock typeBlock, WebDriver driver) {
        String xpathBlock = getXPathLinkBlock(typeBlock);
        driver.findElement(By.xpath(xpathBlock)).click();
    }
    
    public static void inputUserBlockSi(String usuario, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputUserBlockSi)).clear();
        driver.findElement(By.xpath(XPathInputUserBlockSi)).sendKeys(usuario);
    }
    
    public static void inputPasswordBlockSi(String password, WebDriver driver) {
        driver.findElement(By.xpath(XpathInputPasswordBlockSi)).clear();
        driver.findElement(By.xpath(XpathInputPasswordBlockSi)).sendKeys(password);
    }    
    
    public static void inputUserPasswordBlockSi(String usuario, String password, WebDriver driver) {
        inputUserBlockSi(usuario, driver);
        inputPasswordBlockSi(password, driver);
    }
    
    public static void clickEntrarBlockSi(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonEntrarBlockSi));
    }
    
    public static void inputUserBlockNo(String usuario, WebDriver driver) {
    	sendKeysWithRetry(2, usuario, By.xpath(XPathInputUserBlockNo), driver);
    }
    
    public static void inputNumPedidoBlockNo(String numPedido, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputNumPedidoBlockNo)).clear();
        driver.findElement(By.xpath(XPathInputNumPedidoBlockNo)).sendKeys(numPedido);
    }
    
    public static void inputUserAndNumPedidoBlockNo(String usuario, String numPedido, WebDriver driver) {
        inputUserBlockNo(usuario, driver);
        inputNumPedidoBlockNo(numPedido, driver);
    }
    
    public static void clickBuscarPedidoBlockNo(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonBuscarPedidoBlockNo));
    }
}
