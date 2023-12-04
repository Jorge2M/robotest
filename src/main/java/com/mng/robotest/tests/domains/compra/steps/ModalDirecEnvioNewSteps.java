package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DirectionData;
import com.mng.robotest.tests.domains.compra.pageobjects.modals.ModalDirecEnvioNew;

public class ModalDirecEnvioNewSteps extends StepBase {

	private final ModalDirecEnvioNew modalDirecEnvio = new ModalDirecEnvioNew();
	
	@Validation (description="Es visible el Modal para Añadir/Editar una dirección " + SECONDS_WAIT)
	public boolean checkIsVisible(int seconds) {
		return modalDirecEnvio.isVisible(seconds);
	}

	@Step (
		description="Introducir los datos y pulsar \"Guardar\"<br>#{direction.getFormattedHTMLData()}", 
		expected="Los datos se añaden correctamente")
	public void inputDataAndSave(DirectionData direction) {
		modalDirecEnvio.inputData(direction);
		modalDirecEnvio.clickSaveButton();
		new CheckoutSteps().clickEditarDirecEnvio();
		new ModalMultidirectionSteps().checkAfterAddDirection(direction);
	}
	@Step (
		description="Editar los datos y pulsar \"Guardar\"<br>#{direction.getFormattedHTMLData()}",
		expected="Los datos se actualizan correctamente")
	public void inputDataAndEdit(DirectionData direction) {
		modalDirecEnvio.inputDataEdit(direction);
		modalDirecEnvio.clickSaveButton();
		if (channel.isDevice()) {
			new CheckoutSteps().clickEditarDirecEnvio();
		}
		new ModalMultidirectionSteps().checkAfterAddDirection(direction);
	}
	
	@Step (
		description="Clickar el botón <b>Eliminar</b>", 
		expected="Aparece el modal de confirmación de la eliminación")
	public void clickEliminarButton() {
		modalDirecEnvio.clickRemoveButton();
		checkIsModalConfirmacionEliminar();
	}
	
	@Validation (description="Aparece el modal de confirmación de la eliminación")
	public boolean checkIsModalConfirmacionEliminar() {
		return modalDirecEnvio.isVisibleModalConfirmacionEliminar(1);
	}	
	
	@Step (
		description="Clickar el botón <b>Eliminar</b> del modal de confirmación", 
		expected="La dirección se elimina")
	public void confirmEliminarDirection(String address) {
		modalDirecEnvio.clickConfirmEliminarButton();
		new CheckoutSteps().clickEditarDirecEnvio();
		new ModalMultidirectionSteps().checkAddressNotExists(address);
	}	
}
