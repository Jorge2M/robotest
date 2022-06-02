package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.PageReembolsosStpV;
import com.mng.robotest.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test.stpv.shop.checkout.PageResultPagoStpV;
import com.mng.robotest.test.utils.ImporteScreen;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.github.jorge2m.testmaker.service.TestMaker;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

/**
 * Script correspondiente a las pruebas de reembolsos
 * @author jorge.munoz
 *
 */

public class Reembolsos {
	
	//TODO mientras que tengamos problemas con el buscador en Arabia probaremos contra España
//	private final static Pais arabia = PaisGetter.get(PaisShop.Espana);
//	private final static IdiomaPais arabia_arabe = arabia.getListIdiomas().get(0);
	private final static Pais emiratos = PaisGetter.get(PaisShop.UnitedArabEmirates);
	private final static IdiomaPais emiratos_arabe = emiratos.getListIdiomas().get(0);
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = emiratos;
		dCtxSh.idioma = emiratos_arabe;
		return dCtxSh;
	}
	
	/**
	 * Script correspondiente al caso de prueba que configura el reembolso vía transferencia y saldo en cuenta en arabia/inglés 
	 */
	//TODO conseguir reactivar el usuario mng_test_SA_pruebaSaldo@mango.com
//	@Test (
//		groups={"Reembolso", "Canal:desktop,mobile_App:shop"}, 
//		description="Configura el reembolso vía transferencia y saldo en cuenta para un país/idioma determinado")
	public void REE001_configureReembolso() throws Exception {
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = TestMaker.getDriverTestCase();
		
		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			return;		
		}

		boolean paisConSaldoCta = dCtxSh.pais.existsPagoStoreCredit();
		dCtxSh.userRegistered = true;
		dCtxSh.userConnected = "mng_test_SA_pruebaSaldo@mango.com";
		dCtxSh.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();

		if (dCtxSh.pais.getEmailuser()!=null && dCtxSh.pais.getPassuser()!=null) {
			dCtxSh.userConnected = dCtxSh.pais.getEmailuser();
			dCtxSh.passwordUser = dCtxSh.pais.getPassuser();
		}
			
		AccesoStpV.oneStep(dCtxSh, false, driver);
		PageReembolsosStpV.gotoRefundsFromMenu(paisConSaldoCta, dCtxSh.appE, dCtxSh.channel, driver);
		if (paisConSaldoCta) {
			if (PageReembolsos.isCheckedRadio(TypeReembolso.StoreCredit, driver)) {
				PageReembolsosStpV.testConfTransferencia(driver);
				PageReembolsosStpV.selectRadioSalCtaAndRefresh(driver);
			} else {
				PageReembolsosStpV.selectRadioSalCtaAndRefresh(driver);
				PageReembolsosStpV.testConfTransferencia(driver);
			}
		}	 
	}
	
	/**
	 * Realiza un checkout utilizando el Saldo en Cuenta 
	 */
//	@Test (
//		groups={"Reembolso", "Canal:desktop,mobile_App:shop"},
//		description="Se realiza un Checkout utilizando Saldo en Cuenta. Se accede a la configuración al inicio y al final para comprobar que el saldo en cuenta se resta correctamente")
	public void REE002_checkoutWithSaldoCta() throws Exception {
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = TestMaker.getDriverTestCase();

		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			return;
		}

		dCtxSh.userRegistered = true;
		dCtxSh.userConnected = "mng_test_SA_pruebaSaldo@mango.com";
		dCtxSh.passwordUser = dCtxSh.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();

		if (dCtxSh.pais.getEmailuser()!=null && dCtxSh.pais.getPassuser()!=null) {
			dCtxSh.userConnected = dCtxSh.pais.getEmailuser();
			dCtxSh.passwordUser = dCtxSh.pais.getPassuser();
		}
		
		AccesoStpV.oneStep(dCtxSh, true, driver);
		PageReembolsosStpV.gotoRefundsFromMenu(dCtxSh.pais.existsPagoStoreCredit(), dCtxSh.appE, dCtxSh.channel, driver);
		PageReembolsosStpV.selectRadioSalCtaAndRefresh(driver);
		if (PageReembolsos.isVisibleSaveButtonStoreCredit(driver)) {
			PageReembolsosStpV.clickSaveButtonStoreCredit(driver);
		}
		float saldoCtaIni = PageReembolsos.getImporteStoreCredit(driver);
		
		DataBag dataBag = new DataBag(); 
		SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
		secBolsaStpV.altaArticlosConColores(1, dataBag);
		
		//Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.isStoreCredit = true;
		FTCkout.validaPasarelas = false;  
		FTCkout.validaPagos = false;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = false;
		FTCkout.isEmpl = false;
		FTCkout.testCodPromocional = true;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		//Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
		dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
		dCtxPago.setUserWithStoreC(true);
		dCtxPago.setSaldoCta(saldoCtaIni);
		dCtxPago.getFTCkout().validaPagos = true;
		Pago pagoStoreCredit = dCtxSh.pais.getPago("STORECREDIT");
		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		dCtxPago = new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver)
			.pago(pagoStoreCredit)
			.build()
			.checkout(From.Bolsa);
		
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			//Volvemos a la portada (Seleccionamos el link "Seguir de shopping" o el icono de Mango)
			PageResultPagoStpV pageResultPagoStpV = new PageResultPagoStpV(pagoStoreCredit.getTypePago(), dCtxSh.channel, driver);
			pageResultPagoStpV.selectSeguirDeShopping(dCtxSh.appE);
			
			//Calculamos el saldo en cuenta que debería quedar (según si se ha realizado o no el pago);
			float saldoCtaEsperado;
			if (pagoStoreCredit.getTestpasarela().compareTo("s")==0 &&
				pagoStoreCredit.getTestpago().compareTo("s")==0) {
				DataPedido dataPedido = dCtxPago.getListPedidos().get(0);
				float importePago = ImporteScreen.getFloatFromImporteMangoScreen(dataPedido.getImporteTotalSinSaldoCta());
				saldoCtaEsperado = UtilsMangoTest.round(saldoCtaIni - importePago, 2);
			} else {
				saldoCtaEsperado = saldoCtaIni;
			}
			
			//Step (+validaciones) selección menú "Mi cuenta" + "Reembolsos"
			PageReembolsosStpV.gotoRefundsFromMenuAndValidaSalCta(dCtxSh.pais.existsPagoStoreCredit(), saldoCtaEsperado, dCtxSh.appE, dCtxSh.channel, driver);
			
			//Validación en Manto de los Pedidos (si existen)
			List<CheckPedido> listChecks = Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido);
			DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
			PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
		}
	}
}