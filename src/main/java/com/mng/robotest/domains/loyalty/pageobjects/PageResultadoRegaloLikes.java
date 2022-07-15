package com.mng.robotest.domains.loyalty.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageResultadoRegaloLikes extends PageObjTM {
	
	private static final String XPATH_DONE_ICON = "//img[@class='done-icon']";
	
	public PageResultadoRegaloLikes(WebDriver driver) {
		super(driver);
	}
	
	public boolean isEnvioOk(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_DONE_ICON)).wait(maxSeconds).check());
	}
	
}
