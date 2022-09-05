package com.mng.robotest.test.steps.shop.checkout;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.SecStoreCredit;
import com.mng.robotest.test.utils.ImporteScreen; 

public class SecStoreCreditSteps extends StepBase { 
	
	private final SecStoreCredit secStoreCredit = new SecStoreCredit();
	
	private static final String TAG_NOMBRE_PAGO = "@TagNombrePago";
	@Step (
		description="Revisamos el bloque de \"Saldo en cuenta\"", 
		expected="Sólo aparece el método de pago " + TAG_NOMBRE_PAGO)
	public void validateInitialStateOk(DataPago dataPago) throws Exception {
		String nombrePago = dataPago.getDataPedido().getPago().getNombre(channel, app);
		TestMaker.getCurrentStepInExecution().replaceInExpected(TAG_NOMBRE_PAGO, nombrePago);
		
		dataPago.getDataPedido().setImporteTotal(
				new PageCheckoutWrapper().getPrecioTotalFromResumen());
		
		validaBloqueSaldoEnCuenta(true, dataPago);
	}

	@Step (
		description="Seleccionamos el bloque de \"Saldo en cuenta\"", 
		expected="El marcado o desmarcado es correcto")
	public void selectSaldoEnCuentaBlock(Pais pais, DataPago dataPago) throws Exception {
		boolean marcadoInicialmente = secStoreCredit.isChecked();
		secStoreCredit.selectSaldoEnCuenta();
		
		PageCheckoutWrapperSteps pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps();
		PageCheckoutWrapper pageCheckoutWrapper = pageCheckoutWrapperSteps.getPageCheckoutWrapper();
		pageCheckoutWrapperSteps.validateLoadingDisappears(5);
		validaBloqueSaldoEnCuenta(!marcadoInicialmente, dataPago);
		if (marcadoInicialmente) {
			boolean isEmpl = dataPago.getFTCkout().userIsEmployee;
			pageCheckoutWrapperSteps.validaMetodosPagoDisponibles(pais, isEmpl);
			dataPago.getDataPedido().setImporteTotalSinSaldoCta(
					pageCheckoutWrapper.getPrecioTotalSinSaldoEnCuenta());
		} else {
			checkAfterMarkSaldoEnCuenta(pais, pageCheckoutWrapper);
			if (channel.isDevice()) {
				dataPago.getDataPedido().setImporteTotalSinSaldoCta(
						pageCheckoutWrapper.getPrecioTotalSinSaldoEnCuenta());
			}
		}
	}
	
	@Validation (
		description="Aparecen 0 métodos de pago",
		level=State.Warn)
	private boolean checkAfterMarkSaldoEnCuenta(Pais pais, PageCheckoutWrapper pageCheckoutWrapper) {
		int numPagosExpected = 0;
		return (pageCheckoutWrapper.isNumpagos(numPagosExpected));
	}
   
	@Validation
	public ChecksTM validaBloqueSaldoEnCuenta(boolean checkedSaldoEnCta, DataPago dataPago) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Es visible el bloque correspondiente al pago mediante \"Saldo en cuenta\"",
			secStoreCredit.isVisible(), State.Defect);
	  	
	  	boolean isCheckedBlock = secStoreCredit.isChecked();
	  	if (checkedSaldoEnCta) {
		  	checks.add(
				"Está marcado el radio del bloque de \"Saldo en cuenta\"",
				isCheckedBlock, State.Defect);
	  	} else {
		  	checks.add(
				"No está marcado el radio del bloque de \"Saldo en cuenta\"",
				!isCheckedBlock, State.Warn);
	  	}
	  	
	  	if (checkedSaldoEnCta/* || channel==Channel.desktop*/) {
			String impTotResumen = new PageCheckoutWrapper().getPrecioTotalFromResumen();
			float impFloat = ImporteScreen.getFloatFromImporteMangoScreen(impTotResumen);
		  	checks.add(
				"Figura un importe total de 0",
				impFloat==0.0, State.Warn);
	  	}

		float saldoCta = dataPago.getSaldoCta();
	  	checks.add(
			"Figura un saldo en cuenta de: " + saldoCta,
			secStoreCredit.getImporte()==saldoCta, State.Warn);
	  	
	  	return checks;
	}
}