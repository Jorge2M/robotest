package com.mng.robotest.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.domains.compra.pageobjects.envio.SecConfirmDatos;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPedido;

public class SecConfirmDatosSteps extends StepBase {

	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	private final SecConfirmDatos secConfirmDatos = modalDroppoints.getSecConfirmDatos();
	
	@Validation (
		description="Es visible la capa de confirmación de los datos (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean validateIsVisible(int seconds) {
		return (secConfirmDatos.isVisibleUntil(seconds));
	}
	
	public void setDataIfNeeded() throws Exception {
		String codigoPais = dataTest.getCodigoPais();
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
		PageObjTM.waitForPageLoaded(driver, 2);	   
		checkConfirmacionCambioDireccionEnvio(dataPedido);
	}
	
	@Validation
	private ChecksTM checkConfirmacionCambioDireccionEnvio(DataPedido dataPedido) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 2;
		checks.add(
			"Desaparece la capa de Droppoints (lo esperamos hasta " + seconds + " segundos)",
			modalDroppoints.isInvisibleUntil(seconds), State.Warn);
		
		DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
		String textDireccionEnvioCompleta = new PageCheckoutWrapper().getTextDireccionEnvioCompleta();
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
