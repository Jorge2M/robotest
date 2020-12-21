package com.mng.robotest.test80.mango.test.getdata.products;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
//import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.JaxRsClient;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.GarmentDetails;
import com.mng.robotest.test80.mango.test.getdata.products.data.ProductList;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;


public class GetterProducts extends JaxRsClient {
	
	private final String urlForJavaCall;
	private final String urlForBrowserCall;
	private final String saleType;
	private final String codigoPaisAlf;
	private final AppEcom app;
	private final LineaType lineaType;
	private final String seccion;
	private final String galeria;
	private final String familia;
	private final Integer numProducts;
	private final Integer pagina;
	private final WebDriver driver;
	private ProductList productList;
	
	public enum MethodGetter {ApiRest, WebDriver, Any}
	
	private GetterProducts(String url, String codigoPaisAlf, AppEcom app, LineaType lineaType, String seccion, String galeria, 
						   String familia, Integer numProducts, Integer pagina, MethodGetter method, WebDriver driver) throws Exception {
		
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
		this.seccion = seccion;
		this.galeria = galeria;
		this.familia = familia;
		this.numProducts = numProducts;
		this.pagina = pagina;
		this.driver = driver;
		this.productList = getProductList();
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
	
	public ProductList getProductList(MethodGetter method) throws Exception {
		switch (method) {
		case ApiRest:
			return getProductsFromApiRest();
		case WebDriver:
			return getProductsFromWebDriver();
		default:
			return getProductList();
		}
	}
	
	private ProductList getProductList() throws Exception {
		ProductList productList = getProductsFromApiRest();
		if (productList==null && driver!=null) {
			return getProductsFromWebDriver();
		}
		return productList;
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
	
	private WebTarget getWebTargetProductlist(String urlBase) throws Exception {
		Client client = getClientIgnoreCertificates();
		WebTarget webTarget = 
			client
				.target(urlBase.replace("http:", "https:") + "services/productlist/products")
				.path(codigoPaisAlf)
				.path(getLineaPath())
				.path("sections_" + lineaType.name() + "." + seccion + "_" + lineaType.name())
				.queryParam("idSubSection", galeria + "_" + lineaType.name())
				.queryParam("menu", "familia;" + familia)
				.queryParam("pageNum", pagina)
				.queryParam("columnsPerRow", "1")
				.queryParam("rowsPerPage", numProducts);
		
		if ("".compareTo(saleType)!=0) {
			webTarget = webTarget.queryParam("saleType", saleType);
		}
		return webTarget;
	}
	
	private ProductList getProductsFromApiRest() throws Exception {
		WebTarget webTarget = getWebTargetProductlist(urlForJavaCall);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String productsJson = response.readEntity(String.class);
			if (productsJson!=null && productsJson.contains("garments")) {
				return getProductsFromJson(productsJson);
			}
		}
		return null;
	}
	
	private ProductList getProductsFromWebDriver() throws Exception {
		WebTarget webTarget = getWebTargetProductlist(urlForBrowserCall);
		String urlGetProducts = webTarget.getUri().toURL().toString();
		
		String nameTab = "GetProducts";
		String idWindow = driver.getWindowHandle();
		SeleniumUtils.loadUrlInAnotherTabTitle(urlGetProducts, nameTab, driver);
		String body = driver.findElement(By.xpath("//body/pre")).getText();
		SeleniumUtils.closeTabByTitleAndReturnToWidow(nameTab, idWindow, driver);
		
		return getProductsFromJson(body);
	}
	
	private ProductList getProductsFromJson(String productsJson) throws Exception {
		//Without that modification Jackson can't parse the JSON
		productsJson = productsJson.replace("\"garments\":{", "\"garments\":[");
		productsJson = productsJson.replace("}}}]", "}]}]");
		productsJson = productsJson.replaceAll("\"g[0-9]{8}(..){0,1}\":", "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ProductList productList = objectMapper.readValue(productsJson, ProductList.class);
		return productList;
	}

	public List<Garment> getAll() {
		return productList.getGroups().get(0).getGarments();
	}
	public List<Garment> getWithManyColors() {
		List<Garment> listGarmentsWithManyColors = new ArrayList<>();
		List<Garment> listGarments = getAll();
		for (Garment garment : listGarments) {
			if (garment.getColors().size() > 1) {
				listGarmentsWithManyColors.add(garment);
			}
		}
		return listGarmentsWithManyColors;
	}
	
	public List<Garment> getWithStock() {
		List<Garment> listGarmentsWithStock = new ArrayList<>();
		List<Garment> listGarments = getAll();
		for (Garment garment : listGarments) {
			if (garment.getStock() > 0) {
				listGarmentsWithStock.add(garment);
			}
		}
		return listGarmentsWithStock;
	}
	
	public Garment getOneWithTotalLook(WebDriver driver) throws Exception {
		List<Garment> listGarments = getAll();
		for (Garment garment : listGarments) {
			if (getTotalLookGarment(garment, driver)!=null) {
				return garment;
			}
		}
		return null;
	}
	
	private GarmentDetails getTotalLookGarment(Garment product, WebDriver driver) throws Exception {
		WebTarget webTarget = getWebTargetTotalLookGarment(product);
		Response response = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", getIdStockNormalized())
				.get();
		
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			return response.readEntity(GarmentDetails.class);
		}
		return null;
	}
	
	private WebTarget getWebTargetTotalLookGarment(Garment product) {
		Article article = product.getArticleWithMoreStock();
		Client client = ClientBuilder.newClient();
		return ( 
			client
				.target(urlForJavaCall.replace("http:", "https:") + "services/garments")
				.path(article.getGarmentId())
				.path("looktotal")
				.queryParam("color", article.getColor().getId()));
	}
	
	private String getIdStockNormalized() {
		if (app==AppEcom.votf) {
			//Por algún motivo que no entiendo, falla "001.ES.0.false.true.v0" pero funciona "001.ES.0.false.false.v0"
			return (productList.getStockId().replace("false.true", "false.false"));
		}
		return productList.getStockId();
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
		private String seccion = "prendas";
		private String galeria = "camisas";
		private String familia = "14";
		private Integer numProducts = 40;
		private Integer pagina = 1;
		private MethodGetter method = MethodGetter.Any;

		public Builder(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
			this.url = ((InputParamsMango)TestMaker.getTestCase().getInputParamsSuite()).getUrlBase();
			//this.url = dCtxSh.getDnsUrlAcceso();
			this.codigoPaisAlf = dCtxSh.pais.getCodigo_alf();
			this.app = dCtxSh.appE;
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
		public Builder seccion(String seccion) {
			this.seccion = seccion;
			return this;
		}
		public Builder galeria(String galeria) {
			this.galeria = galeria;
			return this;
		}
		public Builder familia(String familia) {
			this.familia = familia;
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
		public GetterProducts build() throws Exception {
			return (
				new GetterProducts(url, codigoPaisAlf, app, lineaType, seccion, galeria, familia, numProducts, pagina, method, driver));
		}
	}
}
