package com.mng.robotest.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.utils.testab.TestABactive;


public class PageLoginVOTF extends PageBase {

	private static final String XPATH_INPUT_USUARIO = "//input[@class='username']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@class='pwd']";
	private static final String XPATH_BUTTON_CONTINUE = "//input[@class[contains(.,'button submit')]]";
	
	public PageLoginVOTF(WebDriver driver) {
		super(driver);
	}
	
	public void goToFromUrlAndSetTestABs(DataCtxShop dCtxSh) throws Exception {	
		waitForPageLoaded(driver);
		activateTestsABs(dCtxSh.channel, dCtxSh.appE);
	}
	
	private void activateTestsABs(Channel channel, AppEcom app) throws Exception {
		TestABactive.currentTestABsToActivate(channel, app, driver);
		driver.navigate().refresh();
	}
	
	public void inputUsuario(String usuario) {
		driver.findElement(By.xpath(XPATH_INPUT_USUARIO)).sendKeys(usuario);
	}
	
	public void inputPassword(String password) {
		driver.findElement(By.xpath(XPATH_INPUT_PASSWORD)).sendKeys(password);
	}

	public void clickButtonContinue() {
		click(By.xpath(XPATH_BUTTON_CONTINUE)).exec();
	}
}
