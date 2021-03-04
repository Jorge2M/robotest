package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Bolsa"
 * @author jorge.munoz
 *
 */
public class SecBolsaMobile extends SecBolsa {
	
	private final LineasArtBolsa lineasArtBolsa;
	
    //TODO cuando suba a PRO la operativa eliminar el 1er XPath
    private static final String XPathAspa = "(//a[@class[contains(.,'iconCross')]] | //span[@class[contains(.,'outline-close')]])";
    private static final String XPathPanelBolsa = "//div[@class[contains(.,'m_bolsa')]]"; 
    private static final String XPathBotonComprar = "//div[@class='comButton']/span"; 
    private static final String XPathPrecioSubTotal = "//div[@class[contains(.,'totalPriceContainer')]]";
    
    
    public SecBolsaMobile(AppEcom app, WebDriver driver) {
    	super(Channel.mobile, app, driver);
    	lineasArtBolsa = new LineasArtBolsaMobile(driver);
    }
    
    @Override
    String getXPathPanelBolsa() {
    	return XPathPanelBolsa;
    }
    
    @Override
    String getXPathBotonComprar() {
    	return XPathBotonComprar;
    }
    
    @Override
    String getXPathPrecioSubTotal() {
    	return XPathPrecioSubTotal;
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
        
        By byTotalEntero = By.xpath("(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][1]");
        By byTotalDecimal = By.xpath("(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][2]");
        ListIterator<WebElement> itTotalEntero = driver.findElements(byTotalEntero).listIterator();
        ListIterator<WebElement> itTotalDecimal = driver.findElements(byTotalDecimal).listIterator();
        
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
		if (state(Present, By.xpath(xpathImpTransp), driver).check()) {
			By byTotalEntero = By.xpath("(" + xpathImpTransp + ")[1]" + "//span[1]");
			By byTotalDecimal = By.xpath("(" + xpathImpTransp + ")[1]" + "//span[2]");
			ListIterator<WebElement> itTotalEntero = driver.findElements(byTotalEntero).listIterator();
			ListIterator<WebElement> itTotalDecimal = driver.findElements(byTotalDecimal).listIterator();

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
		if (stateBolsaExpected==StateBolsa.Open) {
			SecCabecera secCabecera = SecCabecera.getNew(Channel.desktop, app, driver);
			secCabecera.clickIconoBolsaWhenDisp(2);
		} else {
			clickIconoClose();
		}
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void clickIconoClose() {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(Visible, By.xpath(xpathAspa), driver).check()) {
			click(By.xpath(xpathAspa), driver).exec();
		}
	}
	
	public void clickAspaMobil() {
		click(By.xpath(XPathAspa), driver).exec();
	}
	
}
