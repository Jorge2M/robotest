package com.mng.robotest.test80.mango.test.appshop;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.IdiomaPais;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFichaArtOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.Slider;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDataProduct.ProductNav;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.buscador.SecBuscadorStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.SecModalPersonalizacionStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalBuscadorTiendasStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

import javassist.NotFoundException;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.TestMaker;

public class FichaProducto {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	
	
	@Test (
		groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
	public void FIC001_FichaFromSearch_PrimaryFeatures_Reg() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		DataCtxShop dCtxSh = getCtxShForTest(españa, castellano, true, userShop.user, userShop.password);

		AccesoStpV.oneStep(dCtxSh, true, driver);
		SecBuscadorStpV secBuscadorStpV = new SecBuscadorStpV(dCtxSh.appE, dCtxSh.channel, driver);
		PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh.pais.getCodigo_alf(), dCtxSh.appE, driver)
				.numProducts(80)
				.build();
		
		List<FilterType> filterOnline = Arrays.asList(FilterType.Online);
		Optional<Garment> articleOnline = getterProducts.getOneFiltered(filterOnline);
		if (!articleOnline.isPresent()) {
			throw new NotFoundException("Not found article of type " + filterOnline);
		}
		secBuscadorStpV.searchArticulo(articleOnline.get(), dCtxSh.pais, filterOnline);
		pageFichaStpv.checkLinkDispTiendaInvisible();
		
		List<FilterType> filterNoOnlineWithColors = Arrays.asList(FilterType.NoOnline, FilterType.ManyColors); 
		Optional<Garment> articleNoOnlineWithColors = getterProducts.getOneFiltered(filterNoOnlineWithColors);
		if (!articleNoOnlineWithColors.isPresent()) {
			List<String> filtersLabels = filterNoOnlineWithColors.stream().map(Object::toString).collect(Collectors.toList());
			throw new NotFoundException("Not found article with filters " + String.join(",", filtersLabels));
		}
		
		secBuscadorStpV.searchArticulo(articleNoOnlineWithColors.get(), dCtxSh.pais, filterNoOnlineWithColors);
		boolean isTallaUnica = pageFichaStpv.selectAnadirALaBolsaTallaPrevNoSelected();
		ArticuloScreen articulo = new ArticuloScreen(articleNoOnlineWithColors.get());
		pageFichaStpv.selectColorAndSaveData(articulo);
		pageFichaStpv.selectTallaAndSaveData(articulo);

		//Si es talla única -> Significa que lo dimos de alta en la bolsa cuando seleccionamos el click "Añadir a la bolsa"
		//-> Lo damos de baja
		if (isTallaUnica) {
			SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
			secBolsaStpV.clear();
		}

		articulo = pageFichaStpv.getFicha().getArticuloObject();
		pageFichaStpv.selectBuscarEnTiendaButton();
		new ModalBuscadorTiendasStpV(dCtxSh.channel, driver).close();
		if (dCtxSh.appE==AppEcom.shop) {
			pageFichaStpv.selectAnadirAFavoritos();
			pageFichaStpv.changeColorGarment();
			pageFichaStpv.selectRemoveFromFavoritos();
		}

		pageFichaStpv.selectAnadirALaBolsaTallaPrevSiSelected(articulo, dCtxSh);
	}

	@SuppressWarnings("static-access")
	@Test (
		groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones, total look")
	public void FIC002_FichaFromSearch_SecondaryFeatures_NoReg() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest(españa, castellano);

		AccesoStpV.oneStep(dCtxSh, false, driver);
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh.pais.getCodigo_alf(), dCtxSh.appE, driver).build();
		Optional<Garment> articleWithTotalLook = getterProducts.getOneFiltered(FilterType.TotalLook);
		SecBuscadorStpV secBuscadorStpV = new SecBuscadorStpV(dCtxSh.appE, dCtxSh.channel, driver);
		if (!articleWithTotalLook.isPresent()) {
			throw new NotFoundException("Not found article of type " + FilterType.TotalLook);
		}
		secBuscadorStpV.searchArticulo(articleWithTotalLook.get(), dCtxSh.pais);
		
		PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
			if (dCtxSh.appE==AppEcom.outlet && dCtxSh.channel!=Channel.mobile) {
				pageFichaStpV.validaExistsImgsCarruselIzqFichaOld();
			}
			pageFichaStpV.secProductDescOld.validateAreInStateInitial(dCtxSh.appE);
			PageFicha pageFicha = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE, driver);
			if (((PageFichaArtOld)pageFicha).getNumImgsCarruselIzq() > 2) {
				pageFichaStpV.selectImgCarruselIzqFichaOld(2);
			}
			
			if (dCtxSh.channel!=Channel.tablet) {
				pageFichaStpV.selectImagenCentralFichaOld();
				//if (dCtxSh.appE!=AppEcom.outlet) {
				if (dCtxSh.channel.isDevice()) {
					pageFichaStpV.closeZoomImageCentralDevice();
				}
			}
			if (TypePanel.Description.getListApps().contains(dCtxSh.appE) &&
				!dCtxSh.channel.isDevice()) {
				pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Description);
			}
			if (TypePanel.Composition.getListApps().contains(dCtxSh.appE)) {
				pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Composition);
			}
			if (TypePanel.Returns.getListApps().contains(dCtxSh.appE)) {
				pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Returns);
			}
			if (TypePanel.Shipment.getListApps().contains(dCtxSh.appE) &&
				!dCtxSh.channel.isDevice()) {
				pageFichaStpV.secProductDescOld.selectPanel(TypePanel.Shipment);  
			}
		} else {
			boolean isFichaAccesorio = pageFichaStpV.getFicha().isFichaAccesorio(); 
			pageFichaStpV.secFotosNew.validaLayoutFotosNew(isFichaAccesorio, driver);
			pageFichaStpV.secTotalLook.checkIsVisible(driver);

			pageFichaStpV.secBolsaButtonAndLinksNew.selectEnvioYDevoluciones(driver);
			pageFichaStpV.getModEnvioYdevol().clickAspaForClose();
			
			pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.she, driver);
			pageFichaStpV.secBolsaButtonAndLinksNew.selectLinkCompartir(dCtxSh.pais.getCodigo_pais(), driver);

		}
			
		pageFichaStpV.selectGuiaDeTallas(dCtxSh.appE);
		if (dCtxSh.appE==AppEcom.shop) {
			pageFichaStpV.validateSliderIfExists(Slider.ElegidoParaTi);
		}
		
		if (dCtxSh.appE!=AppEcom.outlet) {
			pageFichaStpV.validateSliderIfExists(Slider.CompletaTuLook);
		}
	}
	
	@SuppressWarnings("static-access")
	@Test (
		groups={"FichaProducto", "Canal:desktop_App:shop"}, alwaysRun=true, 
		description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
	public void FIC003_FichaFromGalery_CheckKcSafety() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		Pais corea = PaisGetter.get(PaisShop.CoreaDelSur);
		DataCtxShop dCtxSh = getCtxShForTest(corea, corea.getListIdiomas().get(0));
	 
		AccesoStpV.oneStep(dCtxSh, false, driver);
		
		//TODO en el acceso se ejecuta la función setInitialModalsOff para evitar la aparición de modales
		//pero en el caso de Corea se escapa el de Subscripción en la Newsletter
		(new PagePrehome(dCtxSh, driver)).closeModalNewsLetterIfExists();
		
		Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(
			dCtxSh.appE, KeyMenu1rstLevel.from(
				LineaType.nina, 
				SublineaType.nina_nina, "pantalones"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh);

		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		LocationArticle location1rstArticle = LocationArticle.getInstanceInCatalog(1);
		DataFichaArt dataArtOrigin = pageGaleriaStpV.selectArticulo(location1rstArticle, dCtxSh);
		
		PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		if (pageFichaStpV.getFicha().getTypeFicha()==TypeFicha.Old) {
			if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
				pageFichaStpV.secProductDescOld.selectPanel(TypePanel.KcSafety);
			}
		} else {
			if (TypePanel.KcSafety.getListApps().contains(dCtxSh.appE)) {
				pageFichaStpV.secBolsaButtonAndLinksNew.selectDetalleDelProducto(dCtxSh.appE, LineaType.nina, driver);
			}
		}
		
		pageFichaStpV.selectLinkNavigation(ProductNav.Next, dCtxSh, dataArtOrigin.getReferencia());
		pageFichaStpV.selectLinkNavigation(ProductNav.Prev, dCtxSh, dataArtOrigin.getReferencia());
	}
	
	@Test (
		groups={"FichaProducto", "Canal:desktop,mobile_App:shop"}, 
		alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
	public void FIC005_Articulo_Personalizable_Noreg() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest(españa, castellano);

		AccesoStpV.oneStep(dCtxSh, false, driver);
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		Menu1rstLevel menuPersonalizacion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.he, null, "personalizacion"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		
		//secMenusStpV.checkExistMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
		secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
		LocationArticle articleNum = LocationArticle.getInstanceInCatalog(1);
		pageGaleriaStpV.selectArticulo(articleNum, dCtxSh);
		
		PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		SecModalPersonalizacionStpV modalPersonalizacionStpV = SecModalPersonalizacionStpV.getNewOne(dCtxSh, driver); 
		int numColors = pageFichaStpv.getFicha().getNumColors();
		for (int i=1; i<=numColors; i++) {
			pageFichaStpv.selectColor(i);
			State levelError = (i==numColors) ? State.Defect : State.Info;
			if (modalPersonalizacionStpV.checkArticleCustomizable(levelError)) {
				break;
			}
		}
		
		pageFichaStpv.selectFirstTallaAvailable();
		modalPersonalizacionStpV.selectLinkPersonalizacion();
		//modalPersonalizacionStpV.startCustomization();
		modalPersonalizacionStpV.selectIconCustomization();
		modalPersonalizacionStpV.selectFirstIcon();
		modalPersonalizacionStpV.validateIconSelectedDesktop();
		modalPersonalizacionStpV.selectConfirmarButton();
		modalPersonalizacionStpV.validateCabeceraStep(2);
		modalPersonalizacionStpV.validateWhereDesktop();
		modalPersonalizacionStpV.selectConfirmarButton();
		modalPersonalizacionStpV.validateCabeceraStep(3);
		modalPersonalizacionStpV.validateSelectionColor();
 
		modalPersonalizacionStpV.selectSize();
		modalPersonalizacionStpV.confirmCustomization();
		modalPersonalizacionStpV.checkCustomizationProof();
	}
	
	private DataCtxShop getCtxShForTest(Pais pais, IdiomaPais idioma) {
		return getCtxShForTest(pais, idioma, false, "", "");
	}
	
	private DataCtxShop getCtxShForTest(Pais pais, IdiomaPais idioma, boolean userRegistered, String user, String password) {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais=pais;
		dCtxSh.idioma=idioma;
		if (userRegistered) {
			dCtxSh.userRegistered = false;
			dCtxSh.userConnected = user;
			dCtxSh.passwordUser = password;
		}
		return dCtxSh;
	}
}