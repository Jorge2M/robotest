package com.mng.robotest.tests.domains.menus.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.LineaActions;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.LineaWebCurrent;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.LineaWebNew;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class LineaWeb extends PageBase implements LineaActions {

	protected final LineaType linea;
	protected final SublineaType sublinea;
	
	public static LineaWeb make(LineaType linea, Pais pais, AppEcom app) {
		if (pais.isNewmenu(app)) {
			return new LineaWebNew(linea);
		}
		return new LineaWebCurrent(linea);
	}
	public static LineaWeb make(LineaType linea, SublineaType sublinea, Pais pais, AppEcom app) {
		if (pais.isNewmenu(app)) {
			return new LineaWebNew(linea, sublinea);
		}
		return new LineaWebCurrent(linea, sublinea);
	}
	
	protected LineaWeb(LineaType linea) {
		this.linea = linea;
		this.sublinea = null;
	}
	protected LineaWeb(LineaType linea, SublineaType sublinea) {	
		this.linea = linea;
		this.sublinea = sublinea;
	}
	
	public LineaType getLinea() {
		return linea;
	}
	public SublineaType getSublinea() {
		return sublinea;
	}

}
