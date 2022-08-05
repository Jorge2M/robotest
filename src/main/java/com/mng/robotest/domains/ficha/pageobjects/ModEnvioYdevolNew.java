package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModEnvioYdevolNew extends PageObjTM {

	private static final String XPATH_WRAPPER = "//div[@class='handling-modal-wrapper']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_WRAPPER + "//span[@class[contains(.,'modal-close')]]";
	
	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_WRAPPER)).wait(maxSeconds).check());
	}

	public void clickAspaForClose() {
		click(By.xpath(XPATH_ASPA_FOR_CLOSE)).exec();
	}
}
