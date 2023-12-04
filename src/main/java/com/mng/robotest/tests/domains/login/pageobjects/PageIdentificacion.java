package com.mng.robotest.tests.domains.login.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.CERRAR_SESION;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;

public abstract class PageIdentificacion extends PageBase {
	
	private static final String AVISO_CREDENCIALES_KO = "Tu e-mail o contraseña no son correctos";
	private static final String XP_ERROR_CREDENCIALES_KO = "//*[text()[contains(.,'" + AVISO_CREDENCIALES_KO + "')]]";
	
	public abstract boolean isPage(int seconds);
	public abstract void inputUserPassword(String usuario, String password);
	public abstract void clickButtonEntrar();
	public abstract void clickHasOlvidadoContrasenya();	

	private static final String XP_TAB_REGISTRATE = "//*[@data-testid[contains(.,'registerTab.goToRegister')]]";
	
	public void clickTabRegistrate() {
		state(CLICKABLE, XP_TAB_REGISTRATE).wait(5).check();
		click(XP_TAB_REGISTRATE).setX(0).setY(10).exec();
	}
	
	public void logoff() {
		new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, CLICKABLE);
	}
	
	public boolean isErrorEmailoPasswordKO() {
		return state(PRESENT, XP_ERROR_CREDENCIALES_KO).wait(1).check();
	}

}
