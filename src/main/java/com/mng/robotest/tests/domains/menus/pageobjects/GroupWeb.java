package com.mng.robotest.tests.domains.menus.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.SublineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupResponse.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.*;

public class GroupWeb extends PageBase {
	
	private static final List<LineaType> ALL_LINES = Arrays.asList(SHE, HE, TEEN, NINA, NINO, HOME);
	private static final List<LineaType> ALL_EXCEPT_HOME = Arrays.asList(SHE, HE, TEEN, NINA, NINO); 
	private static final List<LineaType> SHE_LINE = Arrays.asList(SHE);
	private static final List<LineaType> HE_LINE = Arrays.asList(HE);
	private static final List<LineaType> HOME_LINE = Arrays.asList(HOME);
	
	public enum GroupResponse { MENUS, ARTICLES, MORE } 
	
	public enum GroupType {
		NEW_NOW("nuevo", ARTICLES, ALL_LINES),
		SELECTION("SELECTION", ARTICLES, SHE_LINE),
		PRENDAS("prendas", MENUS, ALL_EXCEPT_HOME), 
		ACCESORIOS("accesorios", MENUS, ALL_EXCEPT_HOME), 
		TALLAS_PLUS("tplus", ARTICLES, SHE_LINE), 
		TRAJES("sastreria", MENUS, HE_LINE),
		BASICOS("basics", ARTICLES, HOME_LINE),
		DORMITORIO("dormitorio", MENUS, HOME_LINE),
		BANO("bano", MENUS, HOME_LINE), 
		SALON("salon", MENUS, HOME_LINE),
		COCINA_Y_COMEDOR("cocina", MENUS, HOME_LINE),
		ROPA_CASA("ropa_casa", MENUS, HOME_LINE),
		VELAS_Y_AROMAS("velas", MENUS, HOME_LINE),
		PROMOCION("promocionado", MENUS, ALL_LINES), 
		NUEVA_COLECCION("nuevacol", MENUS, ALL_LINES),
		COLECCIONES("colecciones", MORE, ALL_EXCEPT_HOME);
	
		private final String id; 
		private final GroupResponse groupResponse; 
		private final List<LineaType> lineas; 
		private GroupType(String id, GroupResponse groupResponse, List<LineaType> lineas) {
			this.id = id;
			this.groupResponse = groupResponse;
			this.lineas = lineas;
		}
		
		public static List<GroupType> getGroups(LineaType linea) {
			return Arrays.asList(COLECCIONES);
//			return Arrays.stream(GroupType.values())
//				.filter(g -> g.getLineas().contains(linea))
//				.toList();
		}
		
		public String getId() {
			return id;
		}
		public GroupResponse getGroupResponse() {
			return groupResponse;
		}
		public List<LineaType> getLineas() {
			return lineas;
		}
		public boolean isInLinea(LineaType linea) {
			return getLineas().contains(linea);
		}
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
	
	private final LineaType linea;
	private final SublineaType sublinea;
	private final GroupType group;
	
	public GroupWeb(GroupType group) {
		this.linea = LineaType.SHE;
		this.sublinea = null;
		this.group = group; 
	}
	
	public GroupWeb(LineaType linea, SublineaType sublinea, GroupType group) {
		this.linea = linea;
		this.sublinea = sublinea;
		this.group = group;
	}

	private static final String XP_GROUP_DEVICE = 
			"//*[@data-testid[contains(.,'header.tabButton')] or " + 
				"@data-testid[contains(.,'header.tabLink')] or " + 
				"@data-testid[contains(.,'menu.section')]]";
	
	private static final String XP_GROUP_VIEW_MORE_DEVICE = "//button[@data-testid[contains(.,'viewMore')]]";
	private static final String XP_GROUP_DESKTOP = "//li[@data-testid[contains(.,'menu.section')]]";
	private static final String TAG_GROUP = "@tag_group";
	
	private static final String XP_SUBMENU_WITH_TAG_DESKTOP = 
			"//li[@data-testid[contains(.,'" + TAG_GROUP + "')]]" + 
			"/ul[@id[contains(.,'subMenuColumn3')]]" +  
			"/li[@data-testid[contains(.,'" + TAG_GROUP + "')]]";
	
	//private static final String XP_SUBMENU_DEVICE = "//li[@class[contains(.,'Submenu_selected')]]//div[@data-testid='menu.subMenu']";
	private static final String XP_SUBMENU_DEVICE = "//li[@data-testid[contains(.,'menu.section')]]//div[@data-testid='menu.subMenu']";

	private String getXPathGroupSelected() {
		return getXPathGroup() + "/../self::*[@class[contains(.,'Submenu_selected')]]/button"; //Génesis (14-11-23)
	}
	
	private String getXPathGroup() {
		if (Utils.isMenuNewService(app, dataTest.getPais())) {
			return getXPathGroupDevice();
		}
		return getXPathGroupDesktop();
	}
	
	private String getXPathGroupDevice() {
		String xpath = XP_GROUP_DEVICE;
		if (group.getGroupResponse()==MORE && sublinea==null) {
			xpath = XP_GROUP_VIEW_MORE_DEVICE;
		}
		return xpath + "//self::*[@data-testid[contains(.,'" + group.getId() + "')]]"; 
	}
	
	private String getXPathGroupDesktop() {
		String dataTestId = "[@data-testid[contains(.,'" + group.getId() + "')]]";
		if (group.getGroupResponse()==GroupResponse.ARTICLES) {
			return XP_GROUP_DESKTOP + "//a" + dataTestId;
		} else {
			return XP_GROUP_DESKTOP + "//button" + dataTestId;
		}
	}

	private String getXPathSubmenu() {
		if (channel.isDevice()) {
			return XP_SUBMENU_DEVICE;
		}
		return getXPathSubmenuDesktop(); 
	}
	
	private String getXPathSubmenuDesktop() {
		String xpathSubmenu = XP_SUBMENU_WITH_TAG_DESKTOP.replace(TAG_GROUP, group + "_" + linea.toString().toLowerCase());
		if (sublinea!=null) {
			return xpathSubmenu.replace("subMenuColumn3", "subMenuColumn4"); 
		}
		return xpathSubmenu;
	}
	
	public void click() {
		hoverLinea(); 
		clickGroup();
	}
	public void hover() {
		for (int i=0; i<3; i++) {
			hoverLinea(); 
			hoverGroup();
			if (isGroupCorrectlySelected()) {
				break;
			}
		}
	}	
	public boolean isPresent() {
		hoverLinea();
		return state(VISIBLE, getXPathGroup()).wait(1).check();
	}
	
	private void clickGroup() {
		state(VISIBLE, getXPathGroup()).wait(2).check();
		click(getXPathGroup()).state(VISIBLE).exec(); 
		if (!isVisibleSubMenus() && group.getGroupResponse()==GroupResponse.MENUS) {
			waitMillis(1000);
			click(getXPathGroup()).exec();
		}
	}
	
	private void hoverGroup() {
		state(VISIBLE, getXPathGroup()).wait(2).check();
		moveToElement(getXPathGroup()); 
	}
	
	private boolean isGroupCorrectlySelected() {
		return (isGroupSelected() &&
			   (group.getGroupResponse()!=GroupResponse.MENUS || isVisibleSubMenus()));
	}
	private boolean isGroupSelected() {
		return state(VISIBLE, getXPathGroupSelected()).wait(1).check();
	}
	
	private void hoverLinea() {
		var lineaWeb = new LineaWeb(linea, sublinea);
		lineaWeb.hoverLinea(); 
		if (sublinea!=null) {
			lineaWeb.hoverSublinea();
		}
	}

	public LineaType getLinea() {
		return linea;
	}

	public SublineaType getSublinea() {
		return sublinea;
	}

	public GroupType getGroup() {
		return group;
	}
	
	public boolean isVisibleSubMenus() {
		return state(VISIBLE, getXPathSubmenu()).check();
	}
	
}

