package com.mng.robotest.test.data;

import java.io.Serializable;

import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.utils.PaisGetter;

public class DataTest implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	public Pais pais = null;
	public IdiomaPais idioma = null;
	public boolean userRegistered = false;
	public String userConnected = "";
	public String passwordUser = "";
	
	public DataTest() { }
	
	public DataTest(Pais paisI) {
		this.pais = paisI;
	}
	
	public DataTest(Pais paisI, IdiomaPais idiomaI) {
		this(paisI);
		this.idioma = idiomaI;
	}
	
	public static DataTest getData(PaisShop paisShop) {
		DataTest dataTest = new DataTest();
		dataTest.pais = PaisGetter.get(paisShop);
		dataTest.idioma = dataTest.pais.getListIdiomas().get(0);
		return dataTest;
	}	
	
	@Override
	public Object clone() {
		DataTest dataTest = new DataTest();
		dataTest.pais = this.pais;
		dataTest.idioma = this.idioma;
		dataTest.userRegistered = this.userRegistered;
		dataTest.passwordUser = this.passwordUser;
		return dataTest;
	}
	
	public String getNombrePais() {
		return pais.getNombre_pais();
	}
	
	public String getLiteralIdioma() {
		return idioma.getCodigo().getLiteral();
	}
	
	public String getUserConnected() {
		return this.userConnected;
	}
	
	public String getPasswordUser() {
		return this.passwordUser;
	}
}

