package com.mng.robotest.tests.domains.menus.entity;

import java.util.List;

import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;

public class LineGroups {

	private final LineaType linea;
	private final List<GroupType> groups;
	
	public LineGroups(LineaType linea) {
		this.linea = linea;
		this.groups = GroupType.getGroups(linea);
	}
	
	public LineGroups(LineaType linea, List<GroupType> filter) {
		this.linea = linea;
	    this.groups = GroupType.getGroups(linea).stream()
	            .filter(filter::contains)
	            .toList();
	}

	public LineaType getLinea() {
		return linea;
	}

	public List<GroupType> getGroups() {
		return groups;
	}

}
