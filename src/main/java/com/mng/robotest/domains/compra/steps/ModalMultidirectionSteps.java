package com.mng.robotest.domains.compra.steps;

import java.util.Optional;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.beans.Direction;
import com.mng.robotest.domains.compra.pageobject.ModalMultidirection;
import com.mng.robotest.domains.compra.pageobject.PageCheckoutWrapper;
import com.mng.robotest.domains.transversal.StepBase;

public class ModalMultidirectionSteps extends StepBase {

	private final ModalMultidirection modalMultidirection = new ModalMultidirection();
	
	@Validation(
		description = "Es visible el modal de multidirecciones",
		level=State.Defect)
	public boolean checkIsVisible() throws Exception {
		return modalMultidirection.isVisible();
	}	
	
	@Validation
	public ChecksTM checkInitialContent() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		Optional<Direction> mainDirectionOpt = modalMultidirection.getPrincipalDirection();
	 	checks.add(
			"Aparece una dirección principal",
			mainDirectionOpt.isPresent(), State.Defect);

	 	if (mainDirectionOpt.isPresent()) {
	 		Direction mainDirection = mainDirectionOpt.get();
		 	String direccionCheckout = new PageCheckoutWrapper().getTextDireccionEnvioCompleta();
		 	checks.add(
				"La dirección del checkout contiene la direccion principal <b>" + mainDirection.getLocation() + "</b>",
				direccionCheckout.contains(mainDirectionOpt.get().getAddress()), State.Defect);
		 	
		 	//TODO
		 	//comprobar provincia y población
	 	}
	 	return checks;
	}
	
	@Step (
		description="Clickar el link \"Añadir otra dirección\"",
		expected="Aparece el modal para la introducción de los datos de la nueva dirección")
	public void clickAnyadirOtraDireccion() throws Exception {
		modalMultidirection.clickAnyadirOtraDireccion();
		new ModalDirecEnvioSteps().validateIsOk();
	}
	
	@Validation
	public ChecksTM checkAfterAddDirection(String addressAdded) {
		ChecksTM checks = ChecksTM.getNew();
		Optional<Direction> directionOpt = modalMultidirection.getDirection(addressAdded);
	 	checks.add(
			"Aparece la dirección con address <b>" + addressAdded + "</b>",
			directionOpt.isPresent(), State.Defect);

	 	if (directionOpt.isPresent()) {
		 	new PageCheckoutWrapper().getTextDireccionEnvioCompleta();
		 	checks.add(
				"La dirección añadida no está establecida como la principal",
				!directionOpt.get().isPrincipal(), State.Defect);
	 	}
	 	return checks;		
	}
	
	@Step (
		description="Clickar el link \"Edit\" de la dirección #{addressAdded}",
		expected="Aparece el modal para la introducción de los datos de la dirección")
	public void clickEditAddress(String addressAdded) {
		modalMultidirection.clickEditAddress(addressAdded);
		new ModalDirecEnvioSteps().validateIsOk();
	}
	
	@Validation(
		description = "No existe la dirección #{address}",
		level=State.Defect)
	public boolean checkAddressNotExists(String address) throws Exception {
		return modalMultidirection.getDirection(address).isEmpty();
	}	
	
//	@Validation(
//			description = " Aparece una modal con todas las direcciones guardadas del usuario ",
//			level=State.Defect)
//	public boolean ModalDirecUsuarios() throws Exception {
//		return pageCheckoutWrapper.modalDirecUsuarios();
//	}
//	@Validation(
//			description = "Esta es la dirección principal ",
//			level=State.Defect)
//	public boolean mainDirection() throws Exception {
//		return pageCheckoutWrapper.direction();
//	}
//	@Step (
//			description="Clicamos el CTA-Address",
//			expected="Aparece una modal con todas las direcciones guardadas del usuario")
//	public void clickBntCta() throws Exception {
//			pageCheckoutWrapper.btnAddAddressClick();
//			ModalDirecUsuarios();
//
//	}
//	@Step (
//			description="Comprobamos que la dirección principal es la correcta",
//			expected="coinciden las direccciones")
//	public void CtaMainDirection() throws Exception {
//			mainDirection();
//	}
	
}
