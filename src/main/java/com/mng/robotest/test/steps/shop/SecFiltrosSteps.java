package com.mng.robotest.test.steps.shop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class SecFiltrosSteps extends StepBase {

	private final SecFiltros secFiltros = SecFiltros.make(channel, app);
	
	private static final String TAG_LIT_COLORS_TO_SELECT = "@TagLitColorsToSelect";

	@Step (
		description="Seleccionar los colores <b>" + TAG_LIT_COLORS_TO_SELECT + "</b>", 
		expected="Aparece la galería de imágenes",
		saveNettraffic=SaveWhen.Always)
	public int selectFiltroColores(List<Color> colorsToSelect, String litMenu) {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_LIT_COLORS_TO_SELECT, Color.getListNamesFiltros(colorsToSelect).toString());
		int numArticulos1page = secFiltros.selecFiltroColoresAndReturnNumArticles(colorsToSelect);			
		checkAfterSelectFiltroColores(colorsToSelect, litMenu, numArticulos1page);
		return numArticulos1page;
	}
	
	@Validation
	private ChecksTM checkAfterSelectFiltroColores(
			List<Color> colorsSelected, String litMenu, int numArticulos1page) {
		
		var checks = ChecksTM.getNew();
		List<String> listCodColors = Color.getListCodigosColor(colorsSelected);
		String currentUrl = driver.getCurrentUrl();
	 	checks.add(
			"En la URL (*) aparece el parámetro c= que contiene los códigos de color <b>" + 
			listCodColors.toString() + "</b> (*) " + currentUrl + "<br>",
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
	public void selectFiltroMenus(List<MenuLateralDesktop> menusToSelect) {
		List<String> listMenus = getListMenusStr(menusToSelect);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagLitMenusToSelect, StringUtils.join(menusToSelect, ","));
		secFiltros.selectMenu2onLevel(listMenus);		
		new PageGaleriaSteps().validateGaleriaAfeterSelectMenu();
		GenericChecks.checkDefault();
	}
	
	private List<String> getListMenusStr(List<MenuLateralDesktop> listMenus) {
		List<String> listReturn = new ArrayList<>();
		for (MenuLateralDesktop menu : listMenus) {
			listReturn.add(menu.getNombre());
		}
		return listReturn;
	}
}
