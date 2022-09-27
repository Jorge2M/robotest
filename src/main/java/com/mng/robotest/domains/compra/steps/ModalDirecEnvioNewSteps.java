package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.pageobject.DirectionData;

import com.mng.robotest.domains.compra.pageobject.ModalDirecEnvioNew;
import com.mng.robotest.domains.transversal.StepBase;

public class ModalDirecEnvioNewSteps extends StepBase {

	private final ModalDirecEnvioNew modalDirecEnvio = new ModalDirecEnvioNew();
	
	@Validation (
		description="Visible el Modal para Añadir/Editar una dirección",
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
		if (channel.isDevice()) {
			new CheckoutSteps().clickEditarDirecEnvio();
		}
		new ModalMultidirectionSteps().checkAfterAddDirection(direction);
	}
	@Step (
		description="Editar los datos y pulsar \"Guardar\"<br>#{direction.getFormattedHTMLData()}",
		expected="Los datos se actualizan correctamente")
	public void inputDataAndEdit(DirectionData direction) throws Exception {
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
	public void clickEliminarButton() throws Exception {
		modalDirecEnvio.clickRemoveButton();
		checkIsModalConfirmacionEliminar();
	}
	
	@Validation (
		description="Aparece el modal de confirmación de la eliminación",
		level=State.Defect)
	public boolean checkIsModalConfirmacionEliminar() {
		return modalDirecEnvio.isVisibleModalConfirmacionEliminar(1);
	}	
	
	@Step (
		description="Clickar el botón <b>Eliminar</b> del modal de confirmación", 
		expected="La dirección se elimina")
	public void confirmEliminarDirection(String address) throws Exception {
		modalDirecEnvio.clickConfirmEliminarButton();
		new CheckoutSteps().clickEditarDirecEnvio();
		new ModalMultidirectionSteps().checkAddressNotExists(address);
	}	
}