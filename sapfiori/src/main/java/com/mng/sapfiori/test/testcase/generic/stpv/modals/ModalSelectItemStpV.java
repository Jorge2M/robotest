package com.mng.sapfiori.test.testcase.generic.stpv.modals;

import java.util.List;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectFromListBase;
import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class ModalSelectItemStpV {

	private final ModalSelectFromListBase modalSetFilter;
	
	public ModalSelectItemStpV(ModalSelectFromListBase modalSetFilter) {
		this.modalSetFilter = modalSetFilter;
	}	
	
	public static ModalSelectItemStpV getNew(ModalSelectFromListBase modalSetFilter) {
		return new ModalSelectItemStpV(modalSetFilter);
	}
	
	@Step (
		description = "Clickamos ENTER",
		expected = "Aparece una lista inicial de elementos")
	public ModalSelectItemStpV clickEnterToShowInitialElements() throws Exception {
		modalSetFilter.clickEnterToShowInitialElements();
		checkIsElementListVisible(1);
		return this;
	}
	
	@Step (
		description = "Seleccionamos el elemento de la tabla que contiene el valor <b>#{valueToSelect}</b>")
	public void selectElementInTable(String valueToSelect) throws Exception {
		modalSetFilter.selectElementInTable(valueToSelect);
	}
	
	@Validation (
		description = "Es visible una lista de elementos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsElementListVisible(int maxSeconds) {
		return (modalSetFilter.isElementListPresent(maxSeconds));
	}
	
	@Step (
		description = "Buscar <b>#{valueToSearch}</b> y seleccionar el elemento con el valor <b>#{valueToSelect}</b>",
		expected = "Queda seleccionado el elemento")
	public void searchAndSelectElement(String valueToSearch, String valueToSelect) throws Exception {
		modalSetFilter.findAndSelectElement(valueToSearch, valueToSelect);
	}
	
	@Step (
		description = "Seleccionar los elementos en las posiciones <b>#{listPosElementsToSelect}</b>",
		expected = "Quedan marcados dichos elementos")
	public void selectElementsByPosition(List<Integer> listPosElementsToSelect) throws Exception {
		if (!(modalSetFilter instanceof ModalSelectMultiItem)) {
			throw new ClassCastException(
				modalSetFilter.getClass() + " is not instance of " + ModalSelectMultiItem.class.getName());
		}
		((ModalSelectMultiItem)modalSetFilter).selectElementsByPosition(listPosElementsToSelect);
	}
	
	@Step (
		description = "Seleccionar los elementos <b>#{listValueElementsToSelect}</b>",
		expected = "Quedan marcados dichos elementos")
	public void selectElementsByValue(List<String> listValueElementsToSelect) throws Exception {
		if (!(modalSetFilter instanceof ModalSelectMultiItem)) {
			throw new ClassCastException(
				modalSetFilter.getClass() + " is not instance of " + ModalSelectMultiItem.class.getName());
		}
		((ModalSelectMultiItem)modalSetFilter).searchAndSelectElementsByValue(listValueElementsToSelect);
	}
	
	@Step (
		description = "Clickar el botón OK para acepter los cambios",
		expected = "Desaparece el modal")
	public void clickOkButton() throws Exception {
		modalSetFilter.clickOkButton();
	}
}
