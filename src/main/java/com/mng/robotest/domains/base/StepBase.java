package com.mng.robotest.domains.base;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.legal.legaltexts.LegalTextsPage;
import com.mng.robotest.domains.manto.tests.ManXXX;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.domains.transversal.genericchecks.ChecksMango;
import com.mng.robotest.domains.transversal.genericchecks.ChecksMango.BuilderChecksMango;
import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus;
import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public abstract class StepBase extends PageBase {

	protected void access(boolean clearData) throws Exception {
		if (clearData) {
			accessAndClearData();
		} else {
			access();
		}
	}
	
	protected void accessAndLogin() throws Exception {
		accessAndLogin(false);
	}
	protected void accessAndLogin(boolean clearData) throws Exception {
		dataTest.setUserRegistered(true);
		access(clearData);
	}
	
	protected void access() throws Exception {
		new AccesoSteps().oneStep(false);
	}
	
	protected void accessAndClearData() throws Exception {
		new AccesoSteps().oneStep(true);
	}
	
	protected void clickLinea(LineaType lineaType) {
		new MenuSteps().clickLinea(lineaType);
	}
	protected void clickLinea(LineaType lineaType, SublineaType sublinea) {
		new MenuSteps().clickLinea(lineaType, sublinea);
	}
	protected void clickMenu(String menuLabel) {
		new MenuSteps().clickMenu(menuLabel);
	}
	protected void clickMenu(MenuItem menuItem) {
		clickMenu(FactoryMenus.get(menuItem));
	}
	protected void clickMenu(MenuWeb menu) {
		new MenuSteps().click(menu);
	}
	protected void clickGroup(GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(groupType));
	}
	public void clickGroup(LineaType linea, SublineaType sublinea, GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(linea, sublinea, groupType));
	}
	protected void clickSubMenu(MenuItem menuItem) {
		clickSubMenu(FactoryMenus.get(menuItem));
	}
	protected void clickSubMenu(MenuWeb menu) {
		new MenuSteps().clickSubMenu(menu);
	}
	
	protected void filterGaleryByColor(MenuItem menu, Color color) {
		filterGaleryByColors(menu, Arrays.asList(color));
	}
	protected void filterGaleryByColors(MenuItem menu, List<Color> colors) {
		new SecFiltrosSteps().selectFiltroColores(colors, FactoryMenus.get(menu).getMenu());
	}	
	
	protected List<Article> getArticles(int numArticles) throws Exception {
		return UtilsTest.getArticlesForTest(dataTest.getPais(), app, numArticles, driver);
	}
	
	protected Pair<Article, Article> getTwoArticlesFromDistinctWarehouses() throws Exception {
		return UtilsTest.getTwoArticlesFromDistinctWarehouses(dataTest.getPais(), app);
	}
	
	protected void checkPedidosManto(List<DataPedido> listPedidos) throws Exception {
		new ManXXX(listPedidos).execute();
	}
	protected void checkPedidosManto(List<CheckPedido> listChecks, List<DataPedido> listPedidos) 
			throws Exception {
		new ManXXX(listChecks, listPedidos).execute();
	}	
	
	public static BuilderChecksMango checksGeneric() {
		return new ChecksMango.BuilderChecksMango();
	}
	
	public static void checksDefault() {
		checksGeneric()
			.cookiesAllowed()
			.jsErrors()
			.textsTraduced()
			.analitica().execute();
	}
	
	@Validation
	protected ChecksTM checkLegalTextsVisible(PageBase page) {
		var checks = ChecksTM.getNew();
		var legalTextsPageOpt = page.getLegalTextsPage();
		if (!legalTextsPageOpt.isEmpty()) {
			checkLegalTexts(checks, legalTextsPageOpt.get());
		}
		return checks;
	}

	private void checkLegalTexts(ChecksTM checks, LegalTextsPage legalTextsPage) {
		var legalTexts = legalTextsPage.getLegalTexts();
		checks.setTitle("<b>" + legalTexts.getDescription() + "</b>");
		for (var legalText : legalTexts.getTexts()) {
			checks.add(
				"Aparece el <b>texto legal</b> \"" + legalText.getText() + "\"<br>",
				legalTextsPage.isVisibleLegalText(legalText), Defect);
		}
	}
	
	public String getUserEmail() {
		return getUserEmail(true);
	}
	public String getUserEmail(boolean emailExists) {
		return UtilsMangoTest.getEmailForCheckout(dataTest.getPais(), emailExists);
	}
	
	@Step (
		description="Recargamos la página",
		expected="")
	public void refresh() {
		refreshPage();
	}
	
}
