package com.mng.robotest.tests.domains.reembolsos.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.reembolsos.pageobjects.PageReembolsos;
import com.mng.robotest.tests.domains.reembolsos.steps.PageReembolsosSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

public class Ree002 extends TestBase {

	private static final Pais EMIRATOS = UNITED_ARAB_EMIRATES.getPais();
	private static final IdiomaPais EMIRATOS_ARABE = EMIRATOS.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		dataTest.setPais(EMIRATOS);
		dataTest.setIdioma(EMIRATOS_ARABE);

		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (isPRO()) {
			return;
		}

		dataTest.setUserRegistered(true);
		dataTest.setUserConnected("mng_test_SA_pruebaSaldo@mango.com");
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());

		if (dataTest.getPais().getEmailuser()!=null /*&& dataTest.getPais().getPassuser()!=null*/) {
			dataTest.setUserConnected(dataTest.getPais().getEmailuser());
			//dataTest.setPasswordUser(dataTest.getPais().getPassuser());
		}
		
		accessAndClearData();
		var pageReembolsosSteps = new PageReembolsosSteps();
		pageReembolsosSteps.gotoRefundsFromMenu(dataTest.getPais().existsPagoStoreCredit());
		pageReembolsosSteps.selectRadioSalCtaAndRefresh();
		if (new PageReembolsos().isVisibleSaveButtonStoreCredit()) {
			pageReembolsosSteps.clickSaveButtonStoreCredit();
		}
		float saldoCtaIni = new PageReembolsos().getImporteStoreCredit();
		new SecBolsaSteps().altaArticlosConColores(1);
		
		//Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
		var configCheckout = ConfigCheckout.config()
				.checkPagos()
				.storeCredit()
				.emaiExists()
				.checkPromotionalCode().build();
		
		var dataPago = getDataPago(configCheckout);
		
		//Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
		dataPago.getDataPedido().setEmailCheckout(dataTest.getUserConnected());
		dataPago.setUserWithStoreC(true);
		dataPago.setSaldoCta(saldoCtaIni);
		var pagoStoreCredit = dataTest.getPais().getPago("STORECREDIT");
		
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
			
			pageReembolsosSteps.gotoRefundsFromMenuAndValidaSalCta(dataTest.getPais().existsPagoStoreCredit(), saldoCtaEsperado);
			checkPedidosManto(dataPago.getListPedidos());
		}		
	}

}
