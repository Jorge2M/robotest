package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisDatos extends PageObjTM {
	
    private final static String XPathIsPage = "//div[@class='myDetails']";
    private final static String XPathTitleOk = "//h2[text()[contains(.,'Mis datos')]]";
    private final static String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
    private final static String XPathInputNombre = "//input[@id[contains(.,'cfName')]]";
    private final static String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
    private final static String XPathInputDireccion = "//input[@id[contains(.,'cfDir1')]]";
    private final static String XPathInputCodPostal = "//input[@id[contains(.,'cfCp')]]";
    private final static String XPathInputPoblacion = "//input[@id[contains(.,'cfCity')]]";
    private final static String XPathBotonGuardarCambios = "//div[@class='submitContent']/input[@type='submit']";
    //private final static String XPathPageResOKshop = "//span[@class[contains(.,'icon-fill-done')]]";
    private final static String XPathPageResOK = "//span[text()[contains(.,'Tus datos han sido modificados en nuestra base de datos.')]]";
    private final static String XPathInputPasswordTypePassword = "//input[@id[contains(.,'cfPass')] and @type='password']";
    private final static String XPathInputContentVoid = "//div[@class='inputContent']/input[not(@value)]";
    private final static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    private final static String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
    private final static String XPathOptionPaisSelected = XPathSelectPais + "/option[@selected]";
    private final static String XPathOptionProvinciaSelected = XPathSelectProvincia + "/option[@selected]";
    
    public PageMisDatos(WebDriver driver) {
    	super(driver);
    }
    
//    private String getXPathPageResOK(AppEcom app) {
//    	switch (app) {
//    	case outlet:
//    		return XPathPageResOKoutlet;
//    	default:
//    		return XPathPageResOKshop;
//    	}
//    }
    
    public String getText_inputNombre() {
        return (driver.findElement(By.xpath(XPathInputNombre)).getAttribute("value"));
    }
    
    public String getText_inputApellidos() {
        return (driver.findElement(By.xpath(XPathInputApellidos)).getAttribute("value"));
    }
    
    public String getText_inputEmail() {
        return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
    }

    public String getText_inputDireccion() {
        return (driver.findElement(By.xpath(XPathInputDireccion)).getAttribute("value"));
    }
    
    public String getText_inputCodPostal() {
        return (driver.findElement(By.xpath(XPathInputCodPostal)).getAttribute("value"));
    }
    
    public String getText_inputPoblacion() {
        return (driver.findElement(By.xpath(XPathInputPoblacion)).getAttribute("value"));
    }
    
    public String getCodPaisSelected() {
    	if (state(Present, By.xpath(XPathOptionPaisSelected)).check()) {
            return (driver.findElement(By.xpath(XPathOptionPaisSelected)).getAttribute("value"));
        }
        return "";
    }
    
    public String getProvinciaSelected() {
    	if (state(Present, By.xpath(XPathOptionProvinciaSelected)).check()) {
            return (driver.findElement(By.xpath(XPathOptionProvinciaSelected)).getText());
        }
        return "";
    }    
    
    public boolean isVisiblePasswordTypePassword() {
    	return (state(Visible, By.xpath(XPathInputPasswordTypePassword)).check());
    }
    
    public int getNumInputContentVoid() {
        return (driver.findElements(By.xpath(XPathInputContentVoid)).size());
    }
    
    public boolean isPage() {
    	return isPage(0);
    }
    public boolean isPage(int maxSeconds) {
    	return (state(Present, By.xpath(XPathIsPage)).wait(maxSeconds).check());
    }
    
    public boolean titleOk() {
    	return (state(Present, By.xpath(XPathTitleOk)).check());
    }
    
    public boolean emailIsDisabled() {
        //Obtenemos el atributo "disabled" del email
        String cfMailStatus = driver.findElement(By.xpath(XPathInputEmail)).getAttribute("disabled");
        return (cfMailStatus!=null && cfMailStatus.compareTo("false")!=0);
    }
    
    public String getValueEmailInput() {
        return (driver.findElement(By.xpath(XPathInputEmail)).getAttribute("value"));
    }
    
    public String getValueNombreInput() {
        return (driver.findElement(By.xpath(XPathInputNombre)).getAttribute("value"));
    }
    
    public void setNombreInput(String nombre) {
        driver.findElement(By.xpath(XPathInputNombre)).clear();
        driver.findElement(By.xpath(XPathInputNombre)).sendKeys(nombre);
    }
    
    public void clickGuardarCambios() {
    	click(By.xpath(XPathBotonGuardarCambios)).exec();
    }
    
    public boolean pageResOK() { 
    	//String xpath = getXPathPageResOK(app);
    	return (state(Present, By.xpath(XPathPageResOK)).check());
    }
}
