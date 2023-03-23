package com.mng.robotest.domains.bolsa.pageobjects;

import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsa extends SecBolsaCommon {

	private final LineasArticuloBolsa lineasArtBolsa = new LineasArticuloBolsa();
	
	private static final String XPATH_PANEL_BOLSA_DESKTOP = "//*[@data-testid='bag.opened']";
	private static final String XPATH_PANEL_BOLSA_MOBILE = "//*[@data-testid='bagpage.container']";
	private static final String XPATH_BOTON_COMPRAR = "//button[@data-testid[contains(.,'checkout.button')]]";
	private static final String XPATH_PRECIO_SUBTOTAL_DESKTOP = "//*[@data-testid='bag.preview.summary.price']";
	private static final String XPATH_PRECIO_SUBTOTAL_MOBILE = "//*[@data-testid='bag.preview.summary.price']";

	@Override
	String getXPathPanelBolsa() {
		if (channel==Channel.mobile) {
			return XPATH_PANEL_BOLSA_MOBILE;
		}
		return XPATH_PANEL_BOLSA_DESKTOP;
	}
	
	@Override
	String getXPathBotonComprar() {
		return XPATH_BOTON_COMPRAR; 
	}
	
	@Override
	String getXPathPrecioSubTotal() {
		if (channel==Channel.mobile) {
			return XPATH_PRECIO_SUBTOTAL_MOBILE;
		}
		return XPATH_PRECIO_SUBTOTAL_DESKTOP;
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
		String precioTotal = "";
		ListIterator<WebElement> itTotalEntero = null;
		ListIterator<WebElement> itTotalDecimal = null;
		String xpathCapaBolsa = getXPathPanelBolsa();
		String xpathSubtotal = getXPathPrecioSubTotal();
		By byTotalEntero = By.xpath(xpathCapaBolsa + xpathSubtotal + "//*[@class='bolsa_price_big']");
		By byTotalDecimal = By.xpath(xpathCapaBolsa + xpathSubtotal + "//*[@class='bolsa_price_small']");
		itTotalEntero = getElements(byTotalEntero).listIterator();
		itTotalDecimal = getElements(byTotalDecimal).listIterator();
		
		while (itTotalEntero != null && itTotalEntero.hasNext()) {
			precioTotal += itTotalEntero.next().getText();
		}
		while (itTotalDecimal != null && itTotalDecimal.hasNext()) {
			precioTotal += itTotalDecimal.next().getText();
		}

		return (ImporteScreen.normalizeImportFromScreen(precioTotal));
	} 
	
	@Override
	public String getPrecioTransporte() {
		String precioTotal = "0";
		String xpathImpTransp = getXPathPrecioTransporte();
		if (state(Present, xpathImpTransp).check()) {
			String xpathTotalEntero = xpathImpTransp + "//*[@class='bolsa_price_big']";
			String xpathTotalDecimal = xpathImpTransp + "//*[@class='bolsa_price_small']";
			ListIterator<WebElement> itTotalEntero = getElements(xpathTotalEntero).listIterator();
			ListIterator<WebElement> itTotalDecimal = getElements(xpathTotalDecimal).listIterator();

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

	private void setBolsaDesktopToState(StateBolsa stateBolsaExpected) {
		var secCabecera = new SecCabeceraMostFrequent();
		secCabecera.clickIconoBolsaWhenDisp(2);
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void setBolsaMobileToState(StateBolsa stateBolsaExpected) {
		if (stateBolsaExpected==StateBolsa.OPEN) {
			new SecCabeceraMostFrequent().clickIconoBolsaWhenDisp(2);
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
		if (state(Visible, xpathAspa).check()) {
			click(xpathAspa).exec();
		}
	}
	
}
