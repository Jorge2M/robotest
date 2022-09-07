package com.mng.robotest.test.pageobject.shop.identificacion;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterLogin;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIdentificacion extends PageBase {
	
	private static final String AVISO_CREDENCIALES_KO = "Tu e-mail o contraseña no son correctos";
	private static final String XPATH_ERROR_CREDENCIALES_KO = "//div[@class='formErrors']//li[text()[contains(.,'" + AVISO_CREDENCIALES_KO + "')]]";
	private static final String XPATH_HAS_OLVIDADO_CONTRASENYA = "//span[text()[contains(.,'¿Has olvidado tu contraseña?')]]/../../a";
	private static final String XPATH_INPUT_USER = "//input[@id[contains(.,'userMail')]]";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id[contains(.,'chkPwd')]]";
	private static final String XPATH_SUBMIT_BUTTON = "//div[@class='submitContent']/input[@type='submit']";

	public boolean isVisibleUserUntil(int maxSeconds) {
		return state(Visible, XPATH_INPUT_USER).wait(maxSeconds).check();
	}

	public String getLiteralAvisiCredencialesKO() {
		return AVISO_CREDENCIALES_KO;
	}

	public void inputUserPassword(String usuario, String password) {
		try {
			getElement(XPATH_INPUT_USER).clear();
		}
		catch (Exception e) {
			Log4jTM.getLogger().error(e);
		}
		waitMillis(250);
		getElement(XPATH_INPUT_USER).sendKeys(usuario);
		waitMillis(250);
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}

	public void login(String user, String password) throws Exception {
		iniciarSesion(user, password);
	}
	
	public void logoff() throws Exception {
		SecMenusWrap secMenus = new SecMenusWrap();
		secMenus.closeSessionIfUserLogged();
	}

	
	public void iniciarSesion(String user, String password) {
		clickIniciarSesionAndWait(channel, app);
		isVisibleUserUntil(10);
		//normalizeLoginForDefeatAkamai(channel, app, driver);
		new PageIdentificacion().inputUserPassword(user, password);
		clickButtonEntrar();
		new ModalCambioPais().closeModalIfVisible();
		new ModalActPoliticaPrivacidad().clickOkIfVisible();
		new ModalLoyaltyAfterLogin().closeModalIfVisible();
	}	
	
	private void clickButtonEntrar() {
		click(XPATH_SUBMIT_BUTTON).waitLoadPage(10).exec(); 
		if (isButtonEntrarVisible()) {
			click(XPATH_SUBMIT_BUTTON).type(javascript).waitLoadPage(10).exec();
		}
	}
	
	public boolean isButtonEntrarVisible() {
		return state(Visible, XPATH_SUBMIT_BUTTON).check();
	}

	public void clickIniciarSesionAndWait(Channel channel, AppEcom app) {
		if (channel.isDevice()) {
			//En el caso de mobile nos tenemos que asegurar que están desplegados los menús
			SecCabecera secCabeceraDevice = SecCabecera.getNew(channel, app);
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			
			// Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
			// En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
			SecMenusWrap secMenus = new SecMenusWrap();
			boolean menuClicado = secMenus.getMenusUser().clickMenuIfInState(UserMenu.cerrarSesion, Clickable);
			
			//Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		
		new SecMenusWrap().getMenusUser().moveAndClick(UserMenu.iniciarSesion);
	}
	
	public boolean isErrorEmailoPasswordKO() {
		return state(Present, XPATH_ERROR_CREDENCIALES_KO).check();
	}

	public void clickHasOlvidadoContrasenya() {
		click(XPATH_HAS_OLVIDADO_CONTRASENYA).exec();
	}
}
