package com.mng.robotest.test.pageobject.shop.favoritos;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen.Color;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;


public class ModalFichaFavoritos {
	
	private final WebDriver driver;
	
	private final static String XPathFichaProducto = "//div[@class='favorites-quickview']";
	private final static String XPathColorSelectedFicha = 
									XPathFichaProducto + 
									"//div[@class[contains(.,'color-item')] and @class[contains(.,'active')]]";
	private final static String XPathImgColorSelectedFicha = XPathColorSelectedFicha + "/img";
	
	private ModalFichaFavoritos(WebDriver driver) {
		this.driver = driver;
	}
	
	public static ModalFichaFavoritos getNew(WebDriver driver) {
		return new ModalFichaFavoritos(driver);
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	private String getXPathFichaProducto(String refProducto) {
		return (XPathFichaProducto + "//img[@src[contains(.,'" + refProducto + "')]]/ancestor::div[@class='favorites-quickview']");
	}
	
	private String getXPathButtonAddBolsa(String refProducto) {
		return (getXPathFichaProducto(refProducto) + "//div[@class[contains(.,'add-product')]]");
	}
	
	private String getXPathCapaTallas(String refProducto) {
		return (getXPathFichaProducto(refProducto) + "//div[@id[contains(.,'modalSelectSize')]]");
	}
	
	private String getXPathSelectorTalla(String refProducto) {
		return (getXPathFichaProducto(refProducto) + "//div[@id[contains(.,'sizeSelector')]]");
	}
	
	private String getXPathTalla(String refProducto) {
		return (getXPathCapaTallas(refProducto) + "//li[@onclick[contains(.,'changeSize')]]");
	}
	
	private String getXPathAspaFichaToClose(String refProducto) {
		return (getXPathFichaProducto(refProducto) + "//span[@id='closeQuickviewModal']");
	}
	
	public String getNombreColorSelectedFicha() {
		if (state(Visible, By.xpath(XPathImgColorSelectedFicha), driver).check()) {
			return (driver.findElement(By.xpath(XPathImgColorSelectedFicha)).getAttribute("title"));
		}
		return "";
	}
	
	public String getCodigoColorSelectedFicha() {
		if (state(Visible, By.xpath(XPathColorSelectedFicha), driver).check()) {
			String id = driver.findElement(By.xpath(XPathColorSelectedFicha)).getAttribute("id");
			if (!id.isEmpty()) {
				return (id.replace("color_", ""));
			}
		}
		return "";
	}
	
	public boolean isVisibleFichaUntil(String refProducto, int maxSeconds) {
		String xpathFicha = getXPathFichaProducto(refProducto);
		return (state(Visible, By.xpath(xpathFicha), driver).wait(maxSeconds).check());
	}
	
	public boolean isInvisibleFichaUntil(String refProducto, int maxSeconds) {
		String xpathFicha = getXPathFichaProducto(refProducto);
		return (state(Invisible, By.xpath(xpathFicha), driver)
				.wait(maxSeconds).check());
	}
	
	public boolean isColorSelectedInFicha(Color color) {
		if (!color.getCodigoColor().isEmpty()) {
			return (color.getCodigoColor().compareTo(getCodigoColorSelectedFicha())==0);
		}
		return (getNombreColorSelectedFicha().compareTo(color.getColorName())==0);
	}
	
	public Talla addArticleToBag(String refProducto, int posicionTalla, Channel channel, AppEcom app, Pais pais) 
	throws Exception {
		Talla tallaSelected = selectTallaAvailable(refProducto, posicionTalla);
		clickButtonAddToBagAndWait(refProducto, channel, app, pais);
		return tallaSelected;
	}
	
	public void clickButtonAddToBagAndWait(String refProducto, Channel channel, AppEcom app, Pais pais) 
	throws Exception {
		String xpathAdd = getXPathButtonAddBolsa(refProducto);
		driver.findElement(By.xpath(xpathAdd)).click();
		int maxSecondsToWait = 2;
		
		SecBolsa secBolsa = SecBolsa.make(channel, app, pais, driver);
		secBolsa.isInStateUntil(StateBolsa.Open, maxSecondsToWait);
	}
	
	public String selectTalla(String refProducto, int posicionTalla) {
		despliegaTallasAndWait(refProducto);
		WebElement talla = getListaTallas(refProducto).get(posicionTalla);
		String litTalla = talla.getText();
		talla.click();
		return litTalla;
	}
	
	public Talla selectTallaAvailable(String refProducto, int posicionTalla) {
		despliegaTallasAndWait(refProducto);
		List<WebElement> listTallas = getListaTallas(refProducto);
		
		//Filtramos y nos quedamos sólo con las tallas disponibles
		List<WebElement> listTallasAvailable = new ArrayList<>();
		for (WebElement talla : listTallas) {
			if (!state(Present, talla, driver).by(By.xpath("./span")).check()) {
				listTallasAvailable.add(talla);
			}
		}
		
		WebElement tallaDisponible = listTallasAvailable.get(posicionTalla - 1); 
		Talla talla = Talla.fromLabel(tallaDisponible.getText());
		tallaDisponible.click();
		return talla;
	}	
	
	public List<WebElement> getListaTallas(String refProducto) {
		String xpathTalla = getXPathTalla(refProducto);
		return (driver.findElements(By.xpath(xpathTalla)));
	}
	
	public void despliegaTallasAndWait(String refProducto) {
		String xpathSelector = getXPathSelectorTalla(refProducto);
		driver.findElement(By.xpath(xpathSelector)).click();
		String xpathCapaTallas = getXPathCapaTallas(refProducto);
		new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCapaTallas)));
	}
	
	public void closeFicha(String refProducto) {
		String xpathAspa = getXPathAspaFichaToClose(refProducto);
		driver.findElement(By.xpath(xpathAspa)).click();
	}
}