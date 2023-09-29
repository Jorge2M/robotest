package com.mng.robotest.tests.domains.login.pageobjects;

import com.mng.robotest.tests.domains.bolsa.pageobjects.PageIniciarSesionBolsaMobile;

public class PageLogin extends PageIdentificacion {

	private PageIniciarSesionBolsaMobile pageIniciarSesionBolsaMobile = new PageIniciarSesionBolsaMobile();
	
	@Override
	public boolean isPage(int seconds) {
		return pageIniciarSesionBolsaMobile.isPage(seconds);
	}

	@Override
	public void inputUserPassword(String usuario, String password) {
		pageIniciarSesionBolsaMobile.inputCredentials(usuario, password);
	}
	
	@Override
	public void clickButtonEntrar() {
		pageIniciarSesionBolsaMobile.clickIniciarSesion();
	}
	
	@Override
	public void clickHasOlvidadoContrasenya() {
		pageIniciarSesionBolsaMobile.clickHasOlvidadoContrasenya(); 
	}
	
}
