package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper.LoyaltyData;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeLikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalCambioPaisStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.registro.PageRegistroIniStpV;

/**
 * Clase que contiene los pasos/validaciones asociados al menú desplegable del frame superior que contiene las opciones del usuario:
 *      iniciar sesión
 *      regístrate
 *      pedidos
 *      ayuda
 *      ...
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
public class SecMenusUserStpV {
    
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	private final MenusUserWrapper userMenus;
	
	private SecMenusUserStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		this.app = app;
		this.userMenus = SecMenusWrap.getNew(channel, app, driver).getMenusUser();
	}
	
	public static SecMenusUserStpV getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new SecMenusUserStpV(channel, app, driver));
	}
	
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
        expected="Aparece la página de gestión de favoritos con los artículos correctos")
    public void selectFavoritos(DataFavoritos dataFavoritos) throws Exception {
		userMenus.clickMenuAndWait(UserMenu.favoritos);
        PageFavoritosStpV.validaIsPageOK(dataFavoritos, driver);
    }
    
	@Step (
		description="Seleccionar el menú de usuario \"Regístrate\"", 
        expected="Aparece al página inicial del registro",
        saveHtmlPage=SaveWhen.Always)
    public void selectRegistrate(DataCtxShop dCtxSh) throws Exception {
		userMenus.clickRegistrate();    
        int maxSecondsWait = 5;
        PageRegistroIniStpV.validaIsPageUntil(maxSecondsWait, driver);
        PageRegistroIniStpV.validaIsRGPDVisible(dCtxSh, driver);
    }
    
	@Step (
		description="Clicar el link de Logoff para cerrar la sesión", 
        expected="Aparece el link de login")
    public void logoff() throws Exception {
		userMenus.clickCerrarSesion();
        checkIsVisibleIniciarSesionLink(3);
    }
	
	@Validation (
		description="Aparece el link superior de \"Iniciar sesión\" (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private boolean checkIsVisibleIniciarSesionLink(int maxSecondsWait) {
        return (userMenus.isPresentIniciarSesionUntil(maxSecondsWait));
	}
	
	public void logoffLogin(String userConnect, String userPassword) throws Exception {
		logoff();
		identification(userConnect, userPassword);
	}
	
	@Step (
		description="Identificarse con los datos del registro (#{userConnect} / #{userPassword})", 
        expected="La nueva identificación es correcta")
    public void identification(String userConnect, String userPassword) throws Exception {
        PageIdentificacion.iniciarSesion(userConnect, userPassword, channel, app, driver);
        checkIsVisibleLinkCerrarSesion();
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
	
	@Validation (
		description="Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)",
		level=State.Defect)
	private boolean checkIsVisibleLinkCerrarSesion() {	
	    return (userMenus.isPresentCerrarSesion());
	}

    @Step (
    	description="Seleccionar el link \"Mi cuenta\"", 
        expected="Aparece la página de \"Mi cuenta\"")
	public void clickMenuMiCuenta() throws Exception {
        userMenus.clickMiCuenta();	
        PageMiCuentaStpV.validateIsPage(2, driver);
	}
    
    @Step (
    	description="Se selecciona el menú para el cambio de país", 
        expected="Aparece el modal para el cambio de país")
    public void cambioPaisMobil(DataCtxShop dCtxSh) throws Exception {
        userMenus.clickMenuAndWait(UserMenu.cambioPais);
        ModalCambioPaisStpV.validateIsVisible(5, driver);
        ModalCambioPaisStpV.cambioPais(dCtxSh, driver);
    }
    
    @Step (
    	description="Seleccionar el link \"Mango Likes You\"", 
        expected="Aparece la página de \"Mi cuenta\"")
	public void clickMenuMangoLikesYou() throws Exception {
    	userMenus.clickMangoLikesYou();
    	PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(driver);
    	pageHomeLikesStpV.checkIsPageOk();
	}
    
	@Validation
	public ChecksResult checkVisibilityLinkMangoLikesYou() {	
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		boolean visibilityMLY = userMenus.isPresentMangoLikesYou();
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
	public ChecksResultWithNumberPoints checkAngGetLoyaltyPoints(int maxSecondsWait) throws Exception {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		LoyaltyData loyaltyData = userMenus.checkAndGetLoyaltyPointsUntil(maxSecondsWait);
		checks.setNumberPoints(loyaltyData.numberPoints);
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario (lo esperamos hasta " + maxSecondsWait + " segundos)",
			loyaltyData.isPresent, State.Defect);
	 	
		return checks;
	}
	
	@Validation
    public ChecksResult checkLoyaltyPoints(int initPoints, int donatedPoints, int finalPoints) 
    throws Exception {
		ChecksResult checks = ChecksResult.getNew();
 		int loyaltyPointsExpected = initPoints - donatedPoints;
	 	checks.add(
			"Nos quedan <b>" + loyaltyPointsExpected + "</b> Loyalty Points " + 
			"(teníamos " + initPoints + " y hemos utilizado " + donatedPoints + ")",
			finalPoints==loyaltyPointsExpected, State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Hover sobre el link <b>Iniciar Sesión</b> o <b>Mi cuenta</b> para mostrar el menú de usuario")
	public void hoverLinkForShowUserMenuDesktop() {
		userMenus.hoverLinkForShowMenuDesktop();
	}
	
    public static class ChecksResultWithNumberPoints extends ChecksResult {
    	int numberPoints ;
    	private ChecksResultWithNumberPoints() {
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
