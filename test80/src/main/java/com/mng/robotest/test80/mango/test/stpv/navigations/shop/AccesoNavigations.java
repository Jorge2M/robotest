package com.mng.robotest.test80.mango.test.stpv.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageAlertaVOTF;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageLoginVOTF;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectIdiomaVOTF;
import com.mng.robotest.test80.mango.test.pageobject.votf.PageSelectLineaVOTF;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Login de Manto
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
public class AccesoNavigations {

	public static void goToInitURL(String urlInit, WebDriver driver) throws Exception {
    	String currentUrl = driver.getCurrentUrl();
    	if (currentUrl.compareTo(urlInit)!=0)
    		driver.get(urlInit);
	}
	
    /**
    /* Acceso a la página inicial (home) de la APP Web (shop o VOTF)
     */
    public static void accesoHomeAppWeb(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        if (dCtxSh.appE==AppEcom.votf) {
            accesoVOTF(dCtxSh, dFTest.driver);
            goFromLineasToMultimarcaVOTF(dFTest.driver);
        } 
        else {
            PagePrehome.accesoShopViaPrehome(dCtxSh, dFTest.driver);
        }
        
        dFTest.ctx.setAttribute(Constantes.attrUrlPagPostAcceso, dFTest.driver.getCurrentUrl());
    }
    
    public static void goFromLineasToMultimarcaVOTF(WebDriver driver) throws Exception {
        PageSelectLineaVOTF.clickBanner(LineaType.she, driver);
        PageSelectLineaVOTF.clickMenu(LineaType.she, 1/*numMenu*/, driver);
        SecCabecera.getNew(Channel.desktop, AppEcom.votf, driver).clickLogoMango();
    }
    
    /**
     * Acceso a VOTF (login + selección de idioma)
     */
    public static void accesoVOTF(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        PageLoginVOTF.goToFromUrlAndSetTestABs(dCtxSh.urlAcceso, driver);
        PageLoginVOTF.inputUsuario(dCtxSh.pais.getAccesoVOTF().getUsuario(), driver);
        PageLoginVOTF.inputPassword(dCtxSh.pais.getAccesoVOTF().getPassword(), driver);
        PageLoginVOTF.clickButtonContinue(driver);
        if (dCtxSh.pais.getListIdiomas().size() > 1) {
            PageSelectIdiomaVOTF.selectIdioma(dCtxSh.idioma.getCodigo(), driver);
            PageSelectIdiomaVOTF.clickButtonAceptar(driver);
        }

        if (PageAlertaVOTF.isPage(driver)) {
            PageAlertaVOTF.clickButtonContinuar(driver);
        }
        
    	//Forzamos galería sin React
    	int versionSinReact = 0;
    	TestAB.activateTestABgaleriaReact(versionSinReact, dCtxSh.channel, dCtxSh.appE, driver);
    }    
    
    public static void cambioPaisFromHomeIfNeeded(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        String codigoPais = PageLanding.getCodigoPais(driver);
        if (dCtxSh.pais.getCodigo_pais().compareTo(codigoPais)!=0) {
            cambioPais(dCtxSh, driver);
        }
    }
    
    public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            SecMenusWrapperStpV.secMenuUser.cambioPaisMobil(dCtxSh, driver);
        }
        else {
            SecFooterStpV.cambioPais(dCtxSh, driver);
        }
    }
        
}
