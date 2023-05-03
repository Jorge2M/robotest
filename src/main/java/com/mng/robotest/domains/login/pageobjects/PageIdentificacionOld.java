package com.mng.robotest.domains.login.pageobjects;

import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIdentificacionOld extends PageIdentificacion {
	
	private static final String XPATH_HAS_OLVIDADO_CONTRASENYA = "//span[text()[contains(.,'¿Has olvidado tu contraseña?')]]/../../a";
	private static final String XPATH_INPUT_USER = "//input[@id[contains(.,'userMail')]]";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id[contains(.,'chkPwd')]]";	
	private static final String XPATH_INICIAR_SESION = "//div[@class='submitContent']/input[@type='submit']";
	
	protected PageIdentificacionOld() {}	
	
	@Override
	public boolean isPage(int seconds) {
		return isVisibleInputUser(seconds);
	}
	
	@Override
	public void clickHasOlvidadoContrasenya() {
		click(XPATH_HAS_OLVIDADO_CONTRASENYA).exec();
	}
	
	@Override
	public void inputUserPassword(String usuario, String password) {
		try {
			getElement(XPATH_INPUT_USER).clear();
		}
		catch (Exception e) {
			Log4jTM.getLogger().error(e);
		} 
		getElement(XPATH_INPUT_USER).sendKeys(usuario);
		getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
	}

	@Override
	public void clickButtonEntrar() {
		click(XPATH_INICIAR_SESION).waitLoadPage(10).exec(); 
	}	
	
	private boolean isVisibleInputUser(int seconds) {
		return state(Visible, XPATH_INPUT_USER).wait(seconds).check();
	}

}
