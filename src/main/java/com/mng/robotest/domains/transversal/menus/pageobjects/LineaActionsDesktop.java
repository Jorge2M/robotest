package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public class LineaActionsDesktop extends PageBase implements LineaActions {
	
	private final LineaWeb lineaWeb;
	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String TAG_ID_LINEA = "@LineaId";
//	private static final String XPATH_MENU_FATHER_WRAPPER = "//micro-frontend[@id='header']";
	private static final String XPATH_LINEA = "//li[@data-testid[contains(.,'header.menuItem')]]";
//	private static final String XPATH_LINEAS_MENU_WRAPPER = XPATH_LINEA + "/..";
	private static final String XPATH_LINEA_WITH_TAG = XPATH_LINEA + "//self::*[@data-testid[contains(.,'Item." + TAG_ID_LINEA + "')]]";
//
	private static final String TAG_ID_SUBLINEA = "@SublineaId";
	private static final String XPATH_SUBLINEA_WITH_TAG = "//li[@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] and @data-testid[contains(.,'section')]]";
	private static final String TAG_ID_SUBLINEA2 = "@2SublineaId";
	private static final String XPATH_SUBLINEA_WITH_2TAG = "//li[(@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] or @id[contains(.,'" + TAG_ID_SUBLINEA2 + "')]) and @data-testid[contains(.,'section')]]";
	
	public LineaActionsDesktop(LineaWeb lineaWeb) {
		this.lineaWeb = lineaWeb;
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}
	
	private String getXPathLinea() {
		return XPATH_LINEA_WITH_TAG.replace(TAG_ID_LINEA, getIdLineaEnDOM());
	}	
	public String getIdLineaEnDOM() {
		if (app==AppEcom.outlet) {
			return lineaType.getSufixOutlet(channel);
		}
		return lineaType.name(app);
	}	
	
	private String getXPathSublinea() {
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
	
	private String getXPathLineaSelected() {
		return getXPathLinea() + "//a[@aria-expanded='true']";
	}
	private String getXPathSublineaSelected() {
		return getXPathSublinea() + "//self::*[string-length(normalize-space(@class))>0]";
	}
//	
//	private String getXPathLineaLink() {
//		return XPATH_LINEA + "/a";
//	}
//	
	@Override
	public void clickLinea() {
		click(getXPathLinea()).exec();
	}	
	@Override 
	public void clickSublinea() {
		click(getXPathSublinea()).exec();
	}
	@Override
	public boolean isLineaSelected(int seconds) {
		return state(Present, getXPathLineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaSelected(int seconds) {
		return state(Visible, getXPathSublineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return state(Visible, getXPathLinea()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(Visible, getXPathSublinea()).wait(seconds).check();
	}
	
//
//	public boolean isPresentLineasMenuWrapp() {
//		return state(Present, XPATH_LINEAS_MENU_WRAPPER).check();
//	}
//	
//	public boolean isVisibleMenuSup() {
//		return state(Present, XPATH_LINEAS_MENU_WRAPPER).check();
//	}
//	
//	public boolean isVisibleMenuSupUntil(int seconds) {
//		return state(Visible, XPATH_LINEAS_MENU_WRAPPER).wait(seconds).check();
//	}	
//	
//	public boolean isInvisibleMenuSupUntil(int seconds) {
//		return state(Invisible, XPATH_LINEAS_MENU_WRAPPER).wait(seconds).check();
//	}
//	
//	private enum BringTo {FRONT, BACKGROUND};
//	
//	public void bringMenuBackground() throws Exception {		
//		bringElement(BringTo.BACKGROUND);
//	}	
//	
//	public void bringMenuFront() throws Exception {
//		bringElement(BringTo.FRONT);	
//	}
//	
//	private String getXPathMenuWrapper() {
//		if (app==AppEcom.outlet) {
//			return XPATH_LINEAS_MENU_WRAPPER;
//		}
//		return XPATH_MENU_FATHER_WRAPPER;
//	}	
//
//	private void bringElement(BringTo action) {
//		String display = "none";
//		State stateExpected = State.Invisible;
//		if (action==BringTo.FRONT) {
//			display = "block";
//		}
//		
//		String xpathToBringBack = getXPathMenuWrapper();		
//		WebElement menuWrapp = getElement(xpathToBringBack);
//		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='" + display + "';", menuWrapp);
//		state(stateExpected, xpathToBringBack).wait(1).check();
//	}
//	
//	public List<WebElement> getListaLineas() {
//		return getElements(XPATH_LINEA);
//	}
//	
//	public boolean isLineaPresent() {
//		String xpathLinea = getXPathLineaLink(lineaType);
//		return state(Present, xpathLinea).check();
//	}
//	
//	public boolean isLineaPresentUntil(int seconds) {
//		String xpathLinea = getXPathLineaLink(lineaType);
//		return state(Present, xpathLinea).wait(seconds).check();
//	}	
//	
//	public boolean isLineaVisible() {
//		String xpathLinea = getXPathLineaLink();
//		return state(Present, xpathLinea).check();
//	}
//	
//
//	public void hoverAndWaitMenus() {
//		//Existe un problema aleatorio en Firefox que provoca que el Hover sobre la línea (mientras se está cargando la galería) 
//		//ejecute realmente un hover contra la línea de la izquerda
//		boolean isCapaMenusVisible = false;
//		int i=0;
//		do {
//			hover(lineaType, sublineaType);
//			SecBloquesMenuDesktop secBloques = new SecBloquesMenuDesktopNew();
//			isCapaMenusVisible = secBloques.isCapaMenusLineaVisibleUntil(lineaType, 2);
//			if (!isCapaMenusVisible) {
//				Log4jTM.getLogger().warn("No se hacen visibles los menús después de Hover sobre línea " + lineaType);
//			}
//			i+=1;
//		}
//		while (!isCapaMenusVisible && i<2);
//	}
//	
//	public void hover() {
//		if (sublineaType==null) {
//			hoverLinea(lineaType);
//		} else {
//			selectSublinea(lineaType, sublineaType);
//		}
//	}
//	
//	public void hoverLinea() {
//		//Hover sobre la pestaña -> Hacemos visibles los menús/subimágenes
//		String xpathLinkLinea = getXPathLineaLink(lineaType);
//		state(Visible, xpathLinkLinea).wait(1).check();
//		moveToElement(xpathLinkLinea);
//	}
//	
//	public void selectSublinea() {
//		for (int i=0; i<3; i++) {
//			hoverLinea(lineaType);
//			if (isVisibleSublineaUntil(sublineaType, 2)) {
//				break;
//			}
//		}
//		hoverSublinea(sublineaType);
//	}
//	
//	private boolean isVisibleSublineaUntil(int seconds) {
//		String xpathLinkSublinea = getXPathSublineaLink();	
//		return state(Visible, xpathLinkSublinea).wait(seconds).check();
//	}
//	
//	private void hoverSublinea() {
//		String xpathLinkSublinea = getXPathSublineaLink();
//		moveToElement(xpathLinkSublinea);
//		waitMillis(500);
//		moveToElement(xpathLinkSublinea);
//	}	
}
