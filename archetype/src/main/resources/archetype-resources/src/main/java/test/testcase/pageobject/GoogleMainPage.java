package ${package}.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleMainPage extends PageObject {

	private final static String XPathInputBuscador = "//input[@role='combobox']";
	private final static String XPathBuscarConGoogleButton = "//input[@value='Buscar con Google']";
	private final WebDriver driver;
	
	private GoogleMainPage(WebDriver driver) {
		this.driver = driver;
	}
	public static GoogleMainPage getNew(WebDriver driver) {
		return new GoogleMainPage(driver);
	}
	
	public void inputText(String textToInput) {
		By byInput = By.xpath(XPathInputBuscador);
		driver.findElement(byInput).sendKeys(textToInput);
	}
	
	public ResultsGooglePage clickBuscarConGoogleButton() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathBuscarConGoogleButton));
		return ResultsGooglePage.getNew(driver);
	}
}
