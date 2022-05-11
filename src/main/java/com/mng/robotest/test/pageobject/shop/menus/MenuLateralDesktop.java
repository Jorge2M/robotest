package com.mng.robotest.test.pageobject.shop.menus;

import java.util.List;
import java.util.Arrays;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;

public interface MenuLateralDesktop {
	abstract public AppEcom getApp();
	abstract public LineaType getLinea();
	abstract SublineaType getSublinea();
	abstract String getNombre();
	abstract String getDataGaLabelMenuLateralDesktop();
	abstract String getDataGaLabelMenuSuperiorDesktop();
	abstract public boolean isMenuLateral();
	abstract public boolean isDataForValidateArticleNames();
	abstract public String[] getTextsArticlesGalery();
	abstract int getLevel();
	
	public enum Element {
		article, campaign, slider, map, iframe
	}
	
	public enum GroupMenu {
		Nuevo(Arrays.asList(Element.article), false), 
		Prendas(Arrays.asList(Element.article), true),
		Accesorios(Arrays.asList(Element.article), true),
		RecienNacido(Arrays.asList(Element.article), true),
		BebeNina(Arrays.asList(Element.article), true),
		BebeNino(Arrays.asList(Element.article), true),
		Colecciones(Arrays.asList(Element.article, Element.campaign), true),
		Extras(Arrays.asList(Element.campaign), false),
		Desconocido(Arrays.asList(Element.article, Element.campaign, Element.slider, Element.map, Element.iframe), false);
		
		private List<Element> elementsCanBeContained;
		private boolean isTitleEquivalentToMenuName;
		private GroupMenu(List<Element> elementsCanBeContained, boolean isTitleEquivalentToMenuName) {
			this.elementsCanBeContained = elementsCanBeContained;
			this.isTitleEquivalentToMenuName = isTitleEquivalentToMenuName;
		}
		
		public List<Element> getElementsCanBeContained() {
			return this.elementsCanBeContained;
		}
		
		public boolean isTitleEquivalentToMenuName() {
			return this.isTitleEquivalentToMenuName;
		}
		
		public boolean canContainElement(Element element) {
			return (getElementsCanBeContained().contains(element));
		}
	}
	
	default public GroupMenu getGroup() {
		String datagalabel_MenuSuperior = getDataGaLabelMenuSuperiorDesktop();
		if (datagalabel_MenuSuperior.contains("nuevo")) {
			return GroupMenu.Nuevo;
		}
		if (datagalabel_MenuSuperior.contains("prendas-")) {
			return GroupMenu.Prendas;
		}
		if (datagalabel_MenuSuperior.contains("accesorios-")) {
			return GroupMenu.Accesorios;
		}
		if (datagalabel_MenuSuperior.contains("recien_nacido")) {
			return GroupMenu.RecienNacido;
		}
		if (datagalabel_MenuSuperior.contains("bebe_nina")) {
			return GroupMenu.BebeNina;
		}
		if (datagalabel_MenuSuperior.contains("bebe_nino")) {
			return GroupMenu.BebeNino;
		}
		if (datagalabel_MenuSuperior.contains("colecciones-")) {
			return GroupMenu.Colecciones;
		}
		if (datagalabel_MenuSuperior.contains("extras-")) {
			return GroupMenu.Extras;
		}
		return GroupMenu.Desconocido;
	}
}
