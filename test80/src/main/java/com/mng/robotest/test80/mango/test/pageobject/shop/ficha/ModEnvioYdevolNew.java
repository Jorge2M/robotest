package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModEnvioYdevolNew extends PageObjTM {

	private static String XPathWrapper = "//div[@class='handling-modal-wrapper']";
	private static String XPathAspaForClose = XPathWrapper + "//span[@class[contains(.,'modal-close')]]";

	public ModEnvioYdevolNew(WebDriver driver) {
		super(driver);
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathWrapper)).wait(maxSeconds).check());
	}

	public void clickAspaForClose() {
		click(By.xpath(XPathAspaForClose)).exec();
	}
}
