package com.mng.robotest.test.appshop.reembolsos;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.compra.tests.CompraSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.PageReembolsosSteps;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoSteps;
import com.mng.robotest.test.utils.ImporteScreen;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Ree002 extends TestBase {

	private static final Pais EMIRATOS = PaisGetter.get(PaisShop.UNITED_ARAB_EMIRATES);
	private static final IdiomaPais EMIRATOS_ARABE = EMIRATOS.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		dataTest.pais = EMIRATOS;
		dataTest.idioma = EMIRATOS_ARABE;

		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (isPRO()) {
			return;
		}

		dataTest.userRegistered = true;
		dataTest.userConnected = "mng_test_SA_pruebaSaldo@mango.com";
		dataTest.passwordUser = dataTest.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();

		if (dataTest.pais.getEmailuser()!=null && dataTest.pais.getPassuser()!=null) {
			dataTest.userConnected = dataTest.pais.getEmailuser();
			dataTest.passwordUser = dataTest.pais.getPassuser();
		}
		
		accessAndClearData();
		PageReembolsosSteps pageReembolsosSteps = new PageReembolsosSteps();
		pageReembolsosSteps.gotoRefundsFromMenu(dataTest.pais.existsPagoStoreCredit());
		pageReembolsosSteps.selectRadioSalCtaAndRefresh();
		if (new PageReembolsos().isVisibleSaveButtonStoreCredit()) {
			pageReembolsosSteps.clickSaveButtonStoreCredit();
		}
		float saldoCtaIni = new PageReembolsos().getImporteStoreCredit();
		
		DataBag dataBag = new SecBolsaSteps().altaArticlosConColores(1);
		
		//Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos()
				.storeCredit()
				.emaiExists()
				.checkPromotionalCode().build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		
		//Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
		dataPago.getDataPedido().setEmailCheckout(dataTest.userConnected);
		dataPago.setUserWithStoreC(true);
		dataPago.setSaldoCta(saldoCtaIni);
		Pago pagoStoreCredit = dataTest.pais.getPago("STORECREDIT");
		dataPago.getDataPedido().setDataBag(dataBag);
		
		dataPago = new CheckoutFlow.BuilderCheckout(dataPago)
			.pago(pagoStoreCredit)
			.build()
			.checkout(From.BOLSA);
		
		if (!isPRO()) {
			//Volvemos a la portada (Seleccionamos el link "Seguir de shopping" o el icono de Mango)
			new PageResultPagoSteps().selectSeguirDeShopping(app);
			
			//Calculamos el saldo en cuenta que debería quedar (según si se ha realizado o no el pago);
			float saldoCtaEsperado;
			if (pagoStoreCredit.getTestpasarela().compareTo("s")==0 &&
				pagoStoreCredit.getTestpago().compareTo("s")==0) {
				DataPedido dataPedido = dataPago.getListPedidos().get(0);
				float importePago = ImporteScreen.getFloatFromImporteMangoScreen(dataPedido.getImporteTotalSinSaldoCta());
				saldoCtaEsperado = UtilsMangoTest.round(saldoCtaIni - importePago, 2);
			} else {
				saldoCtaEsperado = saldoCtaIni;
			}
			
			pageReembolsosSteps.gotoRefundsFromMenuAndValidaSalCta(dataTest.pais.existsPagoStoreCredit(), saldoCtaEsperado);
			new CompraSteps().checkPedidosManto(dataPago.getListPedidos());
		}		
	}

}
