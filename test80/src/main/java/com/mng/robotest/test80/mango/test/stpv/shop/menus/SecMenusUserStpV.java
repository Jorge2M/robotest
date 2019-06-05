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
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusUserWrap.LoyaltyData;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusUserDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
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
    
	@Step (
		description="Seleccionar el menú de usuario \"Favoritos\"", 
        expected="Aparece la página de gestión de favoritos con los artículos correctos")
    public static void selectFavoritos(DataFavoritos dataFavoritos, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        SecMenusWrap.secMenusUser.clickFavoritosAndWait(dCtxSh.channel, dCtxSh.appE, driver);      
        PageFavoritosStpV.validaIsPageOK(dataFavoritos, driver);
    }
    
	@Step (
		description="Seleccionar el menú de usuario \"Regístrate\"", 
        expected="Aparece al página inicial del registro",
        saveHtmlPage=SaveWhen.Always)
    public static void selectRegistrate(Channel channel, DataCtxShop dCtxSh,  WebDriver driver) 
    throws Exception {
        SecMenusWrap.secMenusUser.clickRegistrate(channel, driver);    
            
        int maxSecondsWait = 5;
        PageRegistroIniStpV.validaIsPageUntil(maxSecondsWait, driver);
        PageRegistroIniStpV.validaIsRGPDVisible(dCtxSh, driver);
    }
    
	@Step (
		description="Clicar el link de Logoff para cerrar la sesión", 
        expected="Aparece el link de login")
    private static void logoff(Channel channel, WebDriver driver) throws Exception {
        SecMenusWrap.secMenusUser.clickCerrarSesion(Channel.desktop, driver);
        checkIsVisibleIniciarSesionLink(channel, 3, driver);
    }
	
	@Validation (
		description="Aparece el link superior de \"Iniciar sesión\" (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean checkIsVisibleIniciarSesionLink(Channel channel, int maxSecondsWait, WebDriver driver) {
        return (SecMenusWrap.secMenusUser.isPresentIniciarSesionUntil(channel, maxSecondsWait, driver));
	}
	
	public static void logoffLogin(String userConnect, String userPassword, Channel channel, AppEcom appE, WebDriver driver) 
	throws Exception {
		logoff(channel, driver);
		identification(userConnect, userPassword, channel, appE, driver);
	}
	
	@Step (
		description="Identificarse con los datos del registro (#{userConnect} / #{userPassword})", 
        expected="La nueva identificación es correcta")
    public static void identification(String userConnect, String userPassword, Channel channel, AppEcom appE, WebDriver driver) 
    throws Exception {
        PageIdentificacion.iniciarSesion(userConnect, userPassword, channel, appE, driver);
        checkIsVisibleLinkCerrarSesion(channel, driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
	
	@Validation (
		description="Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)",
		level=State.Defect)
	private static boolean checkIsVisibleLinkCerrarSesion(Channel channel, WebDriver driver) {	
	    return (SecMenusWrap.secMenusUser.isPresentCerrarSesion(channel, driver));
	}

    @Step (
    	description="Seleccionar el link \"Mi cuenta\"", 
        expected="Aparece la página de \"Mi cuenta\"")
	public static void clickMenuMiCuenta(Channel channel, AppEcom app, WebDriver driver) throws Exception {
        SecMenusWrap.secMenusUser.clickMiCuenta(app, channel, driver);	
        PageMiCuentaStpV.validateIsPage(2, driver);
	}
    
    @Step (
    	description="Se selecciona el menú para el cambio de país", 
        expected="Aparece el modal para el cambio de país")
    public static void cambioPaisMobil(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        SecMenuLateralMobil.secMenusUser.clickCambioPais(dCtxSh.appE, driver);
        ModalCambioPaisStpV.validateIsVisible(5, driver);
        ModalCambioPaisStpV.cambioPais(dCtxSh, driver);
    }
    
    @Step (
    	description="Seleccionar el link \"Mango Likes You\"", 
        expected="Aparece la página de \"Mi cuenta\"")
	public static void clickMenuMangoLikesYou(Channel channel, WebDriver driver) throws Exception {
    	SecMenusWrap.secMenusUser.clickMangoLikesYou(channel, driver);
    	SecMenusUserDesktop.clickMangoLikesYou(driver);	
    	PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(driver);
    	pageHomeLikesStpV.checkIsPageOk();
	}
    
	@Validation
	public static ChecksResult checkVisibilityLinkMangoLikesYou(Channel channel, AppEcom app, WebDriver driver) {	
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		boolean visibilityMLY = SecMenusWrap.secMenusUser.isPresentMangoLikesYou(channel, driver);
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
	public static ChecksResultWithNumberPoints checkAngGetLoyaltyPoints(int maxSecondsWait, WebDriver driver) 
	throws Exception {
		ChecksResultWithNumberPoints checks = ChecksResultWithNumberPoints.getNew();
		LoyaltyData loyaltyData = SecMenusWrap.secMenusUser.checkAndGetLoyaltyPointsUntil(maxSecondsWait, driver);
		checks.setNumberPoints(loyaltyData.numberPoints);
	 	checks.add(
			"Aparecen Loyalty Points en el menú de usuario (lo esperamos hasta " + maxSecondsWait + " segundos)",
			loyaltyData.isPresent, State.Defect);
	 	
		return checks;
	}
	
	@Validation
    public static ChecksResult checkLoyaltyPoints(int initPoints, int donatedPoints, int finalPoints) 
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
	public static void hoverLinkForShowMenu(WebDriver driver) {
		SecMenusUserDesktop.hoverLinkForShowMenu(driver);
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
