package com.mng.robotest.test.factoryes;

import java.io.Serializable;
import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.factories.CompraFact;
import com.mng.robotest.test.beans.*;

import static com.mng.robotest.test.data.PaisShop.*;

public class ListPagosEspana implements Serializable {
	
	private static final long serialVersionUID = 6058455886676687127L;
	
	private Pais espana = null;
	private IdiomaPais castellano = null;
	private Pais francia = null;
	private IdiomaPais frances = null;
	
	private static final boolean USR_REG = true;
	private static final boolean EMPLEADO = true;
	private static final boolean TEST_VALE = true;
	private static final boolean MANY_ARTICLES = true;
	private static final boolean TWO_ARTICLES = true;
	private static final boolean ANUL_PEDIDO = true;
	
	@Factory(
		  groups={"Compra", "Checkout", "Canal:all_App:all"}, 
		  description=
	  		"Factoría que incluye varios tests por cada uno de los pagos de España " + 
	  		"variando los flags de usuario registrado, empleado y métodos de envío")
//	@Test (
//		groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true, priority=1, 
//		description=
//			"Factoría que incluye varios tests por cada uno de los pagos de España " + 
//			"variando los flags de usuario registrado, empleado y métodos de envío")
	public Object[] COM010_PagoFactory(ITestContext ctx) throws Exception {
		List<Object> listTests = new ArrayList<>(); 
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctx);
		AppEcom appE = (AppEcom)inputData.getApp();
		Channel channel = inputData.getChannel();
		try {
			getDataCountrys();
			if (appE!=AppEcom.votf) {
				createTestPagosEspana(listTests, appE, channel, ctx);
				createTestPagosFrancia(listTests, appE, channel, ctx);
			} else {
				createTestPagosVotf(listTests, appE, channel, ctx);
			}
		}
		catch (Exception e) {
			throw e;
		}
	
		return (listTests.toArray(new Object[listTests.size()]));
	}
	
	private void getDataCountrys() {
		this.espana = ESPANA.getPais();
		this.francia = FRANCE.getPais();
		this.castellano = espana.getListIdiomas().get(0);
		this.frances = francia.getListIdiomas().get(0);
	}
	
	private void createTestPagosEspana(List<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		//Crearemos 3 tests para el pago VISA y 1 para los restantes 
		int prioridad = 1;
		List<Pago> listPagosToTest = espana.getListPagosForTest(app, false);
		boolean usrRegIntermitente = true;
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx) &&
				pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
				if ("VISA".compareTo(pago.getNombre())==0) {
					createTestPago(listTests, espana, castellano, pago, app, channel, !USR_REG, !EMPLEADO, TEST_VALE, MANY_ARTICLES, !ANUL_PEDIDO, prioridad);
					createTestPago(listTests, espana, castellano, pago, app, channel, USR_REG, !EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO, prioridad);
					createTestPago(listTests, espana, castellano, pago, app, channel, USR_REG, EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO, prioridad);
				} else {
					createTestPago(listTests, espana, castellano, pago, app, channel, usrRegIntermitente, !EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO, prioridad);
					usrRegIntermitente=!usrRegIntermitente; //Iremos alternando entre usr registrado y no-registrado
				}
				prioridad+=1;
			}
		}		
	}
	
	private void createTestPagosFrancia(List<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		//Creamos sólo 1 test para el pago VISA-Francia
		List<Pago> listPagosToTest = francia.getListPagosForTest(app, false);
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx) &&
				pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0 &&
				"VISA".compareTo(pago.getNombre())==0) {
					createTestPago(listTests, francia, frances, pago, app, channel, !USR_REG, !EMPLEADO, !TEST_VALE, !TWO_ARTICLES, ANUL_PEDIDO, 1);
					break;
			}
		}		
	}
	
	private void createTestPagosVotf(List<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		List<Pago> listPagosToTest = espana.getListPagosForTest(app, false/*isEmpl*/);
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx) &&
				pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
				createTestPago(listTests, espana, castellano, pago, app, channel, !USR_REG, !EMPLEADO, !TEST_VALE, TWO_ARTICLES, !ANUL_PEDIDO, 1);
				break;
			}
		}		
	}

	private void createTestPago(
			List<Object> listTests, Pais pais, IdiomaPais idioma, Pago pago, AppEcom app, Channel channel, boolean usrRegistrado, 
			boolean empleado, boolean testVale, boolean manyArticles, boolean anulPedido, int prioridad) {
		listTests.add(new CompraFact(pais, idioma, pago, app, channel, usrRegistrado, empleado, testVale, manyArticles, anulPedido, prioridad));
		System.out.println(
			"Creado Test COM010: " +
			"Pais=" + pais.getNombre_pais() +
			",Pago=" + pago.getNameFilter(channel, app) + 
			",Envio=" + pago.getTipoEnvioType(app) +
			",UserReg=" + usrRegistrado + 
			",empleado=" + empleado
		);
	}
}
