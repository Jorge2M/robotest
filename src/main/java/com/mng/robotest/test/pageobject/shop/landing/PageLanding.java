package com.mng.robotest.test.pageobject.shop.landing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerObject;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerObjectFactory;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerType;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop.Element;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageLanding extends PageBase {
	
	private static final String XPATH_MAIN_CONTENT_PAIS = "//div[@class[contains(.,'main-content')] and @data-pais]";
	private static final String XPATH_CONTENIDO = "//div[@class[contains(.,'container-fluid home')]]";
	private static final String XPATH_SLIDER = "//section[@class='entitieswrapper']//div[@class[contains(.,'vsv-slide')]]";
	private static final String XPATH_EDIT_ITEM = "//div[@class[contains(.,'item-edit')] and @data-id]";
	private static final String XPATH_MAP_T1 = "//map[@name[contains(.,'item_')]]/..";
	private static final String XPATH_MAP_T2 = "//img[@class[contains(.,'responsive')] and @hidefocus='true']";

	public boolean isPage() {
		return isPageUntil(0);
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return state(Present, XPATH_CONTENIDO).wait(maxSeconds).check();
	}
	
	public String getCodigoPais() {
		if (state(Present, XPATH_MAIN_CONTENT_PAIS).check()) {
			return getElement(XPATH_MAIN_CONTENT_PAIS).getAttribute("data-pais");
		}
		return "";
	}
	
	public boolean haySliders() {
		return state(Visible, XPATH_SLIDER).check();
	}
	
	public boolean hayMaps() {
		List<WebElement> listaMaps = getListaMaps();
		return (listaMaps!=null && listaMaps.size()>0);
	}
	
	public boolean hayItemsEdits() {
		List<WebElement> listaItemsEdits = getListaItemsEdit();
		return (listaItemsEdits!=null && listaItemsEdits.size()>0);
	}
	
	public List<WebElement> getListaMaps() {
		// Seleccionamos cada uno de los banners visibles
		List<WebElement> listMaps;
		listMaps = getElementsVisible(XPATH_MAP_T1);
		listMaps.addAll(getElementsVisible(XPATH_MAP_T2));
		return listMaps;
	}
	
	public List<WebElement> getListaItemsEdit() {
		List<WebElement> listItemsEdits;
		listItemsEdits = getElementsVisible(XPATH_EDIT_ITEM);
		return listItemsEdits;		
	}
	
	public boolean hayIframes() {
		List<WebElement> listaIFrames = getElementsVisible("//iframe");
		return (listaIFrames!=null && listaIFrames.size()>0);
	}
	
	public boolean hayImgsEnContenido() {
		boolean banners = true;
		String xpathImg = "";
		try {
			if (state(Present, "//*[@class[contains(.,'bannerHome')]]").check()) {
				xpathImg = "//*[@class[contains(.,'bannerHome')]]//img";
			} else {
				if (state(Present, "//*[@id[contains(.,'homeContent')]]").check()) {
					xpathImg = "//*[@id[contains(.,'homeContent')]]//img";
				} else {
					if (state(Present, "//*[@class[contains(.,'contentHolder')]]").check()) {
						xpathImg = "//*[@class[contains(.,'contentHolder')]]//iframe";
					} else {
						if (state(Present, "//*[@id[contains(.,'bodyContent')]]").check()) {
							xpathImg = "//*[@id[contains(.,'bodyContent')]]//img";
						} else {
							banners = false;
						}
					}
				}
			}

			new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathImg)));
		} catch (Exception e) {
			banners = false;
		}

		return banners;
	}

	public boolean haySecc_Art_Banners(AppEcom app) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		if (((PageGaleriaDesktop)pageGaleria).isVisibleAnyArticle()) {
			return true;
		}

		PageFicha pageFicha = PageFicha.of(Channel.desktop);
		if (pageFicha.isPageUntil(0)) {
			return true;
		}
		
		BannerObject banners = BannerObjectFactory.make(BannerType.Standar);
		if (banners.isVisibleAnyBanner()) {
			return true;
		}
		
		return false;
//		return (((PageGaleriaDesktop)pageGaleria).isVisibleAnyArticle() ||
//				state(Present, By.xpath(
//					"(//section[@id='section1'] | " + 
//					 "//div[@class[contains(.,'datos_ficha_producto')]] | " +
//					 "//*[@class='celda'])")).check());
	}
	
	public boolean isSomeElementVisibleInPage(List<Element> elementsCanBeContained, AppEcom app, Channel channel, int maxSeconds) 
	throws Exception {
		for (int i=0; i<maxSeconds; i++) {
			if (isSomeElementVisibleInPage(elementsCanBeContained, app, channel)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
		
	private boolean isSomeElementVisibleInPage(List<Element> elementsCanBeContained, AppEcom app, Channel channel) 
	throws Exception {
		for (Element element : elementsCanBeContained) {
			boolean elementContained = false;
			switch (element) {
			case article:
				PageGaleria pageGaleria = PageGaleria.getNew(channel);
				elementContained = pageGaleria.isVisibleArticleUntil(1, 3);
				break;
			case campaign:
				elementContained = ManagerBannersScreen.isBanners();
				break;
			case slider:
				elementContained = haySliders();
				break;
			case map:
				elementContained = hayMaps();
				break;
			case iframe:
				elementContained = hayIframes();
				break;
			}
				
			if (elementContained) {
				return true;
			}
		}
		
		return false;
	}
}
