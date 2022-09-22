package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.pageobject.DirectionData;
import com.mng.robotest.domains.compra.pageobject.DirectionData2;

import com.mng.robotest.domains.compra.pageobject.ModalDirecEnvioNew;

public class ModalDirecEnvioNewSteps {

	private final ModalDirecEnvioNew modalDirecEnvio = new ModalDirecEnvioNew();
	
	@Validation (
		description="Figura el modal para la edición/alta de una dirección de envío",
		level=State.Defect)
	public boolean checkIsVisible() {
		return modalDirecEnvio.isVisible(2);
	}

	@Step (
		description="Introducir los datos y pulsar \"Guardar\"<br>#{direction.getFormattedHTMLData()}", 
		expected="Los datos se añaden correctamente")
	public void inputDataAndSave(DirectionData direction) throws Exception {
		modalDirecEnvio.inputData(direction);
		modalDirecEnvio.clickSaveButton();
		checkAfterAddDirection(direction);
	}
	@Step (
			description="Editar los datos y pulsar \"Guardar\"<br>#{direction.getFormattedHTMLData()}",
			expected="Los datos se actualizan correctamente")
	public void inputDataAndEdit(DirectionData2 direction) throws Exception {
		modalDirecEnvio.inputDataEdit(direction);
		modalDirecEnvio.clickSaveButton();
		//checkAfterAddDirection(direction);
	}
	
	@Step (
		description="Clickar el botón <b>Eliminar</b>", 
		expected="La dirección se elimina correctamente")
	public void clickEliminarButton(String address) throws Exception {
		modalDirecEnvio.clickRemoveButton();
		new ModalMultidirectionSteps().checkAddressNotExists(address);
	}

	private void checkAfterAddDirection(DirectionData direction) throws Exception {
		String directionAdded = direction.getDireccion();
		new ModalMultidirectionSteps().checkAfterAddDirection(directionAdded);
	}
}
