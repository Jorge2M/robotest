package com.mng.robotest.domains.ficha.pageobjects.tallas;

import java.util.List;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Talla;

public class SSecSelTallasFichaOldDesktop extends PageBase implements SSecSelTallasFicha {
	
	private static final String XPATH_SELECT_TALLA = "//select[@id[contains(.,'productFormSelect')]]";
	private static final String XPATH_OPTION_TALLA_UNICA = XPATH_SELECT_TALLA + "/option[@data-available='true' and @value[contains(.,'99')]]";
	private static final String XPATH_OPTION_TALLA = XPATH_SELECT_TALLA + "/option[not(@data-text='0')]"; 
	
	private String getXPathOptionTallaSegunDisponible(boolean disponible) {
		String disponibleStr = String.valueOf(disponible);
		return (XPATH_OPTION_TALLA + "[@data-available='" + disponibleStr + "']");
	}
	
	private String getXPathOptionTallaSegunDisponible(boolean disponible, String talla) {
		String xpathOption = getXPathOptionTallaSegunDisponible(disponible);
		return (xpathOption + "//self::*[text()[contains(.,'" + talla + "')]]");
	}
	
	@Override
	public boolean isSectionUntil(int seconds) {
		return isVisibleSelectorTallasUntil(seconds);
	}	
	
	@Override
	public boolean isVisibleSelectorTallasUntil(int seconds) {
		return (state(Visible, XPATH_SELECT_TALLA).wait(seconds).check());
	}
	
	@Override
	public int getNumOptionsTallas() {
		return getElements(XPATH_OPTION_TALLA).size();
	}
	
	@Override
	public int getNumOptionsTallasNoDisponibles() {
		String xpathOptions = getXPathOptionTallaSegunDisponible(false);
		return getElements(xpathOptions).size();
	}
	
	@Override
	public boolean isTallaAvailable(String talla) {
		String xpathTalla = getXPathOptionTallaSegunDisponible(true, talla);
		return state(Present, xpathTalla).check();
	}
	
	@Override
	public boolean isTallaUnica() {
		return state(Present, XPATH_OPTION_TALLA_UNICA).check();
	}
	
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return state(Visible, XPATH_OPTION_TALLA).wait(seconds).check();
	}

	private Select despliegaSelectTallas() {
		return new Select(getElement(XPATH_SELECT_TALLA));
	}
	
	/**
	 * @param value talla existente en el atributo value (se trata de la talla en formato número)
	 */
	@Override
	public void selectTallaByValue(String tallaValue) {
		new Select(getElement(XPATH_SELECT_TALLA)).selectByValue(String.valueOf(tallaValue));
	}
	
	@Override
	public void selectTallaByLabel(String tallaLabel) {
		new Select(getElement(XPATH_SELECT_TALLA)).selectByVisibleText(tallaLabel);
	}
	
	@Override
	public void selectTallaByIndex(int posicionEnDesplegable) {
		new Select(getElement(XPATH_SELECT_TALLA)).selectByIndex(posicionEnDesplegable);
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
		if (getTallaNumSelected().compareTo(Talla.T99.getValue())==0) {
			tallaVisible = Talla.T99.getLabels().get(0);
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
		String xpathTalla = "(" + XPATH_OPTION_TALLA + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getText();
		}
		return "";
	}
	
	@Override
	public String getTallaCodNum(int posicion) {
		String xpathTalla = "(" + XPATH_OPTION_TALLA + ")[" + posicion + "]";
		if (state(Present, xpathTalla).check()) {
			return getElement(xpathTalla).getAttribute("value");
		}
		return "";
	}
}
