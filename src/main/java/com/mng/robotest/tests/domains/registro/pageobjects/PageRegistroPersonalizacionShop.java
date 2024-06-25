package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.HE;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.HOME;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.KIDS;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.SHE;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

public abstract class PageRegistroPersonalizacionShop extends PageBase {

	public enum GenderOption { 
		FEMENINO('M'), 
		MASCULINO('H'), 
		NO_BINARIO('X'), 
		PREFIERO_NO_INCLUIRLO('N');
	
		private char code;
		private GenderOption(char code) {
			this.code = code;
		}
		
		public char getCode() {
			return code;
		}
	}
	
	public abstract boolean isPage();
	public abstract boolean isPage(int seconds);
	public abstract boolean isNotPage(int seconds);
	public abstract boolean isPostalCodeVisible();
	public abstract void inputName(String name);
	public abstract void inputPostalCode(String postalCode);
	public abstract void inputDateOfBirth(String date);
	public abstract void selectGender(GenderOption gender);
	public abstract void selectLineas(List<LineaType> lineasToSelect);
	public abstract void clickGuardar();
	public abstract boolean checkMessageErrorMovil(int seconds);
	
	protected static final List<LineaType> ALL_LINEAS = Arrays.asList(SHE, HE, KIDS, HOME); 
	
	public static PageRegistroPersonalizacionShop make(String urlBase, Channel channel) {
		if (PageBase.isPRO(urlBase) || channel.isDevice()) {
			return new PageRegistroPersonalizacionShopOld();
		}
		return new PageRegistroPersonalizacionShopGenesis();
	}
	

	
	public static List<LineaType> getAllLineas() {
		return ALL_LINEAS;
	}

}
