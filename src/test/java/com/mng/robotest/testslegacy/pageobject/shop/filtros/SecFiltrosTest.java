package com.mng.robotest.testslegacy.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.testslegacy.data.Color;

public class SecFiltrosTest {

	@Test
	public void testUrlWithAllTheColors() {
		//Define data-context
		List<Color> listColorsToValidate = Arrays.asList(Color.BLANCO, Color.VERDE);
		String url = getUrlWithColors(listColorsToValidate);
		
		//Execute code to test
		boolean resultCheck = SecFiltros.checkUrlAfterFilterContainsColors(listColorsToValidate, url);
		
		//Validations
		assert(resultCheck==true);
	}
	
	@Test
	public void testUrlWithoutAllTheColors() {
		//Define data-context
		List<Color> listColorsToValidate = Arrays.asList(Color.BLANCO, Color.VERDE);
		String url = getUrlWithColors(Arrays.asList(Color.ROJO, Color.VERDE));
		
		//Execute code to test
		boolean resultCheck = SecFiltros.checkUrlAfterFilterContainsColors(listColorsToValidate, url);
		
		//Validations
		assert(resultCheck==false);
	}
	
	@Test
	public void testUrlNotMeetPattern() {
		//Define data-context
		List<Color> listColorsToValidate = Arrays.asList(Color.BLANCO, Color.VERDE);
		String url = "https://shop.mango.com/es/mujer/prendas";
		
		//Execute code to test
		boolean resultCheck = SecFiltros.checkUrlAfterFilterContainsColors(listColorsToValidate, url);
		
		//Validations
		assert(resultCheck==false);
	}
	
	private String getUrlWithColors(List<Color> listColors) {
		final String separatorNameColors = ".";
		final String separatorCodeColors = ",";
		String url = "https://shop.mango.com/es/mujer/chaquetas_c69427016/";
		String listNameColors = "";
		String listCodeColors = "";
		for (Color color : listColors) {
			listNameColors+=(separatorNameColors + color.getNameFiltro().toLowerCase());
			listCodeColors+=(separatorCodeColors + color.getCodigoColor());
		}
		
		url+=listNameColors.replaceFirst(separatorNameColors, "");
		url+="?c=";
		url+=listCodeColors.replaceFirst(separatorCodeColors, "");
		return url;
	}
}
