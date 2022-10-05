//package com.mng.robotest.test.pageobject.shop.menus;
//
//import java.util.List;
//
//import com.github.jorge2m.testmaker.conf.Channel;
//import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
//import com.mng.robotest.conftestmaker.AppEcom;
//import com.mng.robotest.domains.transversal.PageBase;
//import com.mng.robotest.test.beans.Linea;
//import com.mng.robotest.test.beans.Pais;
//import com.mng.robotest.test.beans.Sublinea;
//import com.mng.robotest.domains.transversal.menus.pageobjects.LineaActionsDesktop;
//import com.mng.robotest.domains.transversal.menus.pageobjects.LineaActionsDesktop;
//import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
//import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
//import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
//import com.mng.robotest.test.pageobject.shop.menus.device.SecMenuLateralDevice;
//import com.mng.robotest.test.pageobject.shop.menus.device.SecMenuLateralDevice.TypeLocator;
//import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;
//
//public class SecMenusWrap extends PageBase {
//	
//	private final MenusUserWrapper secMenusUser = new MenusUserWrapper();
//	private final SecMenuLateralDevice secMenuLateralDevice = new SecMenuLateralDevice();
//	
//	public enum GroupMenu { prendas, accesorios, colecciones }
//	
//	public MenusUserWrapper getMenusUser() {
//		return this.secMenusUser;
//	}
//	
//	public boolean isLineaPresent(LineaType lineaType) {
//		if (channel.isDevice()) {
//			return secMenuLateralDevice.getSecLineasDevice().isLineaPresent(lineaType);
//		}
//		return new SecLineasMenuDesktopNew().isLineaPresent(lineaType);
//	}
//	
//	public boolean isLineaPresentUntil(LineaType lineaType, int seconds) {
//		if (channel.isDevice()) {
//			return secMenuLateralDevice.getSecLineasDevice().isLineaPresent(lineaType);
//		}
//		return new SecLineasMenuDesktopNew().isLineaPresentUntil(lineaType, seconds);
//	}	
//	
//	public LineaType getLineaResultAfterClickMenu(LineaType lineaType, String nombre) { 
//		switch (nombre) {
//		case "rebajas":
////		case "-rebajas":			
////			if (lineaType==LineaType.she)
////				return LineaType.rebajas;
////			break;
//		default:
//			break;
//		}
//		
//		return lineaType;
//	}
//
//	public void closeSessionIfUserLogged() throws Exception {
//		secMenusUser.clickMenuIfInState(UserMenu.CERRAR_SESION, State.Clickable);
//	}
//
//	public List<DataScreenMenu> getListDataScreenMenus(Linea linea, SublineaType sublineaType) throws Exception {
//		if (channel.isDevice()) {
//			return secMenuLateralDevice.getListDataScreenMenus(linea, sublineaType);
//		}
//		return secMenusDesktop
//				.secMenuSuperior
//				.secBlockMenus
//				.getListDataScreenMenus(linea.getType(), sublineaType);
//	}
//
//	/**
//	 * @return codificación que se acostumbra a utilizar para identificar la línea en el DOM
//	 */
//	public String getLineaDOM(LineaType lineaType) {
//		return (getIdLineaEnDOM(channel, app, lineaType));
//	}
//	
//	/**
//	 * @return el id con el que se identifica la línea a nivel del DOM-HTML
//	 */
//	public static String getIdLineaEnDOM(Channel channel, AppEcom app, LineaType lineaShop) {
//		if (app==AppEcom.outlet) {
//			return lineaShop.getSufixOutlet(channel);
//		}
//		return lineaShop.name(app);
//	}
//	
//	public void selecLinea(Pais pais, LineaType lineaType) {
//		if (channel.isDevice()) {
//			secMenuLateralDevice.getSecLineasDevice().selectLinea(pais.getShoponline().getLinea(lineaType));
//		} else {
//			new SecLineasMenuDesktopNew().click(pais, lineaType);
//		}
//	}
//	
//	public void selecSublinea(Pais pais, LineaType lineaType, SublineaType sublineaType) throws Exception {
//		if (channel.isDevice()) {
//			secMenuLateralDevice.getSecLineasDevice().selectLinea(pais.getShoponline().getLinea(lineaType), sublineaType);
//		} else {
//			new SecLineasMenuDesktopNew().selectSublinea(lineaType, sublineaType);
//		}
//	}	
//	
//	public boolean canClickMenuArticles(Pais paisI, Linea linea, Sublinea sublinea) {
//		if (paisI.isVentaOnline()) {
//			if (sublinea==null) {
//				return (linea.getMenusart().compareTo("s")==0);
//			}
//			return sublinea.getMenusart().compareTo("s")==0;
//		}
//		
//		return false;
//	}
//}
