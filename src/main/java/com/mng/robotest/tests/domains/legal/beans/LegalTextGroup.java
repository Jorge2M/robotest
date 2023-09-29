package com.mng.robotest.tests.domains.legal.beans;

import java.util.List;

public class LegalTextGroup {

	private final String description;
	private final List<LegalText> texts;
	
	private LegalTextGroup(String description, List<LegalText> texts) {
		this.description = description;
		this.texts = texts;
	}
	
	public static LegalTextGroup from(String description, List<LegalText> texts) {
		return new LegalTextGroup(description, texts);
	}
	
	public String getDescription() {
		return description;
	}
	public List<LegalText> getTexts() {
		return texts;
	}

}
