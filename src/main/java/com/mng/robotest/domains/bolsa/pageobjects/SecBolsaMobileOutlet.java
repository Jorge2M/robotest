package com.mng.robotest.domains.bolsa.pageobjects;

import java.util.ListIterator;

import org.openqa.selenium.WebElement;

import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsaMobileOutlet extends SecBolsa {
	
	private final LineasArtBolsa lineasArtBolsa = new LineasArtBolsaMobile();
	
	private static final String XPATH_PANEL_BOLSA = "//div[@class[contains(.,'m_bolsa')]]"; 
	private static final String XPATH_BOTON_COMPRAR = "//div[@class='comButton']/span"; 
	private static final String XPATH_PRECIO_SUB_TOTAL = "//div[@class[contains(.,'totalPriceContainer')]]";
	
	@Override
	String getXPathPanelBolsa() {
		return XPATH_PANEL_BOLSA;
	}
	
	@Override
	String getXPathBotonComprar() {
		return XPATH_BOTON_COMPRAR;
	}
	
	@Override
	String getXPathPrecioSubTotal() {
		return XPATH_PRECIO_SUB_TOTAL;
	} 
	
	@Override
	String getXPathPrecioTransporte() {
		String xpathCapaBolsa = getXPathPanelBolsa();
		return "(" + xpathCapaBolsa + "//div[@class[contains(.,'totalPriceContainer')]])[2]"; 
	}
	
	@Override
	public String getPrecioSubTotal() {
		String precioTotal = "";
		String xpathCapaBolsa = getXPathPanelBolsa();
		String xpathSubtotal = getXPathPrecioSubTotal();
		
		String xpathTotalEntero = "(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][1]";
		String xpathTotalDecimal = "(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][2]";
		ListIterator<WebElement> itTotalEntero = getElements(xpathTotalEntero).listIterator();
		ListIterator<WebElement> itTotalDecimal = getElements(xpathTotalDecimal).listIterator();
		
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
			String xpathTotalEntero = "(" + xpathImpTransp + ")[1]" + "//span[1]";
			String xpathTotalDecimal = "(" + xpathImpTransp + ")[1]" + "//span[2]";
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
			setBolsaToState(stateBolsaExpected);
		}
	}
	
	@Override
	public LineasArtBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}
	
	private void setBolsaToState(StateBolsa stateBolsaExpected) {
		if (stateBolsaExpected==StateBolsa.OPEN) {
			SecCabecera secCabecera = SecCabecera.getNew(channel, app);
			secCabecera.clickIconoBolsaWhenDisp(2);
		} else {
			clickIconoClose();
		}
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void clickIconoClose() {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(Visible, xpathAspa).check()) {
			click(xpathAspa).exec();
		}
	}
	
}
