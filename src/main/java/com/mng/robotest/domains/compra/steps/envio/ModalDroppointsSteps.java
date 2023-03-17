package com.mng.robotest.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.envio.ModalDroppoints;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;

public class ModalDroppointsSteps extends StepBase {
	
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private final SecSelectDPointSteps secSelectDPointSteps = new SecSelectDPointSteps();
	private final SecConfirmDatosSteps secConfirmDatosSteps = new SecConfirmDatosSteps();
	
	@Validation
	public ChecksTM validaIsVisible() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
	  	checks.add(
			"Desaparece el mensaje de \"Cargando...\" (lo esperamos hasta " + seconds + " segundos)",
			modalDroppoints.isInvisibleCargandoMsgUntil(seconds), State.Warn);
	  	
	  	checks.add(
			"Aparece un 1er Droppoint visible (lo esperamos hasta " + seconds + " segundos)",
			modalDroppoints.isDroppointVisibleUntil(1, seconds), State.Info);
	  	
	  	checks.add(
			"Sí aparece el modal con el mapa de Droppoints",
			modalDroppoints.isVisible(), State.Defect);
	  	
	  	return checks;
	}
	
	@Validation (
		description="No aparece el modal con el mapa de Droppoints",
		level=State.Defect)
	public boolean validaIsNotVisible() {
		return (!modalDroppoints.isVisible());
	}
	
	public void fluxSelectDroppoint(DataPago dataPago) {
		Pago pago = dataPago.getDataPedido().getPago();
		DataSearchDeliveryPoint dataSearchDp = DataSearchDeliveryPoint.getInstance(pago, app, dataTest.getPais());
		secSelectDPointSteps.searchPoblacion(dataSearchDp);
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
