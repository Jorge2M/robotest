package com.mng.robotest.tests.conf.factories;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.compra.factories.CompraFact;
import com.mng.robotest.testslegacy.beans.*;

public class ListPagosEspana extends FactoryBase implements Serializable {
	
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
		  testName="COM010",
		  groups={"Compra", "Checkout", "Canal:all_App:all"}, 
		  description=
	  		"Factoría que incluye varios tests por cada uno de los pagos de España " + 
	  		"variando los flags de usuario registrado, empleado y métodos de envío")
	public Object[] pagoFactory(ITestContext ctx) throws Exception {
		inputParams = getInputParams(ctx);
		List<Object> listTests = new ArrayList<>(); 
		getDataCountrys(getApp());
		
		createTestPagosEspana(listTests, getApp(), getChannel(), ctx);
		createTestPagosFrancia(listTests, getApp(), getChannel(), ctx);
	
		return (listTests.toArray(new Object[listTests.size()]));
	}
	
	private void getDataCountrys(AppEcom app) {
		this.espana = ESPANA.getPais();
		this.francia = FRANCE.getPais();
		this.castellano = espana.getListIdiomas(app).get(0);
		this.frances = francia.getListIdiomas(app).get(0);
	}
	
	private void createTestPagosEspana(List<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		//Crearemos 3 tests para el pago VISA y 1 para los restantes 
		List<Pago> listPagosToTest = espana.getListPagosForTest(app, false);
		boolean usrRegIntermitente = true;
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx) &&
				pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
				if ("VISA".compareTo(pago.getNombre())==0) {
					createTestPago(listTests, espana, castellano, pago, app, channel, !USR_REG, !EMPLEADO, TEST_VALE, MANY_ARTICLES, !ANUL_PEDIDO);
					createTestPago(listTests, espana, castellano, pago, app, channel, USR_REG, !EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO);
					createTestPago(listTests, espana, castellano, pago, app, channel, USR_REG, EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO);
				} else {
					createTestPago(listTests, espana, castellano, pago, app, channel, usrRegIntermitente, !EMPLEADO, !TEST_VALE, !MANY_ARTICLES, !ANUL_PEDIDO);
					usrRegIntermitente=!usrRegIntermitente; //Iremos alternando entre usr registrado y no-registrado
				}
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
					createTestPago(listTests, francia, frances, pago, app, channel, !USR_REG, !EMPLEADO, !TEST_VALE, !TWO_ARTICLES, ANUL_PEDIDO);
					break;
			}
		}		
	}
	
	private void createTestPago(
			List<Object> listTests, Pais pais, IdiomaPais idioma, Pago pago, AppEcom app, Channel channel, boolean usrRegistrado, 
			boolean empleado, boolean testVale, boolean manyArticles, boolean anulPedido) {
		listTests.add(new CompraFact(pais, idioma, pago, app, channel, usrRegistrado, empleado, testVale, manyArticles, anulPedido));
		System.out.println(
			"Creado Test COM010: " +
			"Pais=" + pais.getNombrePais() +
			",Pago=" + pago.getNameFilter(channel, app) + 
			",Envio=" + pago.getTipoEnvioType(app) +
			",UserReg=" + usrRegistrado + 
			",empleado=" + empleado
		);
	}
}
