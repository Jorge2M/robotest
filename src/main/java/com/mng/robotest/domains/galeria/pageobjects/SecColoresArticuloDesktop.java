package com.mng.robotest.domains.galeria.pageobjects;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.PageBase;

public class SecColoresArticuloDesktop extends PageBase {

	private static final String XPATH_COLORS_ARTICLE_SHOP = 
			"//div[" + 
				"@class[contains(.,'product-colors')] or " + 
				"@class[contains(.,'_1nxc_')]" + //TODO (Outlet) A la espera que Sergio Campillo haga el cambio
			"]";
	
	private static final String TAG_ID_COLOR = "@TagIdColor";
	private static final String XPATH_IMG_COD_COLOR_WITH_TAG_COLOR = "//img[@class[contains(.,'other-color')] and @data-id='" + TAG_ID_COLOR + "']";
	
	String getXPathColorArticle() {
//		if (app==AppEcom.outlet) {
//			return XPathColorsArticleOutlet;
//		} else {
			return XPATH_COLORS_ARTICLE_SHOP;
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
		return XPATH_IMG_COD_COLOR_WITH_TAG_COLOR.replace(TAG_ID_COLOR, codigoColor);
	}
}
