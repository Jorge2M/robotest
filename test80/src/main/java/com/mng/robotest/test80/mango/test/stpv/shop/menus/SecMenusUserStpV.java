package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.WhenSave;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
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
@SuppressWarnings({"javadoc", "static-access"})
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        PageFavoritosStpV.validaIsPageOK(dataFavoritos, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep selectRegistrate(Channel channel, DataCtxShop dCtxSh,  DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el menú de usuario \"Regístrate\"", 
            "Aparece al página inicial del registro");
        datosStep.setSaveHtmlPage(WhenSave.Always);
        try {
            SecMenusWrap.secMenusUser.clickRegistrate(channel, dFTest.driver);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        PageRegistroIniStpV.validaIsPage(datosStep, dFTest);
        
        //Validacion RGPD
        PageRegistroIniStpV.validaIsRGPDVisible(datosStep, dCtxSh, dFTest);
        
        return datosStep;
    }
    
    public static void logoff(Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step. Cerrar sesión + Identificarse
        DatosStep datosStep = new DatosStep (
            "Clicar el link de Logoff para cerrar la sesión", 
            "Aparece el link de login");
        try {
            SecMenusWrap.secMenusUser.clickCerrarSesion(Channel.desktop, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
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
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
	public static void clickMenuMiCuenta(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el link \"Mi cuenta\"", 
            "Aparece la página de \"Mi cuenta\"");
        try {
            SecMenusWrap.secMenusUser.clickMiCuenta(app, channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }		
		
        PageMiCuentaStpV.validateIsPage(datosStep, dFTest);
	}
    
    public static DatosStep cambioPaisMobil(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step. Seleccionamos el link para el cambio de país
        DatosStep datosStep = new DatosStep(
            "Se selecciona el menú para el cambio de país", 
            "Aparece el modal para el cambio de país");
        try {
            SecMenuLateralMobil.secMenusUser.clickCambioPais(dCtxSh.appE, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}

        //Validaciones. 
        ModalCambioPaisStpV.validateIsVisible(datosStep, dFTest);
        
        //Step.
        ModalCambioPaisStpV.cambioPais(dCtxSh, dFTest);
        
        return datosStep;
    }
}
