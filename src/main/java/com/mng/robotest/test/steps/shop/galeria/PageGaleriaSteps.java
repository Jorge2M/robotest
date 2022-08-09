package com.mng.robotest.test.steps.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha.TypeFicha;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataFavoritos;
import com.mng.robotest.test.factoryes.NodoStatus;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.ControlTemporada;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.test.pageobject.shop.galeria.SecBannerHeadGallery;
import com.mng.robotest.test.pageobject.shop.galeria.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test.pageobject.utils.DataArticleGalery;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.UtilsTest;
import com.github.jorge2m.testmaker.service.TestMaker;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

public class PageGaleriaSteps {


	public final SecCrossSellingSteps secCrossSellingSteps;
	public final BannerHeadGallerySteps bannerHead;
	
	public enum TypeGalery { SALES, NO_SALES }
	public enum TypeActionFav { MARCAR, DESMARCAR }
	
	final SecSelectorPreciosSteps secSelectorPreciosSteps;
	final PageGaleria pageGaleria;
	final WebDriver driver;
	final Channel channel;
	final AppEcom app;
	
	private PageGaleriaSteps(Channel channel, AppEcom app, WebDriver driver) {
		this.driver = driver;
		this.channel = channel;
		this.app = app;
		this.secCrossSellingSteps = new SecCrossSellingSteps(channel, app);
		this.secSelectorPreciosSteps = new SecSelectorPreciosSteps(app, channel, driver);
		this.bannerHead = BannerHeadGallerySteps.newInstance(this, driver);
		this.pageGaleria = PageGaleria.getNew(channel, app);
	}
	
	public static PageGaleriaSteps getInstance(Channel channel, AppEcom app, WebDriver driver) {
		return (new PageGaleriaSteps(channel, app, driver));
	}
	
	public SecSelectorPreciosSteps getSecSelectorPreciosSteps() {
		return secSelectorPreciosSteps;
	}

	@Step (
		description="Seleccionamos el artículo #{locationArt} en una pestaña aparte", 
		expected="Aparece la ficha del artículo seleccionado en una pestaña aparte")
	public void selectArticuloEnPestanyaAndBack(LocationArticle locationArt, Pais pais) throws Exception {
		String galeryWindowHandle = driver.getWindowHandle();
		DataFichaArt datosArticulo = new DataFichaArt();

		//Almacenamos el nombre del artículo y su referencia
		WebElement articulo = pageGaleria.getArticulo(locationArt);
		datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pageGaleria.getRefArticulo(articulo));

		String detailWindowHandle = pageGaleria.openArticuloPestanyaAndGo(articulo, app);
		PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(pais);
		pageFichaSteps.validaDetallesProducto(datosArticulo);

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
	public DataFichaArt selectArticulo(LocationArticle locationArt, DataCtxShop dCtxSh) {
		DataFichaArt datosArticulo = new DataFichaArt();
		String urlGaleria = driver.getCurrentUrl();
		
		//Almacenamos el nombre del artículo y su referencia
		WebElement articulo = pageGaleria.getArticulo(locationArt);
		datosArticulo.setNombre(pageGaleria.getNombreArticulo(articulo));
		datosArticulo.setReferencia(pageGaleria.getRefArticulo(articulo));

		pageGaleria.clickArticulo(articulo);
		PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(dCtxSh.pais);
		pageFichaSteps.validaDetallesProducto(datosArticulo);
		pageFichaSteps.validaPrevNext(locationArt, dCtxSh);

		//Validaciones sección BreadCrumb + Next
		if (dCtxSh.channel==Channel.desktop) {
			if (pageFichaSteps.getFicha().getTypeFicha()==TypeFicha.OLD) {
				pageFichaSteps.validaBreadCrumbFichaOld(urlGaleria);
			}
		}
		
		return (datosArticulo);
	}

	public void shopTallasArticulo(int posArticulo) throws Exception {
		if (channel.isDevice() || app==AppEcom.outlet) {
			showTallasOutletAndMovil(posArticulo);
		} else {
			showTallasShopDesktop(posArticulo);
		}
	}

	@Step (
		description="Posicionarse sobre el artículo en la posición <b>#{posArticulo}</b>, esperar que aparezca el link \"Añadir\" y seleccionarlo", 
		expected="Aparece la capa con la información de las tallas")
	private void showTallasOutletAndMovil(int posArticulo) {
		pageGaleria.showTallasArticulo(posArticulo);
		checkIsVisibleCapaInfoTallas(posArticulo, 1);
	}
	
	@Step (
		description="Posicionarse sobre el artículo en la posición <b>#{posArticulo}</b>", 
		expected="Aparece la capa con la información de las tallas")
	private void showTallasShopDesktop(int posArticulo) {
		pageGaleria.showTallasArticulo(posArticulo);
		checkIsVisibleCapaInfoTallas(posArticulo, 1);
	}
	
	@Validation (
		description="Aparece la capa con la información de las tallas (la esperamos hasta #{maxSeconds} segundos",
		level=State.Warn)
	private boolean checkIsVisibleCapaInfoTallas(int posArticulo, int maxSeconds) {
		return pageGaleria.isVisibleArticleCapaTallasUntil(posArticulo, maxSeconds);
	}

	@Step (
		description="Del #{posArticulo}o artículo, seleccionamos la #{posTalla}a talla disponible", 
		expected="Se da de alta correctamente el artículo en la bolsa",
		saveHtmlPage=SaveWhen.Always)
	public boolean selectTallaAvailableArticulo(int posArticulo, int posTalla, DataBag dataBag, DataCtxShop dCtxSh) 
			throws Exception {
		ArticuloScreen articulo = pageGaleria.selectTallaAvailableArticle(posArticulo, posTalla);
		boolean tallaVisible = (articulo!=null);
		//ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = ModalArticleNotAvailableSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		//boolean notVisibleAvisame = modalArticleNotAvailableSteps.validateState(1, StateModal.notvisible, driver);
		if (tallaVisible) {
			dataBag.addArticulo(articulo);
			SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
			secBolsaSteps.validaAltaArtBolsa(dataBag);
		}

		return tallaVisible;
	}

	@Step (
		description="Seleccionamos la primera talla no disponible del listado",
		expected="Se abre el modal de avimase de la prenda",
		saveHtmlPage=SaveWhen.Always)
	public void selectTallaNoDisponibleArticulo() {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		pageGaleriaDesktop.selectTallaArticleNotAvalaible();
	}
	
	public DataScroll scrollFromFirstPage(DataCtxShop dCtxSh) throws Exception {
		DataForScrollStep data = new DataForScrollStep();
		data.numPageToScroll = 99;
		data.ordenacionExpected = FilterOrdenacion.NOordenado;
		return scrollFromFirstPage(data, dCtxSh);
	}
	
	/**
	 * Escrollamos hasta llegar a la página indicada en toPage
	 * @param toPage indica el número de página en el que nos queremos posicionar. Si es PageGaleria.scrollToLast asumimos que queremos llegar hasta el final del catálogo
	 */
	static final String tagIdPage = "@TagIdPage";
	@Step (
		description="Escrollar hasta posicionarse en la " + tagIdPage + " página", 
		expected="Se escrolla correctamente",
		saveNettraffic=SaveWhen.Always)
	public DataScroll scrollFromFirstPage(DataForScrollStep dataForScroll, DataCtxShop dCtxSh) 
	throws Exception {
		DataScroll datosScroll = null;
		int pageToScroll = dataForScroll.numPageToScroll;
		if (dCtxSh.channel.isDevice()) {
			pageToScroll = 3;
		}
		
		String idPage = pageToScroll + "a";
		if (pageToScroll>=PageGaleriaDesktop.MAX_PAGE_TO_SCROLL) {
			idPage = "última";
		}
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagIdPage, idPage);
		int numArticulosInicio = pageGaleria.getNumArticulos();
		datosScroll = pageGaleria.scrollToPageFromFirst(pageToScroll);
		
		if (pageToScroll>=PageGaleriaDesktop.MAX_PAGE_TO_SCROLL) {
			checkVisibilityFooter(pageToScroll, dCtxSh.appE);
		}
		if (pageToScroll < PageGaleriaDesktop.MAX_PAGE_TO_SCROLL) {
			checkAreMoreArticlesThatInitially(datosScroll.articulosMostrados, numArticulosInicio);
		}
		if (dataForScroll.ordenacionExpected != FilterOrdenacion.NOordenado) {
			checkArticlesOrdered(dataForScroll.ordenacionExpected);
		}
		checkNotRepeatedArticles();
		if (dataForScroll.validateArticlesExpected) {
			checkNumArticlesInScreen(datosScroll.articulosTotalesPagina, dataForScroll.numArticlesExpected);
		}
		
		List<GenericCheck> listChecks = new ArrayList<>();
		listChecks.add(GenericCheck.SEO); 
		listChecks.add(GenericCheck.JSerrors); 
		listChecks.add(GenericCheck.Analitica);
		listChecks.add(GenericCheck.TextsTraduced);
		listChecks.add(GenericCheck.NetTraffic);
		listChecks.add(GenericCheck.GoogleAnalytics);
		if (dataForScroll.validaImgBroken) {
			listChecks.add(GenericCheck.ImgsBroken);
		}
		
		datosScroll.step = TestMaker.getCurrentStepInExecution();
		return datosScroll;
	}

	@Validation (
		description="Sí aparece el footer",
		level=State.Warn)
	private boolean checkVisibilityFooter(int pageToScroll, AppEcom app) throws Exception {
		return (new SecFooter(app)).isVisible();
	}

	@Validation (
		description=
			"En pantalla aparecen más artículos (#{numArticlesCurrently}) " + 
			"de los que había inicialmente (#{numArticlesInit})",
		level=State.Warn)
	private boolean checkAreMoreArticlesThatInitially(int numArticlesCurrently, int numArticlesInit) {
		return (numArticlesCurrently > numArticlesInit);
	}
	
	@Validation (
		description="Los artículos aparecen ordenados por #{orderExpected}",
		level=State.Defect)
	private boolean checkArticlesOrdered(FilterOrdenacion orderExpected) throws Exception {
		return (pageGaleria.articlesInOrder(orderExpected));
	}
	
	@Validation
	private ChecksTM checkNotRepeatedArticles() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		List<DataArticleGalery> productsRepeated = pageGaleria.searchArticleRepeatedInGallery();
		String producRepeatedWarning = "";
		if (productsRepeated!=null && productsRepeated.size()>0) {
			producRepeatedWarning+=
				"<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: " + 
				"hay " + productsRepeated.size() + " productos repetidos, " + 
				"por ejemplo el <b>" + productsRepeated.get(0).toString();
		}
	  	checks.add(
			"No aparece ningún artículo repetido" + producRepeatedWarning,
			productsRepeated==null || productsRepeated.size()==0, State.Defect);
	  	
	  	return checks;
	}
	
	@Validation (
		description=
			"En pantalla aparecen exactamente #{numArticlesInPage} artículos " + 
			"(están apareciendo #{numArticlesExpected}",
		level=State.Info)
	private boolean checkNumArticlesInScreen(int numArticlesInPage, int numArticlesExpected) {
		return (numArticlesInPage==numArticlesExpected);
	}
   
	@Step (
		description="Seleccionar la ordenación #{typeOrdenacion}", 
		expected="Los artículos se ordenan correctamente")
	public int seleccionaOrdenacionGaleria(FilterOrdenacion typeOrdenacion, String tipoPrendasGaleria, int numArticulosValidar, 
		   								   DataCtxShop dCtxSh) throws Exception {
		SecFiltros secFiltros = SecFiltros.make(dCtxSh.channel, dCtxSh.appE, driver);
		secFiltros.selecOrdenacionAndReturnNumArticles(typeOrdenacion);	
		
		checkIsVisiblePageWithTitle(tipoPrendasGaleria);
		int numArticulosPant = pageGaleria.getNumArticulos() + pageGaleria.getNumArticulos();
		checkOrderListArticles(typeOrdenacion, numArticulosPant, numArticulosValidar);
 
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO,
				GenericCheck.JSerrors,
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	   
		return numArticulosPant;
	}
	
	@Validation (
		description="Aparece una pantalla en la que el title contiene <b>#{tipoPrendasGaleria}",
		level=State.Warn)
	private boolean checkIsVisiblePageWithTitle(String tipoPrendasGaleria) {
		return (driver.getTitle().toLowerCase().contains(tipoPrendasGaleria));
	}
	
	@Validation
	private ChecksTM checkOrderListArticles(FilterOrdenacion typeOrdenacion, int numArticulosPant, int numArticulosValidar) 
	throws Exception {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Aparecen > 1 prendas",
			numArticulosPant > 1, State.Warn);
		if (numArticulosValidar>=0) {
		  	checks.add(
				"Aparecen " + numArticulosValidar + " artículos",
				numArticulosValidar==numArticulosPant, State.Info);
		}
	  	checks.add(
			"Los artículos aparecen ordenados por " + typeOrdenacion.name(),
			pageGaleria.articlesInOrder(typeOrdenacion), State.Warn);
	  	
	  	return checks;
	}
   
	@Step (
		description="Volver al 1er artículo de la galería (mediante selección del icono de la flecha Up)", 
		expected="Se visualiza el 1er elemento")
	public void backTo1erArticleMobilStep(DataCtxShop dCtxSh) throws Exception {
		pageGaleria.backTo1erArticulo();
		checkBackTo1ersElementOk(dCtxSh);
	}
	
	@Validation
	private ChecksTM checkBackTo1ersElementOk(DataCtxShop dCtxSh) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Es clickable el 1er elemento de la lista",
			pageGaleria.isClickableArticuloUntil(1, 0), State.Warn);
	  	
		SecFiltros secFiltros = SecFiltros.make(dCtxSh.channel, dCtxSh.appE, driver);
		int maxSeconds = 2;
	  	checks.add(
			"Es clickable el bloque de filtros (esperamos hasta " + maxSeconds + " segundos)",
			secFiltros.isClickableFiltroUntil(maxSeconds), State.Warn);
	  	
	  	return checks;
	}

	/**
	 * Pasos/Validaciones consistentes en seleccionar un determinado color de un determinado artículo
	 * @param posColor posición del color en la lista de colores del artículo
	 * @param numArtConColores posición del artículo entre el conjunto de artículos con variedad de colores
	 * @return src de la imagen obtenida al ejecutar el click sobre el color
	 */
	static final String tagSrcPng2oColor = "@srcPng2oColor";
	static final String tagNombre1erArtic = "@nombre1erArt";
	static final String tagPrecio1erArtic = "@precio1erArt";
	@Step (
		description=
			"Seleccionar el #{posColor}o color (" + tagSrcPng2oColor +") no seleccionado del #{numArtConColores}o " + 
			"artículo con variedad de colores (" + tagNombre1erArtic + ", " + tagPrecio1erArtic +")", 
		expected="Se selecciona el color")
	public String selecColorFromArtGaleriaStep(int numArtConColores, int posColor) 
	throws Exception {
		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		SeleniumUtils.waitForPageLoaded(driver, 2);
	   
		StepTM step = TestMaker.getCurrentStepInExecution();
		WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		step.replaceInDescription(tagNombre1erArtic, pageGaleria.getNombreArticulo(articuloColores));
	   
		WebElement colorToClick = pageGaleria.getColorArticulo(articuloColores, false/*selected*/, posColor);
		step.replaceInDescription(tagPrecio1erArtic, pageGaleria.getPrecioArticulo(articuloColores));
	   
		String srcImg1erArt = pageGaleria.getImagenArticulo(articuloColores);
		step.replaceInDescription(tagSrcPng2oColor, colorToClick.getAttribute("src"));
	   
		click(colorToClick, driver).exec();
		Thread.sleep(100);
		
		String srcImgAfterClickColor = pageGaleria.getImagenArticulo(articuloColores);
		checkImageIsModified(srcImg1erArt, srcImgAfterClickColor);
	   
		return srcImgAfterClickColor;
	}
	
	@Validation (
		description="Se modifica la imagen correspondiente al artículo",
		level=State.Defect)
	private boolean checkImageIsModified(String srcImg1erArt, String srcImgAfterClickColor) {
		return (!srcImgAfterClickColor.contains(srcImg1erArt));		
	}
	
	/**
	 * @return src de la imagen obtenida al ejecutar los clicks
	 */
	public String clicksSliderArticuloConColores(int numArtConColores, ArrayList<TypeSlider> typeSliderList) 
	throws Exception {
		return (clicksSliderArticuloConColores(numArtConColores, typeSliderList, ""));		
	}
   
	/**
	 * @param srcImageExpected el src esperado para la imagen resultante de la secuencia de clicks en el slider. Si tiene valor "" no aplicamos validación
	 */
	private static final String tagNombreArt = "@TagNombreArt";
	private static final String tagSliderList = "@TagSliderList";
	@Step (
		description=
			"Clickar la siguiente secuencia de sliders: <b>" + tagSliderList + "</b> del #{numArtConColores}o " + 
			" artículo con variedad de colores (" + tagNombreArt + "). Previamente realizamos un \"Hover\" sobre dicho artículo", 
		expected="Aparece el artículo original(" + tagNombreArt + ")")
	public String clicksSliderArticuloConColores(int numArtConColores, List<TypeSlider> typeSliderList, String srcImageExpected) 
	throws Exception {
		if (channel!=Channel.desktop) {
			throw new RuntimeException("Method clickSliderArticuloConColores doesn't support channel " + channel);
		}

		String slidersListStr = getStringSliderList(typeSliderList);
		StepTM stepTM = TestMaker.getCurrentStepInExecution();
		stepTM.replaceInDescription(tagSliderList, slidersListStr);

		//En el caso de la galería con artículos "Sliders" es preciso esperar la ejecución Ajax. 
		//En caso contrario hay elementos que no están disponibles (como la imagen principal del slider)
		SeleniumUtils.waitForPageLoaded(driver, 2);
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		WebElement articuloColores = pageGaleriaDesktop.getArticuloConVariedadColoresAndHover(numArtConColores);
		stepTM.replaceInDescription(tagNombreArt, pageGaleria.getNombreArticulo(articuloColores));
		stepTM.replaceInExpected(tagNombreArt, pageGaleria.getNombreArticulo(articuloColores));
		String srcImg1erSlider = pageGaleria.getImagenArticulo(articuloColores);
		pageGaleriaDesktop.clickSliderAfterHoverArticle(articuloColores, typeSliderList);
		
		String srcImg2oSlider = pageGaleria.getImagenArticulo(articuloColores);
		checkImageSliderArticleHasChanged(srcImg1erSlider, srcImg2oSlider, typeSliderList.size());
		if ("".compareTo(srcImageExpected)!=0) {
			checkActualImgSliderIsTheExpected(srcImg2oSlider, srcImageExpected);
		}
		return srcImg2oSlider;
	}

	@Validation
	private ChecksTM checkImageSliderArticleHasChanged(String srcImg1erSlider, String srcImg2oSlider, int numClicks) {
		State state = State.Defect;
		if (numClicks>1) {
			//Si hemos realizado varios clicks y sólo hay 2 imágenes habremos vuelto a la inicial
			state = State.Warn;
		}
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Se modifica la imagen asociada al artículo (<b>antes</b>: " + srcImg1erSlider + ", <b>ahora</b>: " + srcImg2oSlider,
			srcImg2oSlider.compareTo(srcImg1erSlider)!=0, state);
		
		return checks;
   }
   
   @Validation (
	   description="El src de la imagen <b>ahora</b> (#{srcImgActual}) es la <b>original</b> (#{srcImgOriginalExpected})",
	   level=State.Defect)
   private boolean checkActualImgSliderIsTheExpected(String srcImgActual, String srcImgOriginalExpected) {
	   return (srcImgActual.compareTo(srcImgOriginalExpected)==0);
   }
	
   private static String getStringSliderList(List<TypeSlider> typeSliderList) {
	   String listStr = "";
	   for (TypeSlider typeSlider : typeSliderList) {
		   listStr+=(typeSlider + ", ");
	   }
	   return listStr;
   }
	
   	static final String tagNumArtConColores = "@TagNumArtConColores";
   	static final String tagNombre1erArt = "@TagNombre1erArt";
   	static final String tagPrecio1erArt = "@TagPrecio1erArt";
	@Step (
		description="Seleccionar el " + tagNumArtConColores + "o artículo con variedad de colores (" + tagNombre1erArt + " " + tagPrecio1erArt + ")", 
		expected="Aparece el artículo original(" + tagNombre1erArt + " " + tagPrecio1erArt + ")",
		saveNettraffic=SaveWhen.Always)
	public void selecArticuloGaleriaStep(int numArtConColores) throws Exception {
		WebElement articuloColores = pageGaleria.getArticuloConVariedadColoresAndHover(numArtConColores);
		String nombre1erArt = pageGaleria.getNombreArticulo(articuloColores);
		String precio1erArt = pageGaleria.getPrecioArticulo(articuloColores);
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagNumArtConColores, String.valueOf(numArtConColores));
		step.replaceInDescription(tagNombre1erArt, nombre1erArt);
		step.replaceInDescription(tagPrecio1erArt, precio1erArt);
		
		pageGaleria.clickArticulo(articuloColores);
		int maxSeconds = 3;
		checkIsFichaArticle(nombre1erArt, precio1erArt, maxSeconds);

		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.GoogleAnalytics, 
				GenericCheck.JSerrors,
				GenericCheck.SEO,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.NetTraffic)).checks(driver);	   
	}
   
   	@SuppressWarnings("static-access")
	@Validation
	private ChecksTM checkIsFichaArticle(String nombre1erArt, String precio1erArt, int maxSeconds) {
		ChecksTM checks = ChecksTM.getNew();
		
		PageFicha pageFicha = PageFicha.newInstance(channel, app);
	  	checks.add(
			"Aparece la página de ficha (la esperamos hasta " + maxSeconds + " segundos)",
			pageFicha.isPageUntil(maxSeconds), State.Warn);
	  	
		String nombreArtFicha = pageFicha.getSecDataProduct().getTituloArt();
		String precioArtFicha = pageFicha.getSecDataProduct().getPrecioFinalArticulo();
	  	checks.add(
	  	    Check.make(
			    "Aparece el artículo anteriormente seleccionado: <br>\" +\n" + 
			    "   - Nombre " + nombre1erArt + "<br>" + 
			    "   - Precio " + precio1erArt,
			    nombreArtFicha.toUpperCase().contains(nombre1erArt.toUpperCase()) &&
			    precioArtFicha.replaceAll(" ", "").toUpperCase().contains(precio1erArt.replaceAll(" ", "").toUpperCase()),
			    State.Info)
	  	    .store(StoreType.None).build());
		
	  	return checks;
	}
	
	@Validation(
		description = "Como mínimo el #{porcentaje} % de los productos son dobles",
		level=State.Info,
		store=StoreType.None)
	public boolean hayPanoramicasEnGaleriaDesktop(float porcentaje) {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		float numArtTotal = pageGaleria.getNumArticulos();
		float numArtDobles = pageGaleriaDesktop.getNumArticulos(TypeArticleDesktop.DOBLE);
		return (!articlesUnderPercentage(numArtTotal, numArtDobles, porcentaje));
	}

   private static boolean articlesUnderPercentage(float numArtTotal, float numArtToMesure, float percentage) {
	   if (numArtTotal==0) {
		   return true;
	   }
	   return ((numArtToMesure / numArtTotal) < (percentage / 100));
   }
   
	@Validation (
		description="Existe algún vídeo en la galería",
		level=State.Warn)
	public boolean validaHayVideoEnGaleria() {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
	  	return (pageGaleriaDesktop.isPresentAnyArticle(TypeArticleDesktop.VIDEO));
	}
   
	@Validation (
		description="Aparece una página con artículos (la esperamos #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validaArtEnContenido(int maxSeconds) {
		return (pageGaleria.isVisibleArticleUntil(1, maxSeconds));
	}   
   
	static final String tagEstadoFinal = "@TagEstadoFinal";
	@Step (
		description="Seleccionamos (para <b>#{actionFav}</b>) los \"Hearth Icons\" asociados a los artículos con posiciones <b>#{posIconsToClick}</b>", 
		expected="Los \"Hearth Icons\" quedan " + tagEstadoFinal)
	public void clickArticlesHearthIcons(List<Integer> posIconsToClick, TypeActionFav actionFav, DataFavoritos dataFavoritos) 
	throws Exception {
		List<ArticuloScreen> listAddFav = pageGaleria.clickArticleHearthIcons(posIconsToClick);
		String estadoFinal = "";
		switch (actionFav) {
		case MARCAR:
			estadoFinal = "Marcados";
			dataFavoritos.addToLista(listAddFav);
			break;
		case DESMARCAR:
			estadoFinal = "Desmarcados";
			dataFavoritos.removeFromLista(listAddFav);
			break;
		default:
			break;
		}
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagEstadoFinal, estadoFinal); 
		checkIconosInCorrectState(actionFav, estadoFinal, posIconsToClick);
	}
	
	@Validation (
		description="Quedan #{estadoFinal} los iconos asociados a los artículos con posiciones <b>#{posIconsSelected.toString()}</b>",
		level=State.Warn)
	private boolean checkIconosInCorrectState(TypeActionFav actionFav, @SuppressWarnings("unused") String estadoFinal, 
											  List<Integer> posIconsSelected) {
		return (pageGaleria.iconsInCorrectState(posIconsSelected, actionFav));
	}
   
	public ListDataArticleGalery selectListadoXColumnasDesktop(NumColumnas numColumnas, ListDataArticleGalery listArticlesGaleriaAnt) 
	throws Exception {
		selectListadoXColumnasDesktop(numColumnas);
		ListDataArticleGalery listArticlesGaleriaAct = pageGaleria.getListDataArticles();
		if (listArticlesGaleriaAnt!=null) {
			int articulosComprobar = 20;
			checkArticlesEqualsToPreviousGalery(articulosComprobar, listArticlesGaleriaAnt, listArticlesGaleriaAct, numColumnas);
		}
	   
		return listArticlesGaleriaAct;
	}
	
	@Step (
		description="Seleccionar el link del listado a <b>#{numColumnas.name()} columnas</b>", 
		expected="Aparece un listado de artículos a #{numColumnas.name()} columnas")
	public void selectListadoXColumnasDesktop(NumColumnas numColumnas)	throws Exception {
		((PageGaleriaDesktop)pageGaleria).clickLinkColumnas(numColumnas);
		checkIsVisibleLayoutListadoXcolumns(numColumnas);
	}
	
	@Validation
	private ChecksTM checkArticlesEqualsToPreviousGalery(
		int articulosComprobar, ListDataArticleGalery listArticlesGaleriaAnt, 
		ListDataArticleGalery listArticlesGaleriaAct, NumColumnas numColumnas) {
   		ChecksTM checks = ChecksTM.getNew();
   		
   		boolean articlesEquals = listArticlesGaleriaAct.isArticleListEquals(listArticlesGaleriaAnt, articulosComprobar);
   		String infoWarning = "";
   		if (!articlesEquals) {
   			DataArticleGalery articleGaleryActualNotFit = listArticlesGaleriaAct.getFirstArticleThatNotFitWith(listArticlesGaleriaAnt);
   			infoWarning+="<br><b style=\"color:" + State.Info.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería anterior (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
   			infoWarning+=listArticlesGaleriaAct.getTableHTLMCompareArticlesGaleria(listArticlesGaleriaAnt);
   		}
   		checks.add(
   			"Los primeros " + articulosComprobar + " artículos de la galería a " + 
   			numColumnas.name() + " columnas son iguales a los de la anterior galería" + infoWarning,
   			articlesEquals, State.Info);
   		
   		return checks;
	}
	
	@Validation (
		description="Aparece el layout correspondiente al listado a <b>#{numColumnas.name()} columnas</b>",
		level=State.Warn)
	private boolean checkIsVisibleLayoutListadoXcolumns(NumColumnas numColumnas) {
		return (pageGaleria.getLayoutNumColumnas()==((PageGaleriaDesktop)pageGaleria).getNumColumnas(numColumnas));
	}
   
	@Validation
	public ChecksTM validaNombresYRefEnOrden(NodoStatus nodoAnt, NodoStatus nodoAct) {
   		ChecksTM checks = ChecksTM.getNew();
   		checks.add(
			"El número de artículos de la galería Nuevo (" + nodoAct.getArticlesNuevo().size() + ") es igual al del nodo " + 
			nodoAnt.getIp() + " (" + nodoAnt.getArticlesNuevo().size() + ")",
			nodoAct.getArticlesNuevo().size()==nodoAnt.getArticlesNuevo().size(), State.Warn);
   		
   		DataArticleGalery articleGaleryActualNotFit = nodoAct.getArticleNuevoThatNotFitWith(nodoAnt);
   		String messageWarning = "";
		if (articleGaleryActualNotFit!=null) {
			messageWarning+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay productos de la galería que no cuadran con los de la galería del nodo " + nodoAnt.getIp() + " (por ejemplo <b>" + articleGaleryActualNotFit.toString() + "</b>). ";
			messageWarning+=nodoAct.getArticlesNuevo().getTableHTLMCompareArticlesGaleria(nodoAnt.getArticlesNuevo());
		}
   		checks.add(
			"El orden y contenido de los artículos en ambos nodos es el mismo" + messageWarning,
			articleGaleryActualNotFit==null, State.Warn);
	   
   		return checks;
	}

	@SuppressWarnings("static-access")
	@Validation
	public ChecksTM validaRebajasHasta70Jun2018(IdiomaPais idioma) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"<b style=\"color:blue\">Rebajas</b></br>" +
	 		"Es visible el banner de cabecera",
	 		new PageGaleriaDesktop().getSecBannerHead().isVisible(), State.Defect);
	 	
	 	String maxPercDiscount = "70";
	 	String textBanner = new PageGaleriaDesktop().getSecBannerHead().getText();
	 	checks.add(
	 		"El banner de cabecera contiene el porcentaje de descuento<b>" + maxPercDiscount + "</b>",
	 		UtilsTest.textContainsSetenta(textBanner, idioma), State.Warn);
	 	
	 	SecMenusDesktop secMenus = new SecMenusDesktop(app, channel);
	 	int menusDescVisibles = secMenus.secMenusFiltroDiscount.getNumberOfVisibleMenus();
	 	checks.add(
	 		"No aparece ningún filtro de descuento",
	 		menusDescVisibles==0, State.Warn);
	 	
	 	return checks;
	}
	
	@Validation (
		description="Estamos en la página de Galería",
		level=State.Warn)
	private boolean checkIsPageGaleria(WebDriver driver) {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
		return (pageGaleriaDesktop.isPage());   
	}

	@Validation
	private ChecksTM checkFiltrosSalesOnInGalerySale(SecMenusFiltroCollection filtrosCollection) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
	 		"<b style=\"color:blue\">Rebajas</b></br>" +
	 		"Son visibles los menús laterales de filtro a nivel detemporadas (Collection)",
	 		filtrosCollection.isVisible(), State.Defect);
	 	
	 	checks.add(
	 		"Aparece el filtro para todas las temporadas <b>All</b>)",
	 		filtrosCollection.isVisibleMenu(FilterCollection.all), State.Warn);
	 	
	 	checks.add(
	 		"Aparece el filtro para las ofertas <b>Sale</b>",
	 		filtrosCollection.isVisibleMenu(FilterCollection.sale), State.Warn);
	 	
	 	checks.add(
	 		"Aparece el filtro para las ofertas <b>Sale</b>",
	 		filtrosCollection.isVisibleMenu(FilterCollection.sale), State.Warn);
	 	
	 	checks.add(
	 	    Check.make(
	 		    "Aparece el filtro para la nueva temporada <b>Next season preview</b>",
	 		    filtrosCollection.isVisibleMenu(FilterCollection.nextSeason), State.Info)
	 	    .store(StoreType.None).build());
	 	
	 	return checks;
	}
		  
	@Validation (
		description=
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"No son visibles los menús laterales de filtro a nivel detemporadas (Collection)<b>",
		level=State.Defect)
	private boolean checkFiltrosSaleInGaleryNoSale(SecMenusFiltroCollection filtrosCollection) {
		return (!filtrosCollection.isVisible());
	}
	
	@Validation (
		description=
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"No aparece el filtro para las ofertas <b>Sale</b>",
		level=State.Warn)
	private boolean checkFiltrosSalesOff(SecMenusFiltroCollection filtrosCollection) {
		return (!filtrosCollection.isVisibleMenu(FilterCollection.sale)); 
	}
  
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, State levelError, StoreType store) {
	   validaArticlesOfTemporadas(listTemporadas, false, levelError, store);
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas) {
	   validaArticlesOfTemporadas(listTemporadas, false, State.Warn, StoreType.Evidences);
   }
   
   public void validaArticlesOfTemporadas(List<Integer> listTemporadas, boolean validaNotNewArticles) {
	   validaArticlesOfTemporadas(listTemporadas, validaNotNewArticles, State.Warn, StoreType.Evidences);
   }
   
   @Validation
   public ChecksTM validaArticlesOfTemporadas(List<Integer> listTemporadas, boolean validaNotNewArticles, 
		   										  State levelError, StoreType store) {
	   ChecksTM checks = ChecksTM.getNew();
	   	
	   PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
	   List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadasX(ControlTemporada.articlesFromOther, listTemporadas);
	   if (validaNotNewArticles) {
	 	  listArtWrong = PageGaleria.getNotNewArticlesFrom(listArtWrong);
	   }
	   String validaNotNewArticlesStr = "";
	   if (validaNotNewArticles) {
	   	validaNotNewArticlesStr = " y no contienen alguna de las etiquetas de artículo nuevo (" + PageGaleria.listLabelsNew + ")";
	   }
	   String infoWarning = "";
	   if (listArtWrong.size() > 0) {
		   infoWarning+=
			   "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
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
	   		   listArtWrong.size()==0, levelError)
           .store(store).build());
	   	
	   return checks;   
   }
   
   @Validation
   public ChecksTM validaNotArticlesOfTypeDesktop(TypeArticle typeArticle, State levelError, StoreType store) {
	   	ChecksTM checks = ChecksTM.getNew();
	   	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)pageGaleria;
	   	List<String> listArtWrong = pageGaleriaDesktop.getArticlesOfType(typeArticle);
	   	checks.add(
	        Check.make(
	   		    "<b style=\"color:blue\">Rebajas</b></br>" +
	   		    "No hay ningún artículo del tipo <b>" + typeArticle + "</b>",
	   		    listArtWrong.size()==0, levelError)
	        .store(store).build());
	        
   		if (listArtWrong.size() > 0) {
   			addInfoArtWrongToDescription(listArtWrong, typeArticle, checks.get(0));
   		}
	   	return checks; 
   }   
   
   private void addInfoArtWrongToDescription(List<String> listArtWrong, TypeArticle typeArticle, Check validation) {
	   String textToAdd =
		   "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
		   "hay " + listArtWrong.size() + " artículos que son del tipo <b>" + typeArticle + "</b><br>:";
	   for (String nameWrong : listArtWrong) {
		   textToAdd+=(nameWrong + "<br>");
	   }
	   textToAdd+="</lin>";
	   String descriptionOrigin = validation.getDescription();
	   validation.setDescription(descriptionOrigin + textToAdd);
   }
   
	@SuppressWarnings("static-access")
	@Step (
		description="Seleccionamos el link <b>Más Info</b>", 
		expected="Se hace visible el aviso legal")
	public static void clickMoreInfoBannerRebajasJun2018(WebDriver driver) {
		new PageGaleriaDesktop().getSecBannerHead().clickLinkInfoRebajas();
		checkAfterClickInfoRebajas(driver);
	}
	
	@SuppressWarnings("static-access")
	@Validation
	private static ChecksTM checkAfterClickInfoRebajas(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		SecBannerHeadGallery secBannerHead = new PageGaleriaDesktop().getSecBannerHead();
		int maxSecondsToWait = 1;
		checks.add(
			"<b style=\"color:blue\">Rebajas</b></br>" +
			"Se despliega la información relativa a las rebajas (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			secBannerHead.isVisibleInfoRebajasUntil(maxSecondsToWait), State.Warn);
		
		checks.add(
			"Aparece el link de <b>Menos info</b>",
			secBannerHead.isVisibleLinkTextInfoRebajas(TypeLinkInfo.LESS), State.Warn);
		
		return checks;
	}
	
	@Validation
	public ChecksTM validateGaleriaAfeterSelectMenu(AppEcom app) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWaitArticle = 8;
		int maxSecondsToWaitIcon = 2;
		checks.add (
			"Como mínimo se obtiene un artículo (lo esperamos hasta " + maxSecondsToWaitArticle + " segundos)",
			pageGaleria.isVisibleArticleUntil(1, maxSecondsToWaitArticle), State.Warn);
		
		if (app==AppEcom.shop) {
			checks.add (
				"El 1er artículo tiene 1 icono de favorito asociado (lo esperamos hasta " + maxSecondsToWaitIcon + " segundos)",
				pageGaleria.isArticleWithHearthIconPresentUntil(1, maxSecondsToWaitIcon), State.Warn);
			
			checks.add (
		        Check.make(
				    "Cada artículo tiene 1 icono de favoritos asociado",
				    pageGaleria.eachArticlesHasOneFavoriteIcon(), State.Info)
		        .store(StoreType.None).build());
		}
		
		return checks;
	}
	
	@SuppressWarnings("static-access")
	@Validation
	public ChecksTM checkVisibilitySubmenus(List<Menu2onLevel> menus2onLevel) {
		ChecksTM checks = ChecksTM.getNew();
		for (Menu2onLevel menu2oNivelTmp : menus2onLevel) {
			checks.add(
				"Aparece el submenú <b>" + menu2oNivelTmp.getNombre() + "</b>",
				((PageGaleriaDesktop)pageGaleria).getSecSubmenusGallery().isVisibleSubmenu(menu2oNivelTmp.getNombre()), 
				State.Warn);
		}
		return checks;
	}
}