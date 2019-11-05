package com.mng.sapfiori.test.testcase.generic.stpv.modals;

import java.util.List;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectFromListBase;
import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class ModalSetFilterFromListStpV {

	private final ModalSelectFromListBase modalSetFilter;
	
	private ModalSetFilterFromListStpV(ModalSelectFromListBase modalSetFilter) {
		this.modalSetFilter = modalSetFilter;
	}	
	
	public static ModalSetFilterFromListStpV getNew(ModalSelectFromListBase modalSetFilter) {
		return new ModalSetFilterFromListStpV(modalSetFilter);
	}
	
	@Step (
		description = "Clickamos ENTER",
		expected = "Aparece una lista inicial de elementos")
	public void clickEnterToShowInitialElements() throws Exception {
		modalSetFilter.clickEnterToShowInitialElements();
		checkIsElementListVisible(1);
	}
	
	@Validation (
		description = "Es visible una lista de elementos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsElementListVisible(int maxSeconds) {
		return (modalSetFilter.isElementListPresent(maxSeconds));
	}
	
	@Step (
		description = "Seleccionar el elemento con el valor <b>#{elementValue}</b>",
		expected = "Queda seleccionado el elemento"
		/*saveHtmlPage=SaveWhen.Always*/)
	public void selectElementByValue(String elementValue) throws Exception {
		modalSetFilter.selectElementByValue(elementValue);
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
		((ModalSelectMultiItem)modalSetFilter).selectElementsByValue(listValueElementsToSelect);
	}
	
	@Step (
		description = "Clickar el botón OK para acepter los cambios",
		expected = "Desaparece el modal")
	public void clickOkButton() throws Exception {
		modalSetFilter.clickOkButton();
	}
}
