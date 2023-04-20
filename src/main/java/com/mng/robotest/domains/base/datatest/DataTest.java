package com.mng.robotest.domains.base.datatest;

import java.io.Serializable;
import java.util.List;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.getdata.usuarios.UserShop;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.PaisGetter;

public class DataTest implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String testCode; 
	private Pais pais = null;
	private IdiomaPais idioma = null;
	private boolean userRegistered = false;
	private String userConnected = "";
	private String passwordUser = "";
	private List<GenericCheck> genericChecksDisabled = null; 
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
		return pais.getNombre_pais();
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
		UserShop userShop = GestorUsersShop.getUser(PaisShop.ESPANA);
		if (pais!=null) {
			userShop = GestorUsersShop.getUser(PaisShop.getPais(pais));
		}
		setUserConnected(userShop.user);
		setPasswordUser(userShop.password);
		setUserRegistered(true);
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public String getCodigoPais() {
		return pais.getCodigo_pais();
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

	public List<GenericCheck> getGenericChecksDisabled() {
		return genericChecksDisabled;
	}

	public void setGenericChecksDisabled(List<GenericCheck> genericChecksDisabled) {
		this.genericChecksDisabled = genericChecksDisabled;
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
}

