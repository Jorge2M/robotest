package com.mng.robotest.test.pageobject.shop.menus;

import java.util.ArrayList;
import java.util.Objects;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;


public final class Menu1rstLevel implements MenuLateralDesktop {
	
	AppEcom app;
	private KeyMenu1rstLevel key;
	private DataScreenMenu dataMenu; 
	private boolean isMenuLateral;
	private String[] textsArticlesGalery = null; //Lista de posibles textos contenidos en los artículos de la galería
	private ArrayList<Menu2onLevel> listMenus2onLevel = new ArrayList<>();
	
	private Menu1rstLevel(AppEcom app, KeyMenu1rstLevel key, DataScreenMenu dataMenu, boolean isMenuLateral, String[] textsArticlesGalery) {
		this.app = app;
		this.key = key;
		this.dataMenu = dataMenu;
		this.isMenuLateral = isMenuLateral;
		this.textsArticlesGalery = textsArticlesGalery;
	}
	
	static Menu1rstLevel from(AppEcom app, KeyMenu1rstLevel key, DataScreenMenu dataMenu, boolean isMenuLateral, String[] textsArticlesGalery) {
		return (new Menu1rstLevel(app, key, dataMenu, isMenuLateral, textsArticlesGalery));
	}
	
	static Menu1rstLevel from(AppEcom app, KeyMenu1rstLevel key, String nombreMenu, boolean isMenuLateral, String[] textsArticlesGalery) {
		DataScreenMenu dataMenu = DataScreenMenu.getNew(nombreMenu);
		return (new Menu1rstLevel(app, key, dataMenu, isMenuLateral, textsArticlesGalery));
	}
	
	public KeyMenu1rstLevel getKey() {
		return this.key;
	}
	
	@Override
	public int getLevel() {
		return 1;
	}
	
	@Override
	public AppEcom getApp() {
		return this.app;
	}
	
	@Override
	public LineaType getLinea() {
		return this.key.lineaType;
	}
	
	@Override
	public SublineaType getSublinea() {
		return this.key.sublineaType;
	}
	
	@Override
	public String getNombre() {
		return this.key.dataMenu.getLabel();
	}
	
//	public void setNombre(String nombreMenu) {
//		this.key.dataMenu.setLabel(nombreMenu);
//	}
	
	@Override
	public String getDataGaLabelMenuLateralDesktop() {
		return (getLinea().getLiteral(app) + ":" + getNombre().toLowerCase());
	}
	
	@Override
	public String getDataGaLabelMenuSuperiorDesktop() {
		return this.dataMenu.getDataGaLabel();
	}
	
	@Override
	public boolean isMenuLateral() {
		return this.isMenuLateral;
	}   
	
	@Override
	public boolean isDataForValidateArticleNames() {
		return (this.textsArticlesGalery!=null &&
				"*".compareTo(this.textsArticlesGalery[0])!=0);
	}
	
	@Override
	public String[] getTextsArticlesGalery() {
		return this.textsArticlesGalery;
	}
	
	public ArrayList<Menu2onLevel> getListMenus2onLevel() {
		return this.listMenus2onLevel;
	}
	
	public String getId() {
		return this.dataMenu.getId();
	}

	public void setDataGaLabel(String dataGaLabel) {
		this.dataMenu.setDataGaLabel(dataGaLabel);
	}
	
	public void addMenu2onLevel(Menu2onLevel menuToAdd) {
		this.listMenus2onLevel.add(menuToAdd);
	}

	@Override public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Menu1rstLevel)) {
			return false;
		}
		Menu1rstLevel menu1rNivel = (Menu1rstLevel) o;
		return (menu1rNivel.key.equals(key));
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
