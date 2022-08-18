package com.mng.robotest.test.steps.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PageJCAS;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test.pageobject.votf.PageLoginVOTF;
import com.mng.robotest.test.pageobject.votf.PageSelectIdiomaVOTF;
import com.mng.robotest.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class AccesoNavigations {

	public static void goToInitURL(WebDriver driver) {
		String canary = "";
		//Temporal para test Canary!!!
		//canary = "?canary=true";
		String urlInitial = TestMaker.getInputParamsSuite().getUrlBase() + canary;
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.compareTo(urlInitial)!=0) {
			driver.get(urlInitial);
		}
	}
	
	public static void accesoHomeAppWeb(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		accesoHomeAppWeb(dCtxSh, true, driver); 
	}
	
	/**
	/* Acceso a la página inicial (home) de la APP Web (shop o VOTF)
	 */
	public static void accesoHomeAppWeb(DataCtxShop dCtxSh, boolean acceptCookies, WebDriver driver) 
	throws Exception {
		PagePrehome pagePrehome = new PagePrehome(dCtxSh.pais, dCtxSh.idioma);
		if (dCtxSh.appE==AppEcom.votf) {
			accesoVOTF(dCtxSh.pais, dCtxSh.idioma);
			goFromLineasToMultimarcaVOTF(dCtxSh, driver);
			pagePrehome.previousAccessShopSteps(acceptCookies);
		} else {
			pagePrehome.accesoShopViaPrehome(acceptCookies);
		}
	}
	
	public static void goFromLineasToMultimarcaVOTF(DataCtxShop dCtxSh, WebDriver driver) {
		PageSelectLineaVOTF pageSelectLineaVOTF = new PageSelectLineaVOTF(driver);
		pageSelectLineaVOTF.clickBanner(LineaType.she);
		pageSelectLineaVOTF.clickMenu(LineaType.she, 1);
		
		//Cuando se selecciona el icono de Mango deja de tener efecto el forzado del TestAB de la cabecera que habíamos ejecutado previamente
		SecCabecera.getNew(Channel.desktop, AppEcom.votf).clickLogoMango();
	}
	
	public static void accesoVOTF(Pais pais, IdiomaPais idioma) throws Exception {
		PageLoginVOTF pageLoginVOTF = new PageLoginVOTF();
		pageLoginVOTF.goToFromUrlAndSetTestABs();
		new PageJCAS().identJCASifExists();
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(pais));
		pageLoginVOTF.inputUsuario(accesoVOTF.getUsuario());
		pageLoginVOTF.inputPassword(accesoVOTF.getPassword());
		pageLoginVOTF.clickButtonContinue();
		if (pais.getListIdiomas().size() > 1) {
			PageSelectIdiomaVOTF pageSelectIdiomaVOTF = new PageSelectIdiomaVOTF();
			pageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo());
			pageSelectIdiomaVOTF.clickButtonAceptar();
		}

		PageAlertaVOTF pageAlertaVOTF = new PageAlertaVOTF();
		if (pageAlertaVOTF.isPage()) {
			pageAlertaVOTF.clickButtonContinuar();
		}
	}	
	
	public static void cambioPaisFromHomeIfNeeded(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		String codigoPais = (new PageLanding()).getCodigoPais();
		if (dCtxSh.pais.getCodigo_pais().compareTo(codigoPais)!=0) {
			cambioPais(dCtxSh, driver);
		}
	}
	
	public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		if (dCtxSh.channel.isDevice() && dCtxSh.appE==AppEcom.outlet) {
			SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
			secMenusSteps.getMenusUser().cambioPaisMobil(dCtxSh);
		} else {
			(new SecFooterSteps(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
		}
	}
		
}
