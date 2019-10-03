package com.mng.robotest.test80.mango.test.pageobject.shop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.webdriverwrapper.TypeOfClick;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class PageMenusManto extends WebdrvWrapp {

	static String iniXPathTitulo = "//td[@class='txt11B' and text()[contains(.,'";
	static String XPathTitulo = "//td[@class='txt11B'] | //form[@id='formTempl']";
    static String XPathCeldaTextMenuPrincipal = "//td[text()[contains(.,'Menú principal')]]";
    static String XPathCabeceraMenu = "//table//td[@bgcolor='#505050']/span";
    static String XPathSubMenus = "//table//td/a[@onclick]";
    static String XPathLinkMenu = "//table//a[@onclick]";
    static String XPathTableLastElement = "//table[@id='tabla_derecha']//tr[last()]/td/a[@onclick]";
    
    /**
     * @param menu
     * @return el xpath correspondiente a un menú concreto
     */
    public static String getXpath_linkMenu(String menu) {
        return ("//a[text()[contains(.,'" + menu + "')]]");
    }
    
    /**
     * @param title
     * @return el XPATH del título de la página
     */
    public static String getXPathTitulo(String title){
    	return (iniXPathTitulo + title + "')]]");
    }
    
    /**
     * @param XPathPosicionInicial
     * @return el XPATH del siguiente elemento al ultimo elemento encontrado del submenu
     */
    private static String getXPathNextElement(String XPathPosicionInicial) {
		String XPathNextPosicion = XPathPosicionInicial + "/../following::td/a";
		return XPathNextPosicion;
	}
    
    /**
     * @param menuName
     * @return el XPATH del siguiente elemento a la cabecera del submenu
     */
    private static String getXPathFirstElement(String menuName) {
		String XPathPosicionInicial = XPathCabeceraMenu + "[text()='" + menuName + "']" + "/../following::td";
		return XPathPosicionInicial;
	}
    
    /**
     * @return el text del titulo de la pagina seleccionada
     */
    public static String getTextMenuTitulo(WebDriver driver) {
        
		return driver.findElement(By.xpath(XPathTitulo)).getText();
	}
    
    /**
     * @return si nos encontramos en la página principal
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCeldaTextMenuPrincipal)));
    }
    
    /**
     * @param subMenu
     * @return valida si la página seleccionada corresponde con el menú seleccionado
     */
    public static boolean validateIsPage(String subMenu, WebDriver driver) {
    	String menuHeaderText = getTextMenuTitulo(driver);
    	String subMenuText = subMenu.replace("· ", "");
    	if (menuHeaderText.contains(subMenuText) || menuHeaderText.contains(subMenuText.toUpperCase()) || menuHeaderText.toUpperCase().contains(subMenuText.toUpperCase())){
    		return true;
    	} else {
    		if(isMenuHeaderVisible(driver)) {
    			return true;
    		}
    	}
    	
    	return false;
	} 

    /**
     * @return nos dice si existe el elemento de la cabecera
     */
	public static boolean isMenuHeaderVisible(WebDriver driver) {
		return (isElementPresent(driver, By.xpath(XPathTitulo)));
	}

	/**
     * @param XPathPosicionInicial, nextMenuName
     * @return comprueba si xpath del siguiente elemento es una cabecera de submenu, comprobando el text() con el de las cabeceras de menu
     */
    private static boolean isNextXPathMenuHeader(String XPathPosicionInicial, String nextMenuName, WebDriver driver) {
		String XPathNextPosicion = XPathPosicionInicial + "/../following::td/child::node()";
		String texto = driver.findElement(By.xpath(XPathNextPosicion)).getText();
		return (!nextMenuName.equals(texto));
	}
    
    /**
     * @param XPathPosicionInicial, nextMenuName
     * @return comprueba si xpath del siguiente elemento es el mismo que el del elemento final de la tabla
     */
    private static boolean isNextXPathEndTable(String XPathPosicionInicial, WebDriver driver) {
		String XPathNextPosicion = XPathPosicionInicial + "/../following::td/child::node()";
		if (!isElementPresent(driver,By.xpath(XPathNextPosicion))) {
			return false;
		}
		//String texto = driver.findElement(By.xpath(XPathNextPosicion)).getText();
		//String lastPositionText = driver.findElement(By.xpath(XPathTableLastElement)).getText();
		return true;
		
	}
    
    /**
     * @return if alert existed
     */
    public static String clickMenuAndAcceptAlertIfExists(String textoMenu, WebDriver driver) throws Exception {
    	try {
    		clickMenu(textoMenu, driver);
		} catch (UnhandledAlertException f) {
	        String textAlert = WebdrvWrapp.acceptAlertIfExists(driver);
	        if ("".compareTo(textAlert)==0) {
	        	return "Unknown or Empty";
	        }
	        return textAlert;
	    }
    	
    	return "";
    }
    
    /**
     * @param textoMenu
     * @return hace click en el submenu corresponiente, revisanto si tiene '
     */
    public static void clickMenu(String textoMenu, WebDriver driver) throws Exception {
    	int maxTimeToWait = 60;
    	int timeWaited = 0;
    	if (textoMenu.contains("'")) {
    		int positionDelete = textoMenu.indexOf("'");
    		String textoMenuRecortado = textoMenu.substring(positionDelete+1, textoMenu.length());
    		//clickAndWaitLoad(driver, By.xpath(getXpath_linkMenu(textoMenuRecortado)), 200);
    		waitClickAndWaitLoad(driver, 60, By.xpath(getXpath_linkMenu(textoMenuRecortado)), 60, TypeOfClick.webdriver);
    	}else {
    		//clickAndWaitLoad(driver, By.xpath(getXpath_linkMenu(textoMenu)), 200);
    		waitClickAndWaitLoad(driver, 60,By.xpath(getXpath_linkMenu(textoMenu)), 60, TypeOfClick.webdriver);
    	}
    	
    	while (!isElementPresent(driver, By.xpath(XPathTitulo)) && timeWaited != maxTimeToWait) {
    		Thread.sleep(1000);
    		timeWaited++;
    	}
    	
    	waitForPageLoaded(driver, 60);
    }
    
    public static List<WebElement> getListLinksMenus(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathLinkMenu)));
    }
    
    /**
     * @return devuelve el text() de todos los menus
     */
    public static ArrayList<String> getListMenuNames(WebDriver driver) {
        ArrayList<String> listMenuNames = new ArrayList<>();
        List<WebElement> listElemMenus = getListLinksMenus(driver);
        for (WebElement menu : listElemMenus)
            listMenuNames.add(menu.getText());
        
        return listMenuNames;
    }
    
    /**
     * @return devuelve el WebElement de todas las cabeceras de menu
     */
    public static List<WebElement> getListCabecerasMenus(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathCabeceraMenu)));
    }
    
    /**
     * @return devuelve el text() de todas las cabeceras de menu
     */
    public static ArrayList<String> getListCabecerasMenusName(WebDriver driver) {
        ArrayList<String> listCabecerasNames = new ArrayList<>();
        List<WebElement> listCabecerasMenus = getListCabecerasMenus(driver);
        for (WebElement menu : listCabecerasMenus)
            listCabecerasNames.add(menu.getText());
        
        return listCabecerasNames;
    }

    /**
     * @param menuName, nextMenuName
     * @return devuelve el text() de todos los submenus
     */
	public static ArrayList<String> getListSubMenusName(String menuName, String nextMenuName, WebDriver driver) {
		ArrayList<String> listSubMenusNames = new ArrayList<>();
        List<WebElement> listSubMenus = getListSubMenus(menuName, nextMenuName, driver);
        for (WebElement menu : listSubMenus)
            listSubMenusNames.add(menu.getText());
        
        return listSubMenusNames;
	}
	
	/**
     * @param menuName, nextMenuName
     * @return devuelve el WebElement de todos los submenus
     */
	public static List<WebElement> getListSubMenus(String menuName, String nextMenuName, WebDriver driver) {
		String XPathPosicionInicial = getXPathFirstElement(menuName);
		List<WebElement> elements = new ArrayList<>();
		elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
		if (nextMenuName==null) {
			while (isNextXPathEndTable(XPathPosicionInicial, driver)){
				XPathPosicionInicial = getXPathNextElement(XPathPosicionInicial);
				elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
			}
			return elements;
		}
		while (isNextXPathMenuHeader(XPathPosicionInicial, nextMenuName, driver)){
			XPathPosicionInicial = getXPathNextElement(XPathPosicionInicial);
			elements.add(driver.findElement(By.xpath(XPathPosicionInicial)));
		}
		return elements;
	}
}
