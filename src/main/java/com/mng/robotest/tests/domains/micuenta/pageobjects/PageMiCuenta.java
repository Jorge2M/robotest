package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.INVISIBLE;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;
import static com.mng.robotest.tests.domains.micuenta.pageobjects.LinkMiCuenta.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.utils.UtilsLoyaltyPage;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class PageMiCuenta extends PageBase {

	abstract String getXPath(LinkMiCuenta link);
	abstract String getXPathNumberPoints();
	
	public static PageMiCuenta make(Pais pais, AppEcom app) {
		if (pais.isMicuentanew(app)) {
			return new PageMiCuentaOld();
		}
		return new PageMiCuentaNew();
	}
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, getXPath(MIS_DATOS)).wait(seconds).check();
	}
	
	public void click(LinkMiCuenta link) {
		var xpLink = getXPath(link);
		click(xpLink).exec();
		if (!state(INVISIBLE, xpLink).wait(1).check()) {
			click(xpLink).exec();
		}
	}
	
	public int getNumberPoints() {
		if (state(VISIBLE, getXPathNumberPoints()).wait(2).check()) {
			String textPoints = getElement(getXPathNumberPoints()).getText();
			return UtilsLoyaltyPage.getPointsFromLiteral(textPoints);
		}
		return 0;
	}	
	
}
