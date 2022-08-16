package com.mng.robotest.domains.ficha.pageobjects;



import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModEnvioYdevolNew extends PageBase {

	private static final String XPATH_WRAPPER = "//div[@class='handling-modal-wrapper']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_WRAPPER + "//span[@class[contains(.,'modal-close')]]";
	
	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, XPATH_WRAPPER).wait(maxSeconds).check());
	}

	public void clickAspaForClose() {
		click(XPATH_ASPA_FOR_CLOSE).exec();
	}
}
