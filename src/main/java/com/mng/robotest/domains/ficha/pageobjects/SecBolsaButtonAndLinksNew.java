package com.mng.robotest.domains.ficha.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsaButtonAndLinksNew extends PageBase {

	public enum LinksAfterBolsa { DETALLE_PRODUCTO, DISPONIBILIDAD_TIENDA, ENVIO_GRATIS_TIENDA, COMPARTIR }
	public enum ActionFavButton { ADD, REMOVE }

	private static final String XPATH_WRAPPER = "//div[@class='product-actions']";
	private static final String XPATH_BUTTON_ADD_BOLSA = "//div[@id='addCartContainer']//button[@id='productFormAdd']";
	private static final String XPATH_BUTTON_FAVORITOS = XPATH_WRAPPER + "//*[@data-testid='button-icon']";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_ADD = XPATH_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'outline')]]";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_REMOVE = XPATH_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'fill')]]";
	private static final String XPATH_DIV_ANADIENDO_FAVORITOS = "//div[@id='favoriteBanner']";
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
	
	public void clickAnadirBolsaButtonAndWait() {
		state(Visible, XPATH_BUTTON_ADD_BOLSA).wait(2).check();
		click(XPATH_BUTTON_ADD_BOLSA).type(javascript).exec();
	}

	public boolean isVisibleButtonFavoritos() {
		return state(Visible, XPATH_BUTTON_FAVORITOS).check();
	}

	public boolean isVisibleButtonFavoritos(ActionFavButton actionButton, int seconds) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		return (state(Visible, xpathButtonFav).wait(seconds).check());
	}

	public void selectFavoritosButton(ActionFavButton actionButton) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		click(xpathButtonFav).waitLink(1).exec();
	}

	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(Visible, XPATH_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(Invisible, XPATH_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	public void clickLinkAndWaitLoad(LinksAfterBolsa linkType) {
		moveToElement(getXPathLink(linkType));
		click(getXPathLink(linkType)).exec();
	}
	
	public boolean checkLinkInState(LinksAfterBolsa linkType, State state) {
		String xpathLink = getXPathLink(linkType);
		return state(state, xpathLink).check();
	}
}
