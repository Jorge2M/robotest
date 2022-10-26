package com.mng.robotest.test.steps.navigations.shop;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PageJCAS;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test.pageobject.votf.PageLoginVOTF;
import com.mng.robotest.test.pageobject.votf.PageSelectIdiomaVOTF;
import com.mng.robotest.test.pageobject.votf.PageSelectLineaVOTF;

public class AccesoNavigations extends StepBase {

	private final Pais pais = dataTest.getPais();
	private final IdiomaPais idioma = dataTest.getIdioma();
	
	public void goToInitURL() {
		String canary = "";
		//Temporal para test Canary!!!
		//canary = "?canary=true";
		String urlInitial = TestMaker.getInputParamsSuite().getUrlBase() + canary;
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.compareTo(urlInitial)!=0) {
			driver.get(urlInitial);
		}
	}
	
	public void accesoHomeAppWeb() throws Exception {
		accesoHomeAppWeb(true); 
	}
	
	public void accesoHomeAppWeb(boolean acceptCookies) throws Exception {
		PagePrehome pagePrehome = new PagePrehome();
		if (app==AppEcom.votf) {
			accesoVOTF();
			goFromLineasToMultimarcaVOTF();
			pagePrehome.previousAccessShopSteps(acceptCookies);
		} else {
			pagePrehome.accesoShopViaPrehome(acceptCookies);
		}
	}
	
	public void goFromLineasToMultimarcaVOTF() {
		PageSelectLineaVOTF pageSelectLineaVOTF = new PageSelectLineaVOTF();
		pageSelectLineaVOTF.clickBanner(LineaType.she);
		pageSelectLineaVOTF.clickMenu(LineaType.she, 1);
		
		//Cuando se selecciona el icono de Mango deja de tener efecto el forzado del TestAB de la cabecera que habíamos ejecutado previamente
		SecCabecera.getNew(channel, AppEcom.votf).clickLogoMango();
	}
	
	public void accesoVOTF() throws Exception {
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
	
	public void cambioPaisFromHomeIfNeeded(Pais newPais, IdiomaPais newIdioma) throws Exception {
		String codigoPaisActual = (new PageLanding()).getCodigoPais();
		if (newPais.getCodigo_pais().compareTo(codigoPaisActual)!=0) {
			cambioPais(newPais, newIdioma);
		}
	}
	
	public void cambioPais(Pais newPais, IdiomaPais newIdioma) {
		if (channel.isDevice() && app==AppEcom.outlet) {
			new SecMenusUserSteps().cambioPaisMobil(newPais, newIdioma);
		} else {
			new SecFooterSteps().cambioPais(newPais, newIdioma);
		}
	}
		
}
