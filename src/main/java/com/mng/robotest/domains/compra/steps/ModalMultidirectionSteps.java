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
				"La dirección del checkout contiene la direccion principal <b>" + mainDirection.getAddress() + "</b>",
				direccionCheckout.contains(mainDirectionOpt.get().getAddress()), State.Defect);
	 	}
	 	
	 	return checks;
	}
	
	@Step (
		description="Clickar el link \"Añadir otra dirección\"",
		expected="Aparece el modal para la introducción de los datos de la nueva dirección")
	public void clickAnyadirOtraDireccion() throws Exception {
		modalMultidirection.clickAnyadirOtraDireccion();
		new ModalDirecEnvioNewSteps().checkIsVisible();
	}
	
	@Validation
	public ChecksTM checkAfterAddDirection(String addressAdded) {
		ChecksTM checks = ChecksTM.getNew();
		String address = addressAdded.toLowerCase();
		int seconds = 5;
		Optional<Direction> directionOpt = modalMultidirection.getDirection(address, seconds);
	 	checks.add(
			"Aparece la dirección con address <b>" + address + "</b> (la esperamos " + seconds + " segundos)",
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
		new ModalDirecEnvioOldSteps().validateIsOk();
	}
	
	@Validation(
		description = "No existe la dirección #{address}",
		level=State.Defect)
	public boolean checkAddressNotExists(String address) throws Exception {
		return modalMultidirection.getDirection(address).isEmpty();
	}	
	
}
