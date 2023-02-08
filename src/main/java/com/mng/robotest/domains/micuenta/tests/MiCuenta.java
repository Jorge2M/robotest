package com.mng.robotest.domains.micuenta.tests;

import java.io.Serializable;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;

import static com.mng.robotest.test.data.PaisShop.*;

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
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
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
}