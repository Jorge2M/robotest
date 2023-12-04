package com.mng.robotest.tests.domains.galeria.pageobjects.sections;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class SecSubMenusGallery extends PageBase {

	abstract String getXPathCapa();
	
	public static SecSubMenusGallery make(AppEcom app, Pais pais) {
		if (pais.isGaleriaKondo(app)) {
			return new SecSubMenusGalleryKondo();
		}
		return new SecSubMenusGalleryNormal();
	}
	
	private String getXPathMenu(String nameMenu) {
		String nameMenuFirstCapital = nameMenu.substring(0, 1).toUpperCase() + nameMenu.substring(1);
		return 
			getXPathCapa() + 
			"//a[text()='" + nameMenu + "' or text()='" + nameMenuFirstCapital + "']";
	}
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, getXPathCapa()).wait(seconds).check();
	}
	
	public boolean isVisibleSubMenu(String nameMenu) {
		return state(VISIBLE, getXPathMenu(nameMenu)).wait(1).check();
	}
	
	public void clickSubMenu(String nameMenu) {
		click(getXPathMenu(nameMenu)).exec();
	}
}
