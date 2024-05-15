package com.mng.robotest.tests.domains.login.pageobjects;

import com.mng.robotest.tests.domains.bolsa.pageobjects.PageLoginGenesis;

public class PageLogin extends PageIdentificacion {

	private PageLoginGenesis pLoginGenesis = new PageLoginGenesis();
	
	@Override
	public boolean isPage(int seconds) {
		return pLoginGenesis.isPage(seconds);
	}

	@Override
	public void inputUserPassword(String usuario, String password) {
		pLoginGenesis.inputCredentials(usuario, password);
	}
	
	@Override
	public void clickButtonEntrar() {
		pLoginGenesis.clickIniciarSesion();
	}
	
	@Override
	public void clickButtonCrearCuenta() {
		pLoginGenesis.clickCrearCuenta();
	}
	
	@Override
	public void clickHasOlvidadoContrasenya() {
		pLoginGenesis.clickHasOlvidadoContrasenya(); 
	}
	
}
