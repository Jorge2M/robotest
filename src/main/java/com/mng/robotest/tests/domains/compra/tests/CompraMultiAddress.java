package com.mng.robotest.tests.domains.compra.tests;

import java.io.Serializable;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class CompraMultiAddress implements TestFromFactory, Serializable {

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
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	}	
	
	@Override
	public String getIdTestInFactory() {	
		return this.indexFact;
	}
	
	@Test (
		groups={"Compra", "Multidireccion", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true,
		description="[Usuario registrado] Acceder a la sección de multidirecciones del checkout y añadir/eliminar una dirección")
	public void COM009_MultiAddress() throws Exception {
		new Com009(pais, idioma).execute();
	}

}