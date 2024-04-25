package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class SecSelectorPreciosDesktop extends PageBase {
	
	public enum TypeClick { LEFT, RIGHT }
	
	abstract String getXPathLineaFiltro();
	abstract String getXPathImporteMinimo();
	abstract String getXPathImporteMaximo();
	abstract String getXPathRightCorner();
	abstract String getXPathLeftCorner();
	abstract String getXPathFiltroWrapper();
	public abstract void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha);
	
	public static SecSelectorPreciosDesktop make(Pais pais, AppEcom app) {
		return new SecSelectorPreciosDesktopNormal();
	}
	
	public boolean isVisible() {
		return state(VISIBLE, getXPathLineaFiltro()).check();
	}

	public int getMinImport() {
		state(VISIBLE, getXPathImporteMinimo()).wait(2).check();
		var impMinElement = getElement(getXPathImporteMinimo());
		return getImportFilter(impMinElement.getText());
	}
	public int getMaxImport() {
		var impMaxElement = getElement(getXPathImporteMaximo());
		return getImportFilter(impMaxElement.getText());
	}
	private int getImportFilter(String importScreen) {
		return (int)(ImporteScreen.getFloatFromImporteMangoScreen(importScreen));		
	}

}
