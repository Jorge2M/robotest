package com.mng.robotest.tests.domains.menus.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.tests.domains.login.pageobjects.PageLogin;
import com.mng.robotest.tests.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.modales.pageobject.ModalCambioPaisSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class SecMenusUserSteps extends StepBase {
	
	private final MenusUserWrapper userMenus = new MenusUserWrapper();
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
		expected="Aparece la página de gestión de favoritos con los artículos correctos",
		saveHtmlPage=IF_PROBLEM)
	public void selectFavoritos() {
		clickUserMenu(FAVORITOS);
		new PageFavoritosSteps().validaIsPageOK();
	}

	@Step (
		description="Seleccionar el menú de usuario <b>Regístrate</b>", 
		expected="Aparece al página inicial del registro",
		saveHtmlPage=ALWAYS)
	public void selectRegistrate() {
		clickUserMenu(REGISTRATE);
		if (channel.isDevice()) {
			new PageLogin().clickTabRegistrate();
		}
		if (!dataTest.getPais().isNewRegister()) {
			var pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
			pageRegistroIniSteps.checkIsPage(5);
			pageRegistroIniSteps.validaIsRGPDVisible();
		} else {
			new PageRegistroInitialShopSteps().checkPage(5);
		}
		checksDefault();
	}

	@Step (
		description="Clicar el link de Cerrar Sesión", 
		expected="Aparece el link de login")
	public void logoff() {
		clickUserMenu(CERRAR_SESION);
		checkIsVisibleIniciarSesionLink(3);
	}
	
	@Validation (
		description="Aparece el link superior de \"Iniciar sesión\" " + SECONDS_WAIT)
	private boolean checkIsVisibleIniciarSesionLink(int seconds) {
		return (userMenus.isMenuInStateUntil(INICIAR_SESION, PRESENT, seconds));
	}
	
	public void logoffLogin(String userConnect, String userPassword) {
		logoff();
		new AccesoSteps().identification(userConnect, userPassword);
	}
	
	@Validation (description="Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)")
	public boolean checkIsVisibleLinkCerrarSesion() {	
		return isVisibleLinkCerrarSesion();
	}
	
	@Validation (description="Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)")
	public boolean checkIsInvisibleLinkCerrarSesion() {	
		return isInvisibleLinkCerrarSesion();
	}	
	
	@Validation (
		description=
			"Aparece el link superior de \"Cerrar Sesión\" (estamos loginados) " + SECONDS_WAIT)	
	public boolean checkIsVisibleLinkCerrarSesionUntil(int seconds) {
		if (isVisibleLinkCerrarSesionExceptionSafe()) {
			return true;
		}
		for (int i=0; i<seconds;i++) {
			if (isVisibleLinkCerrarSesionExceptionSafe()) {
				return true;
			}
			waitMillis(1000);
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
		if (isDesktop()) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		return (userMenus.isMenuInStateUntil(CERRAR_SESION, PRESENT, 1));
	}
	private boolean isInvisibleLinkCerrarSesion() {
		if (isDesktop()) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		return (!userMenus.isMenuInStateUntil(CERRAR_SESION, PRESENT, 1));
	}	

	@Step (
		description="Seleccionar el link \"Mi cuenta\"", 
		expected="Aparece la página de \"Mi cuenta\"")
	public void clickMenuMiCuenta() {
		clickUserMenu(MI_CUENTA);
		new PageMiCuentaSteps().validateIsPage(2);
		checksDefault();
	}
	
	@Step (
		description="Se selecciona el menú para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	public void cambioPaisMobil(Pais newPais, IdiomaPais newIdioma) {
		clickUserMenu(CAMBIO_PAIS);
		var modalCambioPaisSteps = new ModalCambioPaisSteps();
		modalCambioPaisSteps.validateIsVisible(5); 
		modalCambioPaisSteps.cambioPais(newPais, newIdioma);
	}

	private static final String TAG_POINTS = "@TagPoints";
	@Step (
		description=
			"Seleccionar el link \"Mango Likes You\"<br>" + 
			"<b>info</b>: el usuario tiene " + TAG_POINTS + " puntos", 
		expected="Aparece la página de \"Mi cuenta\"")
	public int clickMenuMangoLikesYou() {
		clickUserMenu(MANGO_LIKES_YOU);
		int numberPoints = new PageMangoLikesYouSteps().checkIsPageOk().getNumberPoints();
		checksDefault();
		
		replaceStepDescription(TAG_POINTS, String.valueOf(numberPoints));
		return (numberPoints);
	}

	@Validation
	public ChecksTM checkVisibilityLinkMangoLikesYou() {	
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (isDesktop() && isShop()) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		boolean visibilityMLY = userMenus.isMenuInStateUntil(MANGO_LIKES_YOU, PRESENT, 1);
		if (isShop()) {
			checks.add(
				"Sí aparece el link de \"Mango Likes You\" en el menú de usuario",
				visibilityMLY);
		}
		else {
			checks.add(
				"No aparece el link de \"Mango Likes You\" en el menú de usuario",
				!visibilityMLY);
		}
		
		return checks;  
	}

	@Validation
	public ChecksResultWithNumberPoints checkAngGetLoyaltyPoints(int seconds) {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (isDesktop()) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		LoyaltyData loyaltyData = userMenus.checkAndGetLoyaltyPointsUntil(seconds);
		checks.setNumberPoints(loyaltyData.getNumberPoints());
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario " + getLitSecondsWait(seconds),
			loyaltyData.isPresent());
	 	
		return checks;
	}
	
	@Validation
	public ChecksTM checkLoyaltyPoints(int initPoints, int donatedPoints, int finalPoints) {
		var checks = ChecksTM.getNew();
 		int loyaltyPointsExpected = initPoints - donatedPoints;
 		
 		//TODO hemos de Defect a Warning porque en PRE no funciona esta operativa desde hace tiempo
 		//si en algún momento vuelve a funcionar habrá que poner Defect de nuevo 
	 	checks.add(
			"Los Loyalty Points que figuran ahora en la web (<b>" + finalPoints + "</b>) " + 
			"coinciden con los <b>" + loyaltyPointsExpected + "</b> esperados " + 
			"(inicialmente teníamos " + initPoints + " y hemos utilizado " + donatedPoints + ")",
			finalPoints==loyaltyPointsExpected, WARN);
	 	
	 	return checks;
	}
	
	@Step (
		description="Hover sobre el link <b>Iniciar Sesión</b> o <b>Mi cuenta</b> para mostrar el menú de usuario")
	public void hoverLinkForShowUserMenuDesktop() {
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
