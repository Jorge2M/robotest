package com.mng.robotest.tests.domains.base.datatest;

import java.io.Serializable;
import java.util.List;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Linea;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.datastored.DataBag;
import com.mng.robotest.testslegacy.datastored.DataFavoritos;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class DataTest implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String testCode; 
	private Pais pais = null;
	private IdiomaPais idioma = null;
	private boolean userRegistered = false;
	private String userConnected = "";
	private String passwordUser = "";
	private DataBag dataBag = new DataBag(); 
	private DataFavoritos dataFavoritos = new DataFavoritos();
	
	public DataTest() { 
		//TODO esperar a 2.1.39 TestMaker para poder hacer uso de .getCode();
		var test = TestMaker.getTestCase();
		if (test.isPresent()) {
			testCode = test.get().getName();
		} else {
			testCode = "";
		}
	}
	
	public DataTest(Pais paisI) {
		this();
		this.pais = paisI;
	}
	
	public DataTest(Pais paisI, IdiomaPais idiomaI) {
		this(paisI);
		this.idioma = idiomaI;
	}
	
	public static DataTest getData(PaisShop paisShop) {
		DataTest dataTest = new DataTest();
		dataTest.pais = PaisGetter.from(paisShop);
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
	
	public String getTestCode() {
		return testCode;
	}
	
	public String getNombrePais() {
		return pais.getNombrePais();
	}
	
	public String getLiteralIdioma() {
		return idioma.getCodigo().getLiteral();
	}
	
	public String getUserConnected() {
		if ("".compareTo(userConnected)==0) {
			storeNewUser();
		}
		return userConnected;
	}
	
	public String getPasswordUser() {
		if ("".compareTo(passwordUser)==0) {
			storeNewUser();
		}
		return passwordUser;
	}
	
	private void storeNewUser() {
		var userShop = GestorUsersShop.getUser(PaisShop.ESPANA);
		if (pais!=null) {
			userShop = GestorUsersShop.getUser(PaisShop.getPais(pais));
		}
		setUserConnected(userShop.getUser());
		setPasswordUser(userShop.getPassword());
		setUserRegistered(true);
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public String getCodigoPais() {
		return pais.getCodigoPais();
	}

	public IdiomaPais getIdioma() {
		return idioma;
	}

	public void setIdioma(IdiomaPais idioma) {
		this.idioma = idioma;
	}

	public boolean isUserRegistered() {
		return userRegistered;
	}

	public void setUserRegistered(boolean userRegistered) {
		this.userRegistered = userRegistered;
	}

	public DataBag getDataBag() {
		return dataBag;
	}

	public void setDataBag(DataBag dataBag) {
		this.dataBag = dataBag;
	}

	public DataFavoritos getDataFavoritos() {
		return dataFavoritos;
	}

	public void setDataFavoritos(DataFavoritos dataFavoritos) {
		this.dataFavoritos = dataFavoritos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUserConnected(String userConnected) {
		this.userConnected = userConnected;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	
	public List<Linea> getLineas() {
		return getPais().getShoponline().getLineas();
	}
}

