package com.mng.robotest.tests.domains.chequeregalo.tests;

import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.SHE;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputData.Importe;
import com.mng.robotest.tests.domains.chequeregalo.steps.PageChequeRegaloInputDataSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Rgl002 extends TestBase {

	private final PageChequeRegaloInputDataSteps pgChequeRegaloInputDataSteps;
	
	public Rgl002() throws Exception {
		dataTest.setPais(FRANCE.getPais());
		dataTest.setUserRegistered(true);
		
		var configCheckout = ConfigCheckout.config()
				.checkPagos()
				.checkMisCompras()
				.emaiExists()
				.chequeRegalo().build();
		
		dataTest.setDataPago(configCheckout);
		
		pgChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps();
	}
	
	@Override
	public void execute() throws Exception {
		loginAndClearBolsa();
		selectFooterLinkChequeRegalo();
		inputDataChequeRegalo();
		checkoutChequeRegalo();
		//El acceso al manto de PRE falla constantemente por timeout, no podemos mantener esta validación
		//checkPedido();		
	}
	
	private void loginAndClearBolsa() throws Exception {
		access();
		new BolsaSteps().clear();
	}	

	private void selectFooterLinkChequeRegalo() {
		clickLinea(SHE);
		new FooterSteps().clickLinkFooter(FooterLink.CHEQUE_REGALO_OLD, false);
	}

	private void inputDataChequeRegalo() {
		pgChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
		pgChequeRegaloInputDataSteps.seleccionarCantidades(Importe.EURO_50);

		var chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.MAIL_PERSONAL);
		chequeRegalo.setImporte(Importe.EURO_50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
		pgChequeRegaloInputDataSteps.inputDataAndClickComprar(channel, app, chequeRegalo);
	}
	
	private void checkoutChequeRegalo() throws Exception {
		new BuilderCheckout(dataTest.getDataPago())
			.pago(dataTest.getPais().getPago("VISA"))
			.build()
			.checkout(From.IDENTIFICATION);
	}
	
//	private void checkPedido() throws Exception {
//		List<CheckPedido> listChecks = Arrays.asList(
//			CheckPedido.CONSULTAR_BOLSA, 
//			CheckPedido.CONSULTAR_PEDIDO);
//		
//		checkPedidosManto(listChecks, dataPago.getListPedidos());
//	}
}
