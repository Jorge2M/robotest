package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

/**
 * Section of Page Ficha that contains the button bolsa and the links below (Favoritos, Detalle del producto, Disponibilidad en tienda, Envio y devoluciones y Compartir)
 * @author jorge.munoz
 *
 */

public class SecBolsaButtonAndLinksNew extends WebdrvWrapp {

	public enum LinksAfterBolsa { DetalleProducto, DisponibilidadTienda, EnvioGratisTienda, Compartir }
	public enum ActionFavButton {Add, Remove}

	static String XPathButtonAddBolsa = "//div[@id='addCartContainer']//button[@id='productFormAdd']";
	static String IniXPathButtonFavoritos = "//div[@class='add-cart-container']//button[@id='productFormFavorites'";
	static String XPathButtonFavoritos = IniXPathButtonFavoritos + "]";
	static String XPathButtonFavoritosForAdd = IniXPathButtonFavoritos + " and @data-fav='false']";
	static String XPathButtonFavoritosForRemove = IniXPathButtonFavoritos + " and @data-fav='true']";
	static String XPathDivAnadiendoFavoritos = "//div[@id='favoriteBanner']";
	static String XPathBuscarEnTiendaButton = "//button[@class[contains(.,'garment-finder')]]";
	static String XPathLinkEnvioGratisTienda = "//button[@class[contains(.,'freeShipping')]]";
	static String XPathLinkDisponibilidadTienda = "//button[@id='garmentFinderOption']";
	static String XPathLinkDetalleProducto = "//button[@id='productDetailOption']";
	static String XPathLinkCompartir = "//*[@id='optionsSocialTrigger']";

	public static String getXPathButtonFavoritos(ActionFavButton actionButton) {
		switch (actionButton) {
		case Add:
			return XPathButtonFavoritosForAdd;
		case Remove:
		default:
			return XPathButtonFavoritosForRemove;
		}
	}

	public static String getXPathLink(LinksAfterBolsa linkType) {
		switch (linkType) {
		case EnvioGratisTienda:
			return XPathLinkEnvioGratisTienda;
		case DisponibilidadTienda:
			return XPathLinkDisponibilidadTienda;
		case DetalleProducto:
			return XPathLinkDetalleProducto;
		case Compartir:
		default:
			return XPathLinkCompartir;
		}
	}
	
	public static void clickAnadirBolsaButtonAndWait(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButtonAddBolsa), TypeOfClick.javascript);
	}

	public boolean isVisibleButtonFavoritos(WebDriver driver) {
		return (isElementVisible(driver, By.xpath(XPathButtonFavoritos)));
	}

	public static boolean isVisibleButtonFavoritos(ActionFavButton actionButton, WebDriver driver) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		return isElementVisibleUntil(driver, By.xpath(xpathButtonFav), 1);
	}

	public static void selectFavoritosButton(ActionFavButton actionButton, WebDriver driver) throws Exception {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		clickAndWaitLoad(driver, By.xpath(xpathButtonFav));
	}

	public static boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathDivAnadiendoFavoritos), maxSecondsToWait));
	}

	public static boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait, WebDriver driver) {
		return (isElementInvisibleUntil(driver, By.xpath(XPathDivAnadiendoFavoritos), maxSecondsToWait));
	}

	public static void clickLinkAndWaitLoad(LinksAfterBolsa linkType, WebDriver driver) 
	throws Exception {
		String xpathLink = getXPathLink(linkType);
		clickAndWaitLoad(driver, By.xpath(xpathLink));
	}
}
