package com.mng.robotest.test.steps.shop.checkout.envio;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;


public class ModalDroppointsSteps extends PageBase {
	
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private final SecSelectDPointSteps secSelectDPointSteps = new SecSelectDPointSteps();
	private final SecConfirmDatosSteps secConfirmDatosSteps = new SecConfirmDatosSteps();
	
	@Validation
	public ChecksTM validaIsVisible() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
	  	checks.add(
			"Desaparece el mensaje de \"Cargando...\" (lo esperamos hasta " + maxSeconds + " segundos)",
			modalDroppoints.isInvisibleCargandoMsgUntil(maxSeconds), State.Warn);
	  	
	  	checks.add(
			"Aparece un 1er Droppoint visible (lo esperamos hasta " + maxSeconds + " segundos)",
			modalDroppoints.isDroppointVisibleUntil(1, maxSeconds), State.Info);
	  	
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
	
	public void fluxSelectDroppoint(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
		Pago pago = dCtxPago.getDataPedido().getPago();
		DataSearchDeliveryPoint dataSearchDp = DataSearchDeliveryPoint.getInstance(pago, dCtxSh.appE, dCtxSh.pais);
		secSelectDPointSteps.searchPoblacion(dataSearchDp);
		DataDeliveryPoint dataDp = secSelectDPointSteps.clickDeliveryPointAndGetData(2);
		dCtxPago.getDataPedido().setTypeEnvio(pago.getTipoEnvioType(dCtxSh.appE));
		dCtxPago.getDataPedido().setDataDeliveryPoint(dataDp);
		secSelectDPointSteps.clickSelectButton();
		secConfirmDatosSteps.setDataIfNeeded(dCtxSh.pais.getCodigo_pais());
		secConfirmDatosSteps.clickConfirmarDatosButton(dCtxPago.getDataPedido());				
	}

	public SecSelectDPointSteps getSecSelectDPointSteps() {
		return secSelectDPointSteps;
	}

	public SecConfirmDatosSteps getSecConfirmDatosSteps() {
		return secConfirmDatosSteps;
	}
}
