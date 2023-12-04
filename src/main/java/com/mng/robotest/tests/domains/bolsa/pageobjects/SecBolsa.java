package com.mng.robotest.tests.domains.bolsa.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsa extends SecBolsaCommon {

	private final LineasArticuloBolsa lineasArtBolsa = new LineasArticuloBolsa();
	
	private static final String XP_PANEL_BOLSA_DESKTOP = "//*[@data-testid='bag.opened']";
	private static final String XP_PANEL_BOLSA_MOBILE = "//*[@data-testid='bagpage.container']";
	private static final String XP_BOTON_COMPRAR = "//button[@data-testid[contains(.,'checkout.button')]]";
	private static final String XP_PRECIO_SUBTOTAL_MOBILE = "//*[@data-testid[contains(.,'subTotalprice')]]//*[@data-testid='currentPrice']";
	private static final String XP_PRECIO_SUBTOTAL_DESKTOP = "//*[@data-testid='bag.preview.summary.price']//*[@data-testid='currentPrice']";
	private static final String XP_INICIAR_SESION_MOBILE = "//*[@data-testid[contains(.,'goToLogin')]]";
	private static final String XP_CONTINUAR_SIN_CUENTA_BUTTON_MOBILE = "//*[@data-testid[contains(.,'goToContinueAsGuest')]]";	

	@Override
	String getXPathPanelBolsa() {
		if (channel==Channel.mobile) {
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
		if (channel==Channel.mobile) {
			return XP_PRECIO_SUBTOTAL_MOBILE;
		}
		return XP_PRECIO_SUBTOTAL_DESKTOP;
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
		String precioTotal = getElement(getXPathPrecioSubTotal()).getText(); 
		return ImporteScreen.normalizeImportFromScreen(precioTotal);
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
			if (channel==Channel.mobile) {
				setBolsaMobileToState(stateBolsaExpected);
			} else {
				setBolsaDesktopToState(stateBolsaExpected);
			}
		}
	}

	public void clickIniciarSesionMobile() {
		click(XP_INICIAR_SESION_MOBILE).exec();
	}
	
	public void clickContinuarSinCuentaMobile() {
		click(XP_CONTINUAR_SIN_CUENTA_BUTTON_MOBILE).exec();
	}
	
	public boolean isVisibleContinuarSinCuentaButtonMobile(int seconds) {
		return state(VISIBLE, XP_CONTINUAR_SIN_CUENTA_BUTTON_MOBILE).wait(seconds).check();
	}

	private void setBolsaDesktopToState(StateBolsa stateBolsaExpected) {
		SecCabecera.make().clickIconoBolsaWhenDisp(2);
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void setBolsaMobileToState(StateBolsa stateBolsaExpected) {
		if (stateBolsaExpected==StateBolsa.OPEN) {
			SecCabecera.make().clickIconoBolsaWhenDisp(2);
		} else {
			if (app==AppEcom.outlet) {
				clickIconoCloseMobile();
			} else {
				driver.navigate().back();
			}
		}
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void clickIconoCloseMobile() {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(VISIBLE, xpathAspa).check()) {
			click(xpathAspa).exec();
		}
	}
	
}
