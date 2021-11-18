package com.mng.robotest.test80.mango.test.beans;

import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test80.mango.test.utils.awssecrets.Secret;
import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets.SecretType;

public class AccesoVOTF {
	
	private final String usuario;
	private final String password;

	String entregadomic = "S";

	private AccesoVOTF(String usuario, String password) {
	 	this.usuario = usuario;
	 	this.password = password;
	}

	public static AccesoVOTF forSpain() {
		Secret secret = GetterSecrets.factory().getCredentials(SecretType.VOTF_USER);
		return new AccesoVOTF(secret.getUser(), secret.getPassword());
	}

	public static AccesoVOTF forFrance() {
		return new AccesoVOTF("fra04974", "mng04974");
	}

	public static AccesoVOTF forItaly() {
		return new AccesoVOTF("tda01182", "mng01182");
	}

	public static AccesoVOTF forCountry(PaisShop paisShop) {
		switch (paisShop) {
		case España:
			return forSpain();
		case France:
			return forFrance();
		case Italia:
			return forItaly();
		default:
			throw new IllegalArgumentException("Unsuported country '" + paisShop + "' for retrieve credentials");
		}
	}
	
	public String getUsuario() {
		return this.usuario;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getEntregadomic() {
		return this.entregadomic;
	}

	public void setEntregadomic(String entregadomic) {
		this.entregadomic = entregadomic;
	}
		
	@Override
	public String toString() {
		return "AccesoVOTF [usuario="+ this.usuario + ", password=" + this.password +
				", toString()=" + super.toString() + "]";
	}
}