package com.mng.robotest.tests.domains.registro.pageobjects;

import java.util.StringTokenizer;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.registro.beans.DataNino;
import com.mng.robotest.tests.domains.registro.beans.ListDataNinos;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageRegistroNinosOutlet extends PageBase {
	
	private static final String XP_INPUT_NOMBRE = "//input[@id[contains(.,'cfNameKid')]]";
	private static final String XP_SELECT_DIA_NACIMIENTO = "//select[@id[contains(.,'naciDia')]]";
	private static final String XP_SELECT_MES_NACIMIENTO = "//select[@id[contains(.,'naciMes')]]";
	private static final String XP_SELECT_ANY_NACIMIENTO = "//select[@id[contains(.,'naciAny')]]";
	private static final String XP_BOTON_CONTINUAR = 
			"//div[@class[contains(.,'registerStepsModal')]]" + 
	        "//form[@id[contains(.,'cfKids')]]//input[@type='submit']";
	
	public boolean isPage(int seconds) {
		String xpath = "//form[@id[contains(.,'cfKids')]]";
		return state(PRESENT, xpath).wait(seconds).check();
	}
	
	public int getNumInputsNameNino() {
		return getElements(XP_INPUT_NOMBRE).size();
	}
	
	public String getXPathInputNombre(int numNino) {
		return ("(" + XP_INPUT_NOMBRE + ")[" + numNino + "]");
	}
	
	public String getXPathSelectDiaNac(int numNino) {
		return ("(" + XP_SELECT_DIA_NACIMIENTO + ")[" + numNino + "]");
	}
	
	public String getXPathSelectMesNac(int numNino) {
		return ("(" + XP_SELECT_MES_NACIMIENTO + ")[" + numNino + "]");
	}
	
	public String getXPathSelectAnyNac(int numNino) {
		return ("(" + XP_SELECT_ANY_NACIMIENTO + ")[" + numNino + "]");
	}
	
	/**
	/* Repetimos la introducción de datos n times para paliar errores aleatorios de desaparición de datos ya introducidos
	 */
	public void setDataNinoIfNotExists(ListDataNinos listNinos, int nTimes) {
		for (int i=0; i<nTimes; i++)
			setDataNinoIfNotExists(listNinos);
	}
	
	public void setDataNinoIfNotExists(ListDataNinos listNinos) {
		int i=1;
		for (DataNino dataNino : listNinos.getListNinos()) {
			String nombreNino = dataNino.getNombre();
			String xpathInputNombreNino = getXPathInputNombre(i);
			if (getElement(xpathInputNombreNino).getAttribute("value").compareTo(nombreNino)!=0) {
				getElement(xpathInputNombreNino).clear();
				getElement(xpathInputNombreNino).sendKeys(nombreNino);
			}
			
			String xpathDia = getXPathSelectDiaNac(i);
			String xpathMes = getXPathSelectMesNac(i);
			String xpathAny = getXPathSelectAnyNac(i);
			var fechaToken = new StringTokenizer(dataNino.getFechaNacimiento(), "/");
			new Select(getElement(xpathDia)).selectByVisibleText(fechaToken.nextToken()); //Día nacimiento
			new Select(getElement(xpathMes)).selectByValue(fechaToken.nextToken()); //Mes de nacimiento
			new Select(getElement(xpathAny)).selectByVisibleText(fechaToken.nextToken()); //Año de nacimiento
			
			i+=1;
		}
	}
	
	public void clickContinuar() {
		click(XP_BOTON_CONTINUAR).exec();
		if (!state(INVISIBLE, XP_BOTON_CONTINUAR).wait(2).check()) {
			click(XP_BOTON_CONTINUAR).exec();
		}
	}
}
