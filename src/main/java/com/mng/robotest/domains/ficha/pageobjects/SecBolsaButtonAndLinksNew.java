package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecBolsaButtonAndLinksNew extends PageObjTM {

	public enum LinksAfterBolsa { DETALLE_PRODUCTO, DISPONIBILIDAD_TIENDA, ENVIO_GRATIS_TIENDA, COMPARTIR }
	public enum ActionFavButton { ADD, REMOVE }

	private static final String XPATH_BUTTON_ADD_BOLSA = "//div[@id='addCartContainer']//button[@id='productFormAdd']";
	private static final String INI_XPATH_BUTTON_FAVORITOS = "//div[@class='add-cart-container']//button[@id='productFormFavorites'";
	private static final String XPATH_BUTTON_FAVORITOS = INI_XPATH_BUTTON_FAVORITOS + "]";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_ADD = INI_XPATH_BUTTON_FAVORITOS + " and @data-fav='false']";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_REMOVE = INI_XPATH_BUTTON_FAVORITOS + " and @data-fav='true']";
	private static final String XPATH_DIV_ANADIENDO_FAVORITOS = "//div[@id='favoriteBanner']";
	//private static final String XPATH_BUSCAR_EN_TIENDA_BUTTON = "//button[@class[contains(.,'garment-finder')]]";
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
	
	public SecBolsaButtonAndLinksNew(WebDriver driver) {
		super(driver);
	}
	
	public void clickAnadirBolsaButtonAndWait() {
		click(By.xpath(XPATH_BUTTON_ADD_BOLSA)).type(javascript).exec();
	}

	public boolean isVisibleButtonFavoritos() {
		return (state(Visible, By.xpath(XPATH_BUTTON_FAVORITOS)).check());
	}

	public boolean isVisibleButtonFavoritos(ActionFavButton actionButton) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		return (state(Visible, By.xpath(xpathButtonFav)).wait(2).check());
	}

	public void selectFavoritosButton(ActionFavButton actionButton) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		click(By.xpath(xpathButtonFav)).exec();
	}

	public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS))
				.wait(maxSeconds).check());
	}

	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS))
				.wait(maxSeconds).check());
	}

	public void clickLinkAndWaitLoad(LinksAfterBolsa linkType) {
		String xpathLink = getXPathLink(linkType);
		click(By.xpath(xpathLink)).exec();
	}
	
	public boolean checkLinkInState(LinksAfterBolsa linkType, State state) {
		String xpathLink = getXPathLink(linkType);
		return (state(state, By.xpath(xpathLink)).check());
	}
}
