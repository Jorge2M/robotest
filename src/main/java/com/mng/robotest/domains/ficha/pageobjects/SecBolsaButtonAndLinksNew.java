package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Section of Page Ficha that contains the button bolsa and the links below (Favoritos, Detalle del producto, Disponibilidad en tienda, Envio y devoluciones y Compartir)
 * @author jorge.munoz
 *
 */

public class SecBolsaButtonAndLinksNew {

	public enum LinksAfterBolsa { DETALLE_PRODUCTO, DISPONIBILIDAD_TIENDA, ENVIO_GRATIS_TIENDA, COMPARTIR }
	public enum ActionFavButton { ADD, REMOVE }

	private static final String XPATH_BUTTON_ADD_BOLSA = "//div[@id='addCartContainer']//button[@id='productFormAdd']";
	private static final String INI_XPATH_BUTTON_FAVORITOS = "//div[@class='add-cart-container']//button[@id='productFormFavorites'";
	private static final String XPATH_BUTTON_FAVORITOS = INI_XPATH_BUTTON_FAVORITOS + "]";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_ADD = INI_XPATH_BUTTON_FAVORITOS + " and @data-fav='false']";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_REMOVE = INI_XPATH_BUTTON_FAVORITOS + " and @data-fav='true']";
	private static final String XPATH_DIV_ANADIENDO_FAVORITOS = "//div[@id='favoriteBanner']";
	private static final String XPATH_BUSCAR_EN_TIENDA_BUTTON = "//button[@class[contains(.,'garment-finder')]]";
	private static final String XPATH_LINK_ENVIO_GRATIS_TIENDA = "//button[@class[contains(.,'freeShipping')]]";
	private static final String XPATH_LINK_DISPONIBILIDAD_TIENDA = "//button[@id='garmentFinderOption']";
	private static final String XPATH_LINK_DETALLE_PRODUCTO = "//button[@id='productDetailOption']";
	private static final String XPATH_LINK_COMPARTIR = "//*[@id='optionsSocialTrigger']";

	public static String getXPathButtonFavoritos(ActionFavButton actionButton) {
		switch (actionButton) {
		case ADD:
			return XPATH_BUTTON_FAVORITOS_FOR_ADD;
		case REMOVE:
		default:
			return XPATH_BUTTON_FAVORITOS_FOR_REMOVE;
		}
	}

	public static String getXPathLink(LinksAfterBolsa linkType) {
		switch (linkType) {
		case ENVIO_GRATIS_TIENDA:
			return XPATH_LINK_ENVIO_GRATIS_TIENDA;
		case DISPONIBILIDAD_TIENDA:
			return XPATH_LINK_DISPONIBILIDAD_TIENDA;
		case DETALLE_PRODUCTO:
			return XPATH_LINK_DETALLE_PRODUCTO;
		case COMPARTIR:
		default:
			return XPATH_LINK_COMPARTIR;
		}
	}
	
	public static void clickAnadirBolsaButtonAndWait(WebDriver driver) {
		click(By.xpath(XPATH_BUTTON_ADD_BOLSA), driver).type(javascript).exec();
	}

	public static boolean isVisibleButtonFavoritos(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_BUTTON_FAVORITOS), driver).check());
	}

	public static boolean isVisibleButtonFavoritos(ActionFavButton actionButton, WebDriver driver) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		return (state(Visible, By.xpath(xpathButtonFav), driver).wait(2).check());
	}

	public static void selectFavoritosButton(ActionFavButton actionButton, WebDriver driver) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		click(By.xpath(xpathButtonFav), driver).exec();
	}

	public static boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS), driver)
				.wait(maxSeconds).check());
	}

	public static void clickLinkAndWaitLoad(LinksAfterBolsa linkType, WebDriver driver) {
		String xpathLink = getXPathLink(linkType);
		click(By.xpath(xpathLink), driver).exec();
	}
	
	public static boolean checkLinkInState(LinksAfterBolsa linkType, State state, WebDriver driver) {
		String xpathLink = getXPathLink(linkType);
		return (state(state, By.xpath(xpathLink), driver).check());
	}
}
