package com.mng.robotest.tests.domains.ficha.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.INVISIBLE;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecStickyContentGenesis extends PageBase {

	private static final String XP_CAPA_STICKY_CONTENT = "//div[@id='sticky-content']";
	
	public boolean isVisibleStickyContent(int seconds) {
		return state(VISIBLE, XP_CAPA_STICKY_CONTENT).wait(seconds).check();
	}
	
	public boolean isInvisibleStickyContent(int seconds) {
		return state(INVISIBLE, XP_CAPA_STICKY_CONTENT).wait(seconds).check();
	}	
	
	public boolean isVisibleReferenciaStickyContent(String referencia) {
		String xpath = XP_CAPA_STICKY_CONTENT + "//img[@src[contains(.,'/" + referencia + "_')]]";
		return state(VISIBLE, xpath).check();  
	}

	public boolean isVisibleTallaLabelStickyContent(String tallaLabel) {
		String xpath = XP_CAPA_STICKY_CONTENT + "//p[text()='" + tallaLabel + "']";
		return state(VISIBLE, xpath).check();
	}

	public boolean isVisibleColorCodeStickyContent(String colorCode) {
		String xpath = XP_CAPA_STICKY_CONTENT + "//img[@src[contains(.,'_" + colorCode + "_')]]";
		return state(VISIBLE, xpath).check();
	}
	
}
