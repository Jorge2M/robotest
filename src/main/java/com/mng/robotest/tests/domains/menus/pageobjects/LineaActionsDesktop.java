package com.mng.robotest.tests.domains.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.SublineaType;

public class LineaActionsDesktop extends PageBase implements LineaActions {
	
	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String TAG_ID_LINEA = "@LineaId";
	private static final String XP_LINEA_WITH_TAG = "//li[@data-testid[contains(.,'menu.brand." + TAG_ID_LINEA + "')]]";

	private static final String TAG_ID_SUBLINEA = "@SublineaId";
	private static final String TAG_ID_SUBLINEA2 = "@IdSublinea2";
	private static final String XP_SUBLINEA_WITH_TAG = "//li[" + 
			"@data-testid='menu.subBrand." + TAG_ID_SUBLINEA+ "' or " + //Outlet
			"@data-testid='menu.subBrand.sections_" + TAG_ID_SUBLINEA + "' or " + //Shop
			"@data-testid='menu.subBrand.sections_" + TAG_ID_SUBLINEA2 + "']" + //Shop
			"/button[@data-testid[contains(.,'menu.subBrand')]]";
	
	public LineaActionsDesktop(LineaWeb lineaWeb) {
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}

	private String getXPathLinkLinea() {
		return getXPathLinea() + "/a";
	}
	
	private String getXPathLinea() {
		return XP_LINEA_WITH_TAG.replace(TAG_ID_LINEA, getIdLineaEnDOM());
	}
	
	public String getIdLineaEnDOM() {
		if (isOutlet()) {
			return lineaType.getSufixOutlet(channel);
		}
		return lineaType.name(app).toLowerCase();
	}	
	
	private String getXPathSublinea() {
		//En el caso de Teen no es coherente el id en Shop, va cambiando
		if (lineaType==LineaType.TEEN) {
			return XP_SUBLINEA_WITH_TAG
					.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
					.replace(TAG_ID_SUBLINEA2, sublineaType.getIdTeen2(app));
			
		}
		return XP_SUBLINEA_WITH_TAG
				.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
				.replace(TAG_ID_SUBLINEA2, sublineaType.getId(app));
	}
	
	private String getXPathSublineaSelected() {
		return getXPathSublinea() + "/../self::*[@class[contains(.,'Submenu_selected')]]";
	}

	@Override
	public void clickLinea() {
		state(PRESENT, getXPathLinea()).wait(1).check();
		click(getXPathLinea()).exec();
	}	
	@Override 
	public void clickSublinea() {
		state(PRESENT, getXPathLinea()).wait(1).check();
		click(getXPathSublinea()).exec();
	}
	@Override
	public void hoverLinea() {
		try {
			hoverLineaInternal();
		} catch (StaleElementReferenceException | NoSuchElementException e) {
			hoverLineaInternal();
		}
	}
	private void hoverLineaInternal() {
		state(VISIBLE, getXPathLinea()).wait(3).check();
		moveToElement(getXPathLinea());
	}
	
	
	@Override 
	public void hoverSublinea() {
		state(VISIBLE, getXPathSublinea()).wait(1).check();
		moveToElement(getXPathSublinea());
	}
	@Override
	public boolean isLineaSelected(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (!state(VISIBLE, getXPathLinkLinea()).wait(1).check()) {
				return false;
			}
			String textDecoration = getElement(getXPathLinkLinea()).getCssValue("text-decoration");
			if (textDecoration.contains("underline")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isSublineaSelected(int seconds) {
		return state(VISIBLE, getXPathSublineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return state(VISIBLE, getXPathLinea()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(VISIBLE, getXPathSublinea()).wait(seconds).check();
	}
	
}
