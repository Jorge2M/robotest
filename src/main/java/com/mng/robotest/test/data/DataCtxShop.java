package com.mng.robotest.test.data;

import com.github.jorge2m.testmaker.conf.Channel;

import java.io.Serializable;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.generic.beans.ValePais;

public class DataCtxShop implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	
	public AppEcom appE = AppEcom.shop;
	public Channel channel = Channel.desktop;
	public Pais pais = null;
	public IdiomaPais idioma = null;
	public ValePais vale = null;
	public boolean userRegistered = false;
	public String userConnected = "";
	public String passwordUser = "";
	
	public DataCtxShop() { }
	
	public DataCtxShop(AppEcom appEI, Channel channelI, Pais paisI) {
		this.appE = appEI;
		this.channel = channelI;
		this.pais = paisI;
	}
	
	public DataCtxShop(AppEcom appEI, Channel channelI, Pais paisI, IdiomaPais idiomaI) {
		this(appEI, channelI, paisI);
		this.idioma = idiomaI;
	}
	
	public DataCtxShop(AppEcom appEI, Channel channelI, Pais paisI, IdiomaPais idiomaI, ValePais vale) {
		this(appEI, channelI, paisI, idiomaI);
		this.vale = vale;
	}
	
	@Override
	public Object clone() {
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.appE = this.appE;
		dCtxSh.channel = this.channel;
		dCtxSh.pais = this.pais;
		dCtxSh.idioma = this.idioma;
		dCtxSh.userRegistered = this.userRegistered;
		dCtxSh.passwordUser = this.passwordUser;
		return dCtxSh;
	}
	
	public void setAppEcom(String app) {
		this.appE = AppEcom.valueOf(app);
	}
	
	public void setAppEcom(AppEcom app) {
		this.appE = app;
	}
	
	public void setChannel(String channel) {
		this.channel = Channel.valueOf(channel);
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
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

