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
}
