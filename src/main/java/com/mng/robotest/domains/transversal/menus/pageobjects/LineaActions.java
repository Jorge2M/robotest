package com.mng.robotest.domains.transversal.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface LineaActions {

	public void clickLinea();
	public void clickSublinea();
	public void hoverLinea();
	public void hoverSublinea();
	public boolean isLineaSelected(int seconds);
	public boolean isSublineaSelected(int seconds);
	public boolean isLineaPresent(int seconds);
	public boolean isSublineaPresent(int seconds);

	public static LineaActions make(LineaWeb linea, Channel channel) {
		if (channel.isDevice()) {
			return new LineaActionsDevice(linea);
		}
		return new LineaActionsDesktop(linea);
	}
	
}
