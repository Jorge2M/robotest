package com.mng.robotest.domains.ficha.pageobjects;

import java.util.List;


import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.ficha.beans.DataFoto;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFotosNew extends PageBase {

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
	
	public DataFoto getDataFoto(int line, int position) {
		String xpathFoto = getXPathFoto(line, position);
		List<WebElement> listFotos = getElements(xpathFoto);
		if (listFotos.size() < 1) {
			return null;
		}
		return (new DataFoto(listFotos.get(0).getAttribute("src")));
	}

	public int getNumLinesFotos() {
		if (!state(Present, XPATH_LINE_FOTO).check()) {
			return 0;
		}
		return getElements(XPATH_LINE_FOTO).size();
	}
		
	public int getNumFotosLine(int line) {
		String xpathFotoLine = getXPathFoto(line);
		if (!state(Present, xpathFotoLine).check()) {
			return 0;
		}
		return getElements(xpathFotoLine).size();
	}
}
