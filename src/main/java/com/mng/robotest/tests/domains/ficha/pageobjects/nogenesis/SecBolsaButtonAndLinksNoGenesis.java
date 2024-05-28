package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ActionFavButton;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsaButtonAndLinksNoGenesis extends PageBase {

	private static final String XP_WRAPPER = "//div[@class='product-actions']";
	private static final String XP_BUTTON_ADD_BOLSA = "//*[@data-testid='pdp.form.addToCart']";
	private static final String XP_BUTTON_FAVORITOS = XP_WRAPPER + "//*[@data-testid='button-icon']";
	private static final String XP_BUTTON_FAVORITOS_FOR_ADD = XP_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'outline')]]";
	private static final String XP_BUTTON_FAVORITOS_FOR_REMOVE = XP_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'fill')]]";
	private static final String XP_DIV_ANADIENDO_FAVORITOS = "//div[@id='favoriteBanner']";

	private String getXPathButtonFavoritos(ActionFavButton actionButton) {
		switch (actionButton) {
		case ADD:
			return XP_BUTTON_FAVORITOS_FOR_ADD;
		case REMOVE:
		default:
			return XP_BUTTON_FAVORITOS_FOR_REMOVE;
		}
	}

	public boolean isVisibleBolsaButton(int seconds) {
		return state(VISIBLE, XP_BUTTON_ADD_BOLSA).wait(seconds).check();
	}
	
	public void clickAnadirBolsaButtonAndWait() {
		state(VISIBLE, XP_BUTTON_ADD_BOLSA).wait(2).check();
		click(XP_BUTTON_ADD_BOLSA).type(JAVASCRIPT).exec();
	}

	public boolean isVisibleButtonFavoritos(ActionFavButton actionButton, int seconds) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		return (state(VISIBLE, xpathButtonFav).wait(seconds).check());
	}

	public void selectFavoritosButton(ActionFavButton actionButton) {
		String xpathButtonFav = getXPathButtonFavoritos(actionButton);
		state(VISIBLE, xpathButtonFav).wait(2).check();
		click(xpathButtonFav).exec();
	}

	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(VISIBLE, XP_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(INVISIBLE, XP_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

}
