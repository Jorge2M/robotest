package com.mng.robotest.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAlertaVOTF extends PageBase {
	
	private static final String XPATH_CAPA_ALERTA = "//div[@class='alert']";
	private static final String XPATH_BUTTON_CONTINUAR = "//div[@class='alert']//span[@class='button']";

	public PageAlertaVOTF(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_CAPA_ALERTA)).check());
	}
	 
	public void clickButtonContinuar() {
		click(By.xpath(XPATH_BUTTON_CONTINUAR)).exec();
	}
}
