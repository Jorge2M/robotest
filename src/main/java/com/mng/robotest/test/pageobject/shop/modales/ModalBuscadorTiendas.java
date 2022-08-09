package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalBuscadorTiendas extends PageBase implements PageFromFooter {

	private static final String XPATH_CONTAINER = "//micro-frontend[@id='storeLocator']";
	private static final String XPATH_TIENDAS = XPATH_CONTAINER + "//div[@class[contains(.,'store-list')]]";
	private static final String XPATH_CLOSE_DESKTOP_NOOUTLET = "//*[@class[contains(.,'icon')] and @class[contains(.,'close-modal')]]";
	private static final String XPATH_CLOSE_DESKTOP_OUTLET = "//button[@class[contains(.,'close-modal')]]";
	private static final String XPATH_CLOSE_TABLET = "//div[@class[contains(.,'close-modal')]]";
	private static final String XPATH_LEFT_ARROW_MOBILE = XPATH_CONTAINER + "//span[@role='button']";
	
	@Override
	public String getName() {
		return "Encuentra tu tienda";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return isVisible(maxSeconds);
	}
	
	public boolean isVisible() {
		return isVisible(0);
	}
	
	public boolean isVisible(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_CONTAINER)).wait(maxSeconds).check());
	}
	
	public boolean isPresentAnyTiendaUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_TIENDAS)).wait(maxSeconds).check());
	}
	
	public void close() {
		clickAspaForClose();
	}
	
	private void clickAspaForClose() {
		if (channel==Channel.mobile) {
			click(By.xpath(XPATH_LEFT_ARROW_MOBILE)).exec();
			return;
		} 
		if (channel==Channel.tablet) {
			click(By.xpath(XPATH_CLOSE_TABLET)).exec();
			return;			
		}
		
		if (app==AppEcom.outlet) {
			click(By.xpath(XPATH_CLOSE_DESKTOP_OUTLET)).exec();
		} else {
			click(By.xpath(XPATH_CLOSE_DESKTOP_NOOUTLET)).exec();
		}
	}
}
