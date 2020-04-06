package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import org.openqa.selenium.WebDriver;

public class PageMiCuentaStpV {
	
	private final PageMiCuenta pageMiCuenta;
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	
	private PageMiCuentaStpV(Channel channel, AppEcom app, WebDriver driver) {
		pageMiCuenta = PageMiCuenta.getNew(driver);
		this.driver = driver;
		this.channel = channel;
		this.app = app;
	}
	public static PageMiCuentaStpV getNew(Channel channel, AppEcom app, WebDriver driver) {
		return new PageMiCuentaStpV(channel, app, driver);
	}
	
    @Validation(
        description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
        level=State.Defect)
    public boolean validateIsPage (int maxSecondsToWait) {
        return (pageMiCuenta.isPageUntil(maxSecondsToWait));
    }

    public void goToMisDatos(String usuarioReg) {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
    	clickLinkMisDatos(usuarioReg);
    }

    @Step(
        description = "Seleccionar el link \"Mis datos\"",
        expected = "Aparece la página de \"Mis datos\"")
    private void clickLinkMisDatos (String usuarioReg) {
        pageMiCuenta.clickMisDatos();
        PageMisDatosStpV.validaIsPage(usuarioReg, driver);
    }

    public void goToMisComprasFromMenu(Pais pais) {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
        goToMisComprasFromMenuAndValidate(pais);
    }

    @Step(
        description = "Seleccionar el link \"Mis Compras\"",
        expected = "Aparece la página de \"Mis Compras\"")
    private void goToMisComprasFromMenuAndValidate(Pais pais) {
        pageMiCuenta.clickMisCompras();
        if (channel == Channel.movil_web &&
        	PageInfoNewMisComprasMovil.isPage(driver)) {
            PageInfoNewMisComprasMovilStpV.validateIsPage(driver);
            PageInfoNewMisComprasMovilStpV.clickButtonToMisComprasAndNoValidate(driver);
        }

        PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(channel, driver);
        pageMisComprasStpV.validateIsPage(pais);
    }
 
    public void goToMisDatosAndValidateData(Map<String,String> dataRegistro, String codPais) {
        goToMisDatos(dataRegistro.get("cfEmail"));
        PageMisDatosStpV.validaIsDataAssociatedToRegister(dataRegistro, codPais, driver);
    }

    public void goToSuscripciones() {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
    	clickLinkSuscripciones();
    }

    @Step(
        description = "Seleccionar el link \"Suscripciones\"",
        expected = "Aparece la página de \"Suscripciones\"")
    private void clickLinkSuscripciones() {
        pageMiCuenta.clickSuscripciones();
        PageSuscripcionesStpV.validaIsPage(driver);
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    public void goToSuscripcionesAndValidateData(Map<String,String> datosRegOk) {
        goToSuscripciones();
        PageSuscripcionesStpV.validaIsDataAssociatedToRegister(datosRegOk, driver);
    }

    public void goToMisPedidos (String usrRegistrado) {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
    	clickLinkMisPedidos(usrRegistrado);
    }

    @Step(
        description = "Seleccionar el link \"Mis pedidos\"",
        expected = "Aparece la página de \"Mis pedidos\" sin pedidos")
    private void clickLinkMisPedidos(String usrRegistrado) {
        pageMiCuenta.clickMisPedidos();
        PagePedidosStpV.validaIsPageSinPedidos(usrRegistrado, driver);
    }

    public void goToDevoluciones() {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
    	clickLinkDevoluciones();
    }

    @Step(
        description = "Seleccionar el link \"Devoluciones\"",
        expected = "Aparece la página de \"Devoluciones\"")
    private void clickLinkDevoluciones() {
        pageMiCuenta.clickDevoluciones();
        PageDevolucionesStpV.validaIsPage(driver);
    }

    public void goToReembolsos() {
    	SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
    	userMenusStpV.clickMenuMiCuenta();
    	clickLinkReembolsos();
    }

    @Step(
        description = "Seleccionar el link \"Reembolsos\"",
        expected = "Aparece la página de \"Reembolsos\"")
	private void clickLinkReembolsos() {
        pageMiCuenta.clickReembolsos();
        PageReembolsosStpV.validateIsPage(driver);
    }
}
