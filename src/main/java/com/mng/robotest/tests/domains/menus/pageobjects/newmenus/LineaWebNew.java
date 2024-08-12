package com.mng.robotest.tests.domains.menus.pageobjects.newmenus;

import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb;

public class LineaWebNew extends LineaWeb {

	private final LineaActions lineaActions;

	public LineaWebNew(LineaType linea) {
		super(linea);
		this.lineaActions = LineaActions.make(this, channel);
	}
	public LineaWebNew(LineaType linea, SublineaType sublinea) {
		super(linea, sublinea);
		this.lineaActions = LineaActions.make(this, channel);
	}
	
	@Override
	public void clickLinea() {
		lineaActions.clickLinea();
//		if (!lineaActions.isLineaSelected(1)) {
//			lineaActions.clickLinea();
//		}
	}
	@Override
	public void clickSublinea() {
		lineaActions.clickSublinea();
		if (!lineaActions.isSublineaSelected(1)) {
			lineaActions.clickSublinea();
		}
	}
	
	@Override
	public void hoverLinea() {
		lineaActions.hoverLinea();
		lineaActions.hoverLinea();
		if (!isLineaAvailable(1)) {
			lineaActions.hoverLinea();
		}
	}
	
	@Override
	public void hoverSublinea() {
		for (int i=0; i<3; i++) {
			lineaActions.hoverSublinea();
			if (lineaActions.isSublineaSelected(1)) {
				break;
			}
		}
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return lineaActions.isLineaPresent(seconds);
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return lineaActions.isSublineaPresent(seconds);
	}
	
	@Override
	public boolean isLineaSelected(int seconds) {
		return lineaActions.isLineaSelected(seconds);
	}
	
	@Override
	public boolean isSublineaSelected(int seconds) {
		return lineaActions.isSublineaSelected(seconds);
	}
	
	public LineaType getLinea() {
		return linea;
	}
	public SublineaType getSublinea() {
		return sublinea;
	}
	
	private boolean isLineaAvailable(int seconds) {
		if (channel.isDevice()) {
			return lineaActions.isLineaPresent(seconds);
		}
		return lineaActions.isLineaSelected(seconds);
	}
	
}
