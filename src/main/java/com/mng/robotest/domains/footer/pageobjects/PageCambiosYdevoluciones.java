package com.mng.robotest.domains.footer.pageobjects;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageCambiosYdevoluciones extends PageBase implements PageFromFooter {
	
	private static final String XPATH_FOR_ID_PAGE = "//*[text()[contains(.,'Devoluciones, cambios y reembolsos')]]";
	
	@Override
	public String getName() {
		return "Cambios y devoluciones";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return state(Present, By.xpath(XPATH_FOR_ID_PAGE)).wait(maxSeconds).check();
	}
}
