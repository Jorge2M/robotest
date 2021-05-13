package com.mng.robotest.test80.mango.test.pageobject.shop.ficha.tallas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;


public class SSecSelTallasFichaOldDevice extends PageObjTM implements SSecSelTallasFicha {
	
	private final static String XPathSelectorButton = "//*[@data-testid='sizeSelectorButton']";
	private final static String XPathCapaTallas = "//div[@id='sizesContainerId']";
	private final static String XPathOptionTalla = XPathCapaTallas + "//span[@class='size-text']";
	private final static String XPathTallaSelected = XPathSelectorButton + "//span[@class='size-text']";
	private final static String XPathOptionTallaUnica = "//button[@id='productFormSelect]" + "//span[@class='size-text']";
    
    public SSecSelTallasFichaOldDevice(WebDriver driver) {
    	super(driver);
    }
    
    private String getXPathOptionTallaSegunDisponible(boolean disponible) {
        String symbol = (disponible) ? "<" : ">";
        return (XPathOptionTalla + "//self::*[string-length(normalize-space(text()))" + symbol + "20]");
    }
    
    private String getXPathOptionTallaSegunDisponible(boolean disponible, String talla) {
    	String xpathOption = getXPathOptionTallaSegunDisponible(disponible);
    	return (xpathOption + "//self::*[text()[contains(.,'" + talla + "')]]");
    }
    
    private String getXPathOptionTalla(String talla) {
    	return XPathOptionTalla + "//self::*[text()[contains(.,'" + talla + "')]]";
    }
    
    @Override
    public boolean isVisibleSelectorTallasUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathCapaTallas)).wait(maxSeconds).check());
    }
    
    @Override
    public int getNumOptionsTallas() {
        return (driver.findElements(By.xpath(XPathOptionTalla)).size());
    }
    
    @Override
    public int getNumOptionsTallasNoDisponibles() {
        String xpathOptions = getXPathOptionTallaSegunDisponible(false);
        return (driver.findElements(By.xpath(xpathOptions)).size());
    }
    
    @Override
    public boolean isTallaAvailable(String talla) {
    	String xpathTalla = getXPathOptionTallaSegunDisponible(true, talla);
    	return (state(Present, By.xpath(xpathTalla)).check());
    }
    
    @Override
    public boolean isTallaUnica() {
    	return (state(Present, By.xpath(XPathOptionTallaUnica)).check());
    }
    
    @Override
    public boolean isVisibleListTallasForSelectUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathOptionTalla)).wait(maxSeconds).check());
    }

    private void despliegaSelectTallas() {
    	state(State.Visible, By.xpath(XPathSelectorButton)).wait(2).check();
    	click(By.xpath(XPathSelectorButton)).exec();
    }
    
    @Override
    public void selectTallaByValue(String tallaNum) {
    	despliegaSelectTallas();
    	Talla talla = Talla.getTalla(tallaNum);
    	String xpathTalla = getXPathOptionTalla(talla.getName());
    	state(State.Visible, By.xpath(xpathTalla)).wait(2).check();
    	click(By.xpath(xpathTalla)).exec();
    }
    
    @Override
    public void selectTallaByIndex(int posicionEnDesplegable) {
    	despliegaSelectTallas();
    	click(By.xpath("(" + By.xpath(XPathOptionTalla + ")[" + posicionEnDesplegable + "]"))).exec();
    }
    
    @Override
    public void selectFirstTallaAvailable() {
        despliegaSelectTallas();
        String xpathTallaAvailable = getXPathOptionTallaSegunDisponible(true);
        click(By.xpath(xpathTallaAvailable)).exec();
    }    
    
    /**
     * @return el literal visible de la talla seleccionada en el desplegable
     */
    @Override
    public String getTallaAlfSelected(AppEcom app) {
        String tallaVisible = driver.findElement(By.xpath(XPathTallaSelected)).getText();
        tallaVisible = removeAlmacenFromTalla(tallaVisible);
        
        //Tratamos el caso relacionado con los entornos de test y eliminamos la parte a partir de " - " para contemplar casos como el de 'S - Delivery in 4-7 business day')
        if (tallaVisible.indexOf(" - ") >= 0) {
            tallaVisible = tallaVisible.substring(0, tallaVisible.indexOf(" - "));
        }
        
        return tallaVisible;
    }
    
	@Override
    public String getTallaAlf(int posicion) {
    	String xpathTalla = "(" + XPathOptionTalla + ")[" + posicion + "]";
    	if (state(Present, By.xpath(xpathTalla), driver).check()) {
    		return (driver.findElement(By.xpath(xpathTalla)).getText());
    	}
    	return "";
    }
    
	@Override
    public String getTallaCodNum(int posicion) {
    	String xpathTalla = "(" + XPathOptionTalla + ")[" + posicion + "]";
    	if (state(Present, By.xpath(xpathTalla), driver).check()) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("value"));
    	}
    	return "";
    }
}