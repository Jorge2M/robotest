package com.mng.robotest.domains.transversal.acceso.pageobjects;

import com.mng.robotest.conf.testab.TestABactive;
import com.mng.robotest.domains.base.PageBase;

public class PageLoginVOTF extends PageBase {

	private static final String XPATH_INPUT_USUARIO = "//input[@class='username']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@class='pwd']";
	private static final String XPATH_BUTTON_CONTINUE = "//input[@class[contains(.,'button submit')]]";
	
	public void goToFromUrlAndSetTestABs() throws Exception {	
		waitLoadPage();
		activateTestsABs();
	}
	
	private void activateTestsABs() throws Exception {
		new TestABactive().currentTestABsToActivate();
		driver.navigate().refresh();
	}
	
	public void inputUsuario(String usuario) {
		getElement(XPATH_INPUT_USUARIO).sendKeys(usuario);
	}
	
	public void inputPassword(String password) {
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}

	public void clickButtonContinue() {
		click(XPATH_BUTTON_CONTINUE).exec();
	}
}
