package com.mng.robotest.tests.domains.menus.pageobjects;

import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.GroupWebCurrent;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.GroupWebNew;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;

public abstract class GroupWeb extends PageBase {

	public abstract void click();
	public abstract void hover();
	public abstract boolean isPresent();
	public abstract boolean isVisibleSubMenus();
	
	protected final LineaType linea;
	protected final SublineaType sublinea;
	protected final GroupType group;
	
	public static GroupWeb make(GroupType group, Pais pais) {
		if (IsNewMenu.is(pais)) {
			return new GroupWebNew(group);
		}
		return new GroupWebCurrent(group);
	}
	public static GroupWeb make(LineaType linea, SublineaType sublinea, GroupType group, Pais pais) {
		if (IsNewMenu.is(pais)) {
			return new GroupWebNew(linea, sublinea, group);
		}
		return new GroupWebCurrent(linea, sublinea, group);
	}	
	
	protected GroupWeb(GroupType group) {
		this.linea = LineaType.SHE;
		this.sublinea = null;
		this.group = group; 
	}
	
	protected GroupWeb(LineaType linea, SublineaType sublinea, GroupType group) {
		this.linea = linea;
		this.sublinea = sublinea;
		this.group = group;
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

	public String getIdLinea() {
		if (sublinea!=null) {
			return sublinea.getId(AppEcom.shop);
		}
		if (isOutlet() && group==GroupType.COLECCIONES) {
			return linea.getSufixOutlet(channel);
		}
		return linea.getId2();
	}

}
