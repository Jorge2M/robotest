package com.mng.robotest.tests.domains.galeria.steps;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.entity.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.entity.TypeSlider;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;
import com.mng.robotest.testslegacy.steps.navigations.exceptions.ChannelNotSupportedRuntimeException;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class GaleriaSteps extends StepBase {

	public final BannerHeadGallerySteps bannerHead = new BannerHeadGallerySteps(this);
	private final SecSelectorPreciosSteps secSelectorPreciosSteps = new SecSelectorPreciosSteps();
	private final SecFiltrosSteps secFiltrosSteps = new SecFiltrosSteps();
	
	private final PageGaleria pgGaleria = PageGaleria.make(channel);

	public enum TypeGalery { SALES, NO_SALES }
	public enum TypeActionFav { MARCAR, DESMARCAR }

	public SecSelectorPreciosSteps getSecSelectorPreciosSteps() {
		return secSelectorPreciosSteps;
	}

	@Validation (description="Aparece una galería con algún artículo visible " + SECONDS_WAIT)	
	public boolean isArticlePresent(int seconds) {
		for (int i=0; i<=seconds; i++) {
			if (pgGaleria.isVisibleAnyArticle()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
    @Step(
    	description="Cargamos el <b>Catálogo</b> <a href='#{urlCatalog}'>#{urlCatalog}</a>", 
    	expected="Aparece un catálogo con artículos")    		
    public void loadCatalog(String urlCatalog) {
    	driver.get(urlCatalog);
    	checkArticleGaleriaLoaded();
    }
	
    public void selectPricesInterval(int minim, int maxim) throws Exception {
    	secSelectorPreciosSteps.selectInterval(minim, maxim);
    }
    
	@Step (
		description="Seleccionamos el artículo #{position} en una pestaña aparte", 
		expected="Aparece la ficha del artículo seleccionado en una pestaña aparte")
	public void selectArticuloEnPestanyaAndBack(int position) {
		String galeryWindowHandle = driver.getWindowHandle();
		var datosArticulo = new DataFichaArt();

		//Almacenamos el nombre del artículo y su referencia
		var articulo = pgGaleria.getArticulo(position);
		moveToElement(articulo);
		datosArticulo.setNombre(pgGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pgGaleria.getRefArticulo(articulo));

		String detailWindowHandle = pgGaleria.openArticuloPestanyaAndGo(articulo);
		new FichaSteps().checkDetallesProducto(datosArticulo);

		if (detailWindowHandle.compareTo(galeryWindowHandle)!=0) {
			//Cerramos la pestaña y cambiamos a la ventana padre
			driver.switchTo().window(detailWindowHandle);
			driver.close();
			driver.switchTo().window(galeryWindowHandle);
		}
	}

	@Step (
		description="Seleccionar el artículo #{position}", 
		expected="Aparece la ficha del artículo seleccionado")
	public DataFichaArt selectArticulo(int position) {
		var datosArticulo = new DataFichaArt();
		String urlGaleria = getCurrentUrl();
		
		var articulo = pgGaleria.getArticulo(position);
		datosArticulo.setNombre(pgGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pgGaleria.getRefArticulo(articulo));

		pgGaleria.clickArticulo(articulo);
		var fichaSteps = new FichaSteps();
		fichaSteps.checkDetallesProducto(datosArticulo);
		fichaSteps.checkBreadCrumbFicha(urlGaleria);
		
		return datosArticulo;
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
		pgGaleria.showTallasArticulo(posArticulo);
		checkIsVisibleCapaInfoTallas(posArticulo, 1);
	}
	
	@Validation (
		description="Aparece la capa con la información de las tallas " + SECONDS_WAIT,
		level=WARN)
	private boolean checkIsVisibleCapaInfoTallas(int posArticulo, int seconds) {
		return pgGaleria.isVisibleArticleCapaTallasUntil(posArticulo, seconds);
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
				Log4jTM.getLogger().warn(String.format("Failed to select the first available size for article %d", posArticulo), e);
			}
			posArticulo+=1;
		}
	}	
	
	@Step (
		description="Del #{posArticulo}o artículo, seleccionamos la 1a talla disponible", 
		expected="Se da de alta correctamente el artículo en la bolsa")
	public boolean selectTallaAvailableArticulo(int posArticulo) throws Exception {
		var articulo = pgGaleria.selectTallaAvailableArticle(posArticulo);
		boolean tallaVisible = (articulo!=null && articulo.getTalla()!=null);
		if (tallaVisible) {
			dataTest.getDataBag().add(articulo);
			new BolsaSteps().checkArticlesAddedToBag();
		}
		return tallaVisible;
	}

	@Step (
		description="Seleccionamos la primera talla no disponible del listado",
		expected="Se abre el modal de avimase de la prenda",
		saveHtmlPage=ALWAYS)
	public void selectTallaNoDisponibleArticulo() {
		pgGaleria.selectTallaArticleNotAvalaible();
	}
	
	@Validation (description="Sí aparece el footer", level=WARN)
	public boolean checkVisibilityFooter(AppEcom app) {
		return new SecFooter().isVisible();
	}

	@Validation (
		description=
			"En pantalla aparecen más artículos (#{numArticlesCurrently}) " + 
			"de los que había inicialmente (#{numArticlesInit})",
		level=WARN)
	private boolean checkAreMoreArticlesThatInitially(int numArticlesCurrently, int numArticlesInit) {
		return (numArticlesCurrently > numArticlesInit);
	}

	@Validation (description="Los artículos aparecen ordenados por #{orderExpected}")
	public boolean checkArticlesOrdered(FilterOrdenacion orderExpected) throws Exception {
		return (pgGaleria.articlesInOrder(orderExpected));
	}
	
	@Validation
	public ChecksTM checkNotRepeatedArticles() {
		var checks = ChecksTM.getNew();
		var productsRepeated = pgGaleria.searchArticleRepeatedInGallery();
		String producRepeatedWarning = "";
		if (productsRepeated!=null && !productsRepeated.isEmpty()) {
			producRepeatedWarning+=
				"<br><b style=\"color:" + WARN.getColorCss() + "\">Warning!</b>: " + 
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
		level=INFO)
	public boolean checkNumArticlesInScreen(int numArticlesInPage, int numArticlesExpected) {
		return (numArticlesInPage==numArticlesExpected);
	}
	
	@Validation (
		description="Hay más artículos después del scroll (<b>#{finArticles}</b>) que al principio (<b>#{iniArticles})</b>")
	public boolean checkAreMoreArticles(int iniArticles, int finArticles) {
		return finArticles > iniArticles;
	}

	public int seleccionaOrdenacionGaleria(FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria) 
			throws Exception {
		return seleccionaOrdenacionGaleria(typeOrdenacion, tipoPrendasGaleria, -1);
	}
	
	@Step (
		description="Seleccionar la ordenación #{typeOrdenacion} y posicionarse en el 1er artículo", 
		expected="Los artículos se ordenan correctamente")
	public int seleccionaOrdenacionGaleria(
			FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria, int numArticulosValidar) throws Exception {
		
		pgGaleria.selecOrdenacionAndReturnNumArticles(typeOrdenacion);
		pgGaleria.backTo1erArticulo();

		checkIsVisiblePageWithTitle(tipoPrendasGaleria);
		int numArticulosPant = pgGaleria.getNumArticulos() + pgGaleria.getNumArticulos();
		checkOrderListArticles(typeOrdenacion, numArticulosPant, numArticulosValidar);
 
		checksDefault();
	   
		return numArticulosPant;
	}

	@Validation (
		description="Aparece una pantalla en la que el title contiene <b>#{tipoPrendasGaleria}",
		level=WARN)
	private boolean checkIsVisiblePageWithTitle(String tipoPrendasGaleria) {
		return driver.getTitle().toLowerCase()
				.contains(tipoPrendasGaleria.toLowerCase());
	}
	
	@Validation
	private ChecksTM checkOrderListArticles(FilterOrdenacion typeOrdenacion, int numArticulosPant, int numArticulosValidar) 
	throws Exception {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Aparecen > 1 prendas",
			numArticulosPant > 1, WARN);
		if (numArticulosValidar>=0) {
		  	checks.add(
				"Aparecen " + numArticulosValidar + " artículos",
				numArticulosValidar==numArticulosPant, INFO);
		}
	  	checks.add(
			"Los artículos aparecen ordenados por " + typeOrdenacion.name(),
			pgGaleria.articlesInOrder(typeOrdenacion), WARN);
	  	
	  	return checks;
	}

	@Step (
		description="Volver al 1er artículo de la galería (mediante selección del icono de la flecha Up)", 
		expected="Se visualiza el 1er elemento")
	public void backTo1erArticleMobilStep() throws Exception {
		pgGaleria.backTo1erArticulo();
		checkBackTo1ersElementOk();
	}

	@Validation
	private ChecksTM checkBackTo1ersElementOk() {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Es clickable el 1er elemento de la lista",
			pgGaleria.isClickableArticuloUntil(1, 0), WARN);
	  	
		int seconds = 2;
	  	checks.add(
			"Es clickable el bloque de filtros " + getLitSecondsWait(seconds),
			pgGaleria.isClickableFiltroUntil(seconds), WARN);
	  	
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
			"Seleccionar el #{posColor}o color (" + TAG_SRC_PNG_2O_COLOR +") del #{numArtConColores}o " + 
			"artículo con variedad de colores (" + TAG_NOMBRE_1ER_ARTIC + ", " + TAG_PRECIO_1ER_ARTIC +")", 
		expected="Se selecciona el color")
	public String selecColorFromArtGaleriaStep(int numArtConColores, int posColor) {
		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		waitForPageLoaded(driver, 2);

		var articuloColores = pgGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		replaceStepDescription(TAG_NOMBRE_1ER_ARTIC, pgGaleria.getNombreArticulo(articuloColores));
		replaceStepDescription(TAG_PRECIO_1ER_ARTIC, pgGaleria.getPrecioArticulo(articuloColores));
		String srcImg1erArt = pgGaleria.getImagenArticulo(articuloColores);
		replaceStepDescription(TAG_SRC_PNG_2O_COLOR, srcImg1erArt);
		
		pgGaleria.clickColorArticulo(articuloColores, posColor);
		waitMillis(100);
		
		String srcImgAfterClickColor = pgGaleria.getImagenArticulo(articuloColores);
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
	public String clicksSliderArticuloConColores(int numArtConColores, TypeSlider... typeSliderList) {
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
	public String clicksSliderArticuloConColores(int numArtConColores, String srcImageExpected, TypeSlider... typeSliderList) {
		if (!isDesktop()) {
			throw new ChannelNotSupportedRuntimeException("Method clickSliderArticuloConColores doesn't support channel " + channel);
		}

		String slidersListStr = getStringSliderList(typeSliderList);
		replaceStepDescription(TAG_SLIDER_LIST, slidersListStr);

		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax.
		//En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		waitForPageLoaded(driver, 2);
		var articuloColores = pgGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		
		replaceStepDescription(TAG_NOMBRE_ART, pgGaleria.getNombreArticulo(articuloColores));
		replaceStepExpected(TAG_NOMBRE_ART, pgGaleria.getNombreArticulo(articuloColores));
		
		String srcImg1erSlider = pgGaleria.getImagenArticulo(articuloColores);
		pgGaleria.clickSliders(articuloColores, typeSliderList);

		String srcImg2oSlider = pgGaleria.getImagenArticulo(articuloColores);
		checkImageSliderArticleHasChanged(srcImg1erSlider, srcImg2oSlider, typeSliderList.length);
		if ("".compareTo(srcImageExpected)!=0) {
			checkActualImgSliderIsTheExpected(srcImg2oSlider, srcImageExpected);
		}
		return srcImg2oSlider;
	}

	@Validation
	private ChecksTM checkImageSliderArticleHasChanged(String srcImg1erSlider, String srcImg2oSlider, int numClicks) {
		State state = DEFECT;
		if (numClicks>1) {
			//Si hemos realizado varios clicks y sólo hay 2 imágenes habremos vuelto a la inicial
			state = WARN;
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
		saveNettraffic=ALWAYS)
	public void selecArticuloGaleriaStep(int numArtConColores) {
		WebElement articuloColores = pgGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		String nombre1erArt = pgGaleria.getNombreArticulo(articuloColores);
		String precio1erArt = pgGaleria.getPrecioArticulo(articuloColores);
		replaceStepDescription(TAG_NUM_ART_CON_COLORES, String.valueOf(numArtConColores));
		replaceStepDescription(TAG_NOMBRE_1ER_ART, nombre1erArt);
		replaceStepDescription(TAG_PRECIO_1ER_ART, precio1erArt);
		replaceStepExpected(TAG_NOMBRE_1ER_ART, nombre1erArt);
		replaceStepExpected(TAG_PRECIO_1ER_ART, precio1erArt);

		pgGaleria.clickArticulo(articuloColores);
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
		var pageFicha = PageFicha.make(channel, app, dataTest.getPais());
		
	  	checks.add(
			"Aparece la página de ficha " + getLitSecondsWait(seconds),
			pageFicha.isPage(seconds), WARN);
	  	
		String nombreArtFicha = pageFicha.getTituloArt();
		String precioArtFicha = pageFicha.getPrecioFinalArticulo();
	  	checks.add(
	  	    Check.make(
			    "Aparece el artículo anteriormente seleccionado: <br>\" +\n" + 
			    "   - Nombre " + nombre1erArt + "<br>" + 
			    "   - Precio " + precio1erArt,
			    nombreArtFicha.toUpperCase().contains(nombre1erArt.toUpperCase()) &&
			    precioArtFicha.replace(" ", "").toUpperCase().contains(precio1erArt.replace(" ", "").toUpperCase()),
			    INFO)
	  	    .store(NONE).build());
		
	  	return checks;
	}

	@Validation (
		description="Aparece una página con artículos " + SECONDS_WAIT,	level=WARN)
	public boolean validaArtEnContenido(int seconds) {
		return pgGaleria.isVisibleInScreenArticleUntil(1, seconds);
	}

	private static final String TAG_POS_ICONS = "@TagPosIcons";
	private static final String TAG_ESTADO_FINAL = "@TagEstadoFinal";
	@Step (
		description="Seleccionamos (para <b>#{actionFav}</b>) los \"Hearth Icons\" asociados a los artículos <b>" + TAG_POS_ICONS + "</b>", 
		expected="Los \"Hearth Icons\" quedan " + TAG_ESTADO_FINAL)
	public void clickArticlesHearthIcons(TypeActionFav actionFav, Integer... posIconsToClick) 
			throws Exception {
		replaceStepDescription(TAG_POS_ICONS, Arrays.toString(posIconsToClick));
		
		var listAddFav = pgGaleria.clickArticleHearthIcons(posIconsToClick);
		String estadoFinal = "";
		if (actionFav==TypeActionFav.MARCAR) {
			estadoFinal = "Marcados";
			dataTest.getDataFavoritos().addArticlesToLista(listAddFav);
		} else {
			estadoFinal = "Desmarcados";
			dataTest.getDataFavoritos().removeArticlesFromLista(listAddFav);
		}
		
		replaceStepExpected(TAG_ESTADO_FINAL, estadoFinal);
		checkIconosInCorrectState(actionFav, estadoFinal, posIconsToClick);
	}

	@Validation
	private ChecksTM checkIconosInCorrectState(TypeActionFav actionFav, String estadoFinal, Integer... posIconsSelected) {
		var checks = ChecksTM.getNew();
   		checks.add(
			"Quedan " + estadoFinal + " los iconos asociados a los artículos con posiciones <b>" + Arrays.toString(posIconsSelected) + "</b>",
   	   		pgGaleria.iconsInCorrectState(actionFav, posIconsSelected), WARN);
		return checks;
	}

	public void checkGaleriaAfeterSelectMenu() {
		checkArticleGaleriaLoaded();
		if (isShop()) {
			checkHearthIconVisible();
		}
	}
	
	public ChecksTM checkArticleGaleriaLoaded() {
		return checkArticleGaleriaLoaded(1);
	}
	
	@Validation
	public ChecksTM checkArticleGaleriaLoaded(int posArticulo) {
		var checks = ChecksTM.getNew();
		int secondsArticle = 8;
		checks.add (
			"Aparece la imagen del artículo en la posición <b>" + posArticulo + "</b> " + getLitSecondsWait(secondsArticle),
			pgGaleria.isVisibleArticleUntil(posArticulo, secondsArticle));
		
		return checks;
	}
	
	@Validation
	public ChecksTM checkArticleGaleriaVisibleInScreen(int posArticulo) {
		var checks = ChecksTM.getNew();
		int secondsArticle = 8;
		checks.add (
			"Aparece la imagen del artículo en la posición <b>" + posArticulo + "</b> " + getLitSecondsWait(secondsArticle),
			pgGaleria.isVisibleInScreenArticleUntil(posArticulo, secondsArticle));
		
		return checks;
	}	
	
	@Validation
	public ChecksTM checkHearthIconVisible() {
		var checks = ChecksTM.getNew();
		int secondsIcon = 2;
		checks.add (
			Check.make(
				"El 1er artículo tiene 1 icono de favorito asociado " + getLitSecondsWait(secondsIcon),
				pgGaleria.isArticleWithHearthIconPresentUntil(1, secondsIcon), INFO)
			.store(NONE).build());
		return checks;
	}
	
	public int selectFiltroColores(List<Color> colorsToSelect, String litMenu) {
		return secFiltrosSteps.selectFiltroColores(colorsToSelect, litMenu);
	}
	
	@Step (
		description="Escrollar hasta posicionarse en la última página", 
		expected="Se escrolla correctamente")
	public void scrollToLast() {
		int iniArticles = pgGaleria.getNumArticulos();
		pgGaleria.scrollToLastPage();
		int finArticles = pgGaleria.getNumArticulos();
		checkVisibilityFooter(app);
		checkAreMoreArticles(iniArticles, finArticles);		
		checkNotRepeatedArticles();
	}

}