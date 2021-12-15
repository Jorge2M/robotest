package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktopOld extends SecLineasMenuDesktop {

	private static String XPathMenuFatherWrapper = "//div[@id='navMain']";
	private static String XPathLineasMenuWrapper = "//div[@class='menu-section']";
	
	private final static String XPathLinea = "//ul[@class[contains(.,'menu-section-brands')]]/li[@class[contains(.,'menu-item-brands')]]";
	private final static String XPathLineaSpecificWithTag = 
		XPathLinea + 
		"//self::*[@id='" + TagIdLinea + "' or " +
				  "@id[contains(.,'sections_" + TagIdLinea + "')] or " +
				  "@id[contains(.,'sections-" + TagIdLinea + "')] or " +
				  "@id[contains(.,'prendas_" + TagIdLinea + "')]]";
	
	private final static String XPathImagesSublineaWithTags = 
			"//div[" + 
				"@class='image-item' and @data-label[contains(.,'" + TagIdLinea + "')] and " +
				"@data-label[contains(.,'" + TagIdSublinea + "')]" +
			"]";
	
	public SecLineasMenuDesktopOld(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	private String getXPathImgSublinea(LineaType lineaId, SublineaType sublineaType) {
		return XPathImagesSublineaWithTags
					.replace(TagIdLinea, lineaId.name())
					.replace(TagIdSublinea, "interior-" + sublineaType.getText());
	}

	private String getXPathSublineaLink(SublineaType sublineaType) {
		String idSublineaEnDom = sublineaType.getId(app);
		if (sublineaType==SublineaType.teen_nina ||
			sublineaType==SublineaType.teen_nino) {
			idSublineaEnDom = sublineaType.getId(AppEcom.outlet);
		}
		
		return (XPathSublineaLinkWithTag.replace(TagIdSublinea, idSublineaEnDom));
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
		hoverLinea(lineaType);
		clickImgSublineaIfVisible(lineaType, sublineaType);
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);

		//Esperamos que esté visible la sublínea y realizamos un Hover
		state(Visible, By.xpath(xpathLinkSublinea)).wait(2).check();
		moveToElement(By.xpath(xpathLinkSublinea), driver);
	}
	
	private boolean isVisibleImgSublineaUntil(LineaType lineaType, SublineaType sublineaType, int maxSeconds) {
		String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
		return (state(Visible, By.xpath(xpathImg)).wait(maxSeconds).check());
	}

	private void clickImgSublineaIfVisible(LineaType lineaType, SublineaType sublineaType) {
		int maxSecondsToWait = 1;
		if (isVisibleImgSublineaUntil(lineaType, sublineaType, maxSecondsToWait)) {
			String xpathImg = getXPathImgSublinea(lineaType, sublineaType);
			click(By.xpath(xpathImg)).exec();
			state(Invisible, By.xpath(xpathImg)).wait(1).check();
		}
	}
	
}
