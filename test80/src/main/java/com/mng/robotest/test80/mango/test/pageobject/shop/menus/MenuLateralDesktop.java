package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

public interface MenuLateralDesktop {
	abstract public AppEcom getApp();
	abstract public LineaType getLinea();
	abstract SublineaNinosType getSublinea();
	abstract String getNombre();
	abstract String getDataGaLabelMenuLateralDesktop();
	abstract String getDataGaLabelMenuSuperiorDesktop();
	abstract public boolean isMenuLateral();
    abstract public boolean isDataForValidateArticleNames();
    abstract public String[] getTextsArticlesGalery();
    
    public enum GroupMenu {
    	Nuevo(true, false, false), 
    	Prendas(true, true, false),
    	Accesorios(true, true, false),
    	RecienNacido(true, true, false),
    	BebeNina(true, true, false),
    	BebeNino(true, true, false),
    	Colecciones(true, true, false),
    	Extras(false, false, true),
    	Desconocido(false, false, false);
    	
    	private boolean containsArticles;
    	private boolean isTitleEquivalentToMenuName;
    	private boolean containsOnlyCampaigns;
    	private GroupMenu(boolean containsArticles, boolean isTitleEquivalentToMenuName, boolean containsOnlyCampaigns) {
    		this.containsArticles = containsArticles;
    		this.isTitleEquivalentToMenuName = isTitleEquivalentToMenuName;
    		this.containsOnlyCampaigns = containsOnlyCampaigns;
    	}
    	
    	public boolean containsArticles() {
    		return this.containsArticles;
    	}
    	
    	public boolean isTitleEquivalentToMenuName() {
    		return this.isTitleEquivalentToMenuName;
    	}
    	
    	public boolean containsOnlyCampaigns() {
    		return this.containsOnlyCampaigns;
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
