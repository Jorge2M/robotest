package com.mng.robotest.test.stpv.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.Linea.LineaType;
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
import com.mng.robotest.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Login de Manto
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
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
		PagePrehome pagePrehome = new PagePrehome(dCtxSh, driver);
		if (dCtxSh.appE==AppEcom.votf) {
			accesoVOTF(dCtxSh, driver);
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
		SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
	}
	
	/**
	 * Acceso a VOTF (login + selección de idioma)
	 */
	public static void accesoVOTF(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		PageLoginVOTF pageLoginVOTF = new PageLoginVOTF(driver);
		pageLoginVOTF.goToFromUrlAndSetTestABs(dCtxSh);
		PageJCAS.identJCASifExists(driver);
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(dCtxSh.pais));
		pageLoginVOTF.inputUsuario(accesoVOTF.getUsuario());
		pageLoginVOTF.inputPassword(accesoVOTF.getPassword());
		pageLoginVOTF.clickButtonContinue();
		if (dCtxSh.pais.getListIdiomas().size() > 1) {
			PageSelectIdiomaVOTF pageSelectIdiomaVOTF = new PageSelectIdiomaVOTF(driver);
			pageSelectIdiomaVOTF.selectIdioma(dCtxSh.idioma.getCodigo());
			pageSelectIdiomaVOTF.clickButtonAceptar();
		}

		PageAlertaVOTF pageAlertaVOTF = new PageAlertaVOTF(driver);
		if (pageAlertaVOTF.isPage()) {
			pageAlertaVOTF.clickButtonContinuar();
		}
	}	
	
	public static void cambioPaisFromHomeIfNeeded(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		String codigoPais = (new PageLanding(driver)).getCodigoPais();
		if (dCtxSh.pais.getCodigo_pais().compareTo(codigoPais)!=0) {
			cambioPais(dCtxSh, driver);
		}
	}
	
	public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		if (dCtxSh.channel.isDevice() && dCtxSh.appE==AppEcom.outlet) {
			SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
			secMenusStpV.getMenusUser().cambioPaisMobil(dCtxSh);
		} else {
			(new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
		}
	}
		
}
