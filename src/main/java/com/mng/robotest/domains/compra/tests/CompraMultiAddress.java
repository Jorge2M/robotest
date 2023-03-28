package com.mng.robotest.domains.compra.tests;

import java.io.Serializable;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;

public class CompraMultiAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private String indexFact = "";
	public int prioridad;
	private Pais pais;
	private IdiomaPais idioma;
	
	public CompraMultiAddress() {}
	
	//From @Factory
	public CompraMultiAddress(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
	}	
	
	@Test (
		groups={"Compra", "Multidireccion", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true,
		description="[Usuario registrado] Acceder a la sección de multidirecciones del checkout y añadir/eliminar una dirección")
	public void COM009_MultiAddress() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Com009(pais, idioma).execute();
	}

}