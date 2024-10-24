package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.compra.pageobjects.mobile.Page1EnvioCheckoutMobil;
import com.mng.robotest.tests.domains.compra.pageobjects.mobile.Page2DatosPagoCheckoutMobil;
import com.mng.robotest.tests.domains.compra.steps.envio.ModalDroppointsSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class Page1EnvioCheckoutMobilSteps extends StepBase {

	private final Page1EnvioCheckoutMobil page1EnvioCheckoutMobil = new Page1EnvioCheckoutMobil();
	public final ModalDroppointsSteps modalDroppointsSteps = new ModalDroppointsSteps();
	
	@Validation
	public ChecksTM checkIsPage(boolean userLogged) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Aparece la página correspondiente al paso-1 " + getLitSecondsWait(seconds),
			isPage(seconds), WARN);
		
		checks.add(
			"Aparece el botón de introducción del código promocional",
			page1EnvioCheckoutMobil.isVisibleInputCodigoPromoUntil(0));
		
		if (!userLogged) {
			checks.add(
				"Aparece seleccionado el método de envío \"Estándar\"",
				page1EnvioCheckoutMobil.isPresentEnvioStandard(), WARN);
		}
		return checks;
	}
	
	public boolean isPage(int seconds) {
		return page1EnvioCheckoutMobil.isPage(seconds);
	}

	@Step (
		description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b> (previamente, si no lo estamos, nos posicionamos en el apartado \"1. Envio\")", 
		expected="Se selecciona el método de envío correctamente")
	public void selectMetodoEnvio(
			TipoTransporte tipoTransporte, @SuppressWarnings("unused") String nombrePago) {
		
		page1EnvioCheckoutMobil.selectMetodoAfterPositioningIn1Envio(tipoTransporte);
		if (!tipoTransporte.isEntregaDomicilio()) {
			var modalDroppoints = new ModalDroppoints();
			if (modalDroppoints.isErrorMessageVisibleUntil()) {
				var datosRegistro = dataTest.getDataPago().getDatosRegistro();
				modalDroppoints.searchAgainByUserCp(datosRegistro.get("cfCp"));
			}
		}

		validaBlockSelected(tipoTransporte, 3);
		if (tipoTransporte.isEntregaDomicilio()) {
			modalDroppointsSteps.validaIsNotVisible();
		} else {
			modalDroppointsSteps.validaIsVisible();
		}
	}
	
	@Validation (
		description="Queda seleccionado el bloque correspondiete a <b>#{tipoTransporte}</b> " + SECONDS_WAIT,
		level=WARN)
	public boolean validaBlockSelected(TipoTransporte tipoTransporte, int seconds) {
		return (page1EnvioCheckoutMobil.isBlockSelectedUntil(tipoTransporte, seconds));
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página asociada al Paso-2")
	public void clickContinuarToMetodosPago() {
		page1EnvioCheckoutMobil.clickContinuar();
		new CheckoutSteps().validateLoadingDisappears(10);
		checkAppearsStep2();
		checkAppearsPageWithPaymentMethods();
	}
	
	@Validation (description="Aparece la página asociada al Paso-2")
	private boolean checkAppearsStep2() {
		return new Page2DatosPagoCheckoutMobil().isPage(3);
	}
	
	@Validation (description="Están presentes los métodos de pago")
	private boolean checkAppearsPageWithPaymentMethods() {
		return new PageCheckoutWrapper().isPresentMetodosPago();
	}
	
	@Validation
	public ChecksTM validaResultImputPromoEmpl() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
			"Aparece el descuento total aplicado al empleado " + getLitSecondsWait(seconds),
			page1EnvioCheckoutMobil.isVisibleDescuentoEmpleadoUntil(seconds), WARN);
	 	
	 	checks.add(
			"Aparece un descuento de empleado mayor que 0",
			page1EnvioCheckoutMobil.validateDiscountEmpleadoNotNull(), WARN);
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
		expected="La franja horaria se selecciona correctamente")
	public void selectFranjaHorariaUrgente(int posicion) {
		page1EnvioCheckoutMobil.selectFranjaHorariaUrgente(posicion);
	}
}
