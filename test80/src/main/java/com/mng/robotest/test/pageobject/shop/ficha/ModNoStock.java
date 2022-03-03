package com.mng.robotest.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModNoStock {

	static String XPathModalNoStock = "//div[@class='modalNoStock show']";
	
	public static boolean isModalNoStockVisibleFichaNew(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathModalNoStock), driver)
				.wait(maxSeconds).check());
	}
	
}
