package com.mng.robotest.domains.ficha.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFotosNew extends PageObjTM {

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
	
	public SecFotosNew(WebDriver driver) {
		super(driver);
	}
	
	public DataFoto getDataFoto(int line, int position) {
		String xpathFoto = getXPathFoto(line, position);
		List<WebElement> listFotos = driver.findElements(By.xpath(xpathFoto));
		if (listFotos.size() < 1) {
			return null;
		}
		return (new DataFoto(listFotos.get(0).getAttribute("src")));
	}

	public int getNumLinesFotos() {
		if (!state(Present, By.xpath(XPATH_LINE_FOTO)).check()) {
			return 0;
		}
		return (driver.findElements(By.xpath(XPATH_LINE_FOTO)).size());
	}
		
	public int getNumFotosLine(int line) {
		String xpathFotoLine = getXPathFoto(line);
		if (!state(Present, By.xpath(xpathFotoLine)).check()) {
			return 0;
		}
		return (driver.findElements(By.xpath(xpathFotoLine)).size());
	}
}
