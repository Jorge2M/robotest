package com.mng.robotest.test.pageobject.shop.menus.desktop;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;

public abstract class SecLineasMenuDesktop extends PageBase {
	
	public abstract String getXPathMenuFatherWrapper();
	public abstract String getXPathLineasMenuWrapper();
	public abstract String getXPathLinea();
	public abstract String getXPathLinea(LineaType lineaType);
	public abstract void selectSublinea(LineaType lineaType, SublineaType sublineaType);
	
	protected static final String TAG_ID_LINEA = "@LineaId";
	protected static final String TAG_ID_SUBLINEA = "@SublineaId";
	
	public String getXPathLineaSelected(LineaType lineaType) {
		String xpathLinea = getXPathLinea(lineaType);
		return (xpathLinea + "//a[@aria-expanded='true']");
	}
	
	public String getXPathLineaLink(LineaType lineaType) {
		return (getXPathLinea(lineaType) + "/a");
	}

	public boolean isPresentLineasMenuWrapp() {
		return state(Present, getXPathLineasMenuWrapper()).check();
	}
	
	public boolean isVisibleMenuSup() {
		return state(Present, getXPathLineasMenuWrapper()).check();
	}
	
	public boolean isVisibleMenuSupUntil(int maxSeconds) {
		return state(Visible, getXPathLineasMenuWrapper()).wait(maxSeconds).check();
	}	
	
	public boolean isInvisibleMenuSupUntil(int maxSeconds) {
		return state(Invisible, getXPathLineasMenuWrapper()).wait(maxSeconds).check();
	}
	
	private enum BringTo {FRONT, BACKGROUND};
	
	public void bringMenuBackground() throws Exception {		
		bringElement(BringTo.BACKGROUND);
	}	
	
	public void bringMenuFront() throws Exception {
		bringElement(BringTo.FRONT);	
	}
	
	private String getXPathMenuWrapper() {
		switch (app) {
		case outlet:
			return getXPathLineasMenuWrapper();
		case shop:
		default:
			return getXPathMenuFatherWrapper();
		}
	}	

	private void bringElement(BringTo action) {
		String display = "none";
		State stateExpected = State.Invisible;
		if (action==BringTo.FRONT) {
			display = "block";
		}
		
		String xpathToBringBack = getXPathMenuWrapper();		
		WebElement menuWrapp = driver.findElement(By.xpath(xpathToBringBack));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='" + display + "';", menuWrapp);
		state(stateExpected, By.xpath(xpathToBringBack)).wait(1).check();
	}
	
	public List<WebElement> getListaLineas() {
		return getElements(getXPathLinea());
	}
	
	public boolean isLineaPresent(LineaType lineaType) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return state(Present, xpathLinea).check();
	}
	
	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return state(Present, xpathLinea).wait(maxSeconds).check();
	}	
	
	public boolean isLineaVisible(LineaType lineaType) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return state(Present, xpathLinea).check();
	}
	
	public boolean isLineaSelected(LineaType lineaType) {
		String xpathLinea = getXPathLineaSelected(lineaType);
		return state(Present, xpathLinea).check(); 
	}

	public void selecLinea(Pais pais, LineaType lineaType) {
		Linea linea = pais.getShoponline().getLinea(lineaType);
		if (isLineActiveToSelect(pais, linea)) {
			click(getXPathLineaLink(lineaType)).type(javascript).exec();
		}
	}

	private boolean isLineActiveToSelect(Pais pais, Linea linea) {
		//En el caso concreto de los países con únicamente la línea She -> Aparecen otras pestañas
		return (
			(app==AppEcom.outlet || 
			(pais.getShoponline().getNumLineasTiendas(app) > 1 || 
			 !pais.getShoponline().isLineaTienda(linea)))
		);
	}

	public void hoverLineaAndWaitForMenus(LineaType lineaType, SublineaType sublineaType) {
		//Existe un problema aleatorio en Firefox que provoca que el Hover sobre la línea (mientras se está cargando la galería) 
		//ejecute realmente un hover contra la línea de la izquerda
		boolean isCapaMenusVisible = false;
		int i=0;
		do {
			hoverLinea(lineaType, sublineaType);
			SecBloquesMenuDesktop secBloques = new SecBloquesMenuDesktopNew();
			isCapaMenusVisible = secBloques.isCapaMenusLineaVisibleUntil(lineaType, 2);
			if (!isCapaMenusVisible) {
				Log4jTM.getLogger().warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
			}
			i+=1;
		}
		while (!isCapaMenusVisible && i<2);
	}
	
	public void hoverLinea(LineaType lineaType, SublineaType sublineaType) {
		if (sublineaType==null) {
			hoverLinea(lineaType);
		} else {
			selectSublinea(lineaType, sublineaType);
		}
	}
	
	public void hoverLinea(LineaType lineaType) {
		//Hover sobre la pestaña -> Hacemos visibles los menús/subimágenes
		String xpathLinkLinea = getXPathLineaLink(lineaType);
		state(Visible, xpathLinkLinea).wait(1).check();
		moveToElement(xpathLinkLinea);
	}
}
