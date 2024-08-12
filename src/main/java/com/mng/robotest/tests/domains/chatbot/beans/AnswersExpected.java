package com.mng.robotest.tests.domains.chatbot.beans;

import java.util.List;

public class AnswersExpected {

	private final List<String> answers;

	public AnswersExpected(List<String> answers) {
		this.answers = answers;
	}
	
	public List<String> getAnswers() {
		return answers;
	}
	
	public String toHtml() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < answers.size(); i++) {
	        sb.append(i + 1).append(". ").append(answers.get(i)).append("<br>");
	    }
	    return sb.toString();
	}

}
