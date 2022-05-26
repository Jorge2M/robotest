package com.mng.robotest.test.stpv.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalNewsletterStpV {

	private final static String XPathAspaClose = "//button[@data-testid='newsletterSubscriptionModal.nonModal.close']";

	public static void closeIfVisible(WebDriver driver) {
		if (isVisible(driver)) {
			close(driver);
		}
	}
	
	private static boolean isVisible(WebDriver driver) {
		return PageObjTM.state(Visible, By.xpath(XPathAspaClose), driver).check();
	}
	
	@Step (
		description="Cerramos el modal de la Newsletter", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public static void close(WebDriver driver) {
		PageObjTM.click(By.xpath(XPathAspaClose), driver).exec();
	}
}
