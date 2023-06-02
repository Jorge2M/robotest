package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupResponse.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		TALLAS_PLUS("violeta", ARTICLES, SHE_LINE), 
		TRAJES("sastreria", MENUS, HE_LINE),
		BASICOS("basics", ARTICLES, HOME_LINE),
		DORMITORIO("dormitorio", MENUS, HOME_LINE),
		BANO("bano", MENUS, HOME_LINE), 
		SALON("salon", MENUS, HOME_LINE),
		COCINA_Y_COMEDOR("cociona", MENUS, HOME_LINE),
		ROPA_CASA("ropa_casa", MENUS, HOME_LINE),
		VELAS_Y_AROMAS("velas_y_aromas", MENUS, HOME_LINE),
		PROMOCION("promocionado", MENUS, ALL_LINES), 
		INTIMISSIMI("intimissimi", MENUS, SHE_LINE), 
		RITUALS("rituals", MENUS, SHE_LINE),
		COLECCIONES("colecciones", MORE, ALL_EXCEPT_HOME),		
		DESTACADOS("destacados", MORE, ALL_LINES);
	
		private final String id; 
		private final GroupResponse groupResponse; 
		private final List<LineaType> lineas; 
		private GroupType(String id, GroupResponse groupResponse, List<LineaType> lineas) {
			this.id = id;
			this.groupResponse = groupResponse;
			this.lineas = lineas;
		}
		
		public static List<GroupType> getGroups(LineaType linea) {
			return Arrays.stream(GroupType.values())
				.filter(g -> g.getLineas().contains(linea))
				.toList();
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

	//TODO eliminar el OLD (header.subMenu) cuando suba la nueva versión a PRO (31-05-2023)
	private static final String XPATH_GROUP_DEVICE = 
			"//*[@data-testid[contains(.,'header.tabButton')] or " + 
				"@data-testid[contains(.,'header.tabLink')] or " + 
				"@data-testid[contains(.,'header.subMenu')] or " +
				"@data-testid[contains(.,'menu.section')]]";
	
	private static final String XPATH_GROUP_VIEW_MORE_DEVICE = "//button[@data-testid[contains(.,'viewMore')]]";
	private static final String XPATH_GROUP_DESKTOP = "//li[@data-testid[contains(.,'menu.section')]]";
	private static final String TAG_GROUP = "@tag_group";
	private static final String XPATH_SUBMENU_WITH_TAG_DESKTOP = 
			"//ul[@data-testid[contains(.,'subfamily')]]/li/*[@data-testid[contains(.,'" + TAG_GROUP + "')]]/..";

	//TODO eliminar el OLD cuando suba la nueva versión a PRO (31-05-2023)
	private static final String XPATH_SUBMENU_DEVICE_OLD = "//div[@data-testid='header.subMenu']";
	private static final String XPATH_SUBMENU_DEVICE_NEW = "//div[@data-testid='menu.subMenu']";

	private String getSubmenuDevice() {
		return "(" + XPATH_SUBMENU_DEVICE_OLD + " | " + XPATH_SUBMENU_DEVICE_NEW + ")";
	}
	
	private String getXPathGroup() {
		if (channel.isDevice()) {
			return getXPathGroupDevice();
		}
		return getXPathGroupDesktop();
	}
	private String getXPathGroupDevice() {
		String xpath = XPATH_GROUP_DEVICE;
		if (group.getGroupResponse()==MORE) {
			xpath = XPATH_GROUP_VIEW_MORE_DEVICE;
		}
		return xpath + "//self::*[@data-testid[contains(.,'" + group.getId() + "')]]"; 
	}
	
	private String getXPathGroupDesktop() {
		String dataTestId = "[@data-testid[contains(.,'" + group.getId() + "')]]";
		if (group.getGroupResponse()==GroupResponse.ARTICLES) {
			return XPATH_GROUP_DESKTOP + "//a" + dataTestId;
		} else {
			return XPATH_GROUP_DESKTOP + "//button" + dataTestId;
		}
	}

	private String getXPathSubmenu() {
		if (channel.isDevice()) {
			return getSubmenuDevice();
		}
		return getXPathSubmenuDesktop(); 
	}
	
	private String getXPathSubmenuDesktop() {
		return XPATH_SUBMENU_WITH_TAG_DESKTOP.replace(TAG_GROUP, group + "_" + linea.toString().toLowerCase());
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
		return state(Visible, getXPathGroup()).wait(1).check();
	}
	
	private void clickGroup() {
		state(Visible, getXPathGroup()).wait(2).check();
		click(getXPathGroup()).state(Visible).exec(); 
		if (!isVisibleSubMenus() && group.getGroupResponse()==GroupResponse.MENUS) {
			waitMillis(1000);
			click(getXPathGroup()).exec();
		}
	}
	
	private void hoverGroup() {
		state(Visible, getXPathGroup()).wait(2).check();
		moveToElement(getXPathGroup()); 
	}
	
	private boolean isGroupCorrectlySelected() {
		return (isGroupSelected() &&
			   (group.getGroupResponse()!=GroupResponse.MENUS || isVisibleSubMenus()));
	}
	private boolean isGroupSelected() {
		waitMillis(200);
		String fontWeight = getElement(getXPathGroup()).getCssValue("font-weight");
		return (Integer.valueOf(fontWeight)>500);
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
		return state(Visible, getXPathSubmenu()).check();
	}
	
}

