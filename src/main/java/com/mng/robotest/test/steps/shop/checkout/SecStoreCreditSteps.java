package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.SecStoreCredit;
import com.mng.robotest.test.utils.ImporteScreen; 

@SuppressWarnings({"static-access"})
public class SecStoreCreditSteps { 
	
	private final SecStoreCredit secStoreCredit;
	private final Channel channel;
	private final AppEcom app;
	private final WebDriver driver;
	
	public SecStoreCreditSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.secStoreCredit = new SecStoreCredit();
		this.channel = channel;
		this.app = app;
		this.driver = driver;
	}
	
	static final String tagNombrePago = "@TagNombrePago";
	@Step (
		description="Revisamos el bloque de \"Saldo en cuenta\"", 
		expected="Sólo aparece el método de pago " + tagNombrePago)
	public void validateInitialStateOk(DataCtxPago dCtxPago) throws Exception {
		String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(channel, app);
		TestMaker.getCurrentStepInExecution().replaceInExpected(tagNombrePago, nombrePago);
		
		dCtxPago.getDataPedido().setImporteTotal(
				new PageCheckoutWrapper(channel, app).getPrecioTotalFromResumen());
		
		validaBloqueSaldoEnCuenta(true, dCtxPago);
	}

	@Step (
		description="Seleccionamos el bloque de \"Saldo en cuenta\"", 
		expected="El marcado o desmarcado es correcto")
	public void selectSaldoEnCuentaBlock(Pais pais, DataCtxPago dCtxPago, AppEcom app) throws Exception {
		boolean marcadoInicialmente = secStoreCredit.isChecked();
		secStoreCredit.selectSaldoEnCuenta();
		
		PageCheckoutWrapperSteps pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps(channel, app);
		PageCheckoutWrapper pageCheckoutWrapper = pageCheckoutWrapperSteps.getPageCheckoutWrapper();
		pageCheckoutWrapperSteps.validateLoadingDisappears(5);
		validaBloqueSaldoEnCuenta(!marcadoInicialmente, dCtxPago);
		if (marcadoInicialmente) {
			boolean isEmpl = dCtxPago.getFTCkout().userIsEmployee;
			pageCheckoutWrapperSteps.validaMetodosPagoDisponibles(pais, isEmpl);
			dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(
					pageCheckoutWrapper.getPrecioTotalSinSaldoEnCuenta());
		} else {
			checkAfterMarkSaldoEnCuenta(pais, pageCheckoutWrapper);
			if (channel.isDevice()) {
				dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(
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
	public ChecksTM validaBloqueSaldoEnCuenta(boolean checkedSaldoEnCta, DataCtxPago dCtxPago) throws Exception {
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
			String impTotResumen = new PageCheckoutWrapper(channel, app).getPrecioTotalFromResumen();
			float impFloat = ImporteScreen.getFloatFromImporteMangoScreen(impTotResumen);
		  	checks.add(
				"Figura un importe total de 0",
				impFloat==0.0, State.Warn);
	  	}

		float saldoCta = dCtxPago.getSaldoCta();
	  	checks.add(
			"Figura un saldo en cuenta de: " + saldoCta,
			secStoreCredit.getImporte()==saldoCta, State.Warn);
	  	
	  	return checks;
	}
}