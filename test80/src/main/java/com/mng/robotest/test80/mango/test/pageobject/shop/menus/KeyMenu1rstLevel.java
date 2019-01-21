package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import java.util.Objects;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

public class KeyMenu1rstLevel {
    final LineaType lineaType;
    final SublineaNinosType sublineaType;
    String nombreMenu;
    
    private KeyMenu1rstLevel(LineaType lineaType, SublineaNinosType sublineaType, String nombreMenu) {
    	this.lineaType = lineaType;
    	this.sublineaType = sublineaType;
    	this.nombreMenu = nombreMenu;
    }
    
    public static KeyMenu1rstLevel from(LineaType lineaType, SublineaNinosType sublineaType, String nombreMenu) {
    	return new KeyMenu1rstLevel(lineaType, sublineaType, nombreMenu);
    }
    
    @Override public boolean equals(Object o) {
    	if (o == this)
    		return true;
    	if (!(o instanceof KeyMenu1rstLevel))
    		return false;
    	KeyMenu1rstLevel key = (KeyMenu1rstLevel) o;
    	return (key.lineaType==lineaType &&
    			key.sublineaType==sublineaType &&
    			key.nombreMenu.toLowerCase().compareTo(nombreMenu.toLowerCase())==0);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(lineaType, sublineaType, nombreMenu);
    }
    
    @Override
    public String toString() {
    	return (lineaType + "/" + sublineaType + "/" + nombreMenu);
    }
}
