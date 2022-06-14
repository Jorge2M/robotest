package com.mng.robotest.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAccesoMisCompras extends PageObjTM implements PageFromFooter {

	public enum TypeBlock {SiRegistrado, NoRegistrado}
	
	private static final String XPathContainerBlocks = "//*[@id='myPurchasesDesktop']"; //
	private static final String XPathLinkSiRegistrado = "//button[@data-testid='login']"; //
	private static final String XPathLinkNoRegistrado = "//button[@data-testid='orderForm']"; //
	private static final String XPathInputUserBlockSi = "//input[@data-testid='mngLogin.LoginForm.emil']";
	private static final String XpathInputPasswordBlockSi = "//input[@data-testid='mngLogin.LoginForm.password']";
	private static final String XPathButtonEntrarBlockSi = "//button[@data-testid='mngLogin.LoginForm.button']";
	
	private static final String XPathInputUserBlockNo = "//input[@data-testid[contains(.,'login.guest.email.input')]]";
	private static final String XPathInputNumPedidoBlockNo = "//input[@data-testid[contains(.,'login.guest.orderId.input')]]";
	private static final String XPathButtonBuscarPedidoBlockNo = "//button[@data-testid[contains(.,'login.guest.goToDetails')]]";
	
	
	public PageAccesoMisCompras(WebDriver driver) {
		super(driver);
	}
	
	public String getXPathLinkBlock(TypeBlock typeBlock) {
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
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(getXPathIsPage())).wait(maxSeconds).check());
	}
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int maxSeconds) {
		return (state(Present, By.xpath(getXPathIsPage())).wait(maxSeconds).check());
	}
	
	public boolean isPresentBlock(TypeBlock typeBlock) {
		return isPresentBlock(typeBlock, 0);
	}
	public boolean isPresentBlock(TypeBlock typeBlock, int maxSeconds) {
		String xpathBlock = getXPathLinkBlock(typeBlock);
		return (state(Present, By.xpath(xpathBlock)).wait(maxSeconds).check());
	}
	
	public boolean isVisibleBlockUntil(TypeBlock typeBlock, int maxSeconds) {
		String xpathBlock = getXPathLinkBlock(typeBlock);
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
		if (!state(Invisible, By.xpath(XPathButtonBuscarPedidoBlockNo)).wait(2).check()) {
			click(By.xpath(XPathButtonBuscarPedidoBlockNo)).exec();
		}
	}
}
