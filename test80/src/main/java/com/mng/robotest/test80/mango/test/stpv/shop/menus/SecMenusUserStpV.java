package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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
    
    public static datosStep selectFavoritos(DataFavoritos dataFavoritos, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep       (
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
    
    public static datosStep selectRegistrate(Channel channel, DataCtxShop dCtxSh,  DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        datosStep datosStep = new datosStep       (
            "Seleccionar el menú de usuario \"Regístrate\"", 
            "Aparece al página inicial del registro");
        datosStep.setGrabHTML(true);
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
        datosStep datosStep = new datosStep (
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecMenusWrap.secMenusUser.isPresentIniciarSesionUntil(channel, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public static void logoffLogin(String userConnect, String userPassword, Channel channel, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step. Cerrar sesión + 
        logoff(channel, dFTest);
        
        //Step. Identificarse
        datosStep datosStep = new datosStep (
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecMenusWrap.secMenusUser.isPresentCerrarSesion(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
	public static void clickMenuMiCuenta(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep     (
            "Seleccionar el link \"Mi cuenta\"", 
            "Aparece la página de \"Mi cuenta\"");
        try {
            SecMenusWrap.secMenusUser.clickMiCuenta(app, channel, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }		
		
        PageMiCuentaStpV.validateIsPage(datosStep, dFTest);
	}
    
    public static datosStep cambioPaisMobil(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step. Seleccionamos el link para el cambio de país
        datosStep datosStep = new datosStep(
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
