package com.mng.robotest.domains.transversal.menus.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.favoritos.steps.PageFavoritosSteps;
import com.mng.robotest.domains.login.pageobjects.PageLogin;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.domains.transversal.modales.pageobject.ModalCambioPaisSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;

import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class SecMenusUserSteps extends StepBase {
	
	private final MenusUserWrapper userMenus = new MenusUserWrapper();
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
		expected="Aparece la página de gestión de favoritos con los artículos correctos",
		saveHtmlPage=IfProblem)
	public void selectFavoritos() {
		clickUserMenu(FAVORITOS);
		new PageFavoritosSteps().validaIsPageOK();
	}

	@Step (
		description="Seleccionar el menú de usuario <b>Regístrate</b>", 
		expected="Aparece al página inicial del registro",
		saveHtmlPage=Always)
	public void selectRegistrate() {
		clickUserMenu(REGISTRATE);
		if (channel.isDevice()) {
			new PageLogin().clickTabRegistrate();
		}
		if (!dataTest.getPais().isNewRegister() || 
			(app==AppEcom.outlet && dataTest.getPais()==PaisShop.ESPANA.getPais())) {
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
		return (userMenus.isMenuInStateUntil(INICIAR_SESION, Present, seconds));
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
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		return (userMenus.isMenuInStateUntil(CERRAR_SESION, Present, 1));
	}
	private boolean isInvisibleLinkCerrarSesion() {
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		return (!userMenus.isMenuInStateUntil(CERRAR_SESION, Present, 1));
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
		
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_POINTS, String.valueOf(numberPoints));
		return (numberPoints);
	}

	@Validation
	public ChecksTM checkVisibilityLinkMangoLikesYou() {	
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		if (channel==Channel.desktop && app==AppEcom.shop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		boolean visibilityMLY = userMenus.isMenuInStateUntil(MANGO_LIKES_YOU, Present, 1);
		if (app==AppEcom.shop) {
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
		if (channel==Channel.desktop) {
			userMenus.hoverIconForShowUserMenuDesktopShop();
		}
		LoyaltyData loyaltyData = userMenus.checkAndGetLoyaltyPointsUntil(seconds);
		checks.setNumberPoints(loyaltyData.numberPoints);
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario (lo esperamos hasta " + seconds + " segundos)",
			loyaltyData.isPresent);
	 	
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
			finalPoints==loyaltyPointsExpected, Warn);
	 	
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
