package com.mng.robotest.test80.mango.test.pageobject.shop.ficha.tallas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;

public interface SSecSelTallasFicha {

	public int getNumOptionsTallas();
	public int getNumOptionsTallasNoDisponibles();
	public boolean isTallaAvailable(String talla);
	public boolean isTallaUnica();
	public void selectTallaByValue(int tallaValue);
	public void selectTallaByIndex(int posicionEnDesplegable);
	public void selectFirstTallaAvailable();
	public String getTallaAlfSelected(AppEcom app);
	public String getTallaAlf(int posicion);
	public String getTallaCodNum(int posicion);
	public boolean isVisibleSelectorTallasUntil(int maxSeconds);
	public boolean isVisibleListTallasForSelectUntil(int maxSeconds);
	
	public static SSecSelTallasFicha make(TypeFicha typeFicha, WebDriver driver) {
		if (typeFicha==TypeFicha.Old) {
			return new SSecSelTallasFichaOld(driver);
		}
		return new SSecSelTallasFichaNew(driver);
	}
	
	public default Talla getTallaSelected(AppEcom app) {
		return Talla.from(getTallaAlfSelected(app));
	}
	
	public default void selectTallaByValue(Talla talla) {
		selectTallaByValue(talla.getTallaNum());
	}
	
	
	/**
	 * @return talla eliminando el literal del tipo " [Almacen: 001]
	 */
	public default String removeAlmacenFromTalla(String talla) {
	    Pattern tallaWithAlmacen = Pattern.compile("(.*)( \\[Almacen: [0-9]{3}\\])");
	    Matcher matcher = tallaWithAlmacen.matcher(talla);
	    if (matcher.find()) {
	    	return matcher.group(1);
	    }
	    return talla;
	}
}
