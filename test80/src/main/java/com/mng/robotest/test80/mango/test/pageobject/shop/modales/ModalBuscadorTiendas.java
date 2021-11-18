package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalBuscadorTiendas extends PageObjTM implements PageFromFooter {

	private final Channel channel;
	
	private final static String XPathContainer = "//div[@class[contains(.,'garment-finder-container')]]";
	private final static String XPathTiendas = XPathContainer + "//div[@class[contains(.,'list-store')]]";
	private final static String XPathAspaForCloseDesktop = "//button[@class[contains(.,'close-modal')]]";
	private final static String XPahLeftArrowDevice = "//span[@class[contains(.,'garment-finder-close')]]";
	
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
		return (state(Visible, By.xpath(XPathContainer)).check());
	}
	
	public boolean isPresentAnyTiendaUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathTiendas)).wait(maxSeconds).check());
	}
	
	public void clickAspaForClose() {
		if (channel.isDevice()) {
			click(By.xpath(XPahLeftArrowDevice)).exec();
		} else {
			click(By.xpath(XPathAspaForCloseDesktop)).exec();
		}
	}
}
