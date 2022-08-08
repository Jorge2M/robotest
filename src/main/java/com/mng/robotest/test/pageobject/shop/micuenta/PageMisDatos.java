package com.mng.robotest.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisDatos extends PageBase {
	
	private static final String XPathIsPage = "//div[@class='myDetails']";
	private static final String XPathTitleOk = "//h2[text()[contains(.,'Mis datos')]]";
	private static final String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
	private static final String XPathInputNombre = "//input[@id[contains(.,'cfName')]]";
	private static final String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
	private static final String XPathInputDireccion = "//input[@id[contains(.,'cfDir1')]]";
	private static final String XPathInputCodPostal = "//input[@id[contains(.,'cfCp')]]";
	private static final String XPathInputPoblacion = "//input[@id[contains(.,'cfCity')]]";
	private static final String XPathBotonGuardarCambios = "//div[@class='submitContent']/input[@type='submit']";
	//private static final String XPathPageResOKshop = "//span[@class[contains(.,'icon-fill-done')]]";
	private static final String XPathPageResOK = "//span[text()[contains(.,'Tus datos han sido modificados en nuestra base de datos.')]]";
	private static final String XPathInputPasswordTypePassword = "//input[@id[contains(.,'cfPass')] and @type='password']";
	private static final String XPathInputContentVoid = "//div[@class='inputContent']/input[not(@value)]";
	private static final String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
	private static final String XPathSelectProvincia = "//select[@id[contains(.,':estadosPais')]]";
	private static final String XPathOptionPaisSelected = XPathSelectPais + "/option[@selected]";
	private static final String XPathOptionProvinciaSelected = XPathSelectProvincia + "/option[@selected]";
	
	public PageMisDatos(WebDriver driver) {
		super(driver);
	}
	
//	private String getXPathPageResOK(AppEcom app) {
//		switch (app) {
//		case outlet:
//			return XPathPageResOKoutlet;
//		default:
//			return XPathPageResOKshop;
//		}
//	}
	
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
