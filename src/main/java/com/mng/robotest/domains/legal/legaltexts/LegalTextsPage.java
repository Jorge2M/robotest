package com.mng.robotest.domains.legal.legaltexts;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class LegalTextsPage extends PageBase {

	public abstract List<LegalText> getLegalTexts(Pais pais);
	
	private final List<LegalText> legalTexts;
	
	public LegalTextsPage() {
		this.legalTexts = getLegalTexts(dataTest.getPais());
	}
	
	public List<LegalText> getLegalTexts() {
		return legalTexts;
	}
	
	public List<String> getListCodes() {
		return legalTexts.stream().map(t -> t.getCode()).toList();
	}
	
	public boolean areVisibleAllLegalTexts() {
		return legalTexts.stream()
				.filter(t -> !isVisibleLegalText(t))
				.findAny().isEmpty();
	}
	
	public boolean isVisibleLegalText(String code) {
		var legalTextOpt = getLegalText(code);
		if (legalTextOpt.isEmpty()) {
			return false;
		}
		return isVisibleLegalText(legalTextOpt.get());
	}	
	
	public boolean isVisibleLegalText(LegalText legalText) {
		return 
			isVisibleLegalTextByXPath(legalText) || 
			isVisibleLegalTextByContent(legalText);
	}
	
	private boolean isVisibleLegalTextByXPath(LegalText legalText) {
		try {
			if (!legalText.isShadowDom()) {
				return state(Visible, legalText.getXPathWithLiteral()).check();
			}
			return state(Visible, legalText.getXPath())
					.byShadow(By.cssSelector(legalText.getCssShadowDom())).check();
		} 
		catch (Exception e) {
			return false;
		}
	}
	private boolean isVisibleLegalTextByContent(LegalText legalText) {
		//getElementsVisible(legalText.getXPath()).get(0).getText().contains(legalText.getText())
		return getElementsVisible(legalText.getXPath()).stream()
			.filter(e -> e.getText().contains(legalText.getText()))
			.findAny().isPresent();
	}

	private Optional<LegalText> getLegalText(String code) {
		return legalTexts.stream()
				.filter(t -> t.getCode().compareTo(code)==0)
				.findAny();
	}

}
