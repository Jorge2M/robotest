package com.mng.robotest.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalBuscadorTiendas extends PageObjTM implements PageFromFooter {

	private final Channel channel;
	
	private static final String XPATH_CONTAINER = "//micro-frontend[@id='storeLocator']";
	private static final String XPATH_TIENDAS = XPATH_CONTAINER + "//div[@class[contains(.,'store-list')]]";
	private static final String XPATH_ASPA_FOR_CLOSE_DESKTOP = "//*[@class[contains(.,'icon')] and @class[contains(.,'close-modal')]]";
	private static final String XPATH_LEFT_ARROW_DEVICE = XPATH_CONTAINER + "//span[@role='button']";
	
	public ModalBuscadorTiendas(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
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
	
	public void clickAspaForClose() {
		if (channel.isDevice()) {
			click(By.xpath(XPATH_LEFT_ARROW_DEVICE)).exec();
		} else {
			click(By.xpath(XPATH_ASPA_FOR_CLOSE_DESKTOP)).exec();
		}
	}
}
