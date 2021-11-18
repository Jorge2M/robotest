package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;


public class PageInputPedido extends PageObjTM implements PageFromFooter {
	
	private static final String XPathInputPedido = "//input[@id[contains(.,'pedidoId')]]";
	private static final String XPathInputEmailUsr = "//input[@id[contains(.,'mailPedido')]]";
	private static final String XPathLinkRecuperarDatos = "//*[@onclick[contains(.,'FPedidoMail')]]";
	
	public PageInputPedido(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public String getName() {
		return "Consulta de pedidos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathInputPedido)).wait(maxSeconds).check());
	}
	
	public boolean isVisibleInputPedido() {
		return (state(Visible, By.xpath(XPathInputPedido)).check());
	}
	
	public void inputPedido(String codPedido) {
		driver.findElement(By.xpath(XPathInputPedido)).clear();
		driver.findElement(By.xpath(XPathInputPedido)).sendKeys(codPedido);
	}
	
	public void inputEmailUsr(String emailUsr) {
		driver.findElement(By.xpath(XPathInputEmailUsr)).clear();
		driver.findElement(By.xpath(XPathInputEmailUsr)).sendKeys(emailUsr);
	}
	
	public void clickRecuperarDatos() {
		click(By.xpath(XPathLinkRecuperarDatos)).exec();
	}
}
