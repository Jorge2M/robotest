package com.mng.robotest.tests.domains.setcookies.pageobjects;

public class ModalSetCookiesOld  extends ModalSetCookies {

	private static final String XPATH_WRAPPER = "//micro-frontend[@id='cookies']";
	private static final String XP_CHECKBOX_INACTIVE = XPATH_WRAPPER + "//input[@type='checkbox']";
	
	private String getXPathHeaderSection(CookiesType section) {
		return XPATH_WRAPPER + "//h2[text()='" + section.getLiteral() + "']";
	}
	
	@Override
	public void enable(CookiesType section) {
		clickSection(section);
		enableCheckboxCookies();
	}
	
	@Override
	public void disable(CookiesType section) {
		clickSection(section);
		disableCheckboxCookies();		
	}	
	
	@Override
	public boolean isEnabled(CookiesType section) {
		return
			isSectionUnfold(section) &&
			getElementVisible(XP_CHECKBOX_INACTIVE).isSelected();
	}		
	
	private void enableCheckboxCookies() {
		if (!isCheckboxSelected()) {
			clickCheckboxCookies();
		}
	}
	
	private void disableCheckboxCookies() {
		if (isCheckboxSelected()) {
			clickCheckboxCookies();
		}
	}	
	
	private boolean isCheckboxSelected() {
		return getElementVisible(XP_CHECKBOX_INACTIVE).isSelected();
	}	

	private void clickCheckboxCookies() {
		click(getElementVisible(XP_CHECKBOX_INACTIVE)).exec();
	}
	
	private boolean isSectionUnfold(CookiesType section) {
		var optionSection = getElementWeb(getXPathSection(section));
		if (optionSection==null) {
			return false;
		}
		var headerSection = getElementWeb(getXPathHeaderSection(section));
		return headerSection!=null;
	}	
	
}
