package ${package}.test.testcase.stpv;

import ${package}.test.testcase.pageobject.GoogleMainPage;
import ${package}.test.testcase.pageobject.ResultsGooglePage;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;

public class GoogleMainPageStpV {
	
	private final GoogleMainPage googlePage;
	
	private GoogleMainPageStpV(WebDriver driver) {
		googlePage = GoogleMainPage.getNew(driver);
	}
	public static GoogleMainPageStpV getNew(WebDriver driver) {
		return new GoogleMainPageStpV(driver);
	}
	
	@Step (
		description="Introducimos en el buscador un texto #{textToSearch} (<b>con</b> varias páginas de resultados)",
		expected="")
	public ResultsGooglePageStpV searchTextWithManyResultPages(String textToSearch, WebDriver driver) {
		googlePage.inputText(textToSearch);
		ResultsGooglePage pageResults = googlePage.clickBuscarConGoogleButton();
		
		ResultsGooglePageStpV resultsPageStpV = ResultsGooglePageStpV.getNew(pageResults);
		resultsPageStpV.checkAreManyPages(googlePage);
		return resultsPageStpV;
	}
	
	@Step (
		description="Introducimos en el buscador un texto #{textToSearch} <b>sin</b> varias páginas de resultados",
		expected="")
	public ResultsGooglePageStpV searchTextWithoutManyResultPages(String textToSearch, WebDriver driver) {
		googlePage.inputText(textToSearch);
		googlePage.clickBuscarConGoogleButton();
		
		ResultsGooglePageStpV resultsPageStpV = ResultsGooglePageStpV.getNew(driver);
		resultsPageStpV.checkAreNotManyPages(googlePage);
		return resultsPageStpV;
	}
}
