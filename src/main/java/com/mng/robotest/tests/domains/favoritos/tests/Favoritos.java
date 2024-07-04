package com.mng.robotest.tests.domains.favoritos.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import java.io.Serializable;

public class Favoritos implements TestFromFactory, Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String indexFact = "";
	private Pais pais;
	private IdiomaPais idioma;

	public Favoritos() {}

	//From @Factory
	public Favoritos(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	} 

	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test(
		testName="FAV001",			
		groups={"Favoritos", "Smoke", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario registrado] Alta favoritos desde la galería")
	public void altaFavoritosDesdeGaleria() throws Exception {
		if (pais==null) {
			new Fav001().execute();
		} else {
			new Fav001(pais, idioma).execute();
		}
	}

	@Test(
		testName="FAV002",			
		groups={"Favoritos", "Smoke", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario no registrado] Alta favoritos desde la galería Mango-Home y posterior identificación")
	public void altaFavoritosDesdeFicha() throws Exception {
		if (pais==null) {
			new Fav002().execute();
		} else {
			new Fav002(pais, idioma).execute();
		}
	}
	
}