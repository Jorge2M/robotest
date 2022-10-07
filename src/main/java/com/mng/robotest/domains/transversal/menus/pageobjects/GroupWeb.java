package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupResponse.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

public class GroupWeb extends PageBase {
	
	private static final List<LineaType> ALL_LINES = Arrays.asList(she, he, teen, nina, nino, home);
	private static final List<LineaType> ALL_EXCEPT_HOME = Arrays.asList(she, he, teen, nina, nino); 
	private static final List<LineaType> SHE = Arrays.asList(she);
	private static final List<LineaType> HE = Arrays.asList(he);
	private static final List<LineaType> HOME = Arrays.asList(home);
	
	public enum GroupResponse { MENUS, ARTICLES, MORE } 
	
	public enum GroupType {
		NEW_NOW("nuevo", ARTICLES, ALL_LINES),
		SELECTION("SELECTION", ARTICLES, SHE),
		PRENDAS("prendas", MENUS, ALL_EXCEPT_HOME), 
		ACCESORIOS("accesorios", MENUS, ALL_EXCEPT_HOME), 
		TALLAS_PLUS("violeta", ARTICLES, SHE), 
		TRAJES("sastreria", MENUS, HE),
		BASICOS("basics", ARTICLES, HOME),
		DORMITORIO("dormitorio", MENUS, HOME),
		BANO("bano", MENUS, HOME), 
		SALON("salon", MENUS, HOME),
		COCINA_Y_COMEDOR("cociona", MENUS, HOME),
		ROPA_CASA("ropa_casa", MENUS, HOME),
		VELAS_Y_AROMAS("velas_y_aromas", MENUS, HOME),
		PROMOCION("promocionado", MENUS, ALL_LINES), 
		INTIMISSIMI("intimissimi", MENUS, SHE), 
		RITUALS("rituals", MENUS, SHE),
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
		public String toString() {
			return name().toLowerCase();
		}
	}
	
	private final LineaType linea;
	private final SublineaType sublinea;
	private final GroupType group;
	
	public GroupWeb(GroupType group) {
		this.linea = LineaType.she;
		this.sublinea = null;
		this.group = group; 
	}
	
	public GroupWeb(LineaType linea, SublineaType sublinea, GroupType group) {
		this.linea = linea;
		this.sublinea = sublinea;
		this.group = group;
	}
	
	private static final String XPATH_GROUP_DEVICE = 
			"//*[@data-testid[contains(.,'header.tabButton')] or " + 
				"@data-testid[contains(.,'header.tabLink')] or " + 
				"@data-testid[contains(.,'header.subMenu')]]";
	private static final String XPATH_GROUP_VIEW_MORE_DEVICE = "//button[@data-testid[contains(.,'viewMore')]]";
	private static final String XPATH_GROUP_DESKTOP = "//li[@data-testid[contains(.,'header.section')]]";
	
	private static final String TAG_GROUP = "@tag_group";
	private static final String XPATH_SUBMENU_WITH_TAG_DESKTOP = 
			"//ul[@data-testid[contains(.,'subfamily')]]/li[@id[contains(.,'" + TAG_GROUP + "')]]";
	private static final String XPATH_SUBMENU_DEVICE = "//div[@data-testid='header.subMenu']";

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
	
	private String getXPathGroupLiDesktop() {
		return  XPATH_GROUP_DESKTOP + "//self::*[@id[contains(.,'" + group.getId() + "')]]";
	}
	private String getXPathGroupDesktop() {
		String xpathGroupLi = getXPathGroupLiDesktop();
		if (group.getGroupResponse()==GroupResponse.ARTICLES) {
			return xpathGroupLi + "/a";
		}
		return xpathGroupLi + "/span";
	}
	private String getXPathGroupSelectedDesktop() {
		return getXPathGroupLiDesktop() + "//self::*[string-length(normalize-space(@class))>0]";
	}
	
	private String getXPathSubmenu() {
		if (channel.isDevice()) {
			return XPATH_SUBMENU_DEVICE;
		}
		return XPATH_SUBMENU_WITH_TAG_DESKTOP.replace(TAG_GROUP, group + "_" + linea); 
	}
	
	public void click() {
		hoverLinea(); 
		clickGroup();
	}
	
	private void clickGroup() {
		click(getXPathGroup()).exec(); 
		if (!isVisibleSubMenus() && group.getGroupResponse()==GroupResponse.MENUS) {
			waitMillis(1000);
			click(getXPathGroup()).exec();
		}
	}
	
	private void hoverLinea() {
		LineaWeb lineaWeb = new LineaWeb(linea, sublinea);
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
		return state(State.Visible, getXPathSubmenu()).check();
	}
	
}

