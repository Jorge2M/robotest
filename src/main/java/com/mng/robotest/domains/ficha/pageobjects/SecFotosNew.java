package com.mng.robotest.domains.ficha.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * SectionObject de la ficha nueva correspondiente a la Foto central y líneas inferiores
 * @author jorge.munoz
 *
 */

public class SecFotosNew {

	private static final String XPATH_CAPA = "//div[@class[contains(.,'product-images')]]";
	private static final String XPATH_LINE_FOTO = XPATH_CAPA + "//*[@class[contains(.,'columns')]]";
	
	private static String getXPathLineFotos(int line) {
		return (XPATH_LINE_FOTO + "[" + line + "]");
	}
	
	private static String getXPathFoto(int line) {
		String xpathLine = getXPathLineFotos(line);
		return (xpathLine + "//img[@class[contains(.,'image')]]");
	}
	
	private static String getXPathFoto(int line, int position) {
		String xpathLine = getXPathFoto(line);
		return (xpathLine + "[" + position + "]");
	}
	
	public static DataFoto getDataFoto(int line, int position, WebDriver driver) {
		String xpathFoto = getXPathFoto(line, position);
		List<WebElement> listFotos = driver.findElements(By.xpath(xpathFoto));
		if (listFotos.size() < 1) {
			return null;
		}
		return (new DataFoto(listFotos.get(0).getAttribute("src")));
	}

	public static int getNumLinesFotos(WebDriver driver) {
		if (!state(Present, By.xpath(XPATH_LINE_FOTO), driver).check()) {
			return 0;
		}
		return (driver.findElements(By.xpath(XPATH_LINE_FOTO)).size());
	}
		
	public static int getNumFotosLine(int line, WebDriver driver) {
		String xpathFotoLine = getXPathFoto(line);
		if (!state(Present, By.xpath(xpathFotoLine), driver).check()) {
			return 0;
		}
		return (driver.findElements(By.xpath(xpathFotoLine)).size());
	}
}
