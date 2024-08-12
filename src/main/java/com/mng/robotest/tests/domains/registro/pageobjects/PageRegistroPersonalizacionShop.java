package com.mng.robotest.tests.domains.registro.pageobjects;

import static com.mng.robotest.tests.domains.menus.entity.LineaType.*;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.entity.LineaType;

public abstract class PageRegistroPersonalizacionShop extends PageBase {

	public enum GenderOption { 
		FEMENINO("FEMALE", "M"), 
		MASCULINO("MALE", "H"), 
		NO_BINARIO("NON_BINARY", "X"), 
		PREFIERO_NO_INCLUIRLO("DONT_ANSWER", "N");
	
		private String code;
		private String codeOld;
		private GenderOption(String code, String codeOld) {
			this.code = code;
			this.codeOld = codeOld;
		}
		
		public String getCode() {
			return code;
		}
		public String getCodeOld() {
			return codeOld;
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
	
	public static PageRegistroPersonalizacionShop make() {
		if (!new CommonsRegisterObject().isGenesis()) {
			return new PageRegistroPersonalizacionShopOld();
		}
		return new PageRegistroPersonalizacionShopGenesis();
	}
	

	
	public static List<LineaType> getAllLineas() {
		return ALL_LINEAS;
	}

}
