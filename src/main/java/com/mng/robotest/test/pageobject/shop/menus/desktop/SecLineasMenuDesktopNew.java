package com.mng.robotest.test.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;

public class SecLineasMenuDesktopNew extends SecLineasMenuDesktop {

	private static final String XPATH_MENU_FATHER_WRAPPER = "//micro-frontend[@id='header']";
	private static final String XPATH_LINEA = "//li[@data-testid[contains(.,'header.menuItem')]]";
	private static final String XPATH_LINEAS_MENU_WRAPPER = XPATH_LINEA + "/..";
	private static final String XPATH_LINEA_SPECIFIC_WITH_TAG = XPATH_LINEA + "//self::*[@data-testid[contains(.,'Item." + TAG_ID_LINEA + "')]]";
	
	private static final String TAG_ID_SUBLINEA = "@SublineaId";
	private static final String XPATH_SUBLINEA_WITH_TAG = "//li[@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] and @data-testid[contains(.,'section')]]";
	private static final String TAG_ID_SUBLINEA2 = "@2SublineaId";
	private static final String XPATH_SUBLINEA_WITH_2TAG = "//li[(@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] or @id[contains(.,'" + TAG_ID_SUBLINEA2 + "')]) and @data-testid[contains(.,'section')]]";
	
	private String getXPathSublineaLink(SublineaType sublineaType) {
		if (sublineaType==SublineaType.teen_nino) {
			//Existe un problema en la página y a veces es TeenO y otras veces TeenP
			return XPATH_SUBLINEA_WITH_2TAG
					.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
					.replace(TAG_ID_SUBLINEA2, "teenP") + 
					"//span";
		}
		return XPATH_SUBLINEA_WITH_TAG
				.replace(TAG_ID_SUBLINEA, sublineaType.getId(app)) + 
				"//span";
	}
	
	@Override
	public String getXPathMenuFatherWrapper() {
		return XPATH_MENU_FATHER_WRAPPER;
	}
	
	@Override
	public String getXPathLineasMenuWrapper() {
		return XPATH_LINEAS_MENU_WRAPPER;
	}
	
	@Override
	public String getXPathLinea() {
		return XPATH_LINEA;
	}
	
	@Override
	public String getXPathLinea(LineaType lineaType) {
		String lineaIddom = SecMenusWrap.getIdLineaEnDOM(channel, app, lineaType);
		return (XPATH_LINEA_SPECIFIC_WITH_TAG.replace(TAG_ID_LINEA, lineaIddom));
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
	
	private boolean isVisibleSublineaUntil(SublineaType sublineaType, int seconds) {
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);	
		return state(Visible, xpathLinkSublinea).wait(seconds).check();
	}
	
	private void hoverSublinea(SublineaType sublineaType) {
		String xpathLinkSublinea = getXPathSublineaLink(sublineaType);
		moveToElement(xpathLinkSublinea);
		waitMillis(500);
		moveToElement(xpathLinkSublinea);
	}
	
}
