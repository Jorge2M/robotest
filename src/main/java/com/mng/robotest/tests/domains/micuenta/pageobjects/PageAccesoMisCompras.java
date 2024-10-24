package com.mng.robotest.tests.domains.micuenta.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAccesoMisCompras extends PageBase implements PageFromFooter {

	public enum TypeBlock { SI_REGISTRADO, NO_REGISTRADO }
	
	private static final String XP_CONTAINER_BLOCKS = "//*[@id='myPurchasesDesktop']"; //
	private static final String XP_LINK_SI_REGISTRADO = "//button[@data-testid='login']"; //
	private static final String XP_LINK_NO_REGISTRADO = "//button[@data-testid='orderForm']"; //
	private static final String XP_INPUT_USER_BLOCK_SI = "//input[@data-testid='mngLogin.LoginForm.emil']";
	private static final String XP_INPUT_PASSWORD_BLOCK_SI = "//input[@data-testid='mngLogin.LoginForm.password']";
	private static final String XP_BUTTON_ENTRAR_BLOCK_SI = "//button[@data-testid='mngLogin.LoginForm.button']";
	
	private static final String XP_INPUT_USER_BLOCK_NO = "//input[@data-testid[contains(.,'login.guest.email.input')]]";
	private static final String XP_INPUT_NUM_PEDIDO_BLOCK_NO = "//input[@data-testid[contains(.,'login.guest.orderId.input')]]";
	private static final String XP_BUTTON_BUSCAR_PEDIDO_BLOCK_NO = "//button[@data-testid[contains(.,'login.guest.goToDetails')]]";
	
	private String getXPathLinkBlock(TypeBlock typeBlock) {
		switch (typeBlock) {
		case SI_REGISTRADO:
			return XP_LINK_SI_REGISTRADO;
		case NO_REGISTRADO:
		default:
			return XP_LINK_NO_REGISTRADO;
		}
	}	
	
	private String getXPathIsPage() {
		return XP_CONTAINER_BLOCKS;
	}
	
	@Override
	public String getName() {
		return "Mis Compras";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, getXPathIsPage()).wait(seconds).check();
	}
	
	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(PRESENT, getXPathIsPage()).wait(seconds).check();
	}
	
	public boolean isPresentBlock(TypeBlock typeBlock) {
		return isPresentBlock(typeBlock, 0);
	}
	public boolean isPresentBlock(TypeBlock typeBlock, int seconds) {
		String xpathBlock = getXPathLinkBlock(typeBlock);
		return state(PRESENT, xpathBlock).wait(seconds).check();
	}
	
	public boolean isVisibleBlockUntil(TypeBlock typeBlock, int seconds) {
		String xpathBlock = getXPathLinkBlock(typeBlock);
		return state(VISIBLE, xpathBlock).wait(seconds).check();
	}
	
	public void clickBlock(TypeBlock typeBlock) {
		getElement(getXPathLinkBlock(typeBlock)).click();
	}
	
	public void inputUserBlockSi(String usuario) {
		getElement(XP_INPUT_USER_BLOCK_SI).clear();
		getElement(XP_INPUT_USER_BLOCK_SI).sendKeys(usuario);
	}
	
	public void inputPasswordBlockSi(String password) {
		getElement(XP_INPUT_PASSWORD_BLOCK_SI).clear();
		getElement(XP_INPUT_PASSWORD_BLOCK_SI).sendKeys(password);
	}	
	
	public void inputUserPasswordBlockSi(String usuario, String password) {
		inputUserBlockSi(usuario);
		inputPasswordBlockSi(password);
	}
	
	public void clickEntrarBlockSi() {
		click(XP_BUTTON_ENTRAR_BLOCK_SI).exec();
	}
	
	public void inputUserBlockNo(String usuario) {
		sendKeysWithRetry(usuario, By.xpath(XP_INPUT_USER_BLOCK_NO), 2, driver);
	}
	
	public void inputNumPedidoBlockNo(String numPedido) {
		getElement(XP_INPUT_NUM_PEDIDO_BLOCK_NO).clear();
		getElement(XP_INPUT_NUM_PEDIDO_BLOCK_NO).sendKeys(numPedido);
	}
	
	public void inputUserAndNumPedidoBlockNo(String usuario, String numPedido) {
		inputUserBlockNo(usuario);
		inputNumPedidoBlockNo(numPedido);
	}
	
	public void clickBuscarPedidoBlockNo() {
		click(XP_BUTTON_BUSCAR_PEDIDO_BLOCK_NO).exec();
		if (!state(INVISIBLE, XP_BUTTON_BUSCAR_PEDIDO_BLOCK_NO).wait(2).check()) {
			click(XP_BUTTON_BUSCAR_PEDIDO_BLOCK_NO).exec();
		}
	}
}
