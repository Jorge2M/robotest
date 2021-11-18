package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.Objects;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;


public class Menu2onLevel implements MenuLateralDesktop {

	private KeyMenu2onLevel key;
	private String[] textsArticlesGalery = null; //Lista de posibles textos contenidos en los artículos de la galería
	
	private Menu2onLevel(KeyMenu2onLevel key, String[] textsArticlesGalery) {
		this.key = key;
		this.textsArticlesGalery = textsArticlesGalery;
	}
	
	static Menu2onLevel from(Menu1rstLevel menu1rstLevel, String nombreMenu2onLevel, String[] textsArticlesGalery) {
		KeyMenu2onLevel key2onLevel = KeyMenu2onLevel.from(menu1rstLevel, nombreMenu2onLevel);
		return (from(key2onLevel, textsArticlesGalery));
	}
	
	public static Menu2onLevel from(KeyMenu2onLevel key, String[] textsArticlesGalery) {
		return (new Menu2onLevel(key, textsArticlesGalery));
	}
	
	public KeyMenu2onLevel getKey() {
		return this.key;
	}
	
	@Override
	public int getLevel() {
		return 2;
	}
	
	@Override
	public AppEcom getApp() {
		return this.key.menu1rstLevel.getApp();
	}
	
	@Override
	public LineaType getLinea() {
		return this.key.menu1rstLevel.getLinea();
	}
	
	@Override
	public SublineaType getSublinea() {
		return this.key.menu1rstLevel.getSublinea();
	}
	
	@Override
	public String getNombre() {
		return (this.key.nombreMenu);
	}
	
	@Override
	public String getDataGaLabelMenuLateralDesktop() {
		return (key.menu1rstLevel.getDataGaLabelMenuLateralDesktop() + ":" + key.nombreMenu.toLowerCase());
	}
	
	@Override
	public String getDataGaLabelMenuSuperiorDesktop() {
		return (key.menu1rstLevel.getDataGaLabelMenuSuperiorDesktop());
	}
	
	@Override
	public boolean isMenuLateral() {
		return true;
	}   
	
	@Override
	public String[] getTextsArticlesGalery() {
		return this.textsArticlesGalery;
	}
	
	@Override
	public boolean isDataForValidateArticleNames() {
		return (this.textsArticlesGalery!=null &&
				"*".compareTo(this.textsArticlesGalery[0])!=0);
	}
	
	@Override public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Menu2onLevel)) {
			return false;
		}
		Menu2onLevel menu2onLevel = (Menu2onLevel) o;
		return (menu2onLevel.key.equals(key));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(key);
	}
	
	@Override
	public String toString() {
		return (key.toString());
	}
}
