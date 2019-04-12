package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
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

public class PageHomeMarcasStpV {
	
	private final static String PrefixRebajas = "<b style=\"color:blue\">Rebajas</b></br>";

    public static void validateIsPageWithCorrectLineas(Pais pais, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        AllPagesStpV.validateMainContentPais(pais, driver);
        validateIsPageOk(pais, app, driver);
        SecMenusWrapperStpV.validateLineas(pais, app, channel, driver);
    }
    
    @Validation
    public static ChecksResult validateIsPageOk(Pais pais, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	if (app!=AppEcom.outlet) {
			validations.add(
				"Aparece la home de marcas/multimarcas según el país",
				PageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(pais, app, driver), State.Warn);    
    	}
		validations.add(
			"No aparece ningún tag de error",
			!WebdrvWrapp.isElementPresent(driver, By.xpath("//error")), State.Warn);
		return validations;
    }
    
    public enum TypeHome {Multimarca, PortadaLinea}
    public static void validaRebajasJun2018(boolean salesOnInCountry, TypeHome typeHome, boolean testBanner, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	checkLineaRebajas(salesOnInCountry, dCtxSh, driver);
    	if (testBanner) {
    		checkBannerRebajas(salesOnInCountry, typeHome, dCtxSh, driver);
    	}
    	//checkMsgNewsletterFooter(salesOnInCountry, dCtxSh.idioma, driver);
    }
    
    @Validation
    private static ChecksResult checkLineaRebajas(boolean salesOnInCountry, DataCtxShop dCtxSh, WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSeconds = 3;
        boolean isPresentLinRebajas = SecMenusWrap.isLineaPresentUntil(LineaType.rebajas, dCtxSh.appE, dCtxSh.channel, maxSeconds, driver);
        if (salesOnInCountry && dCtxSh.pais.isVentaOnline()) {
	    	validations.add(
	    		PrefixRebajas + "Aparece la línea \"Rebajas\" (lo esperamos hasta " + maxSeconds + " segundos)",
	    		isPresentLinRebajas, State.Defect);
        }
        else {
	    	validations.add(
	    		PrefixRebajas + "No aparece la línea \"Rebajas\"",
	    		!isPresentLinRebajas, State.Defect);
        }
        
        return validations;
    }
    
    @Validation
    private static ChecksResult checkBannerRebajas(boolean salesOnInCountry, TypeHome typeHome, DataCtxShop dCtxSh, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxBannersToLoad = 1;
    	DataBanner dataBanner1 = null;
        ManagerBannersScreen managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, driver);
        boolean existenBanners = managerBannersScreen.existBanners();
    	validations.add(
    		PrefixRebajas + "Existen banners",
    		existenBanners, State.Defect);    	
    	if (existenBanners) {
    		if (!salesOnInCountry) {
    			boolean isBannerThatLinkWithSale = false;
    			for (DataBanner dataBanner : managerBannersScreen.getListDataBanners()) {
    				if (dataBanner.getUrlBanner().contains("seccion=Rebajas")) {
    					isBannerThatLinkWithSale=true;
    					break;
    				}
    			}
	        	validations.add(
	        		"No hay ningún banner que linque con la sección de rebajas",
	        		!isBannerThatLinkWithSale, State.Warn);
    		}
    		else {
        		dataBanner1 = managerBannersScreen.getBanner(1);
        		boolean bannerLinkedWhithSales = dataBanner1.getUrlBanner().contains("seccion=Rebajas");
	        	validations.add(
	        		"El 1er Banner Sí linca con la sección de rebajas",
	        		bannerLinkedWhithSales, State.Warn);
        
	        	if (dataBanner1!=null && typeHome==TypeHome.Multimarca) {
		        	List<Linea> listLineas = dCtxSh.pais.getShoponline().getListLineasTiendas(dCtxSh.appE);
		        	if (listLineas.size()>1) {
		                for (Linea linea : listLineas) {
		                    String urlLink = dataBanner1.getUrlLinkLinea(linea.getType());
		                	validations.add(
		                		"El 1er Banner contiene links a la línea " + linea.getType(),
		                		urlLink.contains("seccion=Rebajas_" + linea.getType().getId3()), State.Warn);
		                }
		        	}
	        	}
    		}
    	}
    	
    	return validations;
    }
            
    @Validation
    private static ChecksResult checkMsgNewsletterFooter(boolean salesOnInCountry, IdiomaPais idioma, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
    	boolean isMsgWithPercentageSimbol = SecFooter.getNewsLetterMsgText(driver).contains(percentageSymbol);
    	if (salesOnInCountry) {
	    	validations.add(
	    		PrefixRebajas + "El mensaje de NewsLetter del Footer No contiene \"" + percentageSymbol + "\"",
	    		!isMsgWithPercentageSimbol, State.Info, true);    
    	}
    	else {
	    	validations.add(
	    		PrefixRebajas + "El mensaje de NewsLetter del Footer Sí contiene \"" + percentageSymbol + "\"",
	    		isMsgWithPercentageSimbol, State.Warn);    
    	}
    	
    	return validations;
    }
}
