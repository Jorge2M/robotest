package com.mng.robotest.domains.manto.tests;

import java.time.LocalDate;
import java.util.Optional;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.steps.manto.DataMantoAccess;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;
import com.mng.robotest.test.steps.manto.PageMenusMantoSteps;
import com.mng.robotest.test.steps.manto.PageSelTdaMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.test.steps.manto.pedido.PagePedidosMantoSteps;

public class Man000 extends TestBase {

	private static final String CODIGO_ESPANYA = "001";
	private static final String ALMACEN_ESPANYA = "001";
	
	Pais espanya = new Pais();
	static {
		Pais espanya = new Pais();
		espanya.setCodigo_pais(CODIGO_ESPANYA);
		espanya.setNombre_pais("España");
	}

	private final Pago dPagoPrueba = new Pago();
	private final DataMantoAccess dMantoAcc = getMantoAccessData();
	private DataPedido dPedidoPrueba;
	
	@Override
	public void execute() throws Exception {
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
		
		this.dPedidoPrueba = new DataPedido(espanya, null);
		this.dPedidoPrueba.setCodigopais(CODIGO_ESPANYA);
		this.dPagoPrueba.setNombre("");
		this.dPedidoPrueba.setPago(dPagoPrueba);
		new PageSelTdaMantoSteps().selectTienda(ALMACEN_ESPANYA, CODIGO_ESPANYA);
		new PageMenusMantoSteps().goToPedidos();
		
		LocalDate dateSevenDaysAgo = LocalDate.now().minusDays(7);
		new SecFiltrosMantoSteps().setFiltrosWithoutChequeRegaloYbuscar(dPedidoPrueba, TypeSearch.PEDIDO, dateSevenDaysAgo, LocalDate.now());
		
		PagePedidosMantoSteps pagePedidosMantoSteps = new PagePedidosMantoSteps();
		this.dPedidoPrueba = pagePedidosMantoSteps.getPedidoUsuarioRegistrado(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getDataPedido(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getDataCliente(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getTiendaFisicaListaPedidos(dPedidoPrueba);
	}
	
	public DataMantoAccess getMantoAccessData() {
		var dMantoAcc = new DataMantoAccess();
		var testCase = getTestCase();
		var testRun = testCase.getTestRunParent();
		InputParamsTM inputParams = getTestCase().getInputParamsSuite();
		dMantoAcc.setUrlManto(inputParams.getUrlBase());
		dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
		dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
		dMantoAcc.setAppE(AppEcom.shop);
		return dMantoAcc;
	}	
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}	

}
