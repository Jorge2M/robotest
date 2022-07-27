package com.mng.robotest.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SectionBarraSupVOTF extends PageObjTM {

	public static final String TITLE_USERNAME = "USERNAME: "; 
	private static final String XPATH_BARRA = "//div[@class[contains(.,'barraTele')]]";
	
	public SectionBarraSupVOTF(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPresentUsuario(String usuarioVOTF) {
		String usuarioLit = TITLE_USERNAME + usuarioVOTF;
		return (
			state(Present, By.xpath(XPATH_BARRA)).check() &&
			driver.findElement(By.xpath(XPATH_BARRA)).getText().toLowerCase().contains(usuarioLit.toLowerCase()));
	}
	
}
