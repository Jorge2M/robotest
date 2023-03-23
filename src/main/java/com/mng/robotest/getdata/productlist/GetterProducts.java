package com.mng.robotest.getdata.productlist;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.getdata.UtilsData;
import com.mng.robotest.getdata.canonicalproduct.GetterProductApiCanonical;
import com.mng.robotest.getdata.canonicalproduct.entity.EntityProduct;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.ProductList;
import com.mng.robotest.getdata.productlist.filter.FilterStock;
import com.mng.robotest.getdata.productlist.sort.SortFactory.SortBy;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class GetterProducts {
	
	private final String urlForJavaCall;
	private final String urlForBrowserCall;
	private final String nameCloudTest;
	private final String saleType;
	private final String codigoPaisAlf;
	private final String codigoIdiomAlf;
	private final AppEcom app;
	private final LineaType lineaType;
	private final List<MenuProduct> menusCandidates;
	private final Integer numProducts;
	private final Integer minProducts;
	private final Integer pagina;
	private final List<FilterType> filters;
	private final SortBy sortBy;
	private final boolean extraCanonicalInfo;
	private final WebDriver driver;
	private final MethodGetter method;
	private final boolean retryPro;
	private final ProductList productList;
	private final ProductFilter productFilter;
	
	public enum MethodGetter { API_REST, WEBDRIVER, ANY }
	
	private GetterProducts(
			String url, 
			String codigoPaisAlf,
			String codigoIdiomAlf,
			AppEcom app, 
			LineaType lineaType, 
			List<MenuProduct> menusCandidates, 
			Integer numProducts, 
			Integer minProducts, 
			Integer pagina, 
			MethodGetter method, 
			boolean retryPro, 
			List<FilterType> filters,
			SortBy sortBy,
			boolean extraCanonicalInfo,
			WebDriver driver) throws Exception {
		
		urlForJavaCall = getUrlForJavaCall(url);
		urlForBrowserCall = getUrlBase(url);
		nameCloudTest = UtilsData.getNameCloudTest(url);
		if (app==AppEcom.votf) {
			this.saleType = "V";
		} else {
			this.saleType = "";
		}
		
		this.codigoPaisAlf = codigoPaisAlf;
		this.codigoIdiomAlf = codigoIdiomAlf;
		this.app = app;
		this.lineaType = lineaType;
		this.menusCandidates = menusCandidates;
		this.numProducts = numProducts;
		this.minProducts = minProducts;
		this.pagina = pagina;
		this.method = method;
		this.retryPro = retryPro;
		this.driver = driver;
		this.filters = filters;
		this.sortBy = sortBy;
		this.extraCanonicalInfo = extraCanonicalInfo;
		this.productList = getProductList();
		this.productFilter = new ProductFilter(productList, app, urlForJavaCall);
	}
	
	public List<GarmentCatalog> getAll() throws Exception {
		if (productList==null) {
			return new ArrayList<>();
		}
		List<GarmentCatalog> allGarments;
		if (filters!=null && !filters.isEmpty()) {
			allGarments = getAll(filters);
		} else {
			allGarments = productList.getAllGarments(sortBy);
		}
		return allGarments;
	}
	public Optional<GarmentCatalog> getOne() throws Exception {
		if (productList==null) {
			return Optional.empty();
		}
		if (filters!=null && !filters.isEmpty()) {
			return getOne(filters);
		}
		List<GarmentCatalog> allGarments = productList.getAllGarments(sortBy);
		return Optional.of(allGarments.get(0));
	}
	
	public List<GarmentCatalog> getAll(List<FilterType> filters) throws Exception {
		return productFilter.getListFiltered(filters, sortBy);
	}
	
	public Optional<GarmentCatalog> getOne(List<FilterType> filters) throws Exception {
		return productFilter.getOneFiltered(filters, sortBy);
	}
	
	private String getUrlForJavaCall(String initialURL) throws Exception {
		return getUrlBase(initialURL);
	}
	
	private String getUrlBase(String initialURL) throws Exception {
		URI uri = new URI(initialURL);
		String urlTmp = (uri.getScheme() + "://" + uri.getHost());
		if (urlTmp.charAt(urlTmp.length()-1)=='/') {
			return urlTmp;
		} else {
			return urlTmp + "/";
		}
	}
	
	public ProductList getProductList() throws Exception {
		if (productList!=null) {
			return productList;
		}
		ProductList productListReturn = null;
		int sizeMenus = menusCandidates.size();
		for (int i=0; i<sizeMenus; i++) {
			boolean lastMenu = (i+1) == sizeMenus;
			Optional<ProductList> productListMenu = getProductsFromMenu(menusCandidates.get(i), !lastMenu);
			if (productListMenu.isPresent()) {
				if (productListReturn==null) {
					productListReturn = productListMenu.get();
				} else {
					productListReturn.addGroups(productListMenu.get().getGroups());
				}
			}
			if (productListReturn==null ||
				productListReturn.getAllGarments(sortBy)==null ||
				productListReturn.getAllGarments(sortBy).size()>=minProducts) {
				break;
			}
		}
		
		if (extraCanonicalInfo && productListReturn!=null) {
			addCanonicalInfo(productListReturn);
		}
		return productListReturn;
	}
	
	private void addCanonicalInfo(ProductList productList) throws Exception {
		Pais pais = PaisGetter.fromCodAlf(codigoPaisAlf);
		var getterProductApiCanonical = new GetterProductApiCanonical(codigoPaisAlf, pais.getCodigo_alf(), app.name());
		for (GarmentCatalog garmentCatalog : productList.getAllGarments()) {
			Optional<EntityProduct> canonicalProduct = getterProductApiCanonical.getProduct(garmentCatalog.getGarmentId());
			if (canonicalProduct.isPresent()) {
				garmentCatalog.setCanonicalProduct(canonicalProduct.get());
			}
		}
	}

	private Optional<ProductList> getProductsFromMenu(MenuProduct menuCandidate, boolean withStock) {
		try {
			ProductList productListMenu = getProductList(menuCandidate);
			if (productListMenu!=null && 
			   (hasProductsWithStock(productListMenu) || !withStock)) {
				return Optional.of(productListMenu);
			}
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem retriving articles of type {} for country {}. {}", menuCandidate, codigoPaisAlf, e);
		}
		return Optional.empty();
	}
	
	private boolean hasProductsWithStock(ProductList productList) throws Exception {
		return !new FilterStock().filter(productList.getAllGarments(sortBy)).isEmpty();
	}
	
	private ProductList getProductList(MenuProduct menu) throws Exception {
		ProductList productListUrl = getProductListFromUrl(menu);
		if (productListUrl==null && retryPro) {
			productListUrl = getProductListFromPro();
		}
		return productListUrl;
	}
	
	private ProductList getProductListFromUrl(MenuProduct menu) throws Exception {
		switch (method) {
		case API_REST:
			return getProductsFromApiRest(menu);
		case WEBDRIVER:
			return getProductsFromWebDriver(menu);
		default:
			ProductList productListApi = getProductsFromApiRest(menu);
			if (productListApi==null && driver!=null) {
				return getProductsFromWebDriver(menu);
			}
			return productListApi;
		}
	}
	
	private ProductList getProductListFromPro() throws Exception {
		var getterPro = new GetterProducts(
				"https://shop.mango.com/", 
				codigoPaisAlf,
				codigoIdiomAlf,
				app, 
				lineaType, 
				menusCandidates,
				numProducts, 
				3,
				pagina, 
				method, 
				false,
				filters,
				sortBy,
				extraCanonicalInfo,
				driver);
		return getterPro.getProductList();
	}

	private WebTarget getWebTargetProductlist(String urlBase, MenuProduct menu) {
		if ("intimissimi".compareTo(menu.getSeccion())==0) {
			return getWebTargetProductlistIntimissimi(urlBase, menu); 
		}
		return getWebTargetProductlistStandard(urlBase, menu); 
	}
	
	private WebTarget getWebTargetProductlistStandard(String urlBase, MenuProduct menu) {
		Client client = ClientBuilder.newBuilder().build();
		WebTarget webTarget = 
			client
				.target(urlBase.replace("http:", "https:") + "services/productlist/products")
				.path(getPathPaisIdioma())
				.path(getLineaPath());
		
		if (menu.getGaleria()!=null && "".compareTo(menu.getGaleria())!=0) {
			webTarget = webTarget
				.path("sections_" + lineaType.name().toLowerCase() + "." + menu.getSeccion() + "_" + lineaType.name().toLowerCase());
		} else {
			webTarget = webTarget
				.path("sections_" + lineaType.name().toLowerCase() + "." + menu.getSeccion());			
		}
		
		if (menu.getFamilia()!=null && "".compareTo(menu.getFamilia())!=0) {
			String tag = "familia";
			if (menu.getSeccion().contains("accesorio")) {
				tag = "accesorio";
			}
			webTarget = webTarget
				.queryParam("idSubSection", menu.getGaleria() + "_" + lineaType.name().toLowerCase())
				.queryParam("menu", tag + ";" + menu.getFamilia());
		}
		
		webTarget = webTarget
			.queryParam("pageNum", pagina)
			.queryParam("columnsPerRow", "1")
			.queryParam("rowsPerPage", numProducts);
		
		if ("".compareTo(nameCloudTest)!=0) {
			webTarget = webTarget.queryParam("name", nameCloudTest);
		}
		if ("".compareTo(saleType)!=0) { 
			webTarget = webTarget.queryParam("saleType", saleType);
		}
		return webTarget;
	}
	
	private String getPathPaisIdioma() {
		if (codigoIdiomAlf==null || "".compareTo(codigoIdiomAlf)==0) {
			return codigoPaisAlf;
		}
		return codigoPaisAlf + "-" + codigoIdiomAlf.toLowerCase();
	}
	
	private WebTarget getWebTargetProductlistIntimissimi(String urlBase, MenuProduct menu) {
		Client client = ClientBuilder.newBuilder().build();
		WebTarget webTarget = 
			client
				.target(urlBase.replace("http:", "https:") + "ws-catalog-aggregator/desktop/product-list/products/v1")
				.path(codigoPaisAlf)
				.path(codigoPaisAlf.toLowerCase())
				.path(getLineaPath())
				.path("sections_" + lineaType.name().toLowerCase() + "_Perfumes_imssm." + menu.getSeccion() + "_" + lineaType.name().toLowerCase())
				.queryParam("idSubSection", menu.getGaleria() + "_" + lineaType.name().toLowerCase())
				.queryParam("menu", "familia;" + menu.getFamilia())
				.queryParam("pageNum", pagina)
				.queryParam("columnsPerRow", "1")
				.queryParam("rowsPerPage", numProducts);
		
		if ("".compareTo(saleType)!=0) {
			webTarget = webTarget.queryParam("saleType", saleType);
		}
		return webTarget;
	}
	
	private ProductList getProductsFromApiRest(MenuProduct menu) throws Exception {
		WebTarget webTarget = getWebTargetProductlist(urlForJavaCall, menu);
		Invocation.Builder builder = webTarget
				.request(MediaType.APPLICATION_JSON);
		
		if ("".compareTo(nameCloudTest)!=0) {
			builder = builder.cookie("cloudtest-name", nameCloudTest);
		}
		Response response = builder.get();
		
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String productsJson = response.readEntity(String.class);
			if (productsJson!=null && productsJson.contains("garments")) {
				return getProductsFromJson(productsJson);
			}
		}
		return null;
	}
	
	private ProductList getProductsFromWebDriver(MenuProduct menu) throws Exception {
		WebTarget webTarget = getWebTargetProductlist(urlForBrowserCall, menu);
		String urlGetProducts = webTarget.getUri().toURL().toString();
		
		String nameTab = "GetProducts";
		String idWindow = driver.getWindowHandle();
		String body = null;
		try {
			PageObjTM.loadUrlInAnotherTabTitle(urlGetProducts, nameTab, driver);
			var page = new PageBase(driver);
			if (page.state(State.Visible, By.id("rawdata-tab")).check()) {
				page.click(By.id("rawdata-tab")).exec();
			}
			By bodyPreBy = By.xpath("//body//pre");
			if (page.state(State.Present, bodyPreBy).check()) {
				body = driver.findElement(bodyPreBy).getText();
			}
		} 
		finally {
			PageObjTM.closeTabByTitleAndReturnToWidow(nameTab, idWindow, driver);
		}
		
		if (body!=null) {
			return getProductsFromJson(body);
		}
		return null;
	}
	
	private ProductList getProductsFromJson(String productsJson) throws Exception {
		//Without that modification Jackson can't parse the JSON
		productsJson = productsJson.replace("\"garments\":{", "\"garments\":[");
		productsJson = productsJson.replace("}}}]", "}]}]");
		productsJson = productsJson.replace("}}},", "}]},");
		productsJson = productsJson.replaceAll("\"g[0-9]{8}(..){0,1}\":", "");
		productsJson = productsJson.replaceAll("\"g[0-9]{10}(..){0,1}\":", "");
		productsJson = productsJson.replaceAll("\"g[0-9]{8}(..)(..){0,1}\":", "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(productsJson, ProductList.class);
	}
	
	private String getLineaPath() {
		if (app==AppEcom.outlet) {
			return "outlet";
		} else {
			return lineaType.name().toLowerCase();
		}
	}
	
	public static class Builder {
		private final String url;
		private final String codigoPaisAlf;
		private final AppEcom app;
		private final WebDriver driver;
		private LineaType lineaType = LineaType.SHE;
		private Integer numProducts = 40;
		private Integer minProducts = 3;
		private String codigoIdiomAlf = "";
		private List<FilterType> filters = new ArrayList<>();
		private SortBy sortBy;
		private boolean extraCanonicalInfo;
		private List<MenuI> menusCandidates = 
			Arrays.asList(
				Menu.CAMISAS, 
				Menu.PIJAMAS,
				Menu.FALDAS,
				Menu.SHORTS,
				Menu.FULARES);
		
		private Integer pagina = 1;
		private MethodGetter method = MethodGetter.ANY;
		private boolean retryPro = true;

		public Builder(String codPaisAlf, AppEcom app, WebDriver driver) {
			this.url = ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase();
			this.codigoPaisAlf = codPaisAlf;
			this.app = app;
			this.driver = driver;
		}
		
		public Builder(String url, String codigoPaisAlf, AppEcom app, WebDriver driver) {
			this.url = url;
			this.codigoPaisAlf = codigoPaisAlf;
			this.app = app;
			this.driver = driver;
		}
		
		public Builder linea(LineaType lineaType) {
			this.lineaType = lineaType;
			return this;
		}
		public Builder menusCandidates(List<MenuI> menusCandidates) {
			this.menusCandidates = menusCandidates;
			return this;
		}		
		public Builder menu(Menu menu) {
			this.menusCandidates = Arrays.asList(menu);
			return this;
		}
		public Builder pagina(Integer pagina) {
			this.pagina = pagina;
			return this;
		}
		public Builder numProducts(Integer numProducts) {
			this.numProducts = numProducts;
			return this;
		}
		public Builder minProducts(Integer minProducts) {
			this.minProducts = minProducts;
			return this;
		}
		public Builder idiom(String codIdiomAlf) {
			this.codigoIdiomAlf = codIdiomAlf;
			return this;
		}
		public Builder method(MethodGetter method) {
			this.method = method;
			return this;
		}
		public Builder retryPro(boolean retryPro) {
			this.retryPro = retryPro;
			return this;
		}
		public Builder filters(List<FilterType> filters) {
			this.filters = filters;
			return this;
		}
		public Builder filter(FilterType filter) {
			if (this.filters==null) {
				this.filters = Arrays.asList(filter);
			} else {
				this.filters.add(filter);
			}
			return this;
		}
		public Builder sortBy(SortBy sortBy) {
			this.sortBy = sortBy;
			return this;
		}
		public Builder extraCanonicalInfo(boolean extraCanonicalInfo) {
			this.extraCanonicalInfo = extraCanonicalInfo;
			return this;
		}
		public GetterProducts build() throws Exception {
			//TODO eliminar cuando esos 2 productos funcionen correctamente
	        List<FilterType> filtersNew = new ArrayList<>(filters);
	        filtersNew.add(FilterType.BLACK_LIST);
	        filters = filtersNew;
			
			return (
				new GetterProducts(
						url, 
						codigoPaisAlf,
						codigoIdiomAlf,
						app, 
						lineaType, 
						getMenusProduct(), 
						numProducts, 
						minProducts, 
						pagina, 
						method, 
						retryPro, 
						filters,
						sortBy,
						extraCanonicalInfo,
						driver));
		}
		
		private List<MenuProduct> getMenusProduct() {
			List<MenuProduct> listReturn = new ArrayList<>();
			for (MenuI menu : menusCandidates) {
				listReturn.add(new MenuProduct(menu, app));
			}
			return listReturn;
		}
	}
	
	private static class MenuProduct {
		private final String seccion;
		private final String galeria;
		private final String familia;
		
		public MenuProduct(MenuI menu, AppEcom app) {
			this.seccion = menu.getSeccion();
			this.galeria = menu.getGaleria();
			this.familia = menu.getFamilia(app, isPro());
		}
		
		public String getSeccion() {
			return this.seccion;
		}
		public String getGaleria() {
			return this.galeria;
		}
		public String getFamilia() {
			return this.familia;
		}
		
		private boolean isPro() {
			try {
				return new PageBase().isPRO();
			}
			catch (Exception e) {
				return false;
			}
		}
	}
}
