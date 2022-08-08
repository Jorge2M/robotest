package com.mng.robotest.domains.registro.pageobjects;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.registro.pageobjects.beans.DataNino;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataNinos;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroNinos extends PageBase {
	
	private static final String XPATH_INPUT_NOMBRE = "//input[@id[contains(.,'cfNameKid')]]";
	private static final String XPATH_SELECT_DIA_NACIMIENTO = "//select[@id[contains(.,'naciDia')]]";
	private static final String XPATH_SELECT_MES_NACIMIENTO = "//select[@id[contains(.,'naciMes')]]";
	private static final String XPATH_SELECT_ANY_NACIMIENTO = "//select[@id[contains(.,'naciAny')]]";
	private static final String XPATH_BOTON_CONTINUAR = 
		"//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
	
	public boolean isPageUntil(int maxSeconds) {
		String xpath = "//form[@id[contains(.,'cfKids')]]";
		return (state(Present, By.xpath(xpath)).wait(maxSeconds).check());
	}
	
	public int getNumInputsNameNino() {
		return (driver.findElements(By.xpath(XPATH_INPUT_NOMBRE)).size());
	}
	
	public String getXPath_inputNombre(int numNino) {
		return ("(" + XPATH_INPUT_NOMBRE + ")[" + numNino + "]");
	}
	
	public String getXPath_selectDiaNac(int numNino) {
		return ("(" + XPATH_SELECT_DIA_NACIMIENTO + ")[" + numNino + "]");
	}
	
	public String getXPath_selectMesNac(int numNino) {
		return ("(" + XPATH_SELECT_MES_NACIMIENTO + ")[" + numNino + "]");
	}
	
	public String getXPath_selectAnyNac(int numNino) {
		return ("(" + XPATH_SELECT_ANY_NACIMIENTO + ")[" + numNino + "]");
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
			String xpathInputNombreNino = getXPath_inputNombre(i);
			if (driver.findElement(By.xpath(xpathInputNombreNino)).getAttribute("value").compareTo(nombreNino)!=0) {
				driver.findElement(By.xpath(xpathInputNombreNino)).clear();
				driver.findElement(By.xpath(xpathInputNombreNino)).sendKeys(nombreNino);
			}
			
			String xpathDia = getXPath_selectDiaNac(i);
			String xpathMes = getXPath_selectMesNac(i);
			String xpathAny = getXPath_selectAnyNac(i);
			StringTokenizer fechaToken = new StringTokenizer(dataNino.getFechaNacimiento(), "/");
			new Select(driver.findElement(By.xpath(xpathDia))).selectByVisibleText(fechaToken.nextToken()); //Día nacimiento
			new Select(driver.findElement(By.xpath(xpathMes))).selectByValue(fechaToken.nextToken()); //Mes de nacimiento
			new Select(driver.findElement(By.xpath(xpathAny))).selectByVisibleText(fechaToken.nextToken()); //Año de nacimiento
			
			i+=1;
		}
	}
	
	public void clickContinuar() {
		click(By.xpath(XPATH_BOTON_CONTINUAR)).exec();
	}
}
