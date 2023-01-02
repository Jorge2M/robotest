package com.mng.robotest.domains.identification.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterLogin;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.*;

public class PageIdentificacion extends PageBase {
	
	private static final String AVISO_CREDENCIALES_KO = "Tu e-mail o contraseña no son correctos";
	private static final String XPATH_ERROR_CREDENCIALES_KO = "//div[@class='formErrors']//li[text()[contains(.,'" + AVISO_CREDENCIALES_KO + "')]]";
	private static final String XPATH_HAS_OLVIDADO_CONTRASENYA = "//span[text()[contains(.,'¿Has olvidado tu contraseña?')]]/../../a";
//	private static final String XPATH_INPUT_USER = "//input[@id[contains(.,'userMail')]]";
//	private static final String XPATH_INPUT_PASSWORD = "//input[@id[contains(.,'chkPwd')]]";
	private static final String XPATH_INPUT_USER = "//*[@data-testid='logon.login.emailInput']";
	private static final String XPATH_INPUT_PASSWORD = "//*[@data-testid='logon.login.passInput']";	
//	private static final String XPATH_SUBMIT_BUTTON = "//div[@class='submitContent']/input[@type='submit']";
	private static final String XPATH_INICIAR_SESION = "//*[@data-testid[contains(.,'loginButton')]]";	

	public boolean isVisibleUserUntil(int seconds) {
		return state(Visible, XPATH_INPUT_USER).wait(seconds).check();
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
		//waitMillis(250);
		getElement(XPATH_INPUT_USER).sendKeys(usuario);
		//waitMillis(250);
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}

	public void login(String user, String password) {
		iniciarSesion(user, password);
	}
	
	public void logoff() {
		new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, Clickable);
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
		click(XPATH_INICIAR_SESION).waitLoadPage(10).exec(); 
		if (isButtonEntrarVisible()) {
			click(XPATH_INICIAR_SESION).type(javascript).waitLoadPage(10).exec();
		}
	}
	
	public boolean isButtonEntrarVisible() {
		return state(Visible, XPATH_INICIAR_SESION).check();
	}

	public void clickIniciarSesionAndWait(Channel channel, AppEcom app) {
		if (channel.isDevice()) {
			//En el caso de mobile nos tenemos que asegurar que están desplegados los menús
			SecCabecera secCabeceraDevice = new SecCabeceraMostFrequent();
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			
			// Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
			// En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
			boolean menuClicado = new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, Clickable);
			
			//Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		
		new MenusUserWrapper().moveAndClick(INICIAR_SESION);
	}
	
	public boolean isErrorEmailoPasswordKO() {
		return state(Present, XPATH_ERROR_CREDENCIALES_KO).check();
	}

	public void clickHasOlvidadoContrasenya() {
		click(XPATH_HAS_OLVIDADO_CONTRASENYA).exec();
	}
}
