package com.mng.robotest.test.pageobject.shop.menus;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Sublinea;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenuLateralDevice.TypeLocator;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;

public class SecMenusWrap extends PageBase {
	
	private final MenusUserWrapper secMenusUser = MenusUserWrapper.getNew(channel, app);
	private final SecMenuLateralDevice secMenuLateralDevice = new SecMenuLateralDevice(channel, app);
	private final SecMenusDesktop secMenusDesktop = new SecMenusDesktop(app, channel);
	
	public enum GroupMenu { prendas, accesorios, colecciones }
	
	public MenusUserWrapper getMenusUser() {
		return this.secMenusUser;
	}
	
	public boolean isLineaPresent(LineaType lineaType) {
		if (channel.isDevice()) {
			return secMenuLateralDevice.getSecLineasDevice().isLineaPresent(lineaType);
		}
		return secMenusDesktop.secMenuSuperior.secLineas.isLineaPresent(lineaType);
	}
	
	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		if (channel.isDevice()) {
			return secMenuLateralDevice.getSecLineasDevice().isLineaPresent(lineaType);
		}
		return secMenusDesktop.secMenuSuperior.secLineas.isLineaPresentUntil(lineaType, maxSeconds);
	}	
	
	/**
	 * @return la línea a la que se debería acceder cuando se selecciona el menú
	 */
	public static LineaType getLineaResultAfterClickMenu(LineaType lineaType, String nombre) { 
		switch (nombre) {
		case "rebajas":
//		case "-rebajas":			
//			if (lineaType==LineaType.she)
//				return LineaType.rebajas;
//			break;
		default:
			break;
		}
		
		return lineaType;
	}

	public void closeSessionIfUserLogged() throws Exception {
		secMenusUser.clickMenuIfInState(UserMenu.cerrarSesion, State.Clickable);
	}

	public List<DataScreenMenu> getListDataScreenMenus(Linea linea, SublineaType sublineaType) throws Exception {
		if (channel.isDevice()) {
			return secMenuLateralDevice.getListDataScreenMenus(linea, sublineaType);
		}
		return secMenusDesktop
				.secMenuSuperior
				.secBlockMenus
				.getListDataScreenMenus(linea.getType(), sublineaType);
	}

	/**
	 * @return codificación que se acostumbra a utilizar para identificar la línea en el DOM
	 */
	public String getLineaDOM(LineaType lineaType) {
		return (getIdLineaEnDOM(channel, app, lineaType));
	}
	
	/**
	 * @return el id con el que se identifica la línea a nivel del DOM-HTML
	 */
	public static String getIdLineaEnDOM(Channel channel, AppEcom app, LineaType lineaShop) {
		if (app==AppEcom.outlet) {
			return lineaShop.getSufixOutlet(channel);
		}
		return lineaShop.name(app);
	}
	
	public void selecLinea(Pais pais, LineaType lineaType) {
		if (channel.isDevice()) {
			secMenuLateralDevice.getSecLineasDevice().selectLinea(pais.getShoponline().getLinea(lineaType));
		} else {
			secMenusDesktop.secMenuSuperior.secLineas.selecLinea(pais, lineaType);
		}
	}
	
	public void selecSublinea(Pais pais, LineaType lineaType, SublineaType sublineaType) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralDevice.getSecLineasDevice().selectLinea(pais.getShoponline().getLinea(lineaType), sublineaType);
		} else {
			secMenusDesktop.secMenuSuperior.secLineas.selectSublinea(lineaType, sublineaType);
		}
	}	
	
	/**
	 * Selecciona una entrada de menú. Soporta Desktop y Móvil
	 *  Desktop: selecciona una entrada del menú superior
	 *  Móvil:   selecciona una entrada del menú lateral
	 */
	public void clickMenu1erNivel(Pais pais, Menu1rstLevel menu1rstLevel) throws Exception {
		if (channel==Channel.desktop) {
			secMenusDesktop.secMenuSuperior.secBlockMenus.gotoAndClickMenu(menu1rstLevel);
		} else {
			secMenuLateralDevice.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais);
		}
	}
	
	/**
	 * Función que selecciona una determinada línea->menú (lo busca en el href como último elemento del path)
	 */
	public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralDevice.clickMenuLateral1rstLevel(TypeLocator.hrefPortion, menu1rstLevel, pais);
		} else {
			secMenusDesktop.secMenuSuperior.secBlockMenus.seleccionarMenuXHref(menu1rstLevel);
		}
	}	
	
	public boolean canClickMenuArticles(Pais paisI, Linea linea, Sublinea sublinea) {
		if (paisI.isVentaOnline()) {
			if (sublinea==null) {
				return (linea.getMenusart().compareTo("s")==0);
			}
			return sublinea.getMenusart().compareTo("s")==0;
		}
		
		return false;
	}
}

