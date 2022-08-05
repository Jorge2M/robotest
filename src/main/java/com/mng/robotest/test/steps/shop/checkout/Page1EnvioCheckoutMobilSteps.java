package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test.pageobject.shop.checkout.Page2DatosPagoCheckoutMobil;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.steps.shop.checkout.envio.ModalDroppointsSteps;

/**
 * @author jorge.munoz
/* Pasos/Validaciones correspondientes a la página-1 del checkout (1. Envío) en móvil-web
 */

public class Page1EnvioCheckoutMobilSteps {

	private final Page1EnvioCheckoutMobil page1EnvioCheckoutMobil;
	public static ModalDroppointsSteps modalDroppoints;
	
	private final WebDriver driver;
	
	public Page1EnvioCheckoutMobilSteps(WebDriver driver) {
		this.page1EnvioCheckoutMobil = new Page1EnvioCheckoutMobil(driver);
		this.driver = driver;
	}

	@Validation
	public ChecksTM validateIsPage(boolean userLogged) {
		ChecksTM checks = ChecksTM.getNew();
			checks.add(
			"Aparece la página correspondiente al paso-1",
			page1EnvioCheckoutMobil.isPageUntil(1), State.Warn);
			//WebdrvWrapp.isElementPresent(driver, By.xpath("//h2[@data-toggle='step1']")), State.Warn);
			checks.add(
			"Aparece el botón de introducción del código promocional",
			page1EnvioCheckoutMobil.isVisibleInputCodigoPromoUntil(0), State.Defect);
			if (!userLogged) {
				checks.add(
				"Aparece seleccionado el método de envío \"Estándar\"",
				page1EnvioCheckoutMobil.isPresentEnvioStandard(), State.Warn);
			}
			
			return checks;
	}

	@SuppressWarnings("static-access")
	@Step (
		description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b> (previamente, si no lo estamos, nos posicionamos en el apartado \"1. Envio\")", 
		expected="Se selecciona el método de envío correctamente")
	public void selectMetodoEnvio(
			TipoTransporte tipoTransporte, @SuppressWarnings("unused") String nombrePago, DataCtxPago dCtxPago) throws Exception {
		page1EnvioCheckoutMobil.selectMetodoAfterPositioningIn1Envio(tipoTransporte);
		if (!tipoTransporte.isEntregaDomicilio()) {
			if (ModalDroppoints.isErrorMessageVisibleUntil(driver)) {
				ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
			}
		}

		validaBlockSelected(tipoTransporte, 3);
		if (tipoTransporte.isEntregaDomicilio()) {
			modalDroppoints.validaIsNotVisible(Channel.mobile, driver);
		} else {
			modalDroppoints.validaIsVisible(Channel.mobile, driver);
		}
	}
	
	@Validation (
		description="Queda seleccionado el bloque correspondiete a <b>#{tipoTransporte}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validaBlockSelected(TipoTransporte tipoTransporte, int maxSeconds) throws Exception {
		return (page1EnvioCheckoutMobil.isBlockSelectedUntil(tipoTransporte, maxSeconds));
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página asociada al Paso-2")
	public void clickContinuarToMetodosPago(DataCtxShop dCtxSh, boolean saldoEnCuenta) throws Exception {
		page1EnvioCheckoutMobil.clickContinuar();
		new PageCheckoutWrapperSteps(dCtxSh.channel, dCtxSh.appE, driver).validateLoadingDisappears(10);
		checkAppearsStep2(dCtxSh.appE);
		if (!saldoEnCuenta) {
			checkAppearsPageWithPaymentMethods(dCtxSh.pais, dCtxSh.appE);
		}
	}
	
	@Validation (
		description="Aparece la página asociada al Paso-2",
		level=State.Defect)
	private boolean checkAppearsStep2(AppEcom app) {
		return (new Page2DatosPagoCheckoutMobil(Channel.mobile, app, driver).isPageUntil(3));
	}
	
	@Validation (
		description="Están presentes los métodos de pago",
		level=State.Defect)
	private boolean checkAppearsPageWithPaymentMethods(Pais pais, AppEcom app) {
		return (new PageCheckoutWrapper(Channel.mobile, app, driver).isPresentMetodosPago());
	}
	
	@Validation
	public ChecksTM validaResultImputPromoEmpl() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
	 	checks.add(
			"Aparece el descuento total aplicado al empleado (en menos de " + maxSeconds + " segundos)",
			page1EnvioCheckoutMobil.isVisibleDescuentoEmpleadoUntil(maxSeconds), State.Warn);
	 	checks.add(
			"Aparece un descuento de empleado mayor que 0",
			page1EnvioCheckoutMobil.validateDiscountEmpleadoNotNull(), State.Warn);
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
		expected="La franja horaria se selecciona correctamente")
	public void selectFranjaHorariaUrgente(int posicion) {
		page1EnvioCheckoutMobil.selectFranjaHorariaUrgente(posicion);
	}
}
