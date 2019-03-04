package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import org.openqa.selenium.WebDriver;

public class PageMiCuentaStpV {

    @Validation(
        description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
        level=State.Defect)
    public static boolean validateIsPage (int maxSecondsToWait, WebDriver driver) {
        return (PageMiCuenta.isPageUntil(maxSecondsToWait, driver));
    }

    public static void goToMisDatos(String usuarioReg, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest.driver);

        //Step
        goToMisDatos(usuarioReg, dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Mis datos\"",
        expected = "Aparece la página de \"Mis datos\"")
    private static void goToMisDatos (String usuarioReg, DataFmwkTest dFTest) throws Exception {
        PageMiCuenta.clickMisDatos(dFTest.driver);

        //Validaciones
        PageMisDatosStpV.validaIsPage(usuarioReg, dFTest.driver);
    }

    public static void goToMisComprasFromMenu (DataCtxShop dataCtxShop, Channel channel, DataFmwkTest dFTest) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, dataCtxShop.appE, dFTest.driver);
        goToMisComprasFromMenuAndValidate(dataCtxShop, channel, dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Mis Compras\"",
        expected = "Aparece la página de \"Mis Compras\"")
    private static void goToMisComprasFromMenuAndValidate (DataCtxShop dataCtxShop, Channel channel, DataFmwkTest dFTest) throws Exception {
        PageMiCuenta.clickMisCompras(dFTest.driver);

        if (channel == Channel.movil_web){
            PageInfoNewMisComprasMovilStpV.validateIsPage(dFTest.driver);
            PageInfoNewMisComprasMovilStpV.clickButtonToMisComprasAndNoValidate(dFTest);
        }

        PageMisComprasStpV.validateIsPage(dataCtxShop, dFTest.driver);
    }
 
    public static void goToMisDatosAndValidateData(HashMap<String,String> dataRegistro, String codPais, AppEcom app, Channel channel, DataFmwkTest dFTest)
    throws Exception {
        //Step.
        goToMisDatos(dataRegistro.get("cfEmail"), app, channel, dFTest);
        
        //Validaciones.
        PageMisDatosStpV.validaIsDataAssociatedToRegister(dataRegistro, codPais, dFTest.driver);
    }

    public static void goToSuscripciones(AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, app, dFTest.driver);
        goToSuscripciones(dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Suscripciones\"",
        expected = "Aparece la página de \"Suscripciones\"")
    private static void goToSuscripciones(DataFmwkTest dFTest) throws Exception {
        //Step de aqui
        PageMiCuenta.clickSuscripciones(dFTest.driver);

        //Validation
        PageSuscripcionesStpV.validaIsPage(dFTest.driver);
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/);
    }
    
    public static void goToSuscripcionesAndValidateData(HashMap<String,String> datosRegOk, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        goToSuscripciones(app, channel, dFTest);
        
        //Validaciones
        PageSuscripcionesStpV.validaIsDataAssociatedToRegister(datosRegOk, dFTest.driver);
    }

    public static void goToMisPedidos (String usrRegistrado, AppEcom appE, Channel channel, DataFmwkTest dFTest) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, dFTest.driver);
        goToMisPedidos(usrRegistrado, dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Mis pedidos\"",
        expected = "Aparece la página de \"Mis pedidos\" sin pedidos")
    private static void goToMisPedidos(String usrRegistrado, DataFmwkTest dFTest) throws Exception {
        //Step de aqui
        PageMiCuenta.clickMisPedidos(dFTest.driver);

        //Validation
        PagePedidosStpV.validaIsPageSinPedidos(usrRegistrado, dFTest.driver);
    }

    public static void goToDevoluciones(AppEcom appE, Channel channel, DataFmwkTest dFTest) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, dFTest.driver);
        goToDevoluciones(dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Devoluciones\"",
        expected = "Aparece la página de \"Devoluciones\"")
    private static void goToDevoluciones(DataFmwkTest dFTest) throws Exception {
        //Step de aqui
        PageMiCuenta.clickDevoluciones(dFTest.driver);

        //Validation
        PageDevolucionesStpV.validaIsPage(dFTest.driver);
    }

    public static void goToReembolsos(AppEcom appE, Channel channel, DataFmwkTest dFTest) throws Exception {
        SecMenusUserStpV.clickMenuMiCuenta(channel, appE, dFTest.driver);
        goToReembolsos(dFTest);
    }

    @Step(
        description = "Seleccionar el link \"Reembolsos\"",
        expected = "Aparece la página de \"Reembolsos\"")
	private static void goToReembolsos(DataFmwkTest dFTest) throws Exception {
	    //Step de aqui
        PageMiCuenta.clickReembolsos(dFTest.driver);

        //Validation
        PageReembolsosStpV.validateIsPage(dFTest.driver);
    }
}
