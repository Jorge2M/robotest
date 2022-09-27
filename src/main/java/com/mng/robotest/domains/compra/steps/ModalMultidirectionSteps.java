package com.mng.robotest.domains.compra.steps;

import java.util.Optional;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.beans.Direction;
import com.mng.robotest.domains.compra.pageobject.DirectionData;
import com.mng.robotest.domains.compra.pageobject.ModalMultidirection;
import com.mng.robotest.domains.compra.pageobject.PageCheckoutWrapper;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class ModalMultidirectionSteps extends StepBase {

	private final ModalMultidirection modalMultidirection = new ModalMultidirection();
	
	@Validation(
		description = "Es visible el modal de multidirecciones (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean checkIsVisible(int seconds) throws Exception {
		return modalMultidirection.isVisible(seconds);
	}	
	
	@Validation
	public ChecksTM checkInitialContent() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
		Optional<Direction> mainDirectionOpt = modalMultidirection.getPrincipalDirection(seconds);
	 	checks.add(
			"Aparece una dirección principal (la esperamos hasta " + seconds + " segundos)",
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
		GenericChecks.checkDefault();
	}

	
	@Validation
	public ChecksTM checkAfterAddDirection(DirectionData direction) {
		ChecksTM checks = ChecksTM.getNew();
		String address = direction.getDireccion().toLowerCase();
		int seconds = 5;
		Optional<Direction> directionOpt = modalMultidirection.getDirection(address, seconds);
	 	checks.add(
			"Aparece la dirección con address <b>" + address + "</b> (la esperamos " + seconds + " segundos)",
			directionOpt.isPresent(), State.Defect);

	 	if (directionOpt.isPresent()) {
	 		if (direction.isPrincipal()) {
			 	checks.add(
					"La dirección añadida está establecida como la principal",
					directionOpt.get().isPrincipal(), State.Defect);	 			
	 		} else {
			 	checks.add(
					"La dirección añadida no está establecida como la principal",
					!directionOpt.get().isPrincipal(), State.Defect);
	 		}
	 	}
	 	return checks;		
	}
	
	@Step (
		description="Clickar el link \"Edit\" de la dirección #{addressAdded}",
		expected="Aparece el modal para la introducción de los datos de la dirección")
	public void clickEditAddress(String addressAdded) {
		modalMultidirection.clickEditAddress(addressAdded);
		new ModalDirecEnvioNewSteps().checkIsVisible();
	}
	
	@Validation(
		description = "No existe la dirección #{address}",
		level=State.Defect)
	public boolean checkAddressNotExists(String address) throws Exception {
		return modalMultidirection.getDirection(address).isEmpty();
	}	

	@Step (
		description="Cerramos el modal de confirmación de eliminación",
		expected="El modal desaparece")
	public void closeModal() {
		modalMultidirection.closeModal();
		checkModalInvisible(1);
	}

	@Validation(
		description = "El modal de confirmación de eliminación no es visible (esperamos #{seconds} segundos)",
		level=State.Defect)
	public boolean checkModalInvisible(int seconds) {
		return modalMultidirection.isModalInvisible(seconds);
	}
	
	
	
}