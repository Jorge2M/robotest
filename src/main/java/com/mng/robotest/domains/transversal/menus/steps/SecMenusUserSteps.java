package com.mng.robotest.domains.transversal.menus.steps;

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
import com.mng.robotest.domains.identification.pageobjects.PageIdentificacion;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroIniOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.modales.ModalCambioPaisSteps;

import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.*;

public class SecMenusUserSteps extends StepBase {
	
	private final MenusUserWrapper userMenus = new MenusUserWrapper();
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
		expected="Aparece la página de gestión de favoritos con los artículos correctos")
	public void selectFavoritos() throws Exception {
		clickUserMenu(FAVORITOS);
		PageFavoritosSteps pageFavoritosSteps = new PageFavoritosSteps();
		pageFavoritosSteps.validaIsPageOK();
	}

	@Step (
		description="Seleccionar el menú de usuario \"Regístrate\" y la pestaña \"Regístrate\"", 
		expected="Aparece al página inicial del registro",
		saveHtmlPage=SaveWhen.Always)
	public void selectRegistrate() throws Exception {
		clickUserMenu(REGISTRATE);
		PageRegistroIniOutlet pageRegistroIni = new PageRegistroIniOutlet();  
		pageRegistroIni.clickRegisterTab();
		if (app==AppEcom.outlet || channel==Channel.mobile) {
			PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
			pageRegistroIniSteps.validaIsPageUntil(5);
			pageRegistroIniSteps.validaIsRGPDVisible();
		} else {
			PageRegistroInitialShopSteps pageRegistroIniSteps = new PageRegistroInitialShopSteps();
			pageRegistroIniSteps.checkIsPageUntil(5);
		}
		GenericChecks.checkDefault();
	}

	@Step (
		description="Clicar el link de Cerrar Sesión", 
		expected="Aparece el link de login")
	public void logoff() throws Exception {
		clickUserMenu(CERRAR_SESION);
		checkIsVisibleIniciarSesionLink(3);
	}
	
	@Validation (
		description="Aparece el link superior de \"Iniciar sesión\" (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleIniciarSesionLink(int seconds) throws Exception {
		return (userMenus.isMenuInStateUntil(INICIAR_SESION, Present, seconds));
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
		GenericChecks.checkDefault();
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
		return (userMenus.isMenuInStateUntil(CERRAR_SESION, Present, 1));
	}

	@Step (
		description="Seleccionar el link \"Mi cuenta\"", 
		expected="Aparece la página de \"Mi cuenta\"")
	public void clickMenuMiCuenta() {
		clickUserMenu(MI_CUENTA);
		new PageMiCuentaSteps().validateIsPage(2);
		GenericChecks.checkDefault();
	}
	
	@Step (
		description="Se selecciona el menú para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	public void cambioPaisMobil(Pais newPais, IdiomaPais newIdioma) throws Exception {
		clickUserMenu(CAMBIO_PAIS);
		ModalCambioPaisSteps modalCambioPaisSteps = new ModalCambioPaisSteps();
		modalCambioPaisSteps.validateIsVisible(5); 
		modalCambioPaisSteps.cambioPais(newPais, newIdioma);
	}

	private static final String TagPoints = "@TagPoints";
	@Step (
		description=
			"Seleccionar el link \"Mango Likes You\"<br>" + 
			"<b>info</b>: el usuario tiene " + TagPoints + " puntos", 
		expected="Aparece la página de \"Mi cuenta\"")
	public int clickMenuMangoLikesYou() throws Exception {
		clickUserMenu(MANGO_LIKES_YOU);
		PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps();
		int numberPoints = pageHomeLikesSteps.checkIsPageOk().getNumberPoints();
		GenericChecks.checkDefault();
		
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
		boolean visibilityMLY = userMenus.isMenuInStateUntil(MANGO_LIKES_YOU, Present, 1);
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
	public ChecksResultWithNumberPoints checkAngGetLoyaltyPoints(int seconds) throws Exception {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		LoyaltyData loyaltyData = userMenus.checkAndGetLoyaltyPointsUntil(seconds);
		checks.setNumberPoints(loyaltyData.numberPoints);
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario (lo esperamos hasta " + seconds + " segundos)",
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
