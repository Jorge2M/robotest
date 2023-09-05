package com.mng.robotest.domains.transversal.acceso.navigations;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Clickable;
import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.CERRAR_SESION;
import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.INICIAR_SESION;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.login.pageobjects.PageLogin;
import com.mng.robotest.test.beans.AccesoVOTF;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.domains.transversal.acceso.pageobjects.PageAlertaVOTF;
import com.mng.robotest.domains.transversal.acceso.pageobjects.PageLoginVOTF;
import com.mng.robotest.domains.transversal.acceso.pageobjects.PageSelectIdiomaVOTF;
import com.mng.robotest.domains.transversal.acceso.pageobjects.PageSelectLineaVOTF;
import com.mng.robotest.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.domains.transversal.home.pageobjects.PageLanding;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterLogin;

public class AccesoFlows extends StepBase {

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
		if (app==AppEcom.votf) {
			accesoVOTF();
			goFromLineasToMultimarcaVOTF();
			var pagePrehome = new PagePrehome();
			pagePrehome.previousAccessShopSteps();
			pagePrehome.manageCookies(acceptCookies);			
		} else {
			new PagePrehomeSteps().seleccionPaisIdiomaAndEnter(false, acceptCookies);
		}
	}
	
	public void login(String user, String password) {
		clickIniciarSesionAndWait();
		var pageLogin = new PageLogin();
		pageLogin.isPage(5);
		pageLogin.inputUserPassword(user, password);
		pageLogin.clickButtonEntrar();
		new ModalCambioPais().closeModalIfVisible();
		new ModalActPoliticaPrivacidad().clickOkIfVisible();
		new ModalLoyaltyAfterLogin().closeModalIfVisible();
	}	
	
	private void clickIniciarSesionAndWait() {
		if (channel.isDevice()) {
			//En el caso de mobile nos tenemos que asegurar que están desplegados los menús
			SecCabecera secCabeceraDevice = new SecCabeceraMostFrequent();
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			
			// Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
			// En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
			boolean menuClicado = new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, Clickable);
			
			//Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		
		new MenusUserWrapper().moveAndClick(INICIAR_SESION);
	}	
	
	public void goFromLineasToMultimarcaVOTF() {
		var pageSelectLineaVOTF = new PageSelectLineaVOTF();
		pageSelectLineaVOTF.clickBanner(LineaType.SHE);
		pageSelectLineaVOTF.clickMenu(LineaType.SHE, 1);
		
		//Cuando se selecciona el icono de Mango deja de tener efecto el forzado del TestAB de la cabecera que habíamos ejecutado previamente
		new SecCabeceraMostFrequent().clickLogoMango();
	}
	
	public void accesoVOTF() throws Exception {
		var pageLoginVOTF = new PageLoginVOTF();
		pageLoginVOTF.goToFromUrlAndSetTestABs();
		new PageJCAS().identJCASifExists();
		AccesoVOTF accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(pais));
		pageLoginVOTF.inputUsuario(accesoVOTF.getUsuario());
		pageLoginVOTF.inputPassword(accesoVOTF.getPassword());
		pageLoginVOTF.clickButtonContinue();
		if (pais.getListIdiomas(app).size() > 1) {
			PageSelectIdiomaVOTF pageSelectIdiomaVOTF = new PageSelectIdiomaVOTF();
			pageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo());
			pageSelectIdiomaVOTF.clickButtonAceptar();
		}

		var pageAlertaVOTF = new PageAlertaVOTF();
		if (pageAlertaVOTF.isPage()) {
			pageAlertaVOTF.clickButtonContinuar();
		}
	}	
	
	public void cambioPaisFromHomeIfNeeded(Pais newPais, IdiomaPais newIdioma) {
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
