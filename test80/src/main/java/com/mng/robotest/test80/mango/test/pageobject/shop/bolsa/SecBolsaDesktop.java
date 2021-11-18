package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public abstract class SecBolsaDesktop extends SecBolsa {


	public SecBolsaDesktop(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
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
		itTotalEntero = driver.findElements(byTotalEntero).listIterator();
		itTotalDecimal = driver.findElements(byTotalDecimal).listIterator();
		
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
			By byTotalEntero = By.xpath(xpathImpTransp + "//*[@class='bolsa_price_big']");
			By byTotalDecimal = By.xpath(xpathImpTransp + "//*[@class='bolsa_price_small']");
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
			if (channel==Channel.mobile) {
				setBolsaMobileToState(stateBolsaExpected);
			} else {
				setBolsaDesktopToState(stateBolsaExpected);
			}
		}
	}

	private void setBolsaDesktopToState(StateBolsa stateBolsaExpected) {
		SecCabecera secCabecera = SecCabecera.getNew(Channel.desktop, app, driver);
		secCabecera.clickIconoBolsaWhenDisp(2);
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void setBolsaMobileToState(StateBolsa stateBolsaExpected) {
		if (stateBolsaExpected==StateBolsa.Open) {
			//SecCabecera secCabecera = SecCabecera.getNew(Channel.desktop, app, driver);
			SecCabecera secCabecera = SecCabecera.getNew(Channel.mobile, app, driver);
			secCabecera.clickIconoBolsaWhenDisp(2);
		} else {
			clickIconoCloseMobile();
		}
		isInStateUntil(stateBolsaExpected, 2);
	}
	
	private void clickIconoCloseMobile() {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(Visible, By.xpath(xpathAspa), driver).check()) {
			click(By.xpath(xpathAspa), driver).exec();
		}
	}
	
}
