package com.mng.robotest.domains.accesibilidad.steps;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.openqa.selenium.WebDriver;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.steps.shop.genericchecks.Checker;

public class CheckerAccesibility implements Checker {

	private static List<String> tags = Arrays.asList("wcag2aa");
	
	private final State level;
	
	public CheckerAccesibility(State level) {
		this.level = level;
	}
	
	@Override
	public ChecksTM check(WebDriver driver) {
		var checks = ChecksTM.getNew();
		
		var results = getViolations(driver);
		checks.add(
			Check.make(
				"Checks accesibility",
				results.violationFree(), level)
			.info(getDescription(results))
			.build());
		
		return checks;
	}
	
	private String getDescription(Results results) {
		var description = new StringJoiner("<br>");
		description.add(results.getViolations().size() + " accesibility violations found");
		if (!results.violationFree()) {
			results.getViolations().stream()
				.forEach(v -> description.add(v.toString()));
		}
		return description.toString();
	}
	
	private Results getViolations(WebDriver driver) {
		var results = analyze(driver);
		return results;
	}
	
	private Results analyze(WebDriver driver) { 
		return new AxeBuilder()
				.withTags(tags)
				.analyze(driver);
	}
	
}
