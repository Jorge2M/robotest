package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktopNew extends SecLineasMenuDesktop {

	private final static String XPathLinea = "//li[@data-testid[contains(.,'header-menu-item')]]";
	private final static String XPathLineaSpecificWithTag = XPathLinea + "//self::*[@data-testid[contains(.,'item-" + TagIdLinea + "')]]";
	
	private final static String TagIdSublinea = "@SublineaId";
	private final static String XPathSublineaWithTag = "//div[@role='menuitem' and @id[contains(.,'sections_" + TagIdSublinea+ "')]]";
	
	public SecLineasMenuDesktopNew(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	private String getXPathSublineaLink(SublineaType sublineaType) {
		return (XPathSublineaWithTag.replace(TagIdSublinea, sublineaType.getId(AppEcom.shop)));
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
		hoverLinea(lineaType);
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);

		//Esperamos que esté visible la sublínea y realizamos un Hover
		state(Visible, By.xpath(xpathLinkSublinea)).wait(2).check();
		moveToElement(By.xpath(xpathLinkSublinea), driver);
	}
	
}
