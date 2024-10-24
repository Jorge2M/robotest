package com.mng.robotest.tests.domains.chequeregalo.tests;

import java.util.Optional;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compranew.steps.CheckoutNewSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.tests.repository.chequeregalo.entity.ChequeRegaloOutput;
import com.mng.robotest.testslegacy.utils.PaisGetter;

import static com.mng.robotest.testslegacy.data.PaisShop.INDIA;

public class Rgl003 extends TestBase {
	
	private CheckoutNewSteps checkoutSteps = new CheckoutNewSteps();
	
	public Rgl003() throws Exception {
		dataTest.setUserRegistered(true);
		if (inputParamsSuite.getListaPaises().isEmpty()) {
			dataTest.setPais(PaisGetter.from(INDIA));
			dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
		}
	}
	
	@Override
	public void execute() throws Exception {
		boolean isPro = isPRO();
		if (isPro) {
			return;
		}
		
		accessLoginAndClearBolsa(); 
		altaArticulosBolsaAndClickComprar(2);
		
		var chequeRegaloOpt = checkInputChequeRegalo(10.00);
		if (chequeRegaloOpt.isEmpty()) {
			return;
		}
		
		executeVisaPayment();
		checkMisCompras();
//		checkChequeSinSaldo(chequeRegaloOpt.get());
	}
	
	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new BolsaSteps().clear();
	}
	
	private Optional<ChequeRegaloOutput> checkInputChequeRegalo(double amount) {
		var chequeRegaloOpt = checkoutSteps.createChequeRegalo(amount);
		if (chequeRegaloOpt.isEmpty()) {
			return Optional.empty();
		}
		checkoutSteps.inputChequeRegalo(chequeRegaloOpt.get());
		checkoutSteps.removeChequeRegalo(chequeRegaloOpt.get());
		checkoutSteps.inputChequeRegalo(chequeRegaloOpt.get());
		
		return chequeRegaloOpt;
	}
	
	private void checkMisCompras() {
		var dataPago = dataTest.getDataPago();
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().checkIsCompraOnline(codigoPedido);
	}
	
//	private void checkChequeSinSaldo(ChequeRegaloOutput chequeRegalo) throws Exception {
//		selectFooterLinkChequeRegalo();
//		checkChequeRegalo(chequeRegalo);
//	}
	
//	private void selectFooterLinkChequeRegalo() throws Exception {
//		clickLinea(SHE);
//		new FooterSteps().clickLinkFooter(CHEQUE_REGALO);
////		var dnsUrlAcceso = inputParamsSuite.getDnsUrlAcceso();
////		var codigoPais = dataTest.getPais().getCodigoAlf().toLowerCase();
////		driver.get(dnsUrlAcceso + "/" + codigoPais + "/gift-voucher");
//	}
	
//	private void checkChequeRegalo(ChequeRegaloOutput chequeRegalo) {
//		var pgChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps();
//		pgChequeRegaloInputDataSteps.paginaConsultarSaldo(chequeRegalo.getCertificateNumber());
//		pgChequeRegaloInputDataSteps.insertCVVConsultaSaldo(chequeRegalo.getCvc());
//	}
	
}
