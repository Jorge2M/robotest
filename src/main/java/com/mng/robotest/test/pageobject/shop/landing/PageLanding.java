package com.mng.robotest.test.pageobject.shop.landing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerObject;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerObjectFactory;
import com.mng.robotest.test.pageobject.shop.bannersNew.BannerType;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop.Element;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageLanding extends PageObjTM {
	
	private static String XPATH_MAIN_CONTENT_PAIS = "//div[@class[contains(.,'main-content')] and @data-pais]";
	private static String XPATH_CONTENIDO = "//div[@class[contains(.,'container-fluid home')]]";
	private static String XPATH_SLIDER = "//section[@class='entitieswrapper']//div[@class[contains(.,'vsv-slide')]]";
	private static String XPATH_EDIT_ITEM = "//div[@class[contains(.,'item-edit')] and @data-id]";
	private static String XPATH_MAP_T1 = "//map[@name[contains(.,'item_')]]/..";
	private static String XPATH_MAP_T2 = "//img[@class[contains(.,'responsive')] and @hidefocus='true']";

	public boolean isPage() {
		return (isPageUntil(0));
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_CONTENIDO)).wait(maxSeconds).check());
	}
	
	public String getCodigoPais() {
		if (state(Present, By.xpath(XPATH_MAIN_CONTENT_PAIS)).check()) {
			return (driver.findElement(By.xpath(XPATH_MAIN_CONTENT_PAIS)).getAttribute("data-pais"));
		}
		return "";
	}
	
	public boolean haySliders() {
		return (state(Visible, By.xpath(XPATH_SLIDER)).check());
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
		listMaps = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPATH_MAP_T1));
		listMaps.addAll(UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPATH_MAP_T2)));
		return listMaps;
	}
	
	public List<WebElement> getListaItemsEdit() {
		List<WebElement> listItemsEdits;
		listItemsEdits = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPATH_EDIT_ITEM));
		return listItemsEdits;		
	}
	
	/**
	 * Función que indica si hay secciones de vídeo
	 */
	public boolean hayIframes() {
		List<WebElement> listaIFrames = UtilsMangoTest.findDisplayedElements(driver, By.xpath("//iframe"));
		return (listaIFrames!=null && listaIFrames.size()>0);
	}
	
	/**
	 * Función que indica si hay imágenes o no en el contenido de la página
	 */
	public boolean hayImgsEnContenido() {
		boolean banners = true;
		String xpathImg = "";
		try {
			if (state(Present, By.xpath("//*[@class[contains(.,'bannerHome')]]")).check()) {
				xpathImg = "//*[@class[contains(.,'bannerHome')]]//img";
			} else {
				if (state(Present, By.xpath("//*[@id[contains(.,'homeContent')]]")).check()) {
					xpathImg = "//*[@id[contains(.,'homeContent')]]//img";
				} else {
					if (state(Present, By.xpath("//*[@class[contains(.,'contentHolder')]]")).check()) {
						xpathImg = "//*[@class[contains(.,'contentHolder')]]//iframe";
					} else {
						if (state(Present, By.xpath("//*[@id[contains(.,'bodyContent')]]")).check()) {
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
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app);
		if (((PageGaleriaDesktop)pageGaleria).isVisibleAnyArticle()) {
			return true;
		}

		PageFicha pageFicha = PageFicha.newInstance(Channel.desktop, app);
		if (pageFicha.isPageUntil(0)) {
			return true;
		}
		
		BannerObject banners = BannerObjectFactory.make(BannerType.Standar);
		if (banners.isVisibleAnyBanner(driver)) {
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
			PageObjTM.waitMillis(1000);
		}
		return false;
	}
		
	private boolean isSomeElementVisibleInPage(List<Element> elementsCanBeContained, AppEcom app, Channel channel) 
	throws Exception {
		for (Element element : elementsCanBeContained) {
			boolean elementContained = false;
			switch (element) {
			case article:
				PageGaleria pageGaleria = PageGaleria.getNew(channel, app);
				elementContained = pageGaleria.isVisibleArticleUntil(1, 3);
				break;
			case campaign:
				elementContained = ManagerBannersScreen.existBanners(driver);
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
