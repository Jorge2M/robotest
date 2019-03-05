package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
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
    
    public static DatosStep selectFavoritos(DataFavoritos dataFavoritos, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el menú de usuario \"Favoritos\"", 
            "Aparece la página de gestión de favoritos con los artículos correctos");
        try {
            SecMenusWrap.secMenusUser.clickFavoritosAndWait(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        //Validaciones
        PageFavoritosStpV.validaIsPageOK(dataFavoritos, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep selectRegistrate(Channel channel, DataCtxShop dCtxSh,  DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el menú de usuario \"Regístrate\"", 
            "Aparece al página inicial del registro");
        datosStep.setSaveHtmlPage(SaveWhen.Always);
        try {
            SecMenusWrap.secMenusUser.clickRegistrate(channel, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        //Validaciones
        int maxSecondsWait = 5;
        PageRegistroIniStpV.validaIsPageUntil(maxSecondsWait, dFTest);
        
        //Validacion RGPD
        PageRegistroIniStpV.validaIsRGPDVisible(dCtxSh, dFTest.driver);
        
        return datosStep;
    }
    
    private static void logoff(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step. Cerrar sesión + Identificarse
        DatosStep datosStep = new DatosStep (
            "Clicar el link de Logoff para cerrar la sesión", 
            "Aparece el link de login");
        try {
            SecMenusWrap.secMenusUser.clickCerrarSesion(Channel.desktop, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Aparece el link superior de \"Iniciar sesión\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenusWrap.secMenusUser.isPresentIniciarSesionUntil(channel, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static void logoffLogin(String userConnect, String userPassword, Channel channel, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step. Cerrar sesión + 
        logoff(channel, dFTest);
        
        //Step. Identificarse
        DatosStep datosStep = new DatosStep (
            "Identificarse con los datos del registro (" + userConnect + " / " + userPassword + ")", 
            "La nueva identificación es correcta");
        try {
            PageIdentificacion.iniciarSesion(userConnect, userPassword, channel, appE, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        String descripValidac = 
            "1) Aparece el link superior de \"Cerrar Sesión\" (estamos loginados)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenusWrap.secMenusUser.isPresentCerrarSesion(channel, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = false;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
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

        //Validaciones. 
        int maxSecondsWait = 5;
        ModalCambioPaisStpV.validateIsVisible(maxSecondsWait, driver);
        
        //Step.
        ModalCambioPaisStpV.cambioPais(dCtxSh, driver);
    }
}
