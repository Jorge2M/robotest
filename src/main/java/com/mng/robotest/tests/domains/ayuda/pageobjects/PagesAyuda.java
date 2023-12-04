package com.mng.robotest.tests.domains.ayuda.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

public class PagesAyuda extends PageBase implements PageFromFooter {
	
	private static final String TAG = "@TAG";
	private static final String XP_ICON_WITH_TAG = "//*[@data-testid='link-list-grid-item-icon']/..//*[text()='" + TAG + "']";
	private static final String XP_QUESTION_WITH_TAG = "//p[@class[contains(.,'text-body')] and text()='" + TAG + "']";
	private static final String XP_CONTACTAR_BUTTON = "//a[@href[contains(.,'/help/contact')]]";
	
	private String getXPathIcon(String textIcon) {
		return XP_ICON_WITH_TAG.replace(TAG, textIcon);
	}
	
	private String getXPathQuestion(String textQuestion) {
		return XP_QUESTION_WITH_TAG.replace(TAG, textQuestion);
	}
	
	@Override
	public String getName() {
		return "¿En qué podemos ayudarte?";
	}
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		String xpath = getXPathIcon("Devoluciones, cambios y reembolsos");
		return state(VISIBLE, xpath).wait(seconds).check();
	}
	
	public void selectIcon(String textIcon) {
		click(getXPathIcon(textIcon)).exec();
	}
	
	public boolean isQuestionVisible(String textQuestion) {
		return state(VISIBLE, getXPathQuestion(textQuestion)).check();
	}
	
	public void clickQuestion(String textQuestion) {
		click(getXPathQuestion(textQuestion)).exec();
	}
	
	public boolean isTextVisible(String text) {
		String xpathText = "//*[text()[contains(.,'" + text + "')]]";
		return state(VISIBLE, xpathText).check();
	}
	
	public void clickContactarButton() {
		click(XP_CONTACTAR_BUTTON).exec();
	}
}