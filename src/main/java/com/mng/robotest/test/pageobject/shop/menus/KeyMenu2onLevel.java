package com.mng.robotest.test.pageobject.shop.menus;

import java.util.Objects;

public class KeyMenu2onLevel {
	final Menu1rstLevel menu1rstLevel;
	final String nombreMenu;
	
	private KeyMenu2onLevel(Menu1rstLevel menu1rstLevel, String nombreMenu2onLevel) {
		this.menu1rstLevel = menu1rstLevel;
		this.nombreMenu = nombreMenu2onLevel;
	}
	
	public static KeyMenu2onLevel from(Menu1rstLevel menu1rstLevel, String nombreMenu2onLevel) {
		return new KeyMenu2onLevel(menu1rstLevel, nombreMenu2onLevel);
	}
	
	@Override 
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof KeyMenu2onLevel)) {
			return false;
		}
		KeyMenu2onLevel key = (KeyMenu2onLevel) o;
		return (key.menu1rstLevel.equals(menu1rstLevel) &&
				key.nombreMenu.compareTo(nombreMenu)==0);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(menu1rstLevel, nombreMenu);
	}
	
	@Override
	public String toString() {
		return (menu1rstLevel + "/" + nombreMenu);
	}
}
