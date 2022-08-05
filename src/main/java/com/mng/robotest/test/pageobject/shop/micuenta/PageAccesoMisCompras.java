package com.mng.robotest.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAccesoMisCompras extends PageObjTM implements PageFromFooter {

	public enum TypeBlock { SI_REGISTRADO, NO_REGISTRADO }
	
	private static final String XPATH_CONTAINER_BLOCKS = "//*[@id='myPurchasesDesktop']"; //
	private static final String XPATH_LINK_SI_REGISTRADO = "//button[@data-testid='login']"; //
	private static final String XPATH_LINK_NO_REGISTRADO = "//button[@data-testid='orderForm']"; //
	private static final String XPATH_INPUT_USER_BLOCK_SI = "//input[@data-testid='mngLogin.LoginForm.emil']";
	private static final String XPATH_INPUT_PASSWORD_BLOCK_SI = "//input[@data-testid='mngLogin.LoginForm.password']";
	private static final String XPATH_BUTTON_ENTRAR_BLOCK_SI = "//button[@data-testid='mngLogin.LoginForm.button']";
	
	private static final String XPATH_INPUT_USER_BLOCK_NO = "//input[@data-testid[contains(.,'login.guest.email.input')]]";
	private static final String XPPATH_INPUT_NUM_PEDIDO_BLOCK_NO = "//input[@data-testid[contains(.,'login.guest.orderId.input')]]";
	private static final String XPATH_BUTTON_BUSCAR_PEDIDO_BLOCK_NO = "//button[@data-testid[contains(.,'login.guest.goToDetails')]]";
	
	private String getXPathLinkBlock(TypeBlock typeBlock) {
		switch (typeBlock) {
		case SI_REGISTRADO:
			return XPATH_LINK_SI_REGISTRADO;
		case NO_REGISTRADO:
		default:
			return XPATH_LINK_NO_REGISTRADO;
		}
	}	
	
	private String getXPathIsPage() {
		return XPATH_CONTAINER_BLOCKS;
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
		driver.findElement(By.xpath(XPATH_INPUT_USER_BLOCK_SI)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_USER_BLOCK_SI)).sendKeys(usuario);
	}
	
	public void inputPasswordBlockSi(String password) {
		driver.findElement(By.xpath(XPATH_INPUT_PASSWORD_BLOCK_SI)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_PASSWORD_BLOCK_SI)).sendKeys(password);
	}	
	
	public void inputUserPasswordBlockSi(String usuario, String password) {
		inputUserBlockSi(usuario);
		inputPasswordBlockSi(password);
	}
	
	public void clickEntrarBlockSi() {
		click(By.xpath(XPATH_BUTTON_ENTRAR_BLOCK_SI)).exec();
	}
	
	public void inputUserBlockNo(String usuario) {
		sendKeysWithRetry(usuario, By.xpath(XPATH_INPUT_USER_BLOCK_NO), 2, driver);
	}
	
	public void inputNumPedidoBlockNo(String numPedido) {
		driver.findElement(By.xpath(XPPATH_INPUT_NUM_PEDIDO_BLOCK_NO)).clear();
		driver.findElement(By.xpath(XPPATH_INPUT_NUM_PEDIDO_BLOCK_NO)).sendKeys(numPedido);
	}
	
	public void inputUserAndNumPedidoBlockNo(String usuario, String numPedido) {
		inputUserBlockNo(usuario);
		inputNumPedidoBlockNo(numPedido);
	}
	
	public void clickBuscarPedidoBlockNo() {
		click(By.xpath(XPATH_BUTTON_BUSCAR_PEDIDO_BLOCK_NO)).exec();
		if (!state(Invisible, By.xpath(XPATH_BUTTON_BUSCAR_PEDIDO_BLOCK_NO)).wait(2).check()) {
			click(By.xpath(XPATH_BUTTON_BUSCAR_PEDIDO_BLOCK_NO)).exec();
		}
	}
}
