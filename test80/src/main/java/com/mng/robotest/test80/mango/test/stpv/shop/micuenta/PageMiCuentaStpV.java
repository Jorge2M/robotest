package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
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
        SecMenusUserStpV.clickMenuMiCuenta(channel, app, driver);
        goToMisDatos(usuarioReg, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis datos\"",
        expected = "Aparece la página de \"Mis datos\"")
    private static void goToMisDatos (String usuarioReg, WebDriver driver) throws Exception {
        PageMiCuenta.clickMisDatos(driver);
        PageMisDatosStpV.validaIsPage(usuarioReg, driver);
    }

    public static void goToMisComprasFromMenu (DataCtxShop dataCtxShop, Channel channel, WebDriver driver) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, dataCtxShop.appE, driver);
        goToMisComprasFromMenuAndValidate(dataCtxShop, channel, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis Compras\"",
        expected = "Aparece la página de \"Mis Compras\"")
    private static void goToMisComprasFromMenuAndValidate (DataCtxShop dataCtxShop, Channel channel, WebDriver driver) 
    throws Exception {
        PageMiCuenta.clickMisCompras(driver);
        if (channel == Channel.movil_web) {
            PageInfoNewMisComprasMovilStpV.validateIsPage(driver);
            PageInfoNewMisComprasMovilStpV.clickButtonToMisComprasAndNoValidate(driver);
        }

        PageMisComprasStpV.validateIsPage(dataCtxShop, driver);
    }
 
    public static void goToMisDatosAndValidateData(HashMap<String,String> dataRegistro, String codPais, AppEcom app, Channel channel, WebDriver driver)
    throws Exception {
        goToMisDatos(dataRegistro.get("cfEmail"), app, channel, driver);
        PageMisDatosStpV.validaIsDataAssociatedToRegister(dataRegistro, codPais, driver);
    }

    public static void goToSuscripciones(AppEcom app, Channel channel, WebDriver driver) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, app, driver);
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

    public static void goToMisPedidos (String usrRegistrado, AppEcom appE, Channel channel, WebDriver driver) 
    throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, driver);
        goToMisPedidos(usrRegistrado, driver);
    }

    @Step(
        description = "Seleccionar el link \"Mis pedidos\"",
        expected = "Aparece la página de \"Mis pedidos\" sin pedidos")
    private static void goToMisPedidos(String usrRegistrado, WebDriver driver) throws Exception {
        PageMiCuenta.clickMisPedidos(driver);
        PagePedidosStpV.validaIsPageSinPedidos(usrRegistrado, driver);
    }

    public static void goToDevoluciones(AppEcom appE, Channel channel, WebDriver driver) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, driver);
        goToDevoluciones(driver);
    }

    @Step(
        description = "Seleccionar el link \"Devoluciones\"",
        expected = "Aparece la página de \"Devoluciones\"")
    private static void goToDevoluciones(WebDriver driver) throws Exception {
        PageMiCuenta.clickDevoluciones(driver);
        PageDevolucionesStpV.validaIsPage(driver);
    }

    public static void goToReembolsos(AppEcom appE, Channel channel, WebDriver driver) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, driver);
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
