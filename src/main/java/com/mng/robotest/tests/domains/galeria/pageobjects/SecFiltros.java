package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.galeria.pageobjects.genesis.SecFiltrosGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.FiltroMobil;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.SecFiltrosMobilNoGenesis;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;

public interface SecFiltros {
	
	public void showFilters();
	public void acceptFilters();
	public void selectOrdenacion(FilterOrdenacion typeOrden) throws Exception;
	public boolean isVisibleSelectorPrecios();
	public int getMinImportFilter();
	public int getMaxImportFilter();
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight);
	public void selectIntervalImport(int minim, int maxim);
	public boolean isVisibleLabelFiltroPrecioApplied(int minim, int maxim);
	public void selecFiltroColores(List<Color> colorsToSelect);
	public void selectMenu2onLevel(List<String> listMenus);
	public void selectMenu2onLevel(String menuLabel);
	public boolean isVisibleColorTags(List<Color> colors);
	public boolean isClickableFiltroUntil(int seconds);
	public void clickFilterAndSortButton();
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros);
	public void close();
	
	public static SecFiltros make(Channel channel, AppEcom app, Pais pais) {
		if (pais.isGaleriaGenesis(app)) {
			return new SecFiltrosGenesis();
		}
		if (channel.isDevice()) {
			return new SecFiltrosMobilNoGenesis();
		}
		return new SecFiltrosDesktopNoGenesis();
	}
	
	/**
	 * @return if the URL after filter execution (of type https://shop.mango.com/es/mujer/chaquetas_c69427016/negros.blancos?c=1,10)
	 * 		   contains all the code colors
	 */
	public static boolean checkUrlAfterFilterContainsColors(List<Color> listColorsToValidate, String url) {
		Pattern patternUrlFiltroColor = Pattern.compile("\\?c=(.*)");
		Matcher matcher = patternUrlFiltroColor.matcher(url);
		if (!matcher.find()) {
			return false;
		}
			
		var listCodColorsInUrl = getCodColoresFromListCommaSeparated(matcher.group(1));
		for (Color color : listColorsToValidate) {
			if (!listCodColorsInUrl.contains(color.getCodigoColor())) {
				return false;
			}
		}
		
		return true;
	}
	
	private static List<String> getCodColoresFromListCommaSeparated(String listCodColorsCommaSeparated) {
		List<String> listCodColores = new ArrayList<>();
		var tokensColores = new StringTokenizer(listCodColorsCommaSeparated, ",");
		while (tokensColores.hasMoreElements()) {
			listCodColores.add(tokensColores.nextToken());
		}
		return listCodColores;
	}
}
