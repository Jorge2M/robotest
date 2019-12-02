package com.mng.sapfiori.access.test.testcase.generic.webobject.makers;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.buscar.InputBuscador;
import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectEstandardWithoutLabel;
import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectFilterEstandard;
import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectFilterMultiValue;
import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectMultiValue;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputLabel;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForDefineConditions;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForSelectItem;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputWithIconForSelectMultiItem;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalLoading;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalMessages;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectItem;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class StandarElementsMaker {

	private final WebDriver driver;
	
	private StandarElementsMaker(WebDriver driver) {
		this.driver = driver;
	}
	
	public static StandarElementsMaker getNew(WebDriver driver) {
		return new StandarElementsMaker(driver);
	}
	
	//Selects
	public SelectEstandard getSelectEstandard(String label) {
		return SelectFilterEstandard.getNew(label, driver);
	}
	
	public SelectEstandardWithoutLabel getSelectEstandardWithoutLabel(int id) {
		return SelectEstandardWithoutLabel.getNew(id, driver);
	}
	
	public SelectMultiValue getSelectMultiValue(String label) {
		return SelectFilterMultiValue.getNew(label, driver);
	}
	
	//Inputs
	public InputBuscador getInputBuscador() {
		return InputBuscador.getNew(driver);
	}
	
	public InputLabel getInputWithoutIcon(String label) {
		return new InputLabel(label, driver);
	}
	
	public InputWithIconForSelectItem getInputWithIconForSelectItem(String label) {
		return InputWithIconForSelectItem.getNew(label, driver);
	}
	
	public InputWithIconForSelectMultiItem getInputWithIconForSelectMultiItem(String label) {
		return InputWithIconForSelectMultiItem.getNew(label, driver);
	}
	
	public InputWithIconForDefineConditions getInputWithIconForDefineConditions(String label) {
		return InputWithIconForDefineConditions.getNew(label, driver);
	}
	
	//Modales
	public ModalSelectConditions getModalSelectConditions() {
		return ModalSelectConditions.getNew(driver);
	}
	public ModalSelectItem getModalSelectItem(String label) {
		return ModalSelectItem.getNew(label, driver);
	}
	public ModalSelectMultiItem getModalSelectMultiItem(String label) {
		return ModalSelectMultiItem.getNew(label, driver);
	}
	public ModalLoading getModalLoading() {
		return ModalLoading.getNew(driver);
	}
	public ModalMessages getModalErrores() {
		return ModalMessages.getNew(driver);
	}
	
}
