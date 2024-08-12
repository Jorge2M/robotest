package com.mng.robotest.tests.domains.menus.pageobjects.currentmenus;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;

public class LineaActionsDevice extends PageBase implements LineaActions {

	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private String getXPathSublineaLink() {
		return "//*[@data-testid[contains(.,'menu.subBrand." + sublineaType.getId(app) + ".section')]]";
	}

	public LineaActionsDevice(LineaWebCurrent lineaWeb) {
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}

	private String getXPathMenusSublinea() {
		var idSublinea = sublineaType.getId(app);
		return "//ul[@data-testid[contains(.,'menu.family." + idSublinea + "')]]";
	}	
	
	private String getXPathLineaLink() throws IllegalArgumentException {
		String dataTestid = "menu.brand";
		switch (lineaType) {
		case SHE, HE, TEEN, HOME:
			if (isOutlet()) {
				return "//*[@data-testid='" + dataTestid + "." + lineaType.getSufixOutlet(channel).trim() + "']";
			}
			return "//*[@data-testid='" + dataTestid + "." + lineaType.getId2() + "']";
		case NINA, NINO:
			if (isOutlet()) {
				return "//*[@data-testid='" + dataTestid + "." + lineaType.getSufixOutlet(channel) + "']";
			}
		case KIDS:			
			return "//*[@data-testid='" + dataTestid + ".kids']";
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}	
	
	@Override
	public void clickLinea() {
		try {
			clickLineaInternal();
		} catch (StaleElementReferenceException | NoSuchElementException e) {
			waitMillis(1000);
			clickLineaInternal();
		}
 	}
	
	private void clickLineaInternal() {
		boolean toOpenMenus = true;
		var secCabecera = SecCabecera.make();
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		Linea linea = Linea.getLinea(lineaType, dataTest.getPais());
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink()).type(JAVASCRIPT).exec();
		}		
	}
	
	@Override
	public void hoverLinea() {
		clickLinea();
	}
	
	@Override
	public void clickSublinea() {
		if (!isSublineaSelected(0)) {
			click(getXPathSublineaLink()).exec();
		}
	}
	@Override
	public void hoverSublinea() {
		clickSublinea();
	}
	
	@Override
	public boolean isLineaSelected(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isLineaSelected()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	public boolean isLineaSelected() {
		String xpathLineaWithFlagSelected = getXPathLineaLink();
		if (isOutlet() || isTablet()) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(PRESENT, xpathLineaWithFlagSelected).check()) {
			return getElement(xpathLineaWithFlagSelected).getAttribute("class").contains("selected");
		}
		return false;
	}

	@Override
	public boolean isSublineaSelected(int seconds) {
		for (int i=0; i<=seconds; i++) {
			if (isSublineaSelected()) {
				return true;
			}
			if (i<seconds) {
				waitMillis(1000);
			}
		}
		return false;
	}
	
	public boolean isSublineaSelected() {
		return state(VISIBLE, getXPathMenusSublinea()).check();
	}	

	@Override
	public boolean isLineaPresent(int seconds) {
		return state(VISIBLE, getXPathLineaLink() + "/../..").wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(VISIBLE, getXPathSublineaLink()).wait(seconds).check();
	}	
	
}
