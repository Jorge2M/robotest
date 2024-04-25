package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop.SecFiltrosDesktopNormal;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device.SecMultiFiltrosDevice;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;

public interface SecFiltros {
	
	public void selecOrdenacion(FilterOrdenacion typeOrden) throws Exception;
	public void selecFiltroColores(List<Color> colorsToSelect);
	public void selectMenu2onLevel(List<String> listMenus);
	public void selectMenu2onLevel(String menuLabel);
	public boolean isClickableFiltroUntil(int seconds);
	
	public static SecFiltros make(Channel channel, AppEcom app, Pais pais) {
		switch (channel) {
		case desktop:
			return SecFiltrosDesktop.make(app, pais);
		case mobile, tablet:
			if (app==AppEcom.outlet && channel==Channel.tablet) {
				return new SecFiltrosDesktopNormal();
			}
			return SecMultiFiltrosDevice.make();
		default:
			return SecMultiFiltrosDevice.make();
		}
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
	
	public static List<String> getCodColoresFromListCommaSeparated(String listCodColorsCommaSeparated) {
		List<String> listCodColores = new ArrayList<>();
		var tokensColores = new StringTokenizer(listCodColorsCommaSeparated, ",");
		while (tokensColores.hasMoreElements()) {
			listCodColores.add(tokensColores.nextToken());
		}
		return listCodColores;
	}
}
