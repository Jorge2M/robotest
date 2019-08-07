package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import org.openqa.selenium.WebDriver;

public class PageMiCuentaStpV {

    @Validation(
        description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
        level=State.Defect)
    public static boolean validateIsPage (int maxSecondsToWait, WebDriver driver) {
        return (PageMiCuenta.isPageUntil(maxSecondsToWait, driver));
    }

    public static void goToMisDatos(String usuarioReg, AppEcom app, Channel channel, WebDriver driver) throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToMisDatos(usuarioReg, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis datos\"",
        expected = "Aparece la página de \"Mis datos\"")
    private static void goToMisDatos (String usuarioReg, WebDriver driver) throws Exception {
        PageMiCuenta.clickMisDatos(driver);
        PageMisDatosStpV.validaIsPage(usuarioReg, driver);
    }

    public static void goToMisComprasFromMenu (DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToMisComprasFromMenuAndValidate(dCtxSh, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis Compras\"",
        expected = "Aparece la página de \"Mis Compras\"")
    private static void goToMisComprasFromMenuAndValidate (DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        PageMiCuenta.clickMisCompras(driver);
        if (dCtxSh.channel == Channel.movil_web &&
        	PageInfoNewMisComprasMovil.isPage(driver)) {
            PageInfoNewMisComprasMovilStpV.validateIsPage(driver);
            PageInfoNewMisComprasMovilStpV.clickButtonToMisComprasAndNoValidate(driver);
        }

        PageMisComprasStpV.validateIsPage(dCtxSh, driver);
    }
 
    public static void goToMisDatosAndValidateData(HashMap<String,String> dataRegistro, String codPais, AppEcom app, Channel channel, WebDriver driver)
    throws Exception {
        goToMisDatos(dataRegistro.get("cfEmail"), app, channel, driver);
        PageMisDatosStpV.validaIsDataAssociatedToRegister(dataRegistro, codPais, driver);
    }

    public static void goToSuscripciones(AppEcom app, Channel channel, WebDriver driver) throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToSuscripciones(driver);
    }

    @Step(
        description = "Seleccionar el link \"Suscripciones\"",
        expected = "Aparece la página de \"Suscripciones\"")
    private static void goToSuscripciones(WebDriver driver) throws Exception {
        PageMiCuenta.clickSuscripciones(driver);
        PageSuscripcionesStpV.validaIsPage(driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    public static void goToSuscripcionesAndValidateData(HashMap<String,String> datosRegOk, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
        goToSuscripciones(app, channel, driver);
        PageSuscripcionesStpV.validaIsDataAssociatedToRegister(datosRegOk, driver);
    }

    public static void goToMisPedidos (String usrRegistrado, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToMisPedidos(usrRegistrado, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis pedidos\"",
        expected = "Aparece la página de \"Mis pedidos\" sin pedidos")
    private static void goToMisPedidos(String usrRegistrado, WebDriver driver) throws Exception {
        PageMiCuenta.clickMisPedidos(driver);
        PagePedidosStpV.validaIsPageSinPedidos(usrRegistrado, driver);
    }

    public static void goToDevoluciones(AppEcom app, Channel channel, WebDriver driver) throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToDevoluciones(driver);
    }

    @Step(
        description = "Seleccionar el link \"Devoluciones\"",
        expected = "Aparece la página de \"Devoluciones\"")
    private static void goToDevoluciones(WebDriver driver) throws Exception {
        PageMiCuenta.clickDevoluciones(driver);
        PageDevolucionesStpV.validaIsPage(driver);
    }

    public static void goToReembolsos(AppEcom app, Channel channel, WebDriver driver) throws Exception {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToReembolsos(driver);
    }

    @Step(
        description = "Seleccionar el link \"Reembolsos\"",
        expected = "Aparece la página de \"Reembolsos\"")
	private static void goToReembolsos(WebDriver driver) throws Exception {
        PageMiCuenta.clickReembolsos(driver);
        PageReembolsosStpV.validateIsPage(driver);
    }
}
