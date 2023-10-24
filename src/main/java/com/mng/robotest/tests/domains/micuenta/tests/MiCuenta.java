package com.mng.robotest.tests.domains.micuenta.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class MiCuenta implements Serializable {
	
	private static final long serialVersionUID = 2188911402476562105L;
	
	public int prioridad;
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public MiCuenta() {}
	
	//From @Factory
	public MiCuenta(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	}	
	
	@Test (
		groups={"Micuenta", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description="Verificar opciones de 'mi cuenta'")
	@Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
	public void MIC001_Opciones_Mi_Cuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion) 
			throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Mic001(userConDevolucionPeroNoEnPRO, passwordUserConDevolucion).execute();
	}
	
	@Test (
		groups={"Miscompras", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
	@Parameters({"userWithOnlinePurchases", "userWithStorePurchases", "passUserWithOnlinePurchases", "passUserWithStorePurchases"})
	public void MIC002_CheckConsultaMisCompras(
			String userWithOnlinePurchases, String userWithStorePurchases, 
			String passUserWithOnlinePurchases, String passUserWithStorePurchases) throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Mic002(
				pais, idioma, 
				userWithOnlinePurchases, userWithStorePurchases,
				passUserWithOnlinePurchases, passUserWithStorePurchases).execute();
	}
	
	@Test (
		groups={"Micuenta", "Canal:desktop,mobile_App:shop"}, 
		description="Registro y cancelación de la cuenta creada")
	public void MIC003_CancelacionCuenta() throws Exception {
		new Mic003().execute();
	}
	
	@Test (
		groups={"Micuenta", "Canal:desktop_App:shop"}, 
		description="Cancelación de cuenta mediante endpoint de PlayStore")
	public void MIC004_CancelacionCuenta() throws Exception {
		new Mic004().execute();
	}
	
}
