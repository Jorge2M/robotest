package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class PageGestionarClientes extends WebdrvWrapp {

    public enum TypeThirdButton {
        Alta("alta"), Baja("baja");
        
        public String mensaje;
        private TypeThirdButton(String mensaje) {
            this.mensaje = mensaje;
        }
        
        public String getMensaje() {
            return this.mensaje;
        }
        
        public TypeThirdButton buttonExpectedAfterClick() {
            if (this==TypeThirdButton.Alta) {
                return TypeThirdButton.Baja;
            }
            return TypeThirdButton.Alta;
        }
    } 
    
    
    public static String titulo = "Gestionar Clientes";
    static String XPathTitulo = "//td[@class='txt11B' and text()[contains(.,'" + titulo + "')]]";
    static String XPathFormBuscarClientes = "//form[@id='formSelect']";
    static String XPathFormTratarClientes = "//form[@id='formBlock']";
    static String XPathInputDNI = XPathFormBuscarClientes + "//span[text()='DNI']/ancestor::td/following::td//input";
    static String XPathBuscarButton = XPathFormBuscarClientes + "//input[@value='Buscar']";
    static String XPathFormTabla = "//form[@id='formTabla']";
    static String XPathFormTablaDetallesButton = XPathFormTabla + "//input[@value='Detalles']";
    static String XPathSpanMensaje = "//span[text()[contains(.,'";
    
    public static String getXPathIdClienteFromXPathDni(String dni){
    	String XPathDni = getXPathDniTabla(dni);
    	return XPathDni + "/ancestor::tr/td[1]";
    }
    
    public static String getXPathThirdButton(TypeThirdButton typeButton) {
        return (XPathFormTabla + "//input[@value='" + typeButton + "']");
    }
    
    public static String getXPathDniTabla(String dni){
    	return XPathFormTabla + "//td[@title='" + dni + "']";
    }
    
    private static String getXPathSpanMensajeThirdButton(String mensaje) {
		return XPathSpanMensaje + mensaje + "')]]";
    }
    
	private static String getXPathDetallesClienteIdCliente(String idCliente) {
		return "//td[@class='txt8'][text()[contains(.,'" + idCliente + "')]]";
	}
	
	private static String getXPathDetallesClienteDni(String dni) {
		return "//span[text()='" + dni + "']";
	}
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathTitulo)));
    }
    
    public static boolean isVisibleFormBuscarClientes(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathFormBuscarClientes)));
    }
    
    public static boolean isVisibleFormTratarClientes(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathFormBuscarClientes)));
    }
    
    public static void inputDniAndClickBuscarButton(String dni, int waitSeconds, WebDriver driver) throws Exception{
		inputDni(dni, driver);
		clickBuscarButtonAndWaitSeconds(waitSeconds, driver);
    }
    
    public static void inputDni(String dni, WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathInputDNI)).sendKeys(dni);
    }
    
    public static void clickBuscarButtonAndWaitSeconds(int waitSeconds, WebDriver driver) throws Exception {
    	driver.findElement(By.xpath(XPathBuscarButton)).click();
    }

	public static boolean isVisibleTablaInformacion(WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(XPathFormTabla), 20));
	}

	public static boolean getDniTabla(String dni, WebDriver driver) {
		return (isElementVisibleUntil(driver, By.xpath(getXPathDniTabla(dni)), 20));
	}

	public static TypeThirdButton getTypeThirdButton(WebDriver driver) {
	    if (isVisibleThirdButtonUntil(TypeThirdButton.Alta, 0, driver)) {
	        return (TypeThirdButton.Alta);
	    }
	    return (TypeThirdButton.Baja); 
	}
	
	public static String getIdClienteTablaFromDni(String dni, WebDriver driver) {
		return driver.findElement(By.xpath(getXPathIdClienteFromXPathDni(dni))).getText();
	}
	
	public static boolean isVisibleThirdButtonUntil(TypeThirdButton typeButton, int maxSecondsToWait, WebDriver driver) {
	    String xpathButton = getXPathThirdButton(typeButton); 
	    return (isElementVisibleUntil(driver, By.xpath(xpathButton), maxSecondsToWait));
	}
	
	public static void clickThirdButtonAndWaitSeconds(TypeThirdButton typeButton, int waitSeconds, WebDriver driver) throws Exception {
	    String xpathButton = getXPathThirdButton(typeButton);
	    clickAndWaitLoad(driver, By.xpath(xpathButton), waitSeconds);
	}
	
	public static void clickDetallesButtonAndWaitSeconds(int waitSeconds, WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathFormTablaDetallesButton), waitSeconds);
	}
	
	public static boolean isVisibleMensajeClickThirdButton(TypeThirdButton typeButton, WebDriver driver) {
	    String mensaje = typeButton.getMensaje();
	    return (isElementVisible(driver, By.xpath(getXPathSpanMensajeThirdButton(mensaje))));
	}

	public static boolean isVisibleIdClienteClickDetallesButton(String idCliente, WebDriver driver) {
		return isElementVisible(driver, By.xpath(getXPathDetallesClienteIdCliente(idCliente)));
	}

	public static boolean isVisibleDniClickDetallesButton(String dni, WebDriver driver) {
		return isElementVisible(driver, By.xpath(getXPathDetallesClienteDni(dni)));
	}


}