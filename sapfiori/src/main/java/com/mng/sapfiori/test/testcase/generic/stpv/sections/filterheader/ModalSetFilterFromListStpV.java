package com.mng.sapfiori.test.testcase.generic.stpv.sections.filterheader;

import java.util.List;

import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.ModalSetFieldFromListI;
import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.ModalSetMultiFieldFromList;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.utils.State;

public class ModalSetFilterFromListStpV {

	private final ModalSetFieldFromListI modalSetFilter;
	
	private ModalSetFilterFromListStpV(ModalSetFieldFromListI modalSetFilter) {
		this.modalSetFilter = modalSetFilter;
	}	
	
	public static ModalSetFilterFromListStpV getNew(ModalSetFieldFromListI modalSetFilter) {
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
		if (!(modalSetFilter instanceof ModalSetMultiFieldFromList)) {
			throw new ClassCastException(
				modalSetFilter.getClass() + "is not instance of " + ModalSetMultiFieldFromList.class.getName());
		}
		((ModalSetMultiFieldFromList)modalSetFilter).selectElementsByPosition(listPosElementsToSelect);
	}
	
	@Step (
		description = "Seleccionar los elementos <b>#{listValueElementsToSelect}</b>",
		expected = "Quedan marcados dichos elementos")
	public void selectElementsByValue(List<String> listValueElementsToSelect) throws Exception {
		if (!(modalSetFilter instanceof ModalSetMultiFieldFromList)) {
			throw new ClassCastException(
				modalSetFilter.getClass() + "is not instance of " + ModalSetMultiFieldFromList.class.getName());
		}
		((ModalSetMultiFieldFromList)modalSetFilter).selectElementsByValue(listValueElementsToSelect);
	}
	
	@Step (
		description = "Clickar el botón OK para acepter los cambios",
		expected = "Desaparece el modal")
	public void clickOkButton() throws Exception {
		modalSetFilter.clickOkButton();
	}
}
