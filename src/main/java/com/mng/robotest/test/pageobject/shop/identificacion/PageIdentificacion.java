package com.mng.robotest.test.pageobject.shop.identificacion;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
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
		return (state(Visible, By.xpath(XPATH_INPUT_USER)).wait(maxSeconds).check());
	}

	public String getLiteralAvisiCredencialesKO() {
		return AVISO_CREDENCIALES_KO;
	}

	public void inputUserPassword(String usuario, String password) {
		By byInput = By.xpath(XPATH_INPUT_USER);
		try {
			driver.findElement(byInput).clear();
		}
		catch (Exception e) {
			Log4jTM.getLogger().error(e);
		}
		waitMillis(250);
		driver.findElement(byInput).sendKeys(usuario);
		waitMillis(250);
		driver.findElement(By.xpath(XPATH_INPUT_PASSWORD)).sendKeys(password);
	}

	public void loginOrLogoff(DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.userRegistered) {
			iniciarSesion(dCtxSh);
		} else {
			SecMenusWrap secMenus = new SecMenusWrap(dCtxSh.channel, dCtxSh.appE);
			secMenus.closeSessionIfUserLogged();
		}
	}
	
	public void iniciarSesion(DataCtxShop dCtxSh) throws Exception {
		iniciarSesion(dCtxSh.userConnected, dCtxSh.passwordUser, dCtxSh.channel, dCtxSh.appE);
	}
	 
	public void iniciarSesion(String user, String password, Channel channel, AppEcom app) {
		clickIniciarSesionAndWait(channel, app);
		isVisibleUserUntil(10);
		//normalizeLoginForDefeatAkamai(channel, app, driver);
		new PageIdentificacion().inputUserPassword(user, password);
		clickButtonEntrar();
		new ModalCambioPais().closeModalIfVisible();
		new ModalActPoliticaPrivacidad().clickOkIfVisible();
		ModalLoyaltyAfterLogin.closeModalIfVisible(driver);
	}	
	
	private void clickButtonEntrar() {
		click(By.xpath(XPATH_SUBMIT_BUTTON)).waitLoadPage(10).exec(); 
		if (isButtonEntrarVisible()) {
			click(By.xpath(XPATH_SUBMIT_BUTTON)).type(javascript).waitLoadPage(10).exec();
		}
	}
	
	public boolean isButtonEntrarVisible() {
		return (state(Visible, By.xpath(XPATH_SUBMIT_BUTTON), driver).check());
	}

	/**
	 * Seleccionamos el link "Iniciar Sesión" y esperamos a que los campos de input estén disponibles
	 */
	@SuppressWarnings("static-access")
	public void clickIniciarSesionAndWait(Channel channel, AppEcom app) {
		if (channel.isDevice()) {
			//En el caso de mobile nos tenemos que asegurar que están desplegados los menús
			SecCabecera secCabeceraDevice = SecCabecera.getNew(channel, app);
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			
			// Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
			// En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
			SecMenusWrap secMenus = new SecMenusWrap(channel, app);
			boolean menuClicado = secMenus.getMenusUser().clickMenuIfInState(UserMenu.cerrarSesion, Clickable);
			
			//Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		
		SecMenusWrap secMenus = new SecMenusWrap(channel, app);
		secMenus.getMenusUser().moveAndClick(UserMenu.iniciarSesion);
	}
	
	public boolean isErrorEmailoPasswordKO() {
		return (state(Present, By.xpath(XPATH_ERROR_CREDENCIALES_KO)).check());
	}

	public void clickHasOlvidadoContrasenya() {
		click(By.xpath(XPATH_HAS_OLVIDADO_CONTRASENYA)).exec();
	}
}
