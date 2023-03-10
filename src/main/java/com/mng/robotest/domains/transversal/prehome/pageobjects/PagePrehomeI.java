package com.mng.robotest.domains.transversal.prehome.pageobjects;

public interface PagePrehomeI {

	abstract boolean isPage();
	abstract boolean isPageUntil(int seconds);	
	abstract boolean isPaisSelectedWithMarcaCompra();
	abstract void selecionPais();
	abstract void selecPaisIdiomaYAccede();
	abstract boolean isPaisSelected();
	abstract void selecionIdiomaAndEnter();
	abstract void accesoShopViaPrehome(boolean acceptCookies) throws Exception;
	abstract void previousAccessShopSteps(boolean acceptCookies) throws Exception;
}
