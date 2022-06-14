package com.mng.robotest.test.factoryes;

import java.io.Serializable;
import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.CompraFact;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;

public class ListPagosEspana implements Serializable {
	
	private static final long serialVersionUID = 6058455886676687127L;
	
	Pais espana = null;
	IdiomaPais castellano = null;
	Pais francia = null;
	IdiomaPais frances = null;
	Pais USA = null;
	
	final boolean usrReg = true;
	final boolean empleado = true;
	final boolean testVale = true;
	final boolean manyArticles = true;
	final boolean twoArticles = true;
	final boolean anulPedido = true;
	
	@Factory(
		  groups={"Compra", "Canal:all_App:all"}, 
		  description=
	  		"Factoría que incluye varios tests por cada uno de los pagos de España " + 
	  		"variando los flags de usuario registrado, empleado y métodos de envío")
//	@Test (
//		groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true, priority=1, 
//		description=
//			"Factoría que incluye varios tests por cada uno de los pagos de España " + 
//			"variando los flags de usuario registrado, empleado y métodos de envío")
	public Object[] COM010_PagoFactory(ITestContext ctx) throws Exception {
		ArrayList<Object> listTests = new ArrayList<>(); 
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
		catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	
		return (listTests.toArray(new Object[listTests.size()]));
	}
	
	private void getDataCountrys() throws Exception {
		this.espana = PaisGetter.get(PaisShop.ESPANA);
		this.francia = PaisGetter.get(PaisShop.FRANCE);
		this.castellano = espana.getListIdiomas().get(0);
		this.frances = francia.getListIdiomas().get(0);
		this.USA = PaisGetter.get(PaisShop.USA);
	}
	
	private void createTestPagosEspana(ArrayList<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		//Crearemos 3 tests para el pago VISA y 1 para los restantes 
		int prioridad = 1;
		List<Pago> listPagosToTest = espana.getListPagosForTest(app, false);
		boolean usrRegIntermitente = true;
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx)) {
				if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
					if ("VISA".compareTo(pago.getNombre())==0) {
						createTestPago(listTests, espana, castellano, pago, app, channel, !usrReg, !empleado, testVale, manyArticles, !anulPedido, prioridad);
						createTestPago(listTests, espana, castellano, pago, app, channel, usrReg, !empleado, !testVale, !manyArticles, !anulPedido, prioridad);
						createTestPago(listTests, espana, castellano, pago, app, channel, usrReg, empleado, !testVale, !manyArticles, !anulPedido, prioridad);
					} else {
						createTestPago(listTests, espana, castellano, pago, app, channel, usrRegIntermitente, !empleado, !testVale, !manyArticles, !anulPedido, prioridad);
						usrRegIntermitente=!usrRegIntermitente; //Iremos alternando entre usr registrado y no-registrado
					}
					
					prioridad+=1;
				}
			}
		}		
	}
	
	private void createTestPagosFrancia(ArrayList<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		//Creamos sólo 1 test para el pago VISA-Francia
		List<Pago> listPagosToTest = francia.getListPagosForTest(app, false);
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx)) {
				if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
					if ("VISA".compareTo(pago.getNombre())==0) {
						createTestPago(listTests, francia, frances, pago, app, channel, !usrReg, !empleado, !testVale, !twoArticles, anulPedido, 1);
						break;
					}
				}
			}
		}		
	}
	
	private void createTestPagosVotf(ArrayList<Object> listTests, AppEcom app, Channel channel, ITestContext ctx) {
		List<Pago> listPagosToTest = espana.getListPagosForTest(app, false/*isEmpl*/);
		for (Pago pago : listPagosToTest) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx)) {
				if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
					createTestPago(listTests, espana, castellano, pago, app, channel, !usrReg, !empleado, !testVale, twoArticles, !anulPedido, 1);
					break;
				}
			}
		}		
	}

	private void createTestPago(
			ArrayList<Object> listTests, Pais pais, IdiomaPais idioma, Pago pago, AppEcom app, Channel channel, boolean usrRegistrado, 
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
