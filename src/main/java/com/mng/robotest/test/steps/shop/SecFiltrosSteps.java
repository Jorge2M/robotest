package com.mng.robotest.test.steps.shop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test.steps.shop.menus.SecMenuLateralMobilSteps;

public class SecFiltrosSteps {

	static final String tagLitColorsToSelect = "@TagLitColorsToSelect";
	@Step (
		description="Seleccionar los colores <b>" + tagLitColorsToSelect + "</b>", 
		expected="Aparece la galería de imágenes",
		saveNettraffic=SaveWhen.Always)
	public static int selectFiltroColoresStep (AppEcom app, Channel channel, boolean validaciones, String litMenu, List<Color> colorsToSelect, WebDriver driver) 
	throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagLitColorsToSelect, Color.getListNamesFiltros(colorsToSelect).toString());
		SecFiltros secFiltros = SecFiltros.make(channel, app);
		int numArticulos1page = secFiltros.selecFiltroColoresAndReturnNumArticles(colorsToSelect);			
		if (validaciones) {
			checkAfterSelectFiltroColores(colorsToSelect, litMenu, numArticulos1page, driver);
		}
		
		return numArticulos1page;
	}
	
	@Validation
	private static ChecksTM checkAfterSelectFiltroColores(List<Color> colorsSelected, String litMenu, 
															  int numArticulos1page, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 1;
		List<String> listCodColors = Color.getListCodigosColor(colorsSelected);
		String currentUrl = driver.getCurrentUrl();
	 	checks.add(
			"En la URL (*) aparece el parámetro c= que contiene los códigos de color <b>" + 
			listCodColors.toString() + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)" + 
			"(*) " + currentUrl + "<br>",
			SecFiltros.checkUrlAfterFilterContainsColors(colorsSelected, currentUrl), State.Warn);		
		
	 	checks.add(
			"Aparece una pantalla en la que el title contiene \"" + litMenu.toUpperCase(),
			driver.getTitle().toUpperCase().contains(litMenu.toUpperCase()), State.Warn);		
	 	checks.add(
			"En pantalla aparecen >1 artículos (están apareciendo " + numArticulos1page + ")",
			numArticulos1page>1, State.Warn);   
	 	return checks;
	}
	
	static final String tagLitMenusToSelect = "@TagLitMenusToSelect";
	@Step (
		description="Seleccionar los menús <b>" + tagLitMenusToSelect + "</b>", 
		expected="Aparece la galería de artículos",
		saveNettraffic=SaveWhen.Always)
	public static void selectFiltroMenus(AppEcom app, Channel channel, List<MenuLateralDesktop> menusToSelect, WebDriver driver) 
	throws Exception {
		List<String> listMenus = getListMenusStr(menusToSelect);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagLitMenusToSelect, StringUtils.join(menusToSelect, ","));
		SecFiltros secFiltros = SecFiltros.make(channel, app);
		secFiltros.selectMenu2onLevel(listMenus);		
		new SecMenuLateralMobilSteps(channel, app).validaSelecMenu(menusToSelect.get(0));
	}
	
	private static List<String> getListMenusStr(List<MenuLateralDesktop> listMenus) {
		List<String> listReturn = new ArrayList<>();
		for (MenuLateralDesktop menu : listMenus) {
			listReturn.add(menu.getNombre());
		}
		return listReturn;
	}
}
