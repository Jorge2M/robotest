package com.mng.robotest.test.steps.shop.menus;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroIniOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;
import com.mng.robotest.test.steps.shop.modales.ModalCambioPaisSteps;

public class SecMenusUserSteps extends StepBase {
	
	private final MenusUserWrapper userMenus = new SecMenusWrap().getMenusUser();
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
		expected="Aparece la página de gestión de favoritos con los artículos correctos")
	public void selectFavoritos(DataFavoritos dataFavoritos) throws Exception {
		userMenus.clickMenuAndWait(UserMenu.favoritos);
		PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
		pageFavoritosSteps.validaIsPageOK(dataFavoritos);
	}

	@Step (
		description="Seleccionar el menú de usuario \"Regístrate\" y la pestaña \"Regístrate\"", 
		expected="Aparece al página inicial del registro",
		saveHtmlPage=SaveWhen.Always)
	public void selectRegistrate(DataCtxShop dCtxSh) throws Exception {
		userMenus.clickMenuAndWait(UserMenu.registrate);
		PageRegistroIniOutlet pageRegistroIni = new PageRegistroIniOutlet();  
		pageRegistroIni.clickRegisterTab();
		
		if (app==AppEcom.outlet) {
			PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
			pageRegistroIniSteps.validaIsPageUntil(5);
			pageRegistroIniSteps.validaIsRGPDVisible(dCtxSh.pais);
		} else {
			PageRegistroInitialShopSteps pageRegistroIniSteps = new PageRegistroInitialShopSteps();
			pageRegistroIniSteps.checkIsPageUntil(5);
		}

	}

	@Step (
		description="Clicar el link de Cerrar Sesión", 
		expected="Aparece el link de login")
	public void logoff() throws Exception {
		userMenus.clickMenuAndWait(UserMenu.cerrarSesion);
		checkIsVisibleIniciarSesionLink(3);
	}
	
	@Validation (
		description="Aparece el link superior de \"Iniciar sesión\" (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleIniciarSesionLink(int maxSeconds) throws Exception {
		return (userMenus.isMenuInStateUntil(UserMenu.iniciarSesion, Present, maxSeconds));
	}
	
	public void logoffLogin(String userConnect, String userPassword) throws Exception {
		logoff();
		identification(userConnect, userPassword);
	}
	
	@Step (
		description="Identificarse con los datos del registro (#{userConnect})", 
		expected="La nueva identificación es correcta")
	public void identification(String userConnect, String userPassword) throws Exception {
		new PageIdentificacion().iniciarSesion(userConnect, userPassword);
		checkIsVisibleLinkCerrarSesion();
		GenericChecks.checkDefault(driver);
	}
	
	@Validation (
		description="Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)",
		level=State.Defect)
	public boolean checkIsVisibleLinkCerrarSesion() {	
		return isVisibleLinkCerrarSesion();
	}
	
	@Validation (
		description=
			"Aparece el link superior de \"Cerrar Sesión\" (estamos loginados). " + 
			"Lo esperamos hasta #{seconds} segundos",
		level=State.Defect)	
	public boolean checkIsVisibleLinkCerrarSesionUntil(int seconds) {
		if (isVisibleLinkCerrarSesionExceptionSafe()) {
			return true;
		}
		for (int i=0; i<seconds;i++) {
			if (isVisibleLinkCerrarSesionExceptionSafe()) {
				return true;
			}
			PageBase.waitMillis(1000);
		}
		return false;
	}
	private boolean isVisibleLinkCerrarSesionExceptionSafe() {
		try {
			return isVisibleLinkCerrarSesion();
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	private boolean isVisibleLinkCerrarSesion() {
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		return (userMenus.isMenuInStateUntil(UserMenu.cerrarSesion, Present, 1));
	}

	@Step (
		description="Seleccionar el link \"Mi cuenta\"", 
		expected="Aparece la página de \"Mi cuenta\"")
	public void clickMenuMiCuenta() {
		userMenus.clickMenuAndWait(UserMenu.miCuenta);	
		new PageMiCuentaSteps().validateIsPage(2);
		GenericChecks.checkDefault(driver);
	}
	
	@Step (
		description="Se selecciona el menú para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	public void cambioPaisMobil(DataCtxShop dCtxSh) throws Exception {
		userMenus.clickMenuAndWait(UserMenu.cambioPais);
		ModalCambioPaisSteps.validateIsVisible(5, driver); 
		ModalCambioPaisSteps.cambioPais(dCtxSh, driver);
	}

	private static final String TagPoints = "@TagPoints";
	@Step (
		description=
			"Seleccionar el link \"Mango Likes You\"<br>" + 
			"<b>info</b>: el usuario tiene " + TagPoints + " puntos", 
		expected="Aparece la página de \"Mi cuenta\"")
	public int clickMenuMangoLikesYou() throws Exception {
		userMenus.clickMenuAndWait(UserMenu.mangoLikesYou);
		PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps();
		int numberPoints = pageHomeLikesSteps.checkIsPageOk().getNumberPoints();
		
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TagPoints, String.valueOf(numberPoints));
		return (numberPoints);
	}

	@Validation
	public ChecksTM checkVisibilityLinkMangoLikesYou() throws Exception {	
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (channel==Channel.desktop && app==AppEcom.shop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		boolean visibilityMLY = userMenus.isMenuInStateUntil(UserMenu.mangoLikesYou, Present, 1);
		switch (app) {
		case shop:
			checks.add(
				"Sí aparece el link de \"Mango Likes You\" en el menú de usuario",
				visibilityMLY, State.Defect);
			break;
		default:
			checks.add(
				"No aparece el link de \"Mango Likes You\" en el menú de usuario",
				!visibilityMLY, State.Defect);
		}
		
		return checks;  
	}

	@Validation
	public ChecksResultWithNumberPoints checkAngGetLoyaltyPoints(int maxSeconds) throws Exception {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		LoyaltyData loyaltyData = userMenus.checkAndGetLoyaltyPointsUntil(maxSeconds);
		checks.setNumberPoints(loyaltyData.numberPoints);
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario (lo esperamos hasta " + maxSeconds + " segundos)",
			loyaltyData.isPresent, State.Defect);
	 	
		return checks;
	}
	
	@Validation
	public ChecksTM checkLoyaltyPoints(int initPoints, int donatedPoints, int finalPoints) 
	throws Exception {
		ChecksTM checks = ChecksTM.getNew();
 		int loyaltyPointsExpected = initPoints - donatedPoints;
 		
 		//TODO hemos de Defect a Warning porque en PRE no funciona esta operativa desde hace tiempo
 		//si en algún momento vuelve a funcionar habrá que poner Defect de nuevo 
	 	checks.add(
			"Los Loyalty Points que figuran ahora en la web (<b>" + finalPoints + "</b>) " + 
			"coinciden con los <b>" + loyaltyPointsExpected + "</b> esperados " + 
			"(inicialmente teníamos " + initPoints + " y hemos utilizado " + donatedPoints + ")",
			finalPoints==loyaltyPointsExpected, State.Warn);
	 	
	 	return checks;
	}
	
	@Step (
		description="Hover sobre el link <b>Iniciar Sesión</b> o <b>Mi cuenta</b> para mostrar el menú de usuario")
	public void hoverLinkForShowUserMenuDesktop() throws Exception {
		userMenus.hoverIconForShowUserMenuDesktopShop();
	}
	
	public static class ChecksResultWithNumberPoints extends ChecksTM {
		int numberPoints ;
		public ChecksResultWithNumberPoints() {
			super();
		}
		public static ChecksResultWithNumberPoints getNew() {
			return (new ChecksResultWithNumberPoints());
		}
		public int getNumberPoints() {
			return this.numberPoints;
		}
		public void setNumberPoints(int numberPoints) {
			this.numberPoints = numberPoints;
		}
	}
}
