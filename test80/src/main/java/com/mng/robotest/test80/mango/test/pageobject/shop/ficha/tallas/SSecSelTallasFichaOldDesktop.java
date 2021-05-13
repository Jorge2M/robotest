package com.mng.robotest.test80.mango.test.pageobject.shop.ficha.tallas;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;


public class SSecSelTallasFichaOldDesktop extends PageObjTM implements SSecSelTallasFicha {
	
    private final static String XPathSelectTalla = "//select[@id[contains(.,'productFormSelect')]]";
    private final static String XPathOptionTallaUnica = XPathSelectTalla + "/option[@data-available='true' and @value[contains(.,'99')]]";
    private final static String XPathOptionTalla = XPathSelectTalla + "/option[not(@data-text='0')]"; 
    
    public SSecSelTallasFichaOldDesktop(WebDriver driver) {
    	super(driver);
    }
    
    private String getXPathOptionTallaSegunDisponible(boolean disponible) {
        String disponibleStr = String.valueOf(disponible);
        return (XPathOptionTalla + "[@data-available='" + disponibleStr + "']");
    }
    
    private String getXPathOptionTallaSegunDisponible(boolean disponible, String talla) {
    	String xpathOption = getXPathOptionTallaSegunDisponible(disponible);
    	return (xpathOption + "//self::*[text()[contains(.,'" + talla + "')]]");
    }
    
    @Override
    public boolean isVisibleSelectorTallasUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathSelectTalla)).wait(maxSeconds).check());
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

    private Select despliegaSelectTallas() {
        return (new Select(driver.findElement(By.xpath(XPathSelectTalla))));
    }
    
    /**
     * @param value talla existente en el atributo value (se trata de la talla en formato número)
     */
    @Override
    public void selectTallaByValue(String tallaValue) {
        new Select(driver.findElement(By.xpath(XPathSelectTalla))).selectByValue(String.valueOf(tallaValue));
    }
    
    @Override
    public void selectTallaByIndex(int posicionEnDesplegable) {
        new Select(driver.findElement(By.xpath(XPathSelectTalla))).selectByIndex(posicionEnDesplegable);
    }
    
    @Override
    public void selectFirstTallaAvailable() {
        Select selectTalla = despliegaSelectTallas();
        List<WebElement> listOptions = selectTalla.getOptions();
        String valueTallaToSelect = "";
        for (WebElement talla : listOptions) {
            if ("true".compareTo(talla.getAttribute("data-available"))==0) {
                valueTallaToSelect = talla.getAttribute("value");
                break;
            }
        }
        
        selectTalla.selectByValue(valueTallaToSelect);
    }    
    
    /**
     * @return el literal visible de la talla seleccionada en el desplegable
     */
    @Override
    public String getTallaAlfSelected(AppEcom app) {
        Select select = despliegaSelectTallas();
        String tallaVisible = select.getFirstSelectedOption().getText(); 
        tallaVisible = removeAlmacenFromTalla(tallaVisible);
        
        //Tratamos el caso relacionado con los entornos de test y eliminamos la parte a partir de " - " para contemplar casos como el de 'S - Delivery in 4-7 business day')
        if (tallaVisible.indexOf(" - ") >= 0) {
            tallaVisible = tallaVisible.substring(0, tallaVisible.indexOf(" - "));
        }
        
        //Tratamos el caso de talla única donde unificamos el valor a "U"
        if (getTallaNumSelected().compareTo(Talla.U.getTallaNum())==0) {
            tallaVisible = Talla.U.name();
        }
        
        return tallaVisible;
    }
    
	/**
	 * @return el value de la talla seleccionada en el desplegable
	 */
	private String getTallaNumSelected() {
		Select select = despliegaSelectTallas();
		return (select.getFirstSelectedOption().getAttribute("value"));
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