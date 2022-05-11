package com.mng.robotest.test.stpv.navigations.manto;

import static com.mng.robotest.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido.*;

import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test.stpv.manto.PageBolsasMantoStpV;
import com.mng.robotest.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test.stpv.manto.PageSelTdaMantoStpV;
import com.mng.robotest.test.stpv.manto.SecFiltrosMantoStpV;
import com.mng.robotest.test.stpv.manto.SecFiltrosMantoStpV.TypeSearch;
import com.mng.robotest.test.stpv.manto.pedido.PageConsultaPedidoBolsaStpV;
import com.mng.robotest.test.stpv.manto.pedido.PageGenerarPedidoStpV;
import com.mng.robotest.test.stpv.manto.pedido.PagePedidosMantoStpV;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class PedidoNavigations {

	public static void testPedidosEnManto(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) throws Exception {
		//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
		if (appE!=AppEcom.votf) {  
			TestCaseTM testCase = TestMaker.getTestCase().get();
			TestRunTM testRun = testCase.getTestRunParent();
			DataMantoAccess dMantoAcc = new DataMantoAccess();
			dMantoAcc.urlManto = testRun.getParameter(Constantes.paramUrlmanto);
			dMantoAcc.userManto = testRun.getParameter(Constantes.paramUsrmanto);
			dMantoAcc.passManto = testRun.getParameter(Constantes.paramPasmanto);
			dMantoAcc.appE = appE;
			testPedidosEnManto(dMantoAcc, dataCheckPedidos, driver);
		}
	}
	
	private static void testPedidosEnManto(DataMantoAccess dMantoAcc, DataCheckPedidos dataCheckPedidos, WebDriver driver) 
	throws Exception {
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		if (dataCheckPedidos.areChecksToExecute() && typeAccess!=TypeAccess.Bat) {
			PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
			PedidoNavigations.validacionListPedidosStpVs(dataCheckPedidos, dMantoAcc.appE, driver);
		}
	}
	
	/**
	 * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
	 * @param listPaisPedido lista de pedidos a validar
	 */
	public static void validacionListPedidosStpVs(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) 
	throws Exception {
		List<CheckPedido> listChecks = dataCheckPedidos.getListChecks();
		for (DataPedido dataPedido : dataCheckPedidos.getListPedidos()) {
			if (dataPedido.isResultadoOk()) {
				try {
					validaPedidoStpVs(dataPedido, listChecks, appE, driver);
				}
				catch (Exception e) {
					Log4jTM.getLogger().warn("Problem in validation of Pedido", e);
				}	  
			}
		}
	}	
	
	/**
	 * Se ejecuta todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
	 */
	public static void validaPedidoStpVs(DataPedido dataPedido, List<CheckPedido> listChecks, AppEcom app, WebDriver driver) 
	throws Exception {
		PageSelTdaMantoStpV.selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais(), app, driver);
		if (listChecks.contains(CheckPedido.consultarBolsa)) {
			consultarBolsaStpV(dataPedido, app, driver);
		}
		
		if (app!=AppEcom.votf) {
			if (listChecks.contains(CheckPedido.consultarPedido)) {
				consultarPedidoStpV(dataPedido, app, driver);	
			}
			
			if (listChecks.contains(CheckPedido.anular)) {
				anularPedidoStpV(dataPedido, app, driver);
			}
		}
		
		if (listChecks.contains(CheckPedido.consultarBolsa)) {
			
		}
	}
	
	private static void consultarBolsaStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		PageMenusMantoStpV.goToBolsas(driver);
		SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.BOLSA, driver);
		boolean existLinkPedido = PageBolsasMantoStpV.validaLineaBolsa(dataPedido, app, driver).getExistsLinkCodPed();
		if (existLinkPedido) {
			PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.bolsa, app, driver);
		}
	}
	
	private static void consultarPedidoStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		PageMenusMantoStpV.goToPedidos(driver);
		SecFiltrosMantoStpV.setFiltrosHoyYbuscar(dataPedido, TypeSearch.PEDIDO, driver);
		boolean existLinkPedido = PagePedidosMantoStpV.validaLineaPedido(dataPedido, app, driver).getExistsLinkCodPed();
		if (existLinkPedido) { 
			PageConsultaPedidoBolsaStpV.detalleFromListaPedBol(dataPedido, TypeDetalle.pedido, app, driver);
		}
	}
	
	private static void anularPedidoStpV(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		if (!PageDetallePedido.isPage(dataPedido.getCodigoPedidoManto(), driver)) {
			consultarPedidoStpV(dataPedido, app, driver);
		}
		
		PageConsultaPedidoBolsaStpV.clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto(), driver);
		PageGenerarPedidoStpV.changePedidoToEstado(ANULADO, driver);
	}
}
