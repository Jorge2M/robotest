package com.mng.robotest.test80.mango.test.stpv.shop.home;

import java.util.List;
import org.openqa.selenium.WebDriver;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV.TypeHome;
import static com.mng.robotest.test80.mango.test.data.Constantes.PrefixRebajas;

public class BannerRebajas2019StpV {
	
    @Validation
    public static ChecksResult checkBanner(boolean salesOnInCountry, TypeHome typeHome, DataCtxShop dCtxSh, WebDriver driver) {
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
    		} else {
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
}
