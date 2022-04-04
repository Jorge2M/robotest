package com.mng.robotest.test.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktopNew extends SecLineasMenuDesktop {

	//private static String XPathMenuFatherWrapper = "//micro-frontend[@id='header']/div";
	private static String XPathMenuFatherWrapper = "//micro-frontend[@id='header']";
	private final static String XPathLinea = "//li[@data-testid[contains(.,'header.menuItem')]]";
	private static String XPathLineasMenuWrapper = XPathLinea + "/..";
	private final static String XPathLineaSpecificWithTag = XPathLinea + "//self::*[@data-testid[contains(.,'Item." + TagIdLinea + "')]]";
	
	private final static String TagIdSublinea = "@SublineaId";
	private final static String XPathSublineaWithTag = "//li[@id[contains(.,'" + TagIdSublinea+ "')] and @data-testid[contains(.,'section')]]";
	
	public SecLineasMenuDesktopNew(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	private String getXPathSublineaLink(SublineaType sublineaType) {
		return (XPathSublineaWithTag.replace(TagIdSublinea, sublineaType.getId(AppEcom.shop)) + "//span");
	}
	
	@Override
	public String getXPathMenuFatherWrapper() {
		return XPathMenuFatherWrapper;
	}
	
	@Override
	public String getXPathLineasMenuWrapper() {
		return XPathLineasMenuWrapper;
	}
	
	@Override
	public String getXPathLinea() {
		return XPathLinea;
	}
	
	@Override
	public String getXPathLinea(LineaType lineaType) {
		String lineaIddom = SecMenusWrap.getIdLineaEnDOM(Channel.desktop, app, lineaType);
		return (XPathLineaSpecificWithTag.replace(TagIdLinea, lineaIddom));
	}
	
	@Override
	public void selectSublinea(LineaType lineaType, SublineaType sublineaType) {
		for (int i=0; i<3; i++) {
			hoverLinea(lineaType);
			if (isVisibleSublineaUntil(sublineaType, 2)) {
				break;
			}
		}
		hoverSublinea(sublineaType);
	}
	
	private boolean isVisibleSublineaUntil(SublineaType sublineaType, int maxSeconds) {
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);	
		return state(Visible, By.xpath(xpathLinkSublinea)).wait(maxSeconds).check();
	}
	
	private void hoverSublinea(SublineaType sublineaType) {
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);
		moveToElement(By.xpath(xpathLinkSublinea), driver);
		PageObjTM.waitMillis(500);
		moveToElement(By.xpath(xpathLinkSublinea), driver);
	}
	
}
