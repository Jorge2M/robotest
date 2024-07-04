package com.mng.robotest.tests.domains.menus.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.favoritos.steps.FavoritosSteps;
import com.mng.robotest.tests.domains.login.pageobjects.PageLogin;
import com.mng.robotest.tests.domains.loyalty.steps.PageMLYUnirmeAlClubSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMiCuentaOld;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOld;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class MenusUserSteps extends StepBase {
	
	private final MenusUserWrapper userMenus = new MenusUserWrapper();
	
	@Validation(
		description="En los menús de usuario es visible el nombre <b>#{nameUser}</b> " + SECONDS_WAIT)
	public boolean isVisibleNameUser(String nameUser, int seconds) {
		return userMenus.isNameVisible(nameUser, seconds);
	}
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
		expected="Aparece la página de gestión de favoritos con los artículos correctos",
		saveHtmlPage=IF_PROBLEM)
	public void selectFavoritos() {
		clickUserMenu(FAVORITOS);
		new FavoritosSteps().checkPage();
	}

	@Step (
		description="Seleccionar el menú de usuario <b>Regístrate</b>", 
		expected="Aparece al página inicial del registro",
		saveHtmlPage=ALWAYS)
	public void selectRegistrate() {
		clickUserMenu(REGISTRATE);
		if (channel.isDevice()) {
			new PageLogin().clickButtonCrearCuenta();
		}
		if (!dataTest.getPais().isNewRegister()) {
			var pageRegistroIniSteps = new PageRegistroIniStepsOld();
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
		//There is a random problem in the first select of the MyAccount icon
		if (!clickMenuMiCuentaOneTime(State.WARN)) {
			clickMenuMiCuentaOneTime(State.DEFECT);
		}
		checksDefault();
	}
	
	private boolean clickMenuMiCuentaOneTime(State state) {
		clickUserMenu(MI_CUENTA);
		return new MiCuentaSteps().checkIsPage(state, 3).areAllChecksOvercomed();
	}

	public int clickMenuMangoLikesYou() {
		if (dataTest.isUserRegistered()) {
			return clickMenuMangoLikesYouLogged();
		}
		clickMenuMangoLikesYouNotLogged();
		return 0;
	}	
	
	private static final String TAG_POINTS = "@TagPoints";
	@Step (
		description=
			"Seleccionar el link \"Mango Likes You\"<br>" + 
			"<b>info</b>: el usuario tiene " + TAG_POINTS + " puntos", 
		expected="Aparece la página de \"Mango Likes You\"")
	private int clickMenuMangoLikesYouLogged() {
		clickUserMenu(MANGO_LIKES_YOU);
		int numberPoints = new PageMangoLikesYouSteps().checkIsPage().getNumberPoints();
		checksDefault();
		
		replaceStepDescription(TAG_POINTS, String.valueOf(numberPoints));
		return (numberPoints);
	}
	
	@Step (
		description= "Seleccionar el link \"Mango Likes You\"<br>", 
		expected="Aparece la página de \"Unirme al club Mango Likes You\"")
	private void clickMenuMangoLikesYouNotLogged() {
		clickUserMenu(MANGO_LIKES_YOU);
		new PageMLYUnirmeAlClubSteps().isPage(2);
	}
	
	@Step (
		description=
			"Seleccionar el link \"Mi cuenta\"<br>" + 
			"<b>info</b>: el usuario tiene " + TAG_POINTS + " puntos", 
		expected="Aparece la página de \"Mi cuenta\"")
	public int clickMyAccountAndGetPoints() {
		clickUserMenu(MI_CUENTA);
		int numberPoints = new PageMiCuentaOld().getNumberPoints();
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
		var checks = ChecksResultWithNumberPoints.getNew();
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
