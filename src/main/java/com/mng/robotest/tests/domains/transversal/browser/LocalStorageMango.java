package com.mng.robotest.tests.domains.transversal.browser;

public class LocalStorageMango extends LocalStorage {
	
	public void setInitialModalsOff() {
		//Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
		//del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
		setItemInLocalStorage("modalRegistroNewsletter", "0");
		setItemInLocalStorage("modalRegistroNewsletterImpacts", "0");
		setItemInLocalStorage("modalAdhesionLoyalty", "true");
		setItemInLocalStorage("modalSPShown", "1");
		setItemInLocalStorage("MangoShopModalIPConfirmed", "ES-es__2");
		
		String codigoPais = dataTest.getPais().getCodigoAlf();
		String codigoIdioma = dataTest.getIdioma().getCodigo().name().toLowerCase();
		setItemInLocalStorage("userCountrySelected", "\"" + codigoPais + "\""); //Avoid change country modal
		//setItemInLocalStorage("userData", "{\"countryISO\":\"ch\",\"languageISO\":\"fr\",\"brandID\":\"outlet\"}");
		setItemInLocalStorage("userData", "{\"countryISO\":\"" + codigoPais.toLowerCase() + "\",\"languageISO\":\"" + codigoIdioma + "\"}");
	}
	
	public void setInitialModalsOn() {
		clearLocalStorage();
	}
	
}
