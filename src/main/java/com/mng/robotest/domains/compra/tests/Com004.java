package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checqueregalo.PageChequeRegaloInputDataSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Com004 extends TestBase {

	private final PageChequeRegaloInputDataSteps pageChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps(dataTest.pais, driver);
	
	private final UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
	private final DataPago dataPago;
		
	public Com004() throws Exception {
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;		
		dataTest.userRegistered = true;
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
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
		access();
		new SecBolsaSteps().clear();
	}
	
	private void selectFooterLinkChequeRegalo() throws Exception {
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		secMenusSteps.seleccionLinea(LineaType.she, null);
		new SecFooterSteps().clickLinkFooter(FooterLink.CHEQUE_REGALO, false);
	}	
	
	private void inputDataChequeRegalo() throws Exception {
		if(channel.isDevice()){
			String nTarjeta = "100000040043";
			String cvvTarjeta = "618";
			pageChequeRegaloInputDataSteps.paginaConsultarSaldo(nTarjeta);
			pageChequeRegaloInputDataSteps.insertCVVConsultaSaldo(cvvTarjeta);
		}
		pageChequeRegaloInputDataSteps.seleccionarCantidades(Importe.EURO_50);
		pageChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
		
		ChequeRegalo chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.MAIL_PERSONAL);
		chequeRegalo.setImporte(Importe.EURO_50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
		pageChequeRegaloInputDataSteps.inputDataAndClickComprar(channel, app, chequeRegalo);
	}	
	
	private void checkoutChequeRegalo() throws Exception {
		new BuilderCheckout(dataPago)
			.pago(dataTest.pais.getPago("VISA"))
			.build()
			.checkout(From.IDENTIFICATION);
	}	

	private void checkPedido() throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido,
			CheckPedido.anular); 
		
		CompraCommons.checkPedidosManto(listChecks, dataPago.getListPedidos(), app, driver);
	}
}