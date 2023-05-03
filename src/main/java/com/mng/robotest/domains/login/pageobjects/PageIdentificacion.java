package com.mng.robotest.domains.login.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Clickable;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.CERRAR_SESION;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;

public abstract class PageIdentificacion extends PageBase {
	
	private static final String AVISO_CREDENCIALES_KO = "Tu e-mail o contraseña no son correctos";
	private static final String XPATH_ERROR_CREDENCIALES_KO = "//*[text()[contains(.,'" + AVISO_CREDENCIALES_KO + "')]]";
	
	public abstract boolean isPage(int seconds);
	public abstract void inputUserPassword(String usuario, String password);
	public abstract void clickButtonEntrar();
	public abstract void clickHasOlvidadoContrasenya();	
	
	private static final String XPATH_TAB_REGISTRATE = "//p[@class[contains(.,'registerTab')]]";
	
	//TODO eliminar PageIdentificacionOld cuando se haya eliminado en PRO y Outlet (3-05-2023)
	public static PageIdentificacion make() {
		var pageOld = new PageIdentificacionOld();
		var pageNew = new PageIdentificacionNew();
		for (int seconds=0; seconds<3; seconds++) {
			if (pageOld.isPage(seconds)) {
				return pageOld;
			}
			if (pageNew.isPage(seconds)) {
				return pageNew;
			}
		}
		return pageOld;
	}
	
	public void clickTabRegistrate() {
		state(Clickable, XPATH_TAB_REGISTRATE).wait(5).check();
		click(XPATH_TAB_REGISTRATE).exec();
	}
	
	public void logoff() {
		new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, Clickable);
	}
	
	public boolean isErrorEmailoPasswordKO() {
		return state(Present, XPATH_ERROR_CREDENCIALES_KO).wait(1).check();
	}

}
