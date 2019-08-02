package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.Objects;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

public class KeyMenu1rstLevel {
    final LineaType lineaType;
    final SublineaNinosType sublineaType;
    DataScreenMenu dataMenu;
    
    private KeyMenu1rstLevel(LineaType lineaType, SublineaNinosType sublineaType, DataScreenMenu dataMenu) {
    	this.lineaType = lineaType;
    	this.sublineaType = sublineaType;
    	this.dataMenu = dataMenu;
    }
    
    public static KeyMenu1rstLevel from(LineaType lineaType, SublineaNinosType sublineaType, DataScreenMenu dataMenu) {
    	return new KeyMenu1rstLevel(lineaType, sublineaType, dataMenu);
    }
    
    public static KeyMenu1rstLevel from(LineaType lineaType, SublineaNinosType sublineaType, String nombreMenu) {
    	DataScreenMenu dataMenu = DataScreenMenu.getNew(nombreMenu);
    	return new KeyMenu1rstLevel(lineaType, sublineaType, dataMenu);
    }
    
    @Override public boolean equals(Object o) {
    	if (o == this) {
    		return true;
    	}
    	if (!(o instanceof KeyMenu1rstLevel)) {
    		return false;
    	}
    	KeyMenu1rstLevel key = (KeyMenu1rstLevel) o;
    	return (key.lineaType==lineaType &&
    			key.sublineaType==sublineaType &&
    			key.dataMenu.equals(dataMenu));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(lineaType, sublineaType, dataMenu);
    }
    
    @Override
    public String toString() {
    	return (lineaType + "/" + sublineaType + "/" + dataMenu.getLabel());
    }
}
