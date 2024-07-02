package com.mng.robotest.tests.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.SecConfirmDatos;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class SecConfirmDatosSteps extends StepBase {

	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	private final SecConfirmDatos secConfirmDatos = modalDroppoints.getSecConfirmDatos();
	
	@Validation (
		description="Es visible la capa de confirmación de los datos " + SECONDS_WAIT)
	public boolean checkIsVisible(int seconds) {
		return secConfirmDatos.isVisibleUntil(seconds);
	}
	
	public void setDataIfNeeded() {
		if (isCountry(DEUTSCHLAND) &&
			secConfirmDatos.isVisibleInputPostNumberIdDeutschland()) {
			inputPostNumberId("0038594352");
		}
	}
	
	@Step (
		description="Introducir <b>#{postNumberId}</b> en el Post Number Id",
		expected="El valor queda correctamente introducido")
	public void inputPostNumberId(String postNumberId) {
		secConfirmDatos.sendDataInputPostNumberIdDeutschland(postNumberId);
	}
	
	@Step (
		description="Clickamos el botón \"Confirmar Datos\"", 
		expected="La dirección de envío se establece a la de la tienda")
	public void clickConfirmarDatosButton(DataPedido dataPedido) {
		secConfirmDatos.clickConfirmarDatosButtonAndWait(5);
		waitForPageLoaded(driver, 2);	   
		checkConfirmacionCambioDireccionEnvio(dataPedido);
	}
	
	@Validation
	private ChecksTM checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Desaparece la capa de Droppoints " + getLitSecondsWait(seconds),
			modalDroppoints.isInvisibleUntil(seconds), WARN);

		DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
		var pageCheckoutWrapper = new PageCheckoutWrapper();
		checks.add(
			"Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")",
			pageCheckoutWrapper.isDireccionEnvioContains(dataDp.getDireccion(), 2));
		
		checks.add(
			"Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")",
			pageCheckoutWrapper.isDireccionEnvioContains(dataDp.getCPandPoblacion(), 2));
		
		String textDireccionEnvioCompleta = pageCheckoutWrapper.getTextDireccionEnvioCompleta();
		dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
		
		return checks;
	}
	
}
