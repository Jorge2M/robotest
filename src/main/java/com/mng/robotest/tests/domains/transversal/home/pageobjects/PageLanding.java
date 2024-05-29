package com.mng.robotest.tests.domains.transversal.home.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.BannerObjectFactory;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.BannerType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageLanding extends PageBase {
	
	private static final String XP_MAIN_CONTENT_PAIS = "//div[@class[contains(.,'main-content')] and @data-pais]";
	
	private static final String XP_MULTIMARCA_OLD = "//div[@class[contains(.,'container-fluid home')]]";
	private static final String XP_MULTIMARCA_GENESIS = "//*[@data-testid='landings.home.multibrand']";
	private static final String XP_MULTIMARCA_SHOP = "(" + XP_MULTIMARCA_OLD + " | " + XP_MULTIMARCA_GENESIS + ")";
	private static final String XP_MULTIMARCA_OUTLET = "//*[" + 
			"@data-testid='landings.home.brand' or " + 
			"@class[contains(.,'HeroBannerPrimaryLines')]]"; //PRO
	
	private static final String XP_EDIT_ITEM = "//div[@class[contains(.,'item-edit')] and @data-id]";
	private static final String XP_MAP_T1 = "//map[@name[contains(.,'item_')]]/..";
	private static final String XP_MAP_T2 = "//img[@class[contains(.,'responsive')] and @hidefocus='true']";
	private static final String XP_LOYALTY_ELEMENT = "//*[@data-testid[contains(.,'Likes')] or @id[contains(.,'Likes')]]";
		
	private CommsHeaderBanner commsHeaderBanner = new CommsHeaderBanner();

	private String getXPMultimarca() {
		if (isOutlet()) {
			return XP_MULTIMARCA_OUTLET;
		}
		return XP_MULTIMARCA_SHOP;
	}

	public boolean isPageMultimarca() {
		return isPageMultimarca(0);
	}
	
	public boolean isPageMultimarca(int seconds) {
		return state(PRESENT, getXPMultimarca()).wait(seconds).check();
	}
	
	public boolean isPageDependingCountry() {
		return state(PRESENT, XP_MULTIMARCA_SHOP).check();
//		//Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
//		int numLineas = dataTest.getPais().getShoponline().getNumLineasTiendas(app);
//		if (numLineas < 3 || 
//			dataTest.getCodigoPais().equals("512") || 
//			dataTest.getCodigoPais().equals("504") || 
//			dataTest.getCodigoPais().equals("520")) {
//			if(!driver.getPageSource().contains("home")) {
//				return false;
//			}
//		}  else {
//			if (!state(PRESENT, "//*[@data-testid='landings.home.multibrand']").check() && //Genesis
//				!driver.getPageSource().contains("homeMarcas")) { //No Genesis
//				return false;
//			}
//		}
//		return true;
	}	
	
	public String getCodigoPais() {
		if (state(PRESENT, XP_MAIN_CONTENT_PAIS).check()) {
			return getElement(XP_MAIN_CONTENT_PAIS).getAttribute("data-pais");
		}
		return "";
	}
	
	public boolean hayMaps() {
		return !getListaMaps().isEmpty();
	}
	
	public boolean hayItemsEdits() {
		var listaItemsEdits = getListaItemsEdit();
		return (listaItemsEdits!=null && !listaItemsEdits.isEmpty());
	}
	
	public boolean hayImgsEnContenido() {
		boolean banners = true;
		String xpathImg = "";
		try {
			if (state(PRESENT, "//*[@class[contains(.,'bannerHome')]]").check()) {
				xpathImg = "//*[@class[contains(.,'bannerHome')]]//img";
			} else {
				if (state(PRESENT, "//*[@id[contains(.,'homeContent')]]").check()) {
					xpathImg = "//*[@id[contains(.,'homeContent')]]//img";
				} else {
					if (state(PRESENT, "//*[@class[contains(.,'contentHolder')]]").check()) {
						xpathImg = "//*[@class[contains(.,'contentHolder')]]//iframe";
					} else {
						if (state(PRESENT, "//*[@id[contains(.,'bodyContent')]]").check()) {
							xpathImg = "//*[@id[contains(.,'bodyContent')]]//img";
						} else {
							banners = false;
						}
					}
				}
			}
			state(PRESENT, xpathImg).wait(15).check();
		} catch (Exception e) {
			banners = false;
		}

		return banners;
	}

	public boolean isSeccArtBanners() {
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		if (pageGaleria.isVisibleAnyArticle()) {
			return true;
		}

		var pageFicha = PageFicha.make(Channel.desktop, app, dataTest.getPais());
		if (pageFicha.isPage(0)) {
			return true;
		}
		var banners = BannerObjectFactory.make(BannerType.STANDAR, app);
		return banners.isVisibleAnyBanner();
	}
	
	public boolean isVisibleCommsHeaderBannerLoyalty(int seconds) {
		return commsHeaderBanner.isHeaderBannerMangoLikesYou(seconds);
	}
	
	public boolean isVisibleAnyElementLoyalty() {
		return state(VISIBLE, XP_LOYALTY_ELEMENT).check();
	}
	
	private List<WebElement> getListaMaps() {
		List<WebElement> listMaps;
		listMaps = getElementsVisible(XP_MAP_T1);
		listMaps.addAll(getElementsVisible(XP_MAP_T2));
		return listMaps;
	}
	
	private List<WebElement> getListaItemsEdit() {
		List<WebElement> listItemsEdits;
		listItemsEdits = getElementsVisible(XP_EDIT_ITEM);
		return listItemsEdits;		
	}

}
