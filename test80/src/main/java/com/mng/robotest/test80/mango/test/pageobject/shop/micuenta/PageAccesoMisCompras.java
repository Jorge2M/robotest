package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;


public class PageAccesoMisCompras extends PageObjTM implements PageFromFooter {

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
    
    public PageAccesoMisCompras(WebDriver driver) {
    	super(driver);
    }
    
	@Override
	public String getName() {
		return "Mis Compras";
	}
    
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(getXPathIsPage())).wait(maxSeconds).check());
	}
	
    public boolean isPage() {
    	return (state(Present, By.xpath(getXPathIsPage())).check());
    }
    
    public boolean isPresentBlock(TypeBlock typeBlock) {
        String xpathBlock = getXPathBlock(typeBlock);
        return (state(Present, By.xpath(xpathBlock)).check());
    }
    
    public boolean isVisibleBlockUntil(TypeBlock typeBlock, int maxSeconds) {
        String xpathBlock = getXPathBlock(typeBlock);
        return (state(Visible, By.xpath(xpathBlock)).wait(maxSeconds).check());
    }
    
    public void clickBlock(TypeBlock typeBlock) {
        String xpathBlock = getXPathLinkBlock(typeBlock);
        driver.findElement(By.xpath(xpathBlock)).click();
    }
    
    public void inputUserBlockSi(String usuario) {
        driver.findElement(By.xpath(XPathInputUserBlockSi)).clear();
        driver.findElement(By.xpath(XPathInputUserBlockSi)).sendKeys(usuario);
    }
    
    public void inputPasswordBlockSi(String password) {
        driver.findElement(By.xpath(XpathInputPasswordBlockSi)).clear();
        driver.findElement(By.xpath(XpathInputPasswordBlockSi)).sendKeys(password);
    }    
    
    public void inputUserPasswordBlockSi(String usuario, String password) {
        inputUserBlockSi(usuario);
        inputPasswordBlockSi(password);
    }
    
    public void clickEntrarBlockSi() {
    	click(By.xpath(XPathButtonEntrarBlockSi)).exec();
    }
    
    public void inputUserBlockNo(String usuario) {
    	sendKeysWithRetry(usuario, By.xpath(XPathInputUserBlockNo), 2, driver);
    }
    
    public void inputNumPedidoBlockNo(String numPedido) {
        driver.findElement(By.xpath(XPathInputNumPedidoBlockNo)).clear();
        driver.findElement(By.xpath(XPathInputNumPedidoBlockNo)).sendKeys(numPedido);
    }
    
    public void inputUserAndNumPedidoBlockNo(String usuario, String numPedido) {
        inputUserBlockNo(usuario);
        inputNumPedidoBlockNo(numPedido);
    }
    
    public void clickBuscarPedidoBlockNo() {
    	click(By.xpath(XPathButtonBuscarPedidoBlockNo)).exec();
    }
}
