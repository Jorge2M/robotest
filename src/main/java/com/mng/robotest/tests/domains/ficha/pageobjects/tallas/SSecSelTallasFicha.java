package com.mng.robotest.tests.domains.ficha.pageobjects.tallas;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SSecSelTallasFicha {

	public boolean isSectionUntil(int seconds);
	public int getNumOptionsTallas();
	public int getNumOptionsTallasNoDisponibles();
	public boolean isTallaAvailable(String talla);
	public boolean isTallaUnica();
	public void selectTallaByValue(String tallaValue);
	public void selectTallaByLabel(String tallaLabel);
	public void selectTallaByIndex(int posicionEnDesplegable);
	public void selectFirstTallaAvailable();
	public String getTallaAlfSelected(AppEcom app);
	public String getTallaAlf(int posicion);
	public String getTallaCodNum(int posicion);
	public boolean isVisibleSelectorTallasUntil(int seconds);
	public boolean isVisibleListTallasForSelectUntil(int seconds);
	public boolean isVisibleAvisoSeleccionTalla();
	public void closeTallas();
	
	public static SSecSelTallasFicha make(Channel channel, AppEcom app) {
		if (channel.isDevice()) {
			return new SSecSelTallasFichaDevice(channel, app);
		}
		return new SSecSelTallasFichaDesktop();
	}
	
	public default Talla getTallaSelected(AppEcom app, PaisShop pais) {
		return Talla.fromLabel(getTallaAlfSelected(app), pais);
	}
	
	public default void selectTallaByValue(Talla talla) {
		selectTallaByValue(talla.getValue());
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
