package com.mng.robotest.tests.domains.galeria.steps;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.ControlTemporada;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterCollection;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.testslegacy.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.testslegacy.pageobject.utils.DataArticleGalery;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;
import com.mng.robotest.testslegacy.pageobject.utils.DataScroll;
import com.mng.robotest.testslegacy.pageobject.utils.ListDataArticleGalery;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecBannerHeadGallery.TypeLinkInfo.*;

public class PageGaleriaSteps extends StepBase {

	public final SecCrossSellingSteps secCrossSellingSteps = new SecCrossSellingSteps();
	public final BannerHeadGallerySteps bannerHead = new BannerHeadGallerySteps(this);
	private final SecSelectorPreciosSteps secSelectorPreciosSteps = new SecSelectorPreciosSteps();
	private final PageGaleria pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());

	public enum TypeGalery { SALES, NO_SALES }
	public enum TypeActionFav { MARCAR, DESMARCAR }

	public SecSelectorPreciosSteps getSecSelectorPreciosSteps() {
		return secSelectorPreciosSteps;
	}

	@Step (
		description="Seleccionamos el artículo #{locationArt} en una pestaña aparte", 
		expected="Aparece la ficha del artículo seleccionado en una pestaña aparte")
	public void selectArticuloEnPestanyaAndBack(LocationArticle locationArt) {
		String galeryWindowHandle = driver.getWindowHandle();
		var datosArticulo = new DataFichaArt();

		//Almacenamos el nombre del artículo y su referencia
		WebElement articulo = pageGaleria.getArticulo(locationArt);
		datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pageGaleria.getRefArticulo(articulo));

		String detailWindowHandle = pageGaleria.openArticuloPestanyaAndGo(articulo);
		new PageFichaSteps().checkDetallesProducto(datosArticulo);

		if (detailWindowHandle.compareTo(galeryWindowHandle)!=0) {
			//Cerramos la pestaña y cambiamos a la ventana padre
			driver.switchTo().window(detailWindowHandle);
			driver.close();
			driver.switchTo().window(galeryWindowHandle);
		}
	}

	@Step (
		description="Seleccionar el artículo #{locationArt}", 
		expected="Aparece la ficha del artículo seleccionado")
	public DataFichaArt selectArticulo(LocationArticle locationArt) {
		var datosArticulo = new DataFichaArt();
		String urlGaleria = driver.getCurrentUrl();
		
		//Almacenamos el nombre del artículo y su referencia
		var articulo = pageGaleria.getArticulo(locationArt);
		datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pageGaleria.getRefArticulo(articulo));

		pageGaleria.clickArticulo(articulo);
		var pageFichaSteps = new PageFichaSteps();
		pageFichaSteps.checkDetallesProducto(datosArticulo);
		pageFichaSteps.validaPrevNext(locationArt);

		pageFichaSteps.validaBreadCrumbFicha(urlGaleria);
		
		return (datosArticulo);
	}

	public void showTallasArticulo(int posArticulo) {
		if (channel.isDevice()) {
			showTallasDevice(posArticulo);
		} else {
			showTallasDesktop(posArticulo);
		}
	}

	@Step (
		description="Posicionarse sobre el artículo en la posición <b>#{posArticulo}</b>, esperar que aparezca el link \"Añadir\" y seleccionarlo", 
		expected="Aparece la capa con la información de las tallas")
	private void showTallasDevice(int posArticulo) {
		showTallas(posArticulo);
	}
	
	@Step (
		description="Posicionarse sobre el artículo en la posición <b>#{posArticulo}</b>", 
		expected="Aparece la capa con la información de las tallas")
	private void showTallasDesktop(int posArticulo) {
		showTallas(posArticulo);
	}
	
	private void showTallas(int posArticulo) {
		pageGaleria.showTallasArticulo(posArticulo);
		checkIsVisibleCapaInfoTallas(posArticulo, 1);
	}
	
	@Validation (
		description="Aparece la capa con la información de las tallas " + SECONDS_WAIT,
		level=Warn)
	private boolean checkIsVisibleCapaInfoTallas(int posArticulo, int seconds) {
		return pageGaleria.isVisibleArticleCapaTallasUntil(posArticulo, seconds);
	}

	public void selectTallaAvailable() throws Exception {
		int posArticulo=1;
		boolean articleAvailable = false;
		while (!articleAvailable && posArticulo<5) {
			showTallasArticulo(posArticulo);
			try {
				articleAvailable = selectTallaAvailableArticulo(posArticulo);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem selecting first talla available from article " + posArticulo, e);
			}
			posArticulo+=1;
		}
	}	
	
	@Step (
		description="Del #{posArticulo}o artículo, seleccionamos la 1a talla disponible", 
		expected="Se da de alta correctamente el artículo en la bolsa")
	public boolean selectTallaAvailableArticulo(int posArticulo) throws Exception {
		var articulo = pageGaleria.selectTallaAvailableArticle(posArticulo);
		boolean tallaVisible = (articulo!=null);
		if (tallaVisible) {
			dataTest.getDataBag().addArticulo(articulo);
			new SecBolsaSteps().validaAltaArtBolsa();
		}
		return tallaVisible;
	}

	@Step (
		description="Seleccionamos la primera talla no disponible del listado",
		expected="Se abre el modal de avimase de la prenda",
		saveHtmlPage=SaveWhen.Always)
	public void selectTallaNoDisponibleArticulo() {
		pageGaleria.selectTallaArticleNotAvalaible();
	}
	
	public DataScroll scrollFromFirstPage() throws Exception {
		var data = new DataForScrollStep();
		data.setNumPageToScroll(99);
		data.setOrdenacionExpected(RECOMENDADOS);
		return scrollFromFirstPage(data);
	}
	
	/**
	 * Escrollamos hasta llegar a la página indicada en toPage
	 * @param toPage indica el número de página en el que nos queremos posicionar. Si es PageGaleria.scrollToLast asumimos que queremos llegar hasta el final del catálogo
	 */
	private static final String TAG_ID_PAGE = "@TagIdPage";
	
	@Step (
		description="Escrollar hasta posicionarse en la " + TAG_ID_PAGE + " página", 
		expected="Se escrolla correctamente",
		saveNettraffic=SaveWhen.Always)
	public DataScroll scrollFromFirstPage(DataForScrollStep dataForScroll) throws Exception {

		DataScroll datosScroll = null;
		int pageToScroll = dataForScroll.getNumPageToScroll();
		if (channel.isDevice()) {
			pageToScroll = 3;
		}
		
		String idPage = pageToScroll + "a";
		if (pageToScroll>=PageGaleria.MAX_PAGE_TO_SCROLL) {
			idPage = "última";
		}
		replaceStepDescription(TAG_ID_PAGE, idPage);
		int numArticulosInicio = pageGaleria.getNumArticulos();
		datosScroll = pageGaleria.scrollToPageFromFirst(pageToScroll);

		if (pageToScroll>=PageGaleria.MAX_PAGE_TO_SCROLL) {
			checkVisibilityFooter(pageToScroll, app);
		}
		if (pageToScroll < PageGaleria.MAX_PAGE_TO_SCROLL) {
			checkAreMoreArticlesThatInitially(datosScroll.getArticulosMostrados(), numArticulosInicio);
		}
		if (dataForScroll.getOrdenacionExpected()!=RECOMENDADOS) {
			checkArticlesOrdered(dataForScroll.getOrdenacionExpected());
		}
		checkNotRepeatedArticles();
		if (dataForScroll.isValidateArticlesExpected()) {
			checkNumArticlesInScreen(datosScroll.getArticulosTotalesPagina(), dataForScroll.getNumArticlesExpected());
		}
		
		checksDefault();
		if (dataForScroll.isValidaImgBroken()) {
			checksGeneric().imgsBroken().execute();
		}
		
		datosScroll.setStep(getCurrentStep());
		return datosScroll;
	}

	@Validation (
		description="Sí aparece el footer",
		level=Warn)
	private boolean checkVisibilityFooter(int pageToScroll, AppEcom app) {
		return (new SecFooter()).isVisible();
	}

	@Validation (
		description=
			"En pantalla aparecen más artículos (#{numArticlesCurrently}) " + 
			"de los que había inicialmente (#{numArticlesInit})",
		level=Warn)
	private boolean checkAreMoreArticlesThatInitially(int numArticlesCurrently, int numArticlesInit) {
		return (numArticlesCurrently > numArticlesInit);
	}

	@Validation (description="Los artículos aparecen ordenados por #{orderExpected}")
	private boolean checkArticlesOrdered(FilterOrdenacion orderExpected) throws Exception {
		return (pageGaleria.articlesInOrder(orderExpected));
	}
	
	@Validation
	private ChecksTM checkNotRepeatedArticles() {
		var checks = ChecksTM.getNew();
		var productsRepeated = pageGaleria.searchArticleRepeatedInGallery();
		String producRepeatedWarning = "";
		if (productsRepeated!=null && !productsRepeated.isEmpty()) {
			producRepeatedWarning+=
				"<br><b style=\"color:" + Warn.getColorCss() + "\">Warning!</b>: " + 
				"hay " + productsRepeated.size() + " productos repetidos, " + 
				"por ejemplo el <b>" + productsRepeated.get(0).toString();
		}
	  	checks.add(
			"No aparece ningún artículo repetido" + producRepeatedWarning,
			productsRepeated==null || productsRepeated.isEmpty());
	  	
	  	return checks;
	}

	@Validation (
		description=
			"En pantalla aparecen exactamente #{numArticlesInPage} artículos " + 
			"(están apareciendo #{numArticlesExpected}",
		level=Info)
	private boolean checkNumArticlesInScreen(int numArticlesInPage, int numArticlesExpected) {
		return (numArticlesInPage==numArticlesExpected);
	}

	public int seleccionaOrdenacionGaleria(FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria) 
			throws Exception {
		return seleccionaOrdenacionGaleria(typeOrdenacion, tipoPrendasGaleria, -1);
	}
	
	@Step (
		description="Seleccionar la ordenación #{typeOrdenacion}", 
		expected="Los artículos se ordenan correctamente")
	public int seleccionaOrdenacionGaleria(
			FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria, int numArticulosValidar) throws Exception {
		
		var secFiltros = SecFiltros.make(channel, app, dataTest.getPais());
		secFiltros.selecOrdenacionAndReturnNumArticles(typeOrdenacion);

		checkIsVisiblePageWithTitle(tipoPrendasGaleria);
		int numArticulosPant = pageGaleria.getNumArticulos() + pageGaleria.getNumArticulos();
		checkOrderListArticles(typeOrdenacion, numArticulosPant, numArticulosValidar);
 
		checksDefault();
	   
		return numArticulosPant;
	}

	@Validation (
		description="Aparece una pantalla en la que el title contiene <b>#{tipoPrendasGaleria}",
		level=Warn)
	private boolean checkIsVisiblePageWithTitle(String tipoPrendasGaleria) {
		return (driver.getTitle().toLowerCase().contains(tipoPrendasGaleria));
	}
	
	@Validation
	private ChecksTM checkOrderListArticles(FilterOrdenacion typeOrdenacion, int numArticulosPant, int numArticulosValidar) 
	throws Exception {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Aparecen > 1 prendas",
			numArticulosPant > 1, Warn);
		if (numArticulosValidar>=0) {
		  	checks.add(
				"Aparecen " + numArticulosValidar + " artículos",
				numArticulosValidar==numArticulosPant, Info);
		}
	  	checks.add(
			"Los artículos aparecen ordenados por " + typeOrdenacion.name(),
			pageGaleria.articlesInOrder(typeOrdenacion), Warn);
	  	
	  	return checks;
	}

	@Step (
		description="Volver al 1er artículo de la galería (mediante selección del icono de la flecha Up)", 
		expected="Se visualiza el 1er elemento")
	public void backTo1erArticleMobilStep() throws Exception {
		pageGaleria.backTo1erArticulo();
		checkBackTo1ersElementOk();
	}

	@Validation
	private ChecksTM checkBackTo1ersElementOk() {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Es clickable el 1er elemento de la lista",
			pageGaleria.isClickableArticuloUntil(1, 0), Warn);
	  	
		SecFiltros secFiltros = SecFiltros.make(channel, app, dataTest.getPais());
		int seconds = 2;
	  	checks.add(
			"Es clickable el bloque de filtros " + getLitSecondsWait(seconds),
			secFiltros.isClickableFiltroUntil(seconds), Warn);
	  	
	  	return checks;
	}

	/**
	 * Pasos/Validaciones consistentes en seleccionar un determinado color de un determinado artículo
	 * @param posColor posición del color en la lista de colores del artículo
	 * @param numArtConColores posición del artículo entre el conjunto de artículos con variedad de colores
	 * @return src de la imagen obtenida al ejecutar el click sobre el color
	 */
	private static final String TAG_SRC_PNG_2O_COLOR = "@srcPng2oColor";
	private static final String TAG_NOMBRE_1ER_ARTIC = "@nombre1erArt";
	private static final String TAG_PRECIO_1ER_ARTIC = "@precio1erArt";
	
	@Step (
		description=
			"Seleccionar el #{posColor}o color (" + TAG_SRC_PNG_2O_COLOR +") no seleccionado del #{numArtConColores}o " + 
			"artículo con variedad de colores (" + TAG_NOMBRE_1ER_ARTIC + ", " + TAG_PRECIO_1ER_ARTIC +")", 
		expected="Se selecciona el color")
	public String selecColorFromArtGaleriaStep(int numArtConColores, int posColor) {
		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		waitForPageLoaded(driver, 2);

		var articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		replaceStepExpected(TAG_NOMBRE_1ER_ARTIC, pageGaleria.getNombreArticulo(articuloColores));
	   
		var colorToClick = pageGaleria.getColorArticulo(articuloColores, false, posColor);
		replaceStepExpected(TAG_PRECIO_1ER_ARTIC, pageGaleria.getPrecioArticulo(articuloColores));
	   
		String srcImg1erArt = pageGaleria.getImagenArticulo(articuloColores);
		replaceStepDescription(TAG_SRC_PNG_2O_COLOR, colorToClick.getAttribute("src"));
	   
		click(colorToClick).exec();
		waitMillis(100);
		
		String srcImgAfterClickColor = pageGaleria.getImagenArticulo(articuloColores);
		checkImageIsModified(srcImg1erArt, srcImgAfterClickColor);
	   
		return srcImgAfterClickColor;
	}

	@Validation (description="Se modifica la imagen correspondiente al artículo")
	private boolean checkImageIsModified(String srcImg1erArt, String srcImgAfterClickColor) {
		return (!srcImgAfterClickColor.contains(srcImg1erArt));
	}
	
	/**
	 * @return src de la imagen obtenida al ejecutar los clicks
	 */
	public String clicksSliderArticuloConColores(int numArtConColores, TypeSlider... typeSliderList)
			throws Exception {
		return clicksSliderArticuloConColores(numArtConColores, "", typeSliderList);
	}
   
	/**
	 * @param srcImageExpected el src esperado para la imagen resultante de la secuencia de clicks en el slider. Si tiene valor "" no aplicamos validación
	 */
	private static final String TAG_NOMBRE_ART = "@TagNombreArt";
	private static final String TAG_SLIDER_LIST = "@TagSliderList";
	
	@Step (
		description=
			"Clickar la siguiente secuencia de sliders: <b>" + TAG_SLIDER_LIST + "</b> del #{numArtConColores}o " + 
			" artículo con variedad de colores (" + TAG_NOMBRE_ART + "). Previamente realizamos un \"Hover\" sobre dicho artículo", 
		expected="Aparece el artículo original(" + TAG_NOMBRE_ART + ")")
	public String clicksSliderArticuloConColores(int numArtConColores, String srcImageExpected, TypeSlider... typeSliderList) 
			throws Exception {
		if (channel!=Channel.desktop) {
			throw new RuntimeException("Method clickSliderArticuloConColores doesn't support channel " + channel);
		}

		String slidersListStr = getStringSliderList(typeSliderList);
		replaceStepDescription(TAG_SLIDER_LIST, slidersListStr);

		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax.
		//En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		PageObjTM.waitForPageLoaded(driver, 2);
		var pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		var articuloColores = pageGaleriaDesktop.getArticuloConVariedadColoresAndHover(numArtConColores);
		
		replaceStepDescription(TAG_NOMBRE_ART, pageGaleria.getNombreArticulo(articuloColores));
		replaceStepExpected(TAG_NOMBRE_ART, pageGaleria.getNombreArticulo(articuloColores));
		
		String srcImg1erSlider = pageGaleria.getImagenArticulo(articuloColores);
		pageGaleriaDesktop.clickSliders(articuloColores, typeSliderList);

		String srcImg2oSlider = pageGaleria.getImagenArticulo(articuloColores);
		checkImageSliderArticleHasChanged(srcImg1erSlider, srcImg2oSlider, typeSliderList.length);
		if ("".compareTo(srcImageExpected)!=0) {
			checkActualImgSliderIsTheExpected(srcImg2oSlider, srcImageExpected);
		}
		return srcImg2oSlider;
	}

	@Validation
	private ChecksTM checkImageSliderArticleHasChanged(String srcImg1erSlider, String srcImg2oSlider, int numClicks) {
		State state = Defect;
		if (numClicks>1) {
			//Si hemos realizado varios clicks y sólo hay 2 imágenes habremos vuelto a la inicial
			state = Warn;
		}
		var checks = ChecksTM.getNew();
		checks.add(
			"Se modifica la imagen asociada al artículo (<b>antes</b>: " + srcImg1erSlider + ", <b>ahora</b>: " + srcImg2oSlider,
			srcImg2oSlider.compareTo(srcImg1erSlider)!=0, state);
		
		return checks;
   }
   
   @Validation (
	   description="El src de la imagen <b>ahora</b> (#{srcImgActual}) es la <b>original</b> (#{srcImgOriginalExpected})")
   private boolean checkActualImgSliderIsTheExpected(String srcImgActual, String srcImgOriginalExpected) {
	   return (srcImgActual.compareTo(srcImgOriginalExpected)==0);
   }
	
   private static String getStringSliderList(TypeSlider... typeSliderList) {
	   String listStr = "";
	   for (TypeSlider typeSlider : typeSliderList) {
		   listStr+=(typeSlider + ", ");
	   }
	   return listStr;
   }
	
   	private static final String TAG_NUM_ART_CON_COLORES = "@TagNumArtConColores";
   	private static final String TAG_NOMBRE_1ER_ART = "@TagNombre1erArt";
   	private static final String TAG_PRECIO_1ER_ART = "@TagPrecio1erArt";
   	
	@Step (
		description="Seleccionar el " + TAG_NUM_ART_CON_COLORES + "o artículo con variedad de colores (" + TAG_NOMBRE_1ER_ART + " " + TAG_PRECIO_1ER_ART + ")", 
		expected="Aparece el artículo original(" + TAG_NOMBRE_1ER_ART + " " + TAG_PRECIO_1ER_ART + ")",
		saveNettraffic=SaveWhen.Always)
	public void selecArticuloGaleriaStep(int numArtConColores) {
		WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		String nombre1erArt = pageGaleria.getNombreArticulo(articuloColores);
		String precio1erArt = pageGaleria.getPrecioArticulo(articuloColores);
		replaceStepDescription(TAG_NUM_ART_CON_COLORES, String.valueOf(numArtConColores));
		replaceStepDescription(TAG_NOMBRE_1ER_ART, nombre1erArt);
		replaceStepDescription(TAG_PRECIO_1ER_ART, precio1erArt);
		replaceStepExpected(TAG_NOMBRE_1ER_ART, nombre1erArt);
		replaceStepExpected(TAG_PRECIO_1ER_ART, precio1erArt);

		pageGaleria.clickArticulo(articuloColores);
		int seconds = 3;
		checkIsFichaArticle(nombre1erArt, precio1erArt, seconds);

		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}

	@Validation
	private ChecksTM checkIsFichaArticle(String nombre1erArt, String precio1erArt, int seconds) {
		var checks = ChecksTM.getNew();

		PageFicha pageFicha = PageFicha.of(channel);
	  	checks.add(
			"Aparece la página de ficha " + getLitSecondsWait(seconds),
			pageFicha.isPageUntil(seconds), Warn);
	  	
		String nombreArtFicha = pageFicha.getSecDataProduct().getTituloArt();
		String precioArtFicha = pageFicha.getSecDataProduct().getPrecioFinalArticulo();
	  	checks.add(
	  	    Check.make(
			    "Aparece el artículo anteriormente seleccionado: <br>\" +\n" + 
			    "   - Nombre " + nombre1erArt + "<br>" + 
			    "   - Precio " + precio1erArt,
			    nombreArtFicha.toUpperCase().contains(nombre1erArt.toUpperCase()) &&
			    precioArtFicha.replace(" ", "").toUpperCase().contains(precio1erArt.replace(" ", "").toUpperCase()),
			    Info)
	  	    .store(StoreType.None).build());
		
	  	return checks;
	}

	@Validation (
		description="Existe algún vídeo en la galería",
		level=Warn)
	public boolean validaHayVideoEnGaleria() {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		return (pageGaleriaDesktop.isPresentAnyArticle(TypeArticleDesktop.VIDEO));
	}

	@Validation (
		description="Aparece una página con artículos " + SECONDS_WAIT,
		level=Warn)
	public boolean validaArtEnContenido(int seconds) {
		return (pageGaleria.isVisibleArticleUntil(1, seconds));
	}

	private static final String TAG_POS_ICONS = "@TagPosIcons";
	private static final String TAG_ESTADO_FINAL = "@TagEstadoFinal";
	@Step (
		description="Seleccionamos (para <b>#{actionFav}</b>) los \"Hearth Icons\" asociados a los artículos <b>" + TAG_POS_ICONS + "</b>", 
		expected="Los \"Hearth Icons\" quedan " + TAG_ESTADO_FINAL)
	public void clickArticlesHearthIcons(TypeActionFav actionFav, Integer... posIconsToClick) 
			throws Exception {
		replaceStepDescription(TAG_POS_ICONS, Arrays.toString(posIconsToClick));
		
		var listAddFav = pageGaleria.clickArticleHearthIcons(posIconsToClick);
		String estadoFinal = "";
		if (actionFav==TypeActionFav.MARCAR) {
				estadoFinal = "Marcados";
				dataTest.getDataFavoritos().addToLista(listAddFav);
		} else {
				estadoFinal = "Desmarcados";
				dataTest.getDataFavoritos().removeFromLista(listAddFav);
		}
		
		replaceStepExpected(TAG_ESTADO_FINAL, estadoFinal);
		checkIconosInCorrectState(actionFav, estadoFinal, posIconsToClick);
	}

	@Validation
	private ChecksTM checkIconosInCorrectState(TypeActionFav actionFav, String estadoFinal, Integer... posIconsSelected) {
		var checks = ChecksTM.getNew();
   		checks.add(
			"Quedan " + estadoFinal + " los iconos asociados a los artículos con posiciones <b>" + Arrays.toString(posIconsSelected) + "</b>",
   	   		pageGaleria.iconsInCorrectState(actionFav, posIconsSelected), Warn);
		return checks;
	}

	public ListDataArticleGalery selectListadoXColumnasDesktop(
			NumColumnas numColumnas, ListDataArticleGalery listArticlesGaleriaAnt){
		selectListadoXColumnasDesktop(numColumnas);
		var listArticlesGaleriaAct = pageGaleria.getListDataArticles();
		if (listArticlesGaleriaAnt!=null) {
			int articulosComprobar = 20;
			checkArticlesEqualsToPreviousGalery(articulosComprobar, listArticlesGaleriaAnt, listArticlesGaleriaAct, numColumnas);
		}
		return listArticlesGaleriaAct;
	}

	@Step (
		description="Seleccionar el link del listado a <b>#{numColumnas.name()} columnas</b>", 
		expected="Aparece un listado de artículos a #{numColumnas.name()} columnas")
	public void selectListadoXColumnasDesktop(NumColumnas numColumnas)	{
		((PageGaleriaDesktop)pageGaleria).clickLinkColumnas(numColumnas);
		checkIsVisibleLayoutListadoXcolumns(numColumnas);
	}

	@Validation
	private ChecksTM checkArticlesEqualsToPreviousGalery(
		int articulosComprobar, ListDataArticleGalery listArticlesGaleriaAnt, 
		ListDataArticleGalery listArticlesGaleriaAct, NumColumnas numColumnas) {
   		var checks = ChecksTM.getNew();
   		
   		boolean articlesEquals = listArticlesGaleriaAct.isArticleListEquals(listArticlesGaleriaAnt, articulosComprobar);
   		String infoWarning = "";
   		if (!articlesEquals) {
   			DataArticleGalery articleGaleryActualNotFit = listArticlesGaleriaAct.getFirstArticleThatNotFitWith(listArticlesGaleriaAnt);
   			infoWarning+="<br><b style=\"color:" + Info.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería anterior (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
   			infoWarning+=listArticlesGaleriaAct.getTableHTLMCompareArticlesGaleria(listArticlesGaleriaAnt);
   		}
   		checks.add(
   			"Los primeros " + articulosComprobar + " artículos de la galería a " + 
   			numColumnas.name() + " columnas son iguales a los de la anterior galería" + infoWarning,
   			articlesEquals, Info);
   		
   		return checks;
	}

	@Validation (
		description="Aparece el layout correspondiente al listado a <b>#{numColumnas.name()} columnas</b>",
		level=Warn)
	private boolean checkIsVisibleLayoutListadoXcolumns(NumColumnas numColumnas) {
		return (pageGaleria.getLayoutNumColumnas()==((PageGaleriaDesktop)pageGaleria).getNumColumnas(numColumnas));
	}

	@Validation (
		description="Estamos en la página de Galería",
		level=Warn)
	private boolean checkIsPageGaleria(WebDriver driver) {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		return (pageGaleriaDesktop.isPage());
	}

	@Validation
	private ChecksTM checkFiltrosSalesOnInGalerySale(SecMenusFiltroCollection filtrosCollection) {
		var checks = ChecksTM.getNew();
	 	checks.add(
	 		"<b style=\"color:blue\">Rebajas</b></br>" +
	 		"Son visibles los menús laterales de filtro a nivel detemporadas (Collection)",
	 		filtrosCollection.isVisible());
	 	
	 	checks.add(
	 		"Aparece el filtro para todas las temporadas <b>All</b>)",
	 		filtrosCollection.isVisibleMenu(FilterCollection.all), Warn);
	 	
	 	checks.add(
	 		"Aparece el filtro para las ofertas <b>Sale</b>",
	 		filtrosCollection.isVisibleMenu(FilterCollection.sale), Warn);
	 	
	 	checks.add(
	 		"Aparece el filtro para las ofertas <b>Sale</b>",
	 		filtrosCollection.isVisibleMenu(FilterCollection.sale), Warn);
	 	
	 	checks.add(
	 	    Check.make(
	 		    "Aparece el filtro para la nueva temporada <b>Next season preview</b>",
	 		    filtrosCollection.isVisibleMenu(FilterCollection.nextSeason), Info)
	 	    .store(StoreType.None).build());
	 	
	 	return checks;
	}

	@Validation (
		description=
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"No son visibles los menús laterales de filtro a nivel detemporadas (Collection)<b>")
	private boolean checkFiltrosSaleInGaleryNoSale(SecMenusFiltroCollection filtrosCollection) {
		return (!filtrosCollection.isVisible());
	}

	@Validation (
		description=
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"No aparece el filtro para las ofertas <b>Sale</b>",
		level=Warn)
	private boolean checkFiltrosSalesOff(SecMenusFiltroCollection filtrosCollection) {
		return (!filtrosCollection.isVisibleMenu(FilterCollection.sale));
	}

	public void validaArticlesOfTemporadas(List<Integer> listTemporadas, State levelError, StoreType store) {
		validaArticlesOfTemporadas(listTemporadas, false, levelError, store);
	}

	public void validaArticlesOfTemporadas(List<Integer> listTemporadas) {
		validaArticlesOfTemporadas(listTemporadas, false, Warn, StoreType.Evidences);
	}

	public void validaArticlesOfTemporadas(List<Integer> listTemporadas, boolean validaNotNewArticles) {
		validaArticlesOfTemporadas(listTemporadas, validaNotNewArticles, Warn, StoreType.Evidences);
	}

	@Validation
	public ChecksTM validaArticlesOfTemporadas(
			List<Integer> listTemporadas, boolean validaNotNewArticles, State levelError, StoreType store) {
		var checks = ChecksTM.getNew();
		var pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		var listArtWrong = pageGaleriaDesktop.getArticlesTemporadasX(ControlTemporada.ARTICLES_FROM_OTHER, listTemporadas);
		if (validaNotNewArticles) {
			listArtWrong = PageGaleria.getNotNewArticlesFrom(listArtWrong);
		}
		
		String validaNotNewArticlesStr = "";
		if (validaNotNewArticles) {
			validaNotNewArticlesStr = " y no contienen alguna de las etiquetas de artículo nuevo (" + PageGaleria.getListlabelsnew() + ")";
		}
		
		String infoWarning = "";
		if (!listArtWrong.isEmpty()) {
			infoWarning+=
				"<br><lin style=\"color:" + Warn.getColorCss() + ";\"><b>Warning!</b>: " +
				"hay " + listArtWrong.size() + " artículos que no pertenecen a las temporadas " + listTemporadas + ":<br>";
			for (String nameWrong : listArtWrong) {
				infoWarning+=(nameWrong + "<br>");
			}
			infoWarning+="</lin>";
		}
		
		checks.add(
			Check.make(
				"<b style=\"color:blue\">Rebajas</b></br>" +
				"Todos los artículos pertenecen a las temporadas <b>" + listTemporadas.toString() + "</b>" + validaNotNewArticlesStr + infoWarning,
				listArtWrong.isEmpty(), levelError)
			.store(store).build());

		return checks;
	}

	@Validation
	public ChecksTM validaNotArticlesOfTypeDesktop(TypeArticle typeArticle, State levelError, StoreType store) {
		var checks = ChecksTM.getNew();
		var pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		var listArtWrong = pageGaleriaDesktop.getArticlesOfType(typeArticle);
		checks.add(
			Check.make(
				"<b style=\"color:blue\">Rebajas</b></br>" +
				"No hay ningún artículo del tipo <b>" + typeArticle + "</b>",
				listArtWrong.isEmpty(), levelError)
			.store(store).build());

		if (!listArtWrong.isEmpty()) {
			addInfoArtWrongToDescription(listArtWrong, typeArticle, checks.get(0));
		}
		return checks;
	}

	private void addInfoArtWrongToDescription(List<String> listArtWrong, TypeArticle typeArticle, Check validation) {
		String textToAdd =
				"<br><lin style=\"color:" + Warn.getColorCss() + ";\"><b>Warning!</b>: " +
						"hay " + listArtWrong.size() + " artículos que son del tipo <b>" + typeArticle + "</b><br>:";
		for (String nameWrong : listArtWrong) {
			textToAdd+=(nameWrong + "<br>");
		}
		textToAdd+="</lin>";
		String descriptionOrigin = validation.getDescription();
		validation.setDescription(descriptionOrigin + textToAdd);
	}

	@Step (
			description="Seleccionamos el link <b>Más Info</b>",
			expected="Se hace visible el aviso legal")
	public void clickMoreInfoBannerRebajasJun2018() {
		((PageGaleriaDesktop)pageGaleria).clickRebajasBannerHead();
		checkAfterClickInfoRebajas();
	}

	@Validation
	private ChecksTM checkAfterClickInfoRebajas() {
		var checks = ChecksTM.getNew();
		var pageGaleriaDesktop = ((PageGaleriaDesktop)pageGaleria);
		int seconds = 1;
		checks.add(
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"Se despliega la información relativa a las rebajas " + getLitSecondsWait(seconds),
			pageGaleriaDesktop.isVisibleInfoRebajasBannerHead(seconds), Warn);
		
		checks.add(
			"Aparece el link de <b>Menos info</b>",
			pageGaleriaDesktop.isVisibleLinkInfoRebajasBannerHead(LESS), Warn);
		
		return checks;
	}

	@Validation
	public ChecksTM checkGaleriaAfeterSelectMenu() {
		var checks = ChecksTM.getNew();
		int secondsArticle = 8;
		int secondsIcon = 2;
		checks.add (
			"Como mínimo se obtiene un artículo " + getLitSecondsWait(secondsArticle),
			pageGaleria.isVisibleArticleUntil(1, secondsArticle), Warn);
		
		if (app==AppEcom.shop) {
			checks.add (
				Check.make(
					"El 1er artículo tiene 1 icono de favorito asociado " + getLitSecondsWait(secondsIcon),
					pageGaleria.isArticleWithHearthIconPresentUntil(1, secondsIcon), Info)
				.store(StoreType.None).build());
		}
		return checks;
	}

}