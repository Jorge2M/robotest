package com.mng.robotest.tests.domains.transversal.home.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.BannerObjectFactory;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.BannerType;
import com.mng.robotest.tests.domains.transversal.banners.pageobjects.ManagerBannersScreen;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuLateralDesktop.Element;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageLanding extends PageBase {
	
	private static final String XP_MAIN_CONTENT_PAIS = "//div[@class[contains(.,'main-content')] and @data-pais]";
	private static final String XP_CONTENIDO = "//div[@class[contains(.,'container-fluid home')]]";
	private static final String XP_SLIDER = "//section[@class='entitieswrapper']//div[@class[contains(.,'vsv-slide')]]";
	private static final String XP_EDIT_ITEM = "//div[@class[contains(.,'item-edit')] and @data-id]";
	private static final String XP_MAP_T1 = "//map[@name[contains(.,'item_')]]/..";
	private static final String XP_MAP_T2 = "//img[@class[contains(.,'responsive')] and @hidefocus='true']";
	private static final String XP_LOYALTY_ELEMENT = "//*[@data-testid[contains(.,'Likes')] or @id[contains(.,'Likes')]]";
		
	private CommsHeaderBanner commsHeaderBanner = new CommsHeaderBanner();

	public boolean isPage() {
		return isPage(0);
	}
	
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_CONTENIDO).wait(seconds).check();
	}
	
	public boolean isPageDependingCountry() {
		//Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
		int numLineas = dataTest.getPais().getShoponline().getNumLineasTiendas(app);
		if (numLineas < 3 || 
			dataTest.getCodigoPais().equals("512") || 
			dataTest.getCodigoPais().equals("504") || 
			dataTest.getCodigoPais().equals("520")) {
			if(!driver.getPageSource().contains("home")) {
				return false;
			}
		}  else {
			if (!driver.getPageSource().contains("homeMarcas")) {
				return false;
			}
		}
		return true;
	}	
	
	public String getCodigoPais() {
		if (state(PRESENT, XP_MAIN_CONTENT_PAIS).check()) {
			return getElement(XP_MAIN_CONTENT_PAIS).getAttribute("data-pais");
		}
		return "";
	}
	
	public boolean haySliders() {
		return state(VISIBLE, XP_SLIDER).check();
	}
	
	public boolean hayMaps() {
		var listaMaps = getListaMaps();
		return (listaMaps!=null && !listaMaps.isEmpty());
	}
	
	public boolean hayItemsEdits() {
		var listaItemsEdits = getListaItemsEdit();
		return (listaItemsEdits!=null && !listaItemsEdits.isEmpty());
	}
	
	public List<WebElement> getListaMaps() {
		// Seleccionamos cada uno de los banners visibles
		List<WebElement> listMaps;
		listMaps = getElementsVisible(XP_MAP_T1);
		listMaps.addAll(getElementsVisible(XP_MAP_T2));
		return listMaps;
	}
	
	public List<WebElement> getListaItemsEdit() {
		List<WebElement> listItemsEdits;
		listItemsEdits = getElementsVisible(XP_EDIT_ITEM);
		return listItemsEdits;		
	}
	
	public boolean hayIframes() {
		List<WebElement> listaIFrames = getElementsVisible("//iframe");
		return (listaIFrames!=null && !listaIFrames.isEmpty());
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

	public boolean haySecc_Art_Banners() {
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		if (((PageGaleriaDesktop)pageGaleria).isVisibleAnyArticle()) {
			return true;
		}

		var pageFicha = PageFicha.of(Channel.desktop);
		if (pageFicha.isPage(0)) {
			return true;
		}
		var banners = BannerObjectFactory.make(BannerType.STANDAR);
		return banners.isVisibleAnyBanner();
	}
	
	public boolean isSomeElementVisibleInPage(
			List<Element> elementsCanBeContained, Channel channel, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isSomeElementVisibleInPage(elementsCanBeContained, channel)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
		
	private boolean isSomeElementVisibleInPage(List<Element> elementsCanBeContained, Channel channel) {
		for (var element : elementsCanBeContained) {
			boolean elementContained = false;
			switch (element) {
			case ARTICLE:
				var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
				elementContained = pageGaleria.isVisibleArticleUntil(1, 3);
				break;
			case CAMPAIGN:
				elementContained = ManagerBannersScreen.isBanners();
				break;
			case SLIDER:
				elementContained = haySliders();
				break;
			case MAP:
				elementContained = hayMaps();
				break;
			case IFRAME:
				elementContained = hayIframes();
				break;
			}
				
			if (elementContained) {
				return true;
			}
		}
		return false;
	}
	
	private String getXPathMainContent() {
		return ("//div[@class[contains(.,'main-content')] and @data-pais='" + dataTest.getCodigoPais() + "']");
	}

	public boolean isPresentMainContent() {
		return state(PRESENT, getXPathMainContent()).check();
	}
	
	public boolean isVisibleCommsHeaderBannerLoyalty(int seconds) {
		return commsHeaderBanner.isHeaderBannerMangoLikesYou(seconds);
	}
	
	public boolean isVisibleAnyElementLoyalty() {
		return state(VISIBLE, XP_LOYALTY_ELEMENT).check();
	}

}
