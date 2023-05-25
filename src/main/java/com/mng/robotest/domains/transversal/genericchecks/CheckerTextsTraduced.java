package com.mng.robotest.domains.transversal.genericchecks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;

public class CheckerTextsTraduced implements Checker {

	private final State level;
	
	public CheckerTextsTraduced(State level) {
		this.level = level;
	}
	
	public ChecksTM check(WebDriver driver) {
		var checks = ChecksTM.getNew();
		List<String> listElemsWithoutTraduction = getTextsWithSostenido(driver);
		checks.add(
			"No hay textos sin traducir (que comiencen por \"#\")<br>" + 
			getFormatHtmlListMessages(listElemsWithoutTraduction),
			listElemsWithoutTraduction.size()==0, level);
		
		return checks;
	}
	
	private List<String> getTextsWithSostenido(WebDriver driver) {
		List<String> listReturn = new ArrayList<>();
		List<WebElement> listElements = driver.findElements(By.xpath("//*[text()[contains(.,'#')] or @value[contains(.,'#')]]"));
		for (WebElement element : listElements) {
			if (isLiteralWithoutTraduction(element.getText())) {
				listReturn.add(element.getText());
			}
			if (isLiteralWithoutTraduction(element.getAttribute("value"))) {
				listReturn.add(element.getAttribute("value"));
			}
		}
		return listReturn;
	}
	
	private boolean isLiteralWithoutTraduction(String literal) {
		return 
			(literal!=null && literal.length() > 0 && literal.charAt(0) == '#' && 
			(literal.contains(".") || StringUtils.countMatches(literal, "#") > 1));
	}
	
	private String getFormatHtmlListMessages(List<String> listElemsWithoutTraduction) {
		if (listElemsWithoutTraduction.size()==0) {
			return "";
		}
		return 
				"Se han detectado los siguientes elementos con #:<br>" +
				String.join("<br>", listElemsWithoutTraduction);
	}
	
}
 