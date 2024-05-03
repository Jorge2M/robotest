package com.mng.robotest.tests.domains.micuenta.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class MiCuenta implements TestFromFactory, Serializable {
	
	private static final long serialVersionUID = 2188911402476562105L;
	
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public MiCuenta() {}
	
	//From @Factory
	public MiCuenta(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	}	
	
	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test (
		testName="MIC001",			
		groups={"Micuenta", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar opciones de 'mi cuenta'")
	@Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
	public void opcionesMiCuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion) 
			throws Exception {
		new Mic001(userConDevolucionPeroNoEnPRO, passwordUserConDevolucion).execute();
	}
	
	@Test (
		testName="MIC002",			
		groups={"Miscompras", "Smoke", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
	@Parameters({"userWithOnlinePurchases", "userWithStorePurchases", "passUserWithOnlinePurchases", "passUserWithStorePurchases"})
	public void checkConsultaMisCompras(
			String userWithOnlinePurchases, String userWithStorePurchases, 
			String passUserWithOnlinePurchases, String passUserWithStorePurchases) throws Exception {
		new Mic002(
				pais, idioma, 
				userWithOnlinePurchases, userWithStorePurchases,
				passUserWithOnlinePurchases, passUserWithStorePurchases).execute();
	}
	
	@Test (
		testName="MIC003",			
		groups={"Micuenta", "Smoke", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Registro y cancelación de la cuenta creada")
	public void cancelacionCuenta() throws Exception {
		new Mic003().execute();
	}
	
	@Test (
		testName="MIC004",			
		groups={"Micuenta", "Canal:desktop_App:shop"}, 
		description="Cancelación de cuenta mediante endpoint de PlayStore")
	public void cancelacionCuentaEndpointPlaystore() throws Exception {
		new Mic004().execute();
	}
	
}
