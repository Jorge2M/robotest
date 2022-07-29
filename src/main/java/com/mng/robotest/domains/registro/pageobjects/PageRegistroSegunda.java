package com.mng.robotest.domains.registro.pageobjects;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroSegunda extends PageObjTM {

	private static final String XPATH_NEWSLETTER_TITLE = "//div[@class[contains(.,'additionalData')]]//span[@class='info']";
	private static final String XPATH_FORM_STEP2 = "//form[@class[contains(.,'customFormIdSTEP2')]]";
	private static final String XPATH_CHECKBOX_LINEA = "//input[@type='checkbox']";
	private static final String XPATH_SELECT_DIA_NACIM = "//select[@id[contains(.,'naciDia')]]";
	private static final String XPATH_SELECT_MES_NACIM = "//select[@id[contains(.,'naciMes')]]";
	private static final String XPATH_SELECT_ANY_NACIM = "//select[@id[contains(.,'naciAny')]]";
	private static final String XPATH_BUTTON_CONTINUAR = 
		"//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";	
	
	public PageRegistroSegunda(WebDriver driver) {
		super(driver);
	}
	
	private String getXPath_checkboxLinea(String linea) {
		return ("//input[@type='checkbox' and @id[contains(.,':" + linea + "')]]");
	}
	
	private String getXPath_checkboxLineaClickable(String linea) {
		return (getXPath_checkboxLinea(linea) + "/..");
	}
	
	public String getNewsLetterTitleText() {
		try {
			WebElement titleNws = driver.findElement(By.xpath(XPATH_NEWSLETTER_TITLE));
			if (titleNws!=null) {
				return driver.findElement(By.xpath(XPATH_NEWSLETTER_TITLE)).getText();
			}
		}
		catch (Exception e) {
			//retornaremos ""
		}
		
		return "";
	}
	
	public boolean newsLetterTitleContains(String literal) {
		return (getNewsLetterTitleText().contains(literal));
	}
	
	String getXPath_radioNinosInFamily(int numNinos) {
		return ("//div[@class='radiobuttonBtn']/input[@value='" + numNinos + "']");
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_FORM_STEP2)).wait(maxSeconds).check());
	}
	
	public int getNumColecciones() {
		return (driver.findElements(By.xpath(XPATH_CHECKBOX_LINEA)).size());
	}
	
	/**
	 * @param fechaNacimiento en formato DD/MM/AAAA
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		StringTokenizer st = new StringTokenizer(fechaNacimiento, "/");
		setFechaNacimiento(st.nextToken(), st.nextToken(), st.nextToken());
	}
	
	public void setNumeroNinos(int numNinos) {
		String xpathRadio = getXPath_radioNinosInFamily(numNinos);
		driver.findElement(By.xpath(xpathRadio)).click();
	}
	
	public void setFechaNacimiento(String dia, String mes, String any) {
		selectDiaNacimByText(dia);
		selectMesNacimByText(mes);
		selectAnyNacimByText(any);
	}
	
	public boolean isPresentSelectDiaNacim() {
		return (state(Present, By.xpath(XPATH_SELECT_DIA_NACIM)).check());
	}
	
	public void selectDiaNacimByText(String dia) {
		new Select(driver.findElement(By.xpath(XPATH_SELECT_DIA_NACIM)))
			.selectByVisibleText(dia);
	}
	
	public void selectMesNacimByText(String mes) {
		new Select(driver.findElement(By.xpath(XPATH_SELECT_MES_NACIM)))
			.selectByValue(mes);
	}
	
	public void selectAnyNacimByText(String any) {
		new Select(driver.findElement(By.xpath(XPATH_SELECT_ANY_NACIM)))
			.selectByVisibleText(any);
	}
	
	/**
	 * @return si existen todos los checkbox correspondientes a una lista de líneas separadas por comas
	 */
	public boolean isPresentInputForLineas(String lineasComaSeparated) {
		boolean isPresentInputs = true;
		StringTokenizer tokensLinea = new StringTokenizer(lineasComaSeparated, ",");
		while (tokensLinea.hasMoreElements()) {
			String lineaStr=tokensLinea.nextToken();
			String xpathCheckboxLinea = getXPath_checkboxLinea(lineaStr);
			if (!state(Present, By.xpath(xpathCheckboxLinea)).check()) {
				isPresentInputs = false;
				break;
			}
		}
		
		return isPresentInputs; 
	}
	
	/**
	 * Desmarca una serie de líneas al azar (de entre las contenidas en lineasComaSeparated)
	 * @return las líneas desmarcadas separadas por comas
	 */
	public String desmarcarLineasRandom(String lineasComaSeparated) {
		StringTokenizer tokensLin = new StringTokenizer(lineasComaSeparated, ",");
		String lineasDesmarcadas = "";
		int i=0;
		while (tokensLin.hasMoreElements()) { 
			String lineaStr=tokensLin.nextToken();
			if (Math.random() < 0.5) {
				String xpathLineaClick = getXPath_checkboxLineaClickable(lineaStr);
				if (state(Present, By.xpath(xpathLineaClick)).check()) {
					click(By.xpath(xpathLineaClick)).exec();
				}
						
				//Las líneas que desmarcamos las guardamos
				if (i>0) {
					lineasDesmarcadas+=",";
				}
				lineasDesmarcadas+=lineaStr;
				i+=1;
			}
		}	
		
		return lineasDesmarcadas;
	}
	
	public void clickButtonContinuar() {
		click(By.xpath(XPATH_BUTTON_CONTINUAR)).exec();
	}
}
