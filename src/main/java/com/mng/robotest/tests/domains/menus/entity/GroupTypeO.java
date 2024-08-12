package com.mng.robotest.tests.domains.menus.entity;

import static com.mng.robotest.tests.domains.menus.entity.GroupResponse.*;
import static com.mng.robotest.tests.domains.menus.entity.LineaType.*;

import java.util.Arrays;
import java.util.List;

public class GroupTypeO {

	private static final List<LineaType> ALL_LINES = Arrays.asList(SHE, HE, TEEN, NINA, NINO, HOME);
	private static final List<LineaType> ALL_EXCEPT_HOME = Arrays.asList(SHE, HE, TEEN, NINA, NINO); 
	private static final List<LineaType> SHE_LINE = Arrays.asList(SHE);
	private static final List<LineaType> HE_LINE = Arrays.asList(HE);
	private static final List<LineaType> HOME_LINE = Arrays.asList(HOME);
	
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
	//		return Arrays.asList(COLECCIONES);
	//		return Arrays.asList(ACCESORIOS);
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
		public boolean isInLinea(LineaType linea) {
			return getLineas().contains(linea);
		}
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
	
}
