package com.mng.robotest.domains.ayuda.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PagesAyuda extends PageObjTM {
	
	private static final String TAG = "@TAG";
	private static final String XPATH_ICON_WITH_TAG = "//*[@data-testid='link-list-grid-item-icon']/..//*[text()='" + TAG + "']";
	private static final String XPATH_QUESTION_WITH_TAG = "//p[@class[contains(.,'text-body')] and text()='" + TAG + "']";

	public PagesAyuda(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathIcon(String textIcon) {
		return XPATH_ICON_WITH_TAG.replace(TAG, textIcon);
	}
	
	private String getXPathQuestion(String textQuestion) {
		return XPATH_QUESTION_WITH_TAG.replace(TAG, textQuestion);
	}
	
	public void selectIcon(String textIcon) {
		By byIcon = By.xpath(getXPathIcon(textIcon));
		click(byIcon).exec();
	}
	
	public boolean isQuestionVisible(String textQuestion) {
		By byQuestion = By.xpath(getXPathQuestion(textQuestion));
		return state(State.Visible, byQuestion).check();
	}
	
	public void clickQuestion(String textQuestion) {
		By byQuestion = By.xpath(getXPathQuestion(textQuestion));
		click(byQuestion).exec();
	}
	
	public boolean isTextVisible(String text) {
		By byText = By.xpath("//*[text()[contains(.,'" + text + "')]]");
		return state(State.Visible, byText).check();
	}
}