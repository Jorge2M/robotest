package com.mng.robotest.domains.transversal.browser;

public class LocalStorageMango extends LocalStorage {
	
	public void setInitialModalsOff() {
		//Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
		//del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
		setItemInLocalStorage("modalRegistroNewsletter", "0");
		setItemInLocalStorage("modalRegistroNewsletterImpacts", "0");
		setItemInLocalStorage("modalAdhesionLoyalty", "true");
		setItemInLocalStorage("modalSPShown", "1");
		setItemInLocalStorage("MangoShopModalIPConfirmed", "ES-es__2");
	}
	public void setInitialModalsOn() {
		clearLocalStorage();
	}
	
}
