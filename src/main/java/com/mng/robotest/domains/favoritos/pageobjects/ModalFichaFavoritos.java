package com.mng.robotest.domains.favoritos.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa.StateBolsa;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen.Color;

public class ModalFichaFavoritos extends PageBase {
	
	private static final String XPATH_FICHA_PRODUCTO = "//div[@class='favorites-quickview']";
	private static final String XPATH_COLOR_SELECTED_FICHA = XPATH_FICHA_PRODUCTO + 
			"//div[@class[contains(.,'color-item')] and @class[contains(.,'active')]]";
	private static final String XPATH_IMG_COLOR_SELECTED_FICHA = XPATH_COLOR_SELECTED_FICHA + "/img";

	private String getXPathFichaProducto(String refProducto) {
		return (XPATH_FICHA_PRODUCTO + "//img[@src[contains(.,'" + refProducto + "')]]/ancestor::div[@class='favorites-quickview']");
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
		if (state(Visible, XPATH_IMG_COLOR_SELECTED_FICHA).check()) {
			return (getElement(XPATH_IMG_COLOR_SELECTED_FICHA).getAttribute("title"));
		}
		return "";
	}
	
	public String getCodigoColorSelectedFicha() {
		if (state(Visible, XPATH_COLOR_SELECTED_FICHA).check()) {
			String id = getElement(XPATH_COLOR_SELECTED_FICHA).getAttribute("id");
			if (!id.isEmpty()) {
				return (id.replace("color_", ""));
			}
		}
		return "";
	}
	
	public boolean isVisibleFichaUntil(String refProducto, int maxSeconds) {
		String xpathFicha = getXPathFichaProducto(refProducto);
		return (state(Visible, xpathFicha).wait(maxSeconds).check());
	}
	
	public boolean isInvisibleFichaUntil(String refProducto, int maxSeconds) {
		String xpathFicha = getXPathFichaProducto(refProducto);
		return (state(Invisible, xpathFicha).wait(maxSeconds).check());
	}
	
	public boolean isColorSelectedInFicha(Color color) {
		if (!color.getCodigoColor().isEmpty()) {
			return (color.getCodigoColor().compareTo(getCodigoColorSelectedFicha())==0);
		}
		return (getNombreColorSelectedFicha().compareTo(color.getColorName())==0);
	}
	
	public Talla addArticleToBag(String refProducto, int posicionTalla) throws Exception {
		Talla tallaSelected = selectTallaAvailable(refProducto, posicionTalla);
		clickButtonAddToBagAndWait(refProducto);
		return tallaSelected;
	}
	
	public void clickButtonAddToBagAndWait(String refProducto) throws Exception {
		String xpathAdd = getXPathButtonAddBolsa(refProducto);
		getElement(xpathAdd).click();
		int maxSecondsToWait = 2;
		
		SecBolsa secBolsa = SecBolsa.make(channel, app);
		secBolsa.isInStateUntil(StateBolsa.OPEN, maxSecondsToWait);
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
			if (!state(Present, talla).by(By.xpath("./span")).check()) {
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
		state(State.Visible, xpathCapaTallas).wait(1).build();
	}
	
	public void closeFicha(String refProducto) {
		String xpathAspa = getXPathAspaFichaToClose(refProducto);
		driver.findElement(By.xpath(xpathAspa)).click();
	}
}
