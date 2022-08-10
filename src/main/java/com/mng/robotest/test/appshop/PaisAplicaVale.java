package com.mng.robotest.test.appshop;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.compra.tests.CompraCommons;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test.suites.ValesPaisesSuite.VersionValesSuite;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class PaisAplicaVale implements Serializable {

	private static final long serialVersionUID = 7404913971070937277L;
	
	private String index_fact;
	public int prioridad;
	private ConfigCheckout fTCkoutIni;
	private DataCtxShop dCtxSh;

	public PaisAplicaVale(VersionPagosSuite version, DataCtxShop dCtxSh, int prioridad) {
		this.prioridad = prioridad;
		this.fTCkoutIni = ConfigCheckout.config()
				.emaiExists()
				.version(version).build();
		
		setDataFromConstruct(dCtxSh);
	}

	public PaisAplicaVale(VersionValesSuite version, DataCtxShop dCtxSh, int prioridad) {
		this.prioridad = prioridad;
		this.fTCkoutIni = ConfigCheckout.config()
				.emaiExists()
				.version(version).build();
		
		setDataFromConstruct(dCtxSh);
	}

	private void setDataFromConstruct(DataCtxShop dCtxSh) {
		this.dCtxSh = dCtxSh;
		this.index_fact = 
			dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + " - " + 
			dCtxSh.idioma.getCodigo().getLiteral();
		if (dCtxSh.vale!=null) {
			this.index_fact+= 
				" - " + dCtxSh.vale.getCodigoVale() + 
				"(" + dCtxSh.vale.isValid() + "_" + dCtxSh.vale.getPorcDescuento() + "perc)";
		}
	}
	
	@Test (
		groups={"Pagos", "shop-movil-web", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, 
		description="Compra usuario no registrado")
	public void CHK001_Compra() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
		dCtxSh.userRegistered = false;
		DataCtxPago dCtxPago = new DataCtxPago(this.dCtxSh, fTCkoutIni);
		
		dCtxPago = new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver)
			.build()
			.checkout(From.PREHOME);
		
		if (dCtxPago.getFTCkout().checkManto) {
			CompraCommons.checkPedidosManto(dCtxPago.getListPedidos(), dCtxSh.appE, driver);
		}
	}
	
}
