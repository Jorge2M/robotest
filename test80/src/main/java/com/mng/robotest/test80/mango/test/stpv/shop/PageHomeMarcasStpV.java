package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.List;
import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageHomeMarcas;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

@SuppressWarnings("javadoc")
public class PageHomeMarcasStpV {

    public static DatosStep validateIsPageWithCorrectLineas(Pais pais, Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        AllPagesStpV.validateMainContentPais(pais, datosStep, dFTest);

        //Validaciones
        validateIsPageOk(pais, app, datosStep, dFTest);
        
        //Validaciones de las líneas asociadas al país
        SecMenusWrapperStpV.validateLineas(pais, app, channel, datosStep, dFTest);

        return datosStep;
    }
    
    public static void validateIsPageOk(Pais pais, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la home de marcas/multimarcas según el país<br>" +
            "2) No aparece ningún tag de error";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (app!=AppEcom.outlet) {
                if (!PageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais, app, dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
            }
            if (WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//error"))) {
                listVals.add(2, State.Warn);
            }
           
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public enum TypeHome {Multimarca, PortadaLinea}
    public static void validaRebajasJun2018(TypeHome typeHome, boolean areBanners, DataCtxShop dCtxSh, 
    										DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSeconds = 3;
        String validacion1 = "Aparece la línea \"Rebajas\" (lo esperamos hasta " + maxSeconds + " segundos)";
        if (!dCtxSh.pais.isVentaOnline())
        	validacion1 = "No aparece la línea \"Rebajas\"";
        
        String descripValidac = 
            "<b style=\"color:blue\">Rebajas</b></br>" +
            "1) " + validacion1;
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (dCtxSh.pais.isVentaOnline()) {
	            if (!SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, maxSeconds, dFTest.driver)) {
	                listVals.add(1, State.Defect);
	            }
            }
            else {
                if (SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, 0, dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
            }

            datosStep.setListResultValidations(listVals);
            
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        if (areBanners) {
        	int maxBannersToLoad = 1;
        	DataBanner dataBanner1 = null;
            ManagerBannersScreen managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, dFTest.driver);
            descripValidac = 
                "<b style=\"color:blue\">Rebajas</b></br>" +
                "1) Existen banners<br>" +
                "2) El 1er Banner linca con la sección de rebajas";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
            listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!managerBannersScreen.existBanners()) {
                    listVals.add(1, State.Defect);
                }
                if (managerBannersScreen.existBanners()) {
                    dataBanner1 = managerBannersScreen.getBanner(1);
                    if (!dataBanner1.getUrlBanner().contains("seccion=Rebajas")) {
                        listVals.add(2, State.Warn);
                    }
                }
                else {
                    listVals.add(2, State.Warn);
                }
    
                datosStep.setListResultValidations(listVals);
                
            } finally { listVals.checkAndStoreValidations(descripValidac); }        
            
            if (dataBanner1!=null && typeHome==TypeHome.Multimarca) {
            	List<Linea> listLineas = dCtxSh.pais.getShoponline().getListLineasTiendas(dCtxSh.appE);
            	if (listLineas.size()>1) {
	                descripValidac = "<b style=\"color:blue\">Rebajas</b></br>";
	                int i=1;
	                for (Linea linea : listLineas) {
	                	descripValidac+=
	                	i + ") El 1er Banner contiene links a la línea " + linea.getType() + "<br>";
	                	i++;
	                }
	                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
                    listVals = ListResultValidation.getNew(datosStep);
	                try {
	                    //i)
	                    String urlLink;
		                i=1;
		                for (Linea linea : listLineas) {
		                    urlLink = dataBanner1.getUrlLinkLinea(linea.getType());
		                    if (!urlLink.contains("seccion=Rebajas_" + linea.getType().getId3())) {
		                    	listVals.add(i, State.Warn);		                	
		                    }
		                    i++;
		                }
		                
	                    datosStep.setListResultValidations(listVals);
	                    
	                } finally { listVals.checkAndStoreValidations(descripValidac); }        
            	}
            }
            
            String percentageSymbol = UtilsTestMango.getPercentageSymbol(dCtxSh.idioma);
            descripValidac = 
                "<b style=\"color:blue\">Rebajas</b></br>" +
                "1) El mensaje de NewsLetter del Footer no contiene \"" + percentageSymbol + "\"";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
            listVals = ListResultValidation.getNew(datosStep);
            try {
                if (SecFooter.getNewsLetterMsgText(dFTest.driver).contains(percentageSymbol)) {
                    listVals.add(1, State.Info_NoHardcopy);
                }
    
                datosStep.setListResultValidations(listVals);
                
            } finally { listVals.checkAndStoreValidations(descripValidac); }
        }
    }
}
