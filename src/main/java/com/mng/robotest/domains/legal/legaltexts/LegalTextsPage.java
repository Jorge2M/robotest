package com.mng.robotest.domains.legal.legaltexts;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.legal.beans.LegalText;
import com.mng.robotest.domains.legal.beans.LegalTextGroup;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class LegalTextsPage extends PageBase {

	public abstract LegalTextGroup getLegalTexts(Pais pais);
	
	private final LegalTextGroup legalTexts;
	
	protected LegalTextsPage() {
		this.legalTexts = getLegalTexts(dataTest.getPais());
	}
	
	public LegalTextGroup getLegalTexts() {
		return legalTexts;
	}
	
	public List<String> getListCodes() {
		return legalTexts.getTexts().stream()
				.map(t -> t.getCode()).toList();
	}
	
	public boolean areVisibleAllLegalTexts() {
		return legalTexts.getTexts().stream()
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
		return getElementsVisible(legalText.getXPath()).stream()
			.anyMatch(e -> e.getText().contains(legalText.getText()));
	}

	private Optional<LegalText> getLegalText(String code) {
		return legalTexts.getTexts().stream()
				.filter(t -> t.getCode().compareTo(code)==0)
				.findAny();
	}

}
