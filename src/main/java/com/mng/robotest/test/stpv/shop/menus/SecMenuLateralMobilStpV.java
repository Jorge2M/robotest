package com.mng.robotest.test.stpv.shop.menus;

import java.util.Arrays;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Linea.TypeContentMobil;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenuLateralDevice;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenuLateralDevice.TypeLocator;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class SecMenuLateralMobilStpV {
	
	private final Channel channel;
	private final AppEcom app;
	private final WebDriver driver;
	private final SecMenuLateralDevice secMenuLateral;
	
	private SecMenuLateralMobilStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.channel = channel;
		this.app = app;
		this.driver = driver;
		secMenuLateral = new SecMenuLateralDevice(channel, app, driver);
	}
	
	public static SecMenuLateralMobilStpV getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new SecMenuLateralMobilStpV(channel, app, driver));
	}
	
	@Step (
		description="Seleccionar el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>", 
		expected="Aparece la galería de productos asociada al menú",
		saveNettraffic=SaveWhen.Always,
		saveHtmlPage=SaveWhen.Always)
	public void selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
		secMenuLateral.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais);
		validaSelecMenu(menu1rstLevel);
	}
	
	@Validation(
		description="No existe el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>",
		level=State.Defect)
	public boolean checkNotExistsMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, Pais pais) {
		return (!secMenuLateral.existsMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais));
	}

	public void validaSelecMenu(MenuLateralDesktop menu) throws Exception {
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, driver);
		pageGaleriaStpV.validateGaleriaAfeterSelectMenu(app);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}

	@Validation
	private ChecksTM checkGaleriaAfterSelectNuevo() {
		ChecksTM validations = ChecksTM.getNew();
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile, app, driver);
		int maxSeconds = 3;
		validations.add(
			"Aparece algún artículo (esperamos " + maxSeconds + " segundos)",
			pageGaleria.isVisibleArticleUntil(1, maxSeconds), State.Warn);

		return validations;   
	}
	
	@Step (
		description=
			"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
			"<b style=\"color:brown;\">#{lineaType.getNameUpper()}</b>",
		expected="Aparece la página correcta asociada a la línea #{lineaType.getNameUpper()}")
	public void seleccionLinea(LineaType lineaType, Pais pais) throws Exception {
		secMenuLateral.getSecLineasDevice().selectLinea(pais.getShoponline().getLinea(lineaType)); 
		validaSelecLinea(pais, lineaType, null);
	}	
	
	@Step (
		description=
			"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
			"<b style=\"color:brown;\">#{lineaType.name()} / #{sublineaType.getNameUpper()}</b>",
		expected="Aparece la página correcta asociada a la línea/sublínea")
	public void seleccionSublineaNinos(LineaType lineaType, SublineaType sublineaType, Pais pais) throws Exception {
		secMenuLateral
			.getSecLineasDevice()
			.selecSublineaNinosIfNotSelected(pais.getShoponline().getLinea(lineaType), sublineaType);
		validaSelecLinea(pais, lineaType, sublineaType);
	}
	
	/**
	 * Validamos el resultado esperado después de seleccionar una línea (she, he, kids...) en Móbil
	 */
	public void validaSelecLinea(Pais pais, LineaType lineaType, SublineaType sublineaType) throws Exception {
		Linea linea = pais.getShoponline().getLinea(lineaType);
		TypeContentMobil typeContent = linea.getContentMobilType();
		if (sublineaType!=null) {
			typeContent = linea.getSublineaNinos(sublineaType).getContentMobilType();
		}
		
		switch (typeContent) {
		case menus2:
			validaSelecLineaWithMenus2onLevelAssociated(lineaType, sublineaType);
			break;
		case sublineas:			
			validaSelecLineaNinosWithSublineas(lineaType);
			break;
		case articulos:
			PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.mobile, app, driver);
			int maxSeconds = 3;
			pageGaleriaStpV.validaArtEnContenido(maxSeconds);
			break;
		default:
			throw new IllegalArgumentException("TypeContent " + typeContent + " not valid for channel mobil");
		}
	}
		
	@Validation
	public ChecksTM validaSelecLineaNinosWithSublineas(LineaType lineaNinosType) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaNinosType + "</b>",
			secMenuLateral.getSecLineasDevice().isSelectedLinea(lineaNinosType), State.Warn);
	 	validations.add(
			"Es visible el bloque con las sublíneas de " + lineaNinosType,
			secMenuLateral.getSecLineasDevice().isVisibleBlockSublineasNinos(lineaNinosType), State.Warn);
	 	return validations;
	}
	
	@Validation
	public ChecksTM validaSelecLineaWithMenus2onLevelAssociated(LineaType lineaType, SublineaType sublineaType) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está seleccionada la línea <b>" + lineaType + "</b>",
			secMenuLateral.getSecLineasDevice().isSelectedLinea(lineaType), State.Warn);
	 	validations.add(
			"Son visibles links de Menú de 2o nivel",
			secMenuLateral.isMenus2onLevelDisplayed(sublineaType), State.Warn);
	 	return validations;
	}	
	
	final static String tagTextMenu = "@TagTextMenu";
	@Step (
		description="Selección del menú <b>" + tagTextMenu + "</b> (data-label contains #{menu1rstLevel.getDataGaLabelMenuSuperiorDesktop()})", 
		expected="El menú se ejecuta correctamente")
	public void stepClickMenu1rstLevel(Menu1rstLevel menu1rstLevel, Pais pais) throws Exception {
		secMenuLateral.clickMenuLateral1rstLevel(TypeLocator.dataGaLabelPortion, menu1rstLevel, pais);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagTextMenu, menu1rstLevel.getNombre());
		ModalCambioPais.closeModalIfVisible(driver);
		validaPaginaResultMenu2onLevel();
	}	
	
	public void validaPaginaResultMenu2onLevel() throws Exception {
		checkElementsAfterClickMenu2onLevel();
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.ImgsBroken)).checks(driver);
	}
	
	@Validation (
		description="Aparecen artículos, banners, frames, maps o Sliders",
		level=State.Warn)
	private boolean checkElementsAfterClickMenu2onLevel() throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile, app, driver);
		PageLanding pageLanding = new PageLanding(driver);
		return (
			pageGaleria.isVisibleArticleUntil(1, 3) ||
			pageLanding.hayIframes() ||
			pageLanding.hayMaps() ||
			pageLanding.haySliders() ||
			ManagerBannersScreen.existBanners(driver));
	}
}
