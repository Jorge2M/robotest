package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class CheckerTextsTraduced implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		List<String> listElemsWithoutTraduction = getTextsWithSostenido(driver);
		validations.add(
			"No hay textos sin traducir (que comiencen por \"#\")<br>" + 
			getFormatHtmlListMessages(listElemsWithoutTraduction),
			listElemsWithoutTraduction.size()==0, GenericCheck.TextsTraduced.getLevel());
		
		return validations;
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
		if (literal!=null && literal.length() > 0 &&
			literal.charAt(0) == '#' && 
			(literal.contains(".") || StringUtils.countMatches(literal, "#") > 1)) {
			return true;
		}
		return false;
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
 