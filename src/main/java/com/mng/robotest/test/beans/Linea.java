package com.mng.robotest.test.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.*;

import java.util.ArrayList;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public class Linea implements Serializable {

	private static final long serialVersionUID = -4709433951288421080L;
	public enum TypeContentDesk { BANNERS, ARTICULOS, VACIO }
	public enum TypeContentMobil { BLOQUES_NUEVO, BLOQUES_REBAJ, SUBLINEAS, MENUS2, ARTICULOS }
	
	String id;
	String outlet;
	String shop;
	String menus;
	String menusart;
	String contentDesk;
	String contentMovil;
	String carrusels;
	String panoramicas;
	String extended = "n";
	
	public String getId() {
		return this.id;
	}
	
	@XmlAttribute(name="id")
	public void setId(String id) {
		this.id = id;
	}
	
	public String getShop() {
		return this.shop;
	}
	
	@XmlAttribute(name="shop")
	public void setShop(String shop) {
		this.shop = shop;
	}
	
	public String getOutlet() {
		return this.outlet;
	}
		
	@XmlAttribute(name="outlet")
	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}	
	
	public String getContentDesk() {
		return this.contentDesk;
	}
	
	@XmlAttribute(name="menus")
	public void setMenus(String menus) {
		this.menus = menus;
	}
		
	public String getMenus() {
		return this.menus;
	}
		
	@XmlAttribute(name="menusart")
	public void setMenusart(String menusart) {
		this.menusart = menusart;
	}
		
	public String getMenusart() {
		return this.menusart;
	}		
	
	@XmlAttribute(name="contentDesk")
	public void setContentDesk(String contentDesk) {
		this.contentDesk = contentDesk;
	}
	
	public String getContentMovil() {
		return this.contentMovil;
	}
			
	@XmlAttribute(name="contentMovil")
	public void setContentMovil(String contentMovil) {
		this.contentMovil = contentMovil;
	}
		
	public String getCarrusels() {
		return this.carrusels;
	}
		
	public String[] getListCarrusels() {
		return (this.carrusels.split(","));
	}
				
	@XmlAttribute(name="carrusels")
	public void setCarrusels(String carrusels) {
		this.carrusels = carrusels;
	}		
	
	public String getPanoramicas() {
		return this.panoramicas;
	}
	
	public String getExtended() {
		return this.extended;
	}
		
	@XmlAttribute(name="extended")
	public void setExtended(String extended) {
		this.extended = extended;
	}	
				
	@XmlAttribute(name="panoramicas")
	public void setPanoramicas(String panoramicas) {
		this.panoramicas = panoramicas;
	}		

	public static Linea getLinea(LineaType lineaType, Pais pais) {
		return pais.getShoponline().getLinea(lineaType);
	}
	
	public LineaType getType() {
		return (LineaType.valueOf(getId()));
	}
	
	public boolean isActiveIn(Channel channel) {
		return getType().isActiveIn(channel);
	}
	
	public boolean isActiveIn(AppEcom app) {
		switch (app) {
		case outlet:
			return "s".compareTo(getOutlet())==0;
		case shop:
		case votf:
		default:
			return "s".compareTo(getShop())==0;
		}
	}
		
	@XmlElement(name="sublinea") 
	List<Sublinea> listSublineas = new LinkedList<>();
		
	private List<Sublinea> getListSublineas() {
		return this.listSublineas;
	}
	public List<Sublinea> getListSublineas(AppEcom app) {
		List<Sublinea> sublineasApp = new ArrayList<>();
		for (Sublinea sublinea : listSublineas) {
			if (sublinea.isActiveIn(app)) {
				sublineasApp.add(sublinea);
			}
		}
		return sublineasApp;
	}
	
	public Sublinea getSublineaNinos(SublineaType sublineaType) {
		Sublinea sublineaRet = null;
		boolean encontrada = false;
		Iterator<Sublinea> it = getListSublineas().iterator();
		while (!encontrada && it.hasNext()) {
			Sublinea sublineaTmp = it.next();
			if (sublineaTmp.getTypeSublinea() == sublineaType) {
				sublineaRet = sublineaTmp;
				encontrada = true;
			}
		}
		
		return sublineaRet;			
	}
	
	public boolean existsSublineas() {
		return (!getListSublineas().isEmpty());
	}
	
	public TypeContentDesk getContentDeskType() {
		return TypeContentDesk.valueOf(getContentDesk());
	}

	public TypeContentMobil getContentMobilType() {
		return TypeContentMobil.valueOf(getContentMovil());
	}

	@Override
	public String toString() {
		return "Linea [id="+ this.id + ", existe=" + this.shop + ", contentDesk=" + this.contentDesk + ", contentMovil=" + this.contentMovil + ", carrusels=" + this.carrusels + ", panoramicas=" + this.panoramicas +
				", toString()=" + super.toString() + "]";
	}
}