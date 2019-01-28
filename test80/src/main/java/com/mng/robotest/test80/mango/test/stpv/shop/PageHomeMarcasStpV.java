package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (app!=AppEcom.outlet) {
                if (!PageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais, app, dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            //2)
            if (WebdrvWrapp.isElementPresent(dFTest.driver, By.xpath("//error"))) 
                fmwkTest.addValidation(2, State.Warn, listVals);
           
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (dCtxSh.pais.isVentaOnline()) {
	            if (!SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, maxSeconds, dFTest.driver))
	                fmwkTest.addValidation(1, State.Defect, listVals);
            }
            else {
                if (SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, 0, dFTest.driver))
                    fmwkTest.addValidation(1, State.Defect, listVals);
            }

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        if (areBanners) {
        	int maxBannersToLoad = 1;
        	DataBanner dataBanner1 = null;
            ManagerBannersScreen managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, dFTest.driver);
            descripValidac = 
                "<b style=\"color:blue\">Rebajas</b></br>" +
                "1) Existen banners<br>" +
                "2) El 1er Banner linca con la sección de rebajas";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!managerBannersScreen.existBanners())
                    fmwkTest.addValidation(1, State.Defect, listVals);
                //2)
                if (managerBannersScreen.existBanners()) {
                    dataBanner1 = managerBannersScreen.getBanner(1);
                    if (!dataBanner1.getUrlBanner().contains("seccion=Rebajas"))
                        fmwkTest.addValidation(2, State.Warn, listVals);
                }
                else
                    fmwkTest.addValidation(2, State.Warn, listVals);
    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
                
            } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
            
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
	                try {
	                    List<SimpleValidation> listVals = new ArrayList<>();
	                    //i)
	                    String urlLink;
		                i=1;
		                for (Linea linea : listLineas) {
		                    urlLink = dataBanner1.getUrlLinkLinea(linea.getType());
		                    if (!urlLink.contains("seccion=Rebajas_" + linea.getType().getId3()))
		                    	fmwkTest.addValidation(i, State.Warn, listVals);		                	
		                    i++;
		                }
		                
	                    datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
	                    
	                } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
            	}
            }
            
            String percentageSymbol = UtilsTestMango.getPercentageSymbol(dCtxSh.idioma);
            descripValidac = 
                "<b style=\"color:blue\">Rebajas</b></br>" +
                "1) El mensaje de NewsLetter del Footer no contiene \"" + percentageSymbol + "\"";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (SecFooter.getNewsLetterMsgText(dFTest.driver).contains(percentageSymbol))
                    fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
    
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
                
            } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
    }
}
