package com.mng.robotest.domains.ayuda.pageobjects;

import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PagesAyuda extends PageBase implements PageFromFooter {
	
	private static final String TAG = "@TAG";
	private static final String XPATH_ICON_WITH_TAG = "//*[@data-testid='link-list-grid-item-icon']/..//*[text()='" + TAG + "']";
	private static final String XPATH_QUESTION_WITH_TAG = "//p[@class[contains(.,'text-body')] and text()='" + TAG + "']";
	
	private String getXPathIcon(String textIcon) {
		return XPATH_ICON_WITH_TAG.replace(TAG, textIcon);
	}
	
	private String getXPathQuestion(String textQuestion) {
		return XPATH_QUESTION_WITH_TAG.replace(TAG, textQuestion);
	}
	
	@Override
	public String getName() {
		return "¿En qué podemos ayudarte?";
	}
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		String xpath = getXPathIcon("Devoluciones, cambios y reembolsos");
		return state(State.Visible, xpath).wait(maxSeconds).check();
	}
	
	public void selectIcon(String textIcon) {
		click(getXPathIcon(textIcon)).exec();
	}
	
	public boolean isQuestionVisible(String textQuestion) {
		return state(State.Visible, getXPathQuestion(textQuestion)).check();
	}
	
	public void clickQuestion(String textQuestion) {
		click(getXPathQuestion(textQuestion)).exec();
	}
	
	public boolean isTextVisible(String text) {
		String xpathText = "//*[text()[contains(.,'" + text + "')]]";
		return state(State.Visible, xpathText).check();
	}
}