package com.mng.robotest.test80.mango.test.getdata.products;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.JaxRsClient;
import com.mng.robotest.test80.mango.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.ProductList;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class GetterProducts extends JaxRsClient {
	
	private final String urlForJavaCall;
	private final String urlForBrowserCall;
	private final String saleType;
	private final String codigoPaisAlf;
	private final AppEcom app;
	private final LineaType lineaType;
	private final List<MenuProduct> menusCandidates;
	private final Integer numProducts;
	private final Integer pagina;
	private final WebDriver driver;
	private final MethodGetter method;
	private final boolean retryPro;
	private final ProductList productList;
	private final ProductFilter productFilter;
	
	public enum MethodGetter {ApiRest, WebDriver, Any}
	
	private GetterProducts(
			String url, String codigoPaisAlf, AppEcom app, LineaType lineaType, List<MenuProduct> menusCandidates, 
			Integer numProducts, Integer pagina, MethodGetter method, boolean retryPro, WebDriver driver) throws Exception {
		
		urlForJavaCall = getUrlForJavaCall(url, driver);
		urlForBrowserCall = getUrlBase(url);
		switch (app) {
		case votf:
			this.saleType = "V";
			break;
		default:
			this.saleType = "";
		}
		
		this.codigoPaisAlf = codigoPaisAlf;
		this.app = app;
		this.lineaType = lineaType;
		this.menusCandidates = menusCandidates;
		this.numProducts = numProducts;
		this.pagina = pagina;
		this.method = method;
		this.retryPro = retryPro;
		this.driver = driver;
		this.productList = getProductList();
		this.productFilter = new ProductFilter(productList, app, urlForJavaCall);
	}
	
	public List<Garment> getAll() {
		return productList.getGroups().get(0).getGarments();
	}
	
	public List<Garment> getFiltered(FilterType filter) throws Exception {
		return getFiltered(Arrays.asList(filter));
	}
	
	public List<Garment> getFiltered(List<FilterType> listFilters) throws Exception {
		return productFilter.getListFiltered(listFilters);
	}
	
	public Optional<Garment> getOneFiltered(FilterType filter) throws Exception {
		return getOneFiltered(Arrays.asList(filter));
	}
	
	public Optional<Garment> getOneFiltered(List<FilterType> listFilters) throws Exception {
		return productFilter.getOneFiltered(listFilters);
	}
	
	private String getUrlForJavaCall(String initialURL, WebDriver driver) throws Exception {
		String cloudtestName = getNameCloudtestFromCookie(driver);
		if (cloudtestName==null) {
			return getUrlBase(initialURL);
		} else {
			return "https://" + cloudtestName + ".dev.mango.com/";
		}
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
	
	private ProductList getProductList() throws Exception {
		int sizeMenus = menusCandidates.size();
		for (int i=0; i<sizeMenus; i++) {
			MenuProduct menuCandidate = menusCandidates.get(i);
			try {
				ProductList productList = getProductList(menuCandidate);
				if (productList!=null) {
					Filter filterStock = new FilterStock();
					if (!filterStock.filter(productList.getGroups().get(0).getGarments()).isEmpty() ||
						(i+1) == sizeMenus) {
						return productList;
					}
				}
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem retriving articles of type " + menuCandidate + " for country " + codigoPaisAlf, e);
			}
		}
		return null;
	}
	
	private ProductList getProductList(MenuProduct menu) throws Exception {
		ProductList productList = getProductListFromUrl(menu);
		if (productList==null && retryPro) {
			productList = getProductListFromPro(menu);
		}
		return productList;
	}
	
	private ProductList getProductListFromUrl(MenuProduct menu) throws Exception {
		switch (method) {
		case ApiRest:
			return getProductsFromApiRest(menu);
		case WebDriver:
			return getProductsFromWebDriver(menu);
		default:
			ProductList productList = getProductsFromApiRest(menu);
			if (productList==null && driver!=null) {
				return getProductsFromWebDriver(menu);
			}
			return productList;
		}
	}
	
	private ProductList getProductListFromPro(MenuProduct menu) throws Exception {
		GetterProducts getterPro = new GetterProducts(
				"https://shop.mango.com/", 
				codigoPaisAlf, 
				app, 
				lineaType, 
				menusCandidates,
				numProducts, 
				pagina, 
				method, 
				false,
				driver);
		return getterPro.getProductList();
	}

	private String getNameCloudtestFromCookie(WebDriver driver) {
		if (driver==null) {
			return null;
		}
		Cookie cookieName = driver.manage().getCookieNamed("cloudtest-name");
		if (cookieName!=null) {
			return cookieName.getValue();
		}
		return null;
	}
	
	private WebTarget getWebTargetProductlist(String urlBase, MenuProduct menu) throws Exception {
		if ("intimissimi".compareTo(menu.getSeccion())==0) {
			return getWebTargetProductlistIntimissimi(urlBase, menu); 
		}
		return getWebTargetProductlistStandard(urlBase, menu); 
	}
	
	private WebTarget getWebTargetProductlistStandard(String urlBase, MenuProduct menu) throws Exception {
		Client client = getClientIgnoreCertificates();
		WebTarget webTarget = 
			client
				.target(urlBase.replace("http:", "https:") + "services/productlist/products")
				.path(codigoPaisAlf)
				.path(getLineaPath())
				.path("sections_" + lineaType.name() + "." + menu.getSeccion() + "_" + lineaType.name())
				.queryParam("idSubSection", menu.getGaleria() + "_" + lineaType.name())
				.queryParam("menu", "familia;" + menu.getFamilia())
				.queryParam("pageNum", pagina)
				.queryParam("columnsPerRow", "1")
				.queryParam("rowsPerPage", numProducts);
		
		if ("".compareTo(saleType)!=0) {
			webTarget = webTarget.queryParam("saleType", saleType);
		}
		return webTarget;
	}
	
	private WebTarget getWebTargetProductlistIntimissimi(String urlBase, MenuProduct menu) throws Exception {
		Client client = getClientIgnoreCertificates();
		WebTarget webTarget = 
			client
				.target(urlBase.replace("http:", "https:") + "ws-catalog-aggregator/desktop/product-list/products/v1")
				.path(codigoPaisAlf)
				.path(codigoPaisAlf.toLowerCase())
				.path(getLineaPath())
				.path("sections_" + lineaType.name() + "_Perfumes_imssm." + menu.getSeccion() + "_" + lineaType.name())
				.queryParam("idSubSection", menu.getGaleria() + "_" + lineaType.name())
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
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
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
			SeleniumUtils.loadUrlInAnotherTabTitle(urlGetProducts, nameTab, driver);
			if (PageObjTM.state(State.Visible, By.id("rawdata-tab"), driver).check()) {
				PageObjTM.click(By.id("rawdata-tab"), driver).exec();
			}
			By bodyPreBy = By.xpath("//body//pre");
			if (PageObjTM.state(State.Present, bodyPreBy, driver).check()) {
				body = driver.findElement(bodyPreBy).getText();
			}
		} 
		finally {
			SeleniumUtils.closeTabByTitleAndReturnToWidow(nameTab, idWindow, driver);
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
		ProductList productList = objectMapper.readValue(productsJson, ProductList.class);
		return productList;
	}
	
	private String getLineaPath() {
		switch (app) {
		case outlet:
			return "outlet";
		default:
			return lineaType.name();
		}
	}
	
	public static class Builder {
		private final String url;
		private final String codigoPaisAlf;
		private final AppEcom app;
		private final WebDriver driver;
		private LineaType lineaType = LineaType.she;
		private Integer numProducts = 40;
		private List<Menu> menusCandidates = 
			Arrays.asList(
				Menu.Shorts, 
				Menu.Camisas, 
				Menu.Pijamas,
				Menu.Faldas,
				Menu.Fulares);
		
		private Integer pagina = 1;
		private MethodGetter method = MethodGetter.Any;
		private boolean retryPro = true;

		public Builder(String codPaisAlf, AppEcom app, WebDriver driver) throws Exception {
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
		public Builder menusCandidates(List<Menu> menusCandidates) {
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
		public Builder method(MethodGetter method) {
			this.method = method;
			return this;
		}
		public Builder retryPro(boolean retryPro) {
			this.retryPro = retryPro;
			return this;
		}
		public GetterProducts build() throws Exception {
			return (
				new GetterProducts(url, codigoPaisAlf, app, lineaType, getMenusProduct(), numProducts, pagina, method, retryPro, driver));
		}
		
		private List<MenuProduct> getMenusProduct() {
			List<MenuProduct> listReturn = new ArrayList<>();
			for (Menu menu : menusCandidates) {
				listReturn.add(new MenuProduct(menu, app, url));
			}
			return listReturn;
		}
	}
	
	private static class MenuProduct {
		private final String seccion;
		private final String galeria;
		private final String familia;
		
		public MenuProduct(Menu menu, AppEcom app, String urlBase) {
			this.seccion = menu.getSeccion();
			this.galeria = menu.getGaleria();
			this.familia = menu.getFamilia(app, isPro(app, urlBase));
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
		
		private boolean isPro(AppEcom app, String urlBase) {
			return UtilsMangoTest.isEntornoPRO(app, urlBase);
		}
	}
}
