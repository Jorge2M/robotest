package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.steps.shop.checqueregalo.PageChequeRegaloInputDataSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Com007 extends TestBase {

	private final PageChequeRegaloInputDataSteps pageChequeRegaloInputDataSteps;
	
	private final DataCtxPago dataPago;
	
	public Com007() throws Exception {
		dataTest.pais = PaisGetter.get(PaisShop.FRANCE);
		dataTest.userRegistered = true;
		dataTest.userConnected = "francia.test@mango.com";
		dataTest.passwordUser = GetterSecrets.factory()
			.getCredentials(SecretType.SHOP_FRANCIA_USER)
			.getPassword();
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
				.emaiExists()
				.chequeRegalo().build();
		
		dataPago = new DataCtxPago(dataTest, configCheckout);
		
		pageChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps(dataTest.pais, driver);
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
		AccesoSteps.oneStep(dataTest, false, driver);
		new SecBolsaSteps(dataTest).clear();
	}	

	private void selectFooterLinkChequeRegalo() throws Exception {
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dataTest);
		secMenusSteps.seleccionLinea(LineaType.she, null, dataTest);
		SecFooterSteps secFooterSteps = new SecFooterSteps(channel, app, driver);
		secFooterSteps.clickLinkFooter(FooterLink.CHEQUE_REGALO_OLD, false);
	}

	private void inputDataChequeRegalo() throws Exception {
		pageChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
		pageChequeRegaloInputDataSteps.seleccionarCantidades(Importe.EURO_50);

		ChequeRegalo chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.MAIL_PERSONAL);
		chequeRegalo.setImporte(Importe.EURO_50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
		pageChequeRegaloInputDataSteps.inputDataAndClickComprar(channel, app, chequeRegalo);
	}
	
	private void checkoutChequeRegalo() throws Exception {
		new BuilderCheckout(dataTest, dataPago, driver)
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
