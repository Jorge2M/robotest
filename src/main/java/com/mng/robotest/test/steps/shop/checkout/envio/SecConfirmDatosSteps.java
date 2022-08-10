package com.mng.robotest.test.steps.shop.checkout.envio;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test.pageobject.shop.checkout.envio.SecConfirmDatos;


@SuppressWarnings({"static-access"})
public class SecConfirmDatosSteps extends StepBase {

	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	private final SecConfirmDatos secConfirmDatos = modalDroppoints.getSecConfirmDatos();
	
	@Validation (
		description="Es visible la capa de confirmación de los datos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validateIsVisible(int maxSeconds) {
		return (secConfirmDatos.isVisibleUntil(maxSeconds));
	}
	
	public void setDataIfNeeded(String codigoPais) throws Exception {
		if (PaisShop.DEUTSCHLAND == PaisShop.getPais(codigoPais)) {
			if (secConfirmDatos.isVisibleInputPostNumberIdDeutschland()) {
				inputPostNumberId("0038594352");
			}
		}
	}
	
	@Step (
		description="Introducir <b>#{postNumberId}</b> en el Post Number Id",
		expected="El valor queda correctamente introducido")
	public void inputPostNumberId(String postNumberId) throws Exception {
		secConfirmDatos.sendDataInputPostNumberIdDeutschland(postNumberId);
	}
	
	@Step (
		description="Clickamos el botón \"Confirmar Datos\"", 
		expected="La dirección de envío se establece a la de la tienda")
	public void clickConfirmarDatosButton(DataPedido dataPedido) {
		secConfirmDatos.clickConfirmarDatosButtonAndWait(5);
		SeleniumUtils.waitForPageLoaded(driver, 2);	   
		checkConfirmacionCambioDireccionEnvio(dataPedido);
	}
	
	@Validation
	private ChecksTM checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
		checks.add(
			"Desaparece la capa de Droppoints (lo esperamos hasta " + maxSeconds + " segundos)",
			modalDroppoints.isInvisibleUntil(maxSeconds), State.Warn);
		
		DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
		String textDireccionEnvioCompleta = new PageCheckoutWrapper(channel, app, driver).getTextDireccionEnvioCompleta();
		dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
		checks.add(
			"Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")",
			textDireccionEnvioCompleta.contains(dataDp.getDireccion()), State.Defect);
		
		checks.add(
			"Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")",
			textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion()), State.Defect);
		
		return checks;
	}
}
