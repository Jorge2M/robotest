package com.mng.robotest.tests.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.testslegacy.beans.Pago;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalDroppointsSteps extends StepBase {
	
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private final SecSelectDPointSteps secSelectDPointSteps = new SecSelectDPointSteps();
	private final SecConfirmDatosSteps secConfirmDatosSteps = new SecConfirmDatosSteps();
	
	@Validation
	public ChecksTM validaIsVisible() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
	  	checks.add(
			"Desaparece el mensaje de \"Cargando...\" " + getLitSecondsWait(seconds),
			modalDroppoints.isInvisibleCargandoMsgUntil(seconds), WARN);
	  	
	  	checks.add(
			"Aparece un 1er Droppoint visible " + getLitSecondsWait(seconds),
			modalDroppoints.isDroppointVisibleUntil(1, seconds), INFO);
	  	
	  	checks.add(
			"Sí aparece el modal con el mapa de Droppoints",
			modalDroppoints.isVisible());
	  	
	  	return checks;
	}
	
	@Validation (description="No aparece el modal con el mapa de Droppoints")
	public boolean validaIsNotVisible() {
		return (!modalDroppoints.isVisible());
	}
	
	public void fluxSelectDroppoint() {
		var dataPago = dataTest.getDataPago();
		Pago pago = dataPago.getDataPedido().getPago();
		DataSearchDeliveryPoint dataSearchDp = new DataSearchDeliveryPoint(pago, app, dataTest.getPais());
		if (dataSearchDp.getData()!=null) {
			secSelectDPointSteps.searchPoblacion(dataSearchDp);
		}
		DataDeliveryPoint dataDp = secSelectDPointSteps.clickDeliveryPointAndGetData(2);
		dataPago.getDataPedido().setTypeEnvio(pago.getTipoEnvioType(app));
		dataPago.getDataPedido().setDataDeliveryPoint(dataDp);
		secSelectDPointSteps.clickSelectButton();
		secConfirmDatosSteps.setDataIfNeeded();
		secConfirmDatosSteps.clickConfirmarDatosButton(dataPago.getDataPedido());				
	}

	public SecSelectDPointSteps getSecSelectDPointSteps() {
		return secSelectDPointSteps;
	}

	public SecConfirmDatosSteps getSecConfirmDatosSteps() {
		return secConfirmDatosSteps;
	}
}
