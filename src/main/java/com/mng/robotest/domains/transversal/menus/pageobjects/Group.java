package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.mng.robotest.test.beans.Linea.LineaType.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.Group.GroupResponse.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.device.SecLineasDevice;

public class Group extends PageBase {
	
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
		DORMITORIO("dormitorio", ARTICLES, HOME),
		BANO("bano", ARTICLES, HOME), 
		SALON("salon", ARTICLES, HOME),
		COCINA_Y_COMEDOR("cociona", ARTICLES, HOME),
		ROPA_CASA("ropa_casa", ARTICLES, HOME),
		VELAS_Y_AROMAS("velas_y_aromas", ARTICLES, HOME),
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
	
	public Group(LineaType linea, SublineaType sublinea, GroupType group) {
		this.linea = linea;
		this.sublinea = sublinea;
		this.group = group;
	}
	
	private static final String XPATH_GROUP = "//button[@data-testid[contains(.,'header.tabButton')]]";
	private static final String XPATH_GROUP_VIEW_MORE = "//button[@data-testid[contains(.,'viewMore')]]";

	private String getXPathGroup() {
		String xpath = XPATH_GROUP;
		if (group.getGroupResponse()==MORE) {
			xpath = XPATH_GROUP_VIEW_MORE;
		}
		return xpath + "//self::*[@data-testid[contains(.,'" + group.getId() + "')]]"; 
	}
	
	public void click() {
		clickLinea();
		clickGroup();
	}
	
	private void clickGroup() {
		click(getXPathGroup()).exec(); 
	}
	
	private void clickLinea() {
		SecLineasDevice.make(app).selectLinea(linea, sublinea);
	}
	
}

