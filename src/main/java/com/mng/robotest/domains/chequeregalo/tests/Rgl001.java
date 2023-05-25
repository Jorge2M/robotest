package com.mng.robotest.domains.chequeregalo.tests;

import java.util.Arrays;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.chequeregalo.pageobjects.PageChequeRegaloInputData.Importe;
import com.mng.robotest.domains.chequeregalo.steps.PageChequeRegaloInputDataSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

public class Rgl001 extends TestBase {

	private final PageChequeRegaloInputDataSteps pageChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps();
	
	private final DataPago dataPago;
		
	public Rgl001() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
				.checkPagos()
				.emaiExists()
				.chequeRegalo().build();
		
		dataPago = getDataPago(configCheckout);		
	}
	
	@Override
	public void execute() throws Exception {
		loginAndClearBolsa();
		selectFooterLinkChequeRegalo();
		inputDataChequeRegalo();
		checkoutChequeRegalo();
		checkPedido();		
	}

	private void loginAndClearBolsa() throws Exception {
		accessAndLogin();
		new SecBolsaSteps().clear();
	}
	
	private void selectFooterLinkChequeRegalo() {
		clickLinea(SHE);
		new SecFooterSteps().clickLinkFooter(FooterLink.CHEQUE_REGALO, false);
	}	
	
	private void inputDataChequeRegalo() {
		if(channel.isDevice()){
			String nTarjeta = "100000040043";
			String cvvTarjeta = "618";
			pageChequeRegaloInputDataSteps.paginaConsultarSaldo(nTarjeta);
			pageChequeRegaloInputDataSteps.insertCVVConsultaSaldo(cvvTarjeta);
		}
		pageChequeRegaloInputDataSteps.seleccionarCantidades(Importe.EURO_50);
		pageChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
		
		var chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.MAIL_PERSONAL);
		chequeRegalo.setImporte(Importe.EURO_50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
		pageChequeRegaloInputDataSteps.inputDataAndClickComprar(channel, app, chequeRegalo);
	}	
	
	private void checkoutChequeRegalo() throws Exception {
		new BuilderCheckout(dataPago)
			.pago(dataTest.getPais().getPago("VISA"))
			.build()
			.checkout(From.IDENTIFICATION);
	}	

	private void checkPedido() throws Exception {
		var listChecks = Arrays.asList(
			CheckPedido.CONSULTAR_BOLSA, 
			CheckPedido.CONSULTAR_PEDIDO,
			CheckPedido.ANULAR); 
		
		checkPedidosManto(listChecks, dataPago.getListPedidos());
	}
}