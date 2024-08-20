package com.mng.robotest.tests.domains.bolsa.pageobjects;

import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa.OPEN;

import java.net.URISyntaxException;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.favoritos.entity.Favorite;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.utils.ImporteScreen;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsa extends SecBolsaBase {

	private final LineasArticuloBolsa lineasArtBolsa = new LineasArticuloBolsa();
	
	private static final String XP_PANEL_BOLSA_DESKTOP = "//*[@data-testid='bag.opened']";
	private static final String XP_PANEL_BOLSA_MOBILE = "//*[@data-testid='bagpage.container']";
	private static final String XP_BOTON_COMPRAR = "//button[@data-testid[contains(.,'checkout.button')]]";
	private static final String XP_PRECIO_SUBTOTAL = "//*[@data-testid[contains(.,'subTotalprice')]]//*[@data-testid='currentPrice']";
	private static final String XP_SIGN_IN_BUTTON_MOBILE = "//*[@data-testid='shoppingCart.loginModal.cta.login']";
	private static final String XP_CREATE_ACCOUNT_BUTTON_MOBILE = "//*[@data-testid='shoppingCart.loginModal.cta.register']";	
	private static final String XP_CONTINUE_AS_GUEST_LINK_MOBILE = "//*[@data-testid='shoppingCart.loginModal.cta.guest']";
	private static final String XP_HEARTH_ICON = "//*[@data-testid='bag.item.moveToWishlist.button']";

	@Override
	String getXPathPanelBolsa() {
		if (isMobile()) {
			return XP_PANEL_BOLSA_MOBILE;
		}
		return XP_PANEL_BOLSA_DESKTOP;
	}
	
	@Override
	String getXPathBotonComprar() {
		return XP_BOTON_COMPRAR; 
	}
	
	@Override
	String getXPathPrecioSubTotal() {
		return XP_PRECIO_SUBTOTAL;
	}  
	
	@Override
	public LineasArticuloBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}	
	
	@Override
	String getXPathPrecioTransporte() {
		String xpathCapaBolsa = getXPathPanelBolsa();
		return xpathCapaBolsa + "//*[@class='contenedor_precio_transporte']"; 
	}
	
	@Override
	public String getPrecioSubTotal() {
		isInStateUntil(StateBolsa.OPEN, 2);
		var subtotalOpt = getElementIfExists(getXPathPrecioSubTotal());
		if (subtotalOpt.isPresent()) {
			String importScreen = subtotalOpt.get().getText();
			return ImporteScreen.normalizeImportFromScreen(importScreen);
		}
		return "0";
	} 
	
	@Override
	public String getPrecioTransporte() {
		String precioTotal = "0";
		String xpathImpTransp = getXPathPrecioTransporte();
		if (state(PRESENT, xpathImpTransp).check()) {
			String xpathTotalEntero = xpathImpTransp + "//*[@class='bolsa_price_big']";
			String xpathTotalDecimal = xpathImpTransp + "//*[@class='bolsa_price_small']";
			var itTotalEntero = getElements(xpathTotalEntero).listIterator();
			var itTotalDecimal = getElements(xpathTotalDecimal).listIterator();

			while (itTotalEntero != null && itTotalEntero.hasNext()) {
				precioTotal += itTotalEntero.next().getText();
			}
			while (itTotalDecimal != null && itTotalDecimal.hasNext()) {
				precioTotal += itTotalDecimal.next().getText();
			}
			precioTotal = ImporteScreen.normalizeImportFromScreen(precioTotal);
		}

		return precioTotal;
	}

	@Override
	public void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected) {
		if (!isInStateUntil(stateBolsaExpected, 1)) {
			if (isMobile()) {
				setBolsaMobileToState(stateBolsaExpected);
			} else {
				setBolsaDesktopToState(stateBolsaExpected);
			}
		}
	}
	
	@Override
	public void addArticleToFavorites() {
		var artBolsa = lineasArtBolsa.getArticuloDataByPosicion(1);
		click(XP_HEARTH_ICON).exec();
		moveFromBolsaToFavoritos(artBolsa);
	}
	
	private void moveFromBolsaToFavoritos(ArticuloDataBolsaScreen artBolsa) {
		var favorite = Favorite.from(artBolsa.getReferencia(), artBolsa.getCodColor());
		dataTest.addFavorite(favorite);
		dataTest.getDataBag().remove(favorite);
	}

	public void clickIniciarSesionMobile() {
		click(XP_SIGN_IN_BUTTON_MOBILE).exec();
	}
	
	public void clickContinuarSinCuentaMobile() {
		click(XP_CONTINUE_AS_GUEST_LINK_MOBILE).exec();
		
		//Random problem in click without effect
		if (!state(INVISIBLE, XP_CONTINUE_AS_GUEST_LINK_MOBILE).wait(1).check()) {
			click(XP_CONTINUE_AS_GUEST_LINK_MOBILE).exec();
		}
	}
	
	public void clickRegistroMobile() {
		state(VISIBLE, XP_CREATE_ACCOUNT_BUTTON_MOBILE).wait(1).check();
		click(XP_CREATE_ACCOUNT_BUTTON_MOBILE).exec();
	}	
	
	public boolean isVisibleContinuarSinCuentaButtonMobile(int seconds) {
		return state(VISIBLE, XP_CONTINUE_AS_GUEST_LINK_MOBILE).wait(seconds).check();
	}

	private void setBolsaDesktopToState(StateBolsa stateBolsaExpected) {
		SecCabecera.make().clickIconoBolsaWhenDisp(2);
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void setBolsaMobileToState(StateBolsa stateBolsaExpected) {
		if (stateBolsaExpected==StateBolsa.OPEN) {
			SecCabecera.make().clickIconoBolsaWhenDisp(2);
			manageRandomProblemInPreDevice();
		} else {
			if (isOutlet()) {
				clickIconoCloseMobile();
			} else {
				driver.navigate().back();
			}
		}
		isInStateUntil(stateBolsaExpected, 3);
	}

	//Manejo de problema random en PRE según el cual la página pasa
	//a visión Desktop después de seleccionar el icono
	private void manageRandomProblemInPreDevice() {
		if (UtilsTest.todayBeforeDate("2025-02-09") && 
			!isInStateUntil(OPEN, 1) && !isPRO()) {
			try {
				driver.get(inputParamsSuite.getDnsUrlAcceso() + "/mobil");
				SecCabecera.make().clickIconoBolsaWhenDisp(2);
			} catch (URISyntaxException e) {
				Log4jTM.getLogger().warn("Problem managing bag problem", e);
			}
		}
	}
	
	private void clickIconoCloseMobile() {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(VISIBLE, xpathAspa).check()) {
			click(xpathAspa).exec();
		}
	}
	
}
