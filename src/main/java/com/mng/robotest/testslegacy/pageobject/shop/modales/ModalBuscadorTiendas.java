package com.mng.robotest.testslegacy.pageobject.shop.modales;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalBuscadorTiendas extends PageBase implements PageFromFooter {

	private static final String XP_CONTAINER = "//micro-frontend[@id='storeLocator']";
	private static final String XP_TIENDAS = XP_CONTAINER + "//div[@data-testid='store-container']/ul";
	private static final String XP_CLOSE_DESKTOP = "//*[@data-testid='close-modal']";
	private static final String XP_CLOSE_TABLET = "//div[@class[contains(.,'close-modal')]]";
	private static final String XP_LEFT_ARROW_MOBILE = XP_CONTAINER + "//span[@role='button']";
	
	@Override
	public String getName() {
		return "Encuentra tu tienda";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return isVisible(seconds);
	}
	
	public boolean isVisible() {
		return isVisible(0);
	}
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_CONTAINER).wait(seconds).check();
	}
	public boolean isInvisible(int seconds) {
		return state(VISIBLE, XP_CONTAINER).wait(seconds).check();
	}	
	
	public boolean isPresentAnyTiendaUntil(int seconds) {
		return state(PRESENT, XP_TIENDAS).wait(seconds).check();
	}
	
	public void close() {
		clickAspaForClose();
	}
	
	private void clickAspaForClose() {
		if (isMobile()) {
			click(XP_LEFT_ARROW_MOBILE).exec();
			return;
		} 
		if (isTablet()) {
			click(XP_CLOSE_TABLET).type(JAVASCRIPT).exec();
		}
		
		click(XP_CLOSE_DESKTOP).exec();
	}
}
