package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;

public interface SecFiltros {
	
    public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception;
    public void selectCollection(FilterCollection coleccion) throws Exception;
    public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception;
    public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) throws Exception;
    public boolean isClickableFiltroUntil(int seconds);
    public boolean isCollectionFilterPresent() throws Exception;
    
    //Static factory
	public static SecFiltros newInstance(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			return (SecFiltrosDesktop.getInstance(app, driver));
		case movil_web:
		default:
			if (app==AppEcom.outlet) {
				return (SecSimpleFiltrosMobil.getInstance(app, driver));
			}
			return (SecMultiFiltrosMobil.getInstance(app, driver));
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
	    	
	    List<String> listCodColorsInUrl = getCodColoresFromListCommaSeparated(matcher.group(1));
	    for (Color color : listColorsToValidate) {
	    	if (!listCodColorsInUrl.contains(color.getCodigoColor())) {
	    		return false;
	    	}
	    }
	    
	    return true;
	}
	
	public static List<String> getCodColoresFromListCommaSeparated(String listCodColorsCommaSeparated) {
	    List<String> listCodColores = new ArrayList<>();
	    StringTokenizer tokensColores = new StringTokenizer(listCodColorsCommaSeparated, ",");
	    while (tokensColores.hasMoreElements()) {
	    	listCodColores.add(tokensColores.nextToken());
	    }
	    return listCodColores;
	}
}
