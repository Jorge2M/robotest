package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.steps.Page2IdentCheckoutSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.pageobjects.CommonsRegisterObject;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroInitialShop;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroInitialShopSteps extends StepBase {

	private final PageRegistroInitialShop pgRegistroInitial = PageRegistroInitialShop.make();
	
	public void checkPage(int seconds) {
		checkIsPage(seconds);
		checkRadioPubli();
	}
	
	@Validation (
		description="Aparece la página inicial del proceso de registro " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgRegistroInitial.isPage(seconds);
	}

	@Validation
	private ChecksTM checkRadioPubli() {
		var checks = ChecksTM.getNew();
		boolean isSelectedPubli = pgRegistroInitial.isSelectedCheckboxGivePromotions();
		if (dataTest.getPais().getRegister().isPubli()) {
		  	checks.add(
				"Sí está seleccionado el radiobox de publicidad",
				isSelectedPubli, WARN);
		} else {
		  	checks.add(
				"No está seleccionado el radiobox de publicidad",
				!isSelectedPubli, WARN);
		}
		return checks;
	}		
	
	@Step (
		description=
			"Introducir los datos:<br>" + 
			"  - Email: <b>#{data.getEmail()}</b><br>" +
			"  - Contraseña: <b>#{data.getPassword()}</b></br>" + 
			"  - Mobil: <b>#{data.getMovil()}</b><br>" +
			"  - Check promociones: <b>#{data.isCheckPromotions()}</b>",
		expected=
			"La introducción de datos es correcta")
	public void inputData(DataNewRegister data) {
		pgRegistroInitial.inputMovil(data.getMovil());
		pgRegistroInitial.inputPassword(data.getPassword());
		pgRegistroInitial.inputEmail(data.getEmail());
		if (data.isCheckPromotions()) {
			pgRegistroInitial.enableCheckBoxGivePromotions();
		} else {
			pgRegistroInitial.disableCheckBoxGivePromotions();
		}
	}
	
	@Step (
		description="Pulsar el botón <b>Crear cuenta</b>",
		expected="Aparece la página de personalización del registro")
	public void clickCreateAccountButton() {
		pgRegistroInitial.clickCreateAccountButton();
		new PageRegistroPersonalizacionShopSteps().checkPage(10);
		checksDefault();
	}	
	
	@Step (
		description="Pulsar el botón <b>Crear cuenta</b> desde el flujo Bolsa -> Checkout",
		expected="Aparece la página de introducción de datos del checkout")
	public void clickCreateAccountButtonFromBolsa() {
		pgRegistroInitial.clickCreateAccountButton();
		new Page2IdentCheckoutSteps().checkIsPageAccessFromBolsaDevice(2);
	}	
	
	@Step (
		description="Pulsar el botón <b>Crear cuenta</b>",
		expected="")
	public void clickCreateAccountButtonWithoutCheck() {
		pgRegistroInitial.clickCreateAccountButton();
	}	
	
	@Validation(
	    description="Aparece un modal de error con el texto \"¿ya tienes cuenta?\" " + SECONDS_WAIT)
	public boolean checkUserExistsMessage(int seconds) {
		return pgRegistroInitial.checkUserExistsModalMessage(seconds);
	}

	@Validation(
	    description="Aparece un error de móvil incorrecto " + SECONDS_WAIT)	
	public boolean checkPhoneInvalidMessage(int seconds) {
		return pgRegistroInitial.checkMessageErrorMovil(seconds);
	}
	
	@Step (
		description="Cerrar el modal con el mensaje de error",
		expected="Desaparece el modal")
	public void closeModalError() {
		pgRegistroInitial.closeModalMessageError();
	}

	@Step(
		description="Introducir la fecha de nacimiento <b>#{birthdate}</b>",
		expected="El dato se introduce correctamente")	
	public void inputBirthDate(String birthdate) {
		pgRegistroInitial.inputBirthDate(birthdate);
	}
	
	@Step(
		description="Seleccionar el link <b>Collection and use of optional personal information</b>",
		expected="Aparece el apartado de collection and use")	
	public void clickLinkGivePromotions() {
		pgRegistroInitial.clickLinkGivePromotions();
	}	
		
	@Step(
		description="Seleccionar el link <b>Collection and use</b>",
		expected="Aparece el apartado de collection and use")	
	public void clickConsentPersonalInformationLink() {
		pgRegistroInitial.clickConsentPersonalInformationLink();
		checkPersonalInformationInfoVisible();
	}	
	@Validation(
	    description="Aparece el apartado de \"Collection and use of your personal information\"")	
	public boolean checkPersonalInformationInfoVisible() {
		return pgRegistroInitial.checkPersonalInformationInfoVisible();
	}
	
	@Step(
		description="Seleccionar el checkbox <b>Collection and use</b>",
		expected="Se selecciona el checkbox")	
	public void clickConsentPersonalInformationRadio() {
		pgRegistroInitial.clickConsentPersonalInformationRadio();
	}

	@Step (
		description="Pulsar el link <b>Política de privacidad</b>",
		expected="Aparece el modal de \"Cómo protegemos y tratamos tus datos?\"")	
	public boolean clickPoliticaPrivacidad() {
		pgRegistroInitial.clickPoliticaPrivacidad();
		pgRegistroInitial.keyDown(5);
		if (!pgRegistroInitial.isModalPoliticaPrivacidadVisible(1)) {
			pgRegistroInitial.clickPoliticaPrivacidad();
			pgRegistroInitial.keyDown(5);
		}
		return checkIsModalPoliticaPrivacidadVisible(1);
	}
	@Validation (description="Aparece el modal de \"Cómo protegemos y tratamos tus datos?\" " + SECONDS_WAIT)
	public boolean checkIsModalPoliticaPrivacidadVisible(int seconds) {
		return pgRegistroInitial.isModalPoliticaPrivacidadVisible(seconds);
	}	
	
	public void clickCondicionesVenta() {
		clickCondicionesVentaStep();
		if (new CommonsRegisterObject().isGenesis()) {
			back();
		}
	}
	
	public void clickPoliticaPrivacidadModal() {
		clickPoliticaPrivacidadModalStep();
		if (new CommonsRegisterObject().isGenesis()) {
			back();
		}
	}
	
	@Step (
		description="Pulsar el link <b>Política de privacidad</b> del modal",
		expected="Aparece una nueva página con la política de privacidad y cookies")
	private void clickPoliticaPrivacidadModalStep() {
		pgRegistroInitial.clickPoliticaPrivacidadModal();
		new PagePoliticaPrivacidadSteps().checkisPage(4);
	}
	
	@Step (
		description="Pulsar el link <b>Condiciones de venta</b> del modal",
		expected="Aparece una nueva página con la política de privacidad y cookies")
	private void clickCondicionesVentaStep() {
		pgRegistroInitial.clickCondicionesVenta();
		new PageCondicionesVentaSteps().checkisPage(4);
	}	
	
	@Validation (description="Desaparece el modal de \"Cómo protegemos y tratamos tus datos?\"")
	public boolean checkIsModalPoliticaPrivacidadInvisible() {
		return pgRegistroInitial.isModalPoliticaPrivacidadInvisible(2);
	}
	
}
