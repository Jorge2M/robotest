package com.mng.robotest.test.pageobject.shop.menus.device;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;

public interface SecLineasDevice {

	public String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException;
	public String getXPathSublineaNinosLink(SublineaType sublineaType);
	public void selectLinea(LineaType lineaType);
	public void selectLinea(LineaType lineaType, SublineaType sublineaType);
	public void selecSublineaNinosIfNotSelected(LineaType lineaType, SublineaType sublineaType);
	public boolean isSelectedLinea(LineaType lineaType);
	public boolean isVisibleBlockSublineasNinos(LineaType lineaNinosType);
	public boolean isLineaPresent(LineaType lineaType);
	
	public static SecLineasDevice make(AppEcom app) {
		if (app==AppEcom.outlet) {
			return new SecLineasDeviceOutlet();
		}
		return new SecLineasDeviceShop();
	}
	
	
}
