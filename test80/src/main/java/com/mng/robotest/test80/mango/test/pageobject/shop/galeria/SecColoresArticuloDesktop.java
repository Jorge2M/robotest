package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class SecColoresArticuloDesktop {

	private final AppEcom app;
	
	private final String XPathColorsArticleShop = "//div[@class[contains(.,'product-colors')]]";
	final static String TagIdColor = "@TagIdColor";
	final static String XPathImgCodColorWithTagColor = "//img[@class[contains(.,'other-color')] and @data-id='" + TagIdColor + "']";
	
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
			return xpathColor + "//button[@class[contains(.,'selected')]]/img";
		}
	}

	String getXPathImgCodigoColor(String codigoColor) {
		return XPathImgCodColorWithTagColor.replace(TagIdColor, codigoColor);
	}
}
