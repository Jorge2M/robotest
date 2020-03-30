package com.mng.sapfiori.access.test.testcase.generic.stpv.modals;

import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions.ConditionExclude;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions.ConditionInclude;
import com.mng.testmaker.boundary.aspects.step.Step;

public class ModalSelectConditionsStpV {

	private final ModalSelectConditions modalSelectConditions;
	
	public ModalSelectConditionsStpV(ModalSelectConditions modalSelectConditions) {
		this.modalSelectConditions = modalSelectConditions;
	}
	
	public static ModalSelectConditionsStpV getNew(ModalSelectConditions modalSelectConditions) {
		return new ModalSelectConditionsStpV(modalSelectConditions);
	}
	
	@Step (
		description="Incluir valores <b>#{condition.toString()} #{texto}</b>",
		expected="El valor se incluye automáticamente")
	public void include(ConditionInclude condition, String texto) throws Exception {
		modalSelectConditions.include(condition, texto);
	}
	
	@Step (
		description="Incluir valores <b>#{condition.toString()} #{texto}</b>",
		expected="El valor se incluye automáticamente")
	public void exclude(ConditionExclude condition, String texto) throws Exception {
		modalSelectConditions.exclude(condition, texto);
	}
	
	@Step (
		description="Seleccionar el botón <b>OK</b>",
		expected="Desaparece el modal de definición de condiciones")
	public void clickOk() {
		modalSelectConditions.clickOk();
	}
	
}
