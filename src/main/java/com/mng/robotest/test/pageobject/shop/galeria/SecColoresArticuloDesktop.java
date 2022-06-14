package com.mng.robotest.test.pageobject.shop.galeria;

import com.mng.robotest.conftestmaker.AppEcom;

public class SecColoresArticuloDesktop {

	private final AppEcom app;
	
	private final String XPathColorsArticleShop = 
			"//div[" + 
				"@class[contains(.,'product-colors')] or " + 
				"@class[contains(.,'_1nxc_')]" + //TODO (Outlet) A la espera que Sergio Campillo haga el cambio
			"]";
	static final String TagIdColor = "@TagIdColor";
	static final String XPathImgCodColorWithTagColor = "//img[@class[contains(.,'other-color')] and @data-id='" + TagIdColor + "']";
	
	SecColoresArticuloDesktop(AppEcom app) {
		this.app = app;
	}
	
	String getXPathColorArticle() {
//		if (app==AppEcom.outlet) {
//			return XPathColorsArticleOutlet;
//		} else {
			return XPathColorsArticleShop;
//		}
	}
	
	String getXPathImgColorRelativeArticle(boolean selected) {
		String xpathColor = getXPathColorArticle();
		if (!selected) {
			return xpathColor + "//img";
		}
		if (app==AppEcom.outlet) {
			return xpathColor + "//self[@class[contains(.,'--selected')]]//img";
		} else {
			return xpathColor + "//button[@class[contains(.,'_3JgrI')]]/img";
		}
	}

	String getXPathImgCodigoColor(String codigoColor) {
		return XPathImgCodColorWithTagColor.replace(TagIdColor, codigoColor);
	}
}
