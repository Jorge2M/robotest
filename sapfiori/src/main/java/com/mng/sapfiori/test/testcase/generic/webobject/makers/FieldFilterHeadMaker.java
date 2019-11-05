package com.mng.sapfiori.test.testcase.generic.webobject.makers;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectFilterEstandard;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectFilterMultiValue;
import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectMultiValue;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputFilterFromSelectItem;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputFilterFromSelectMultiItem;
import com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal.InputFilterWithConditions;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class FieldFilterHeadMaker extends WebdrvWrapp {

	private final WebDriver driver;
	
	public FieldFilterHeadMaker(WebDriver driver) {
		this.driver = driver;
	}
	
	public InputBuscador getInputBuscador() {
		return InputBuscador.getNew(driver);
	}
	
	public InputFilterFromSelectItem getInputModalWithSelectItem(String label) {
		return InputFilterFromSelectItem.getNew(label, driver);
	}
	
	public InputFilterFromSelectMultiItem getInputModalWithSelectMultiItem(String label) {
		return InputFilterFromSelectMultiItem.getNew(label, driver);
	}
	
	public InputFilterWithConditions getInputModalWithSelectConditions(String label) {
		return InputFilterWithConditions.getNew(label, driver);
	}

	public SelectEstandard getSelectEstandard(String label) {
		return SelectFilterEstandard.getNew(label, driver);
	}
	
	public SelectMultiValue getSelectMultiValue(String label) {
		return SelectFilterMultiValue.getNew(label, driver);
	}
	
//	public InputFechaModule getInputFecha(String label) {
//		return InputFechaModule.getNew(label, driver);
//	}
}
