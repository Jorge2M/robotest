package com.mng.robotest.test80.mango.test.getdata.products;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.By;
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
	
	private final String urlDomain;
	private final String saleType;
	private final String codigoPaisAlf;
	private final AppEcom app;
	private final LineaType lineaType;
	private final String seccion;
	private final String galeria;
	private final String familia;
	private final Integer numProducts;
	private final Integer pagina;
	private final ProductList productList;
	
	private GetterProducts(String url, String codigoPaisAlf, AppEcom app, LineaType lineaType, String seccion, 
						   String galeria, String familia, Integer numProducts, Integer pagina, WebDriver driver) throws Exception {
		String urlTmp = getDnsUrl(url);
		if (urlTmp.charAt(urlTmp.length()-1)=='/') {
			urlDomain = urlTmp;
		} else {
			urlDomain = urlTmp + "/";
		}
		
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
		this.productList = getProductList(driver);
	}
	
	private ProductList getProductList(WebDriver driver) throws Exception {
		WebTarget webTarget = getWebTargetProductlist();
		String productsJson = getProductsFromApiRest(webTarget);
		if (productsJson==null && driver!=null) {
			String urlGetProducts = webTarget.getUri().toURL().toString();
			productsJson = getProductsFromWebDriver(urlGetProducts, driver);
		}
		
		//Without that modification Jackson can't parse the JSON
		productsJson = productsJson.replace("\"garments\":{", "\"garments\":[");
		productsJson = productsJson.replace("}}}]", "}]}]");
		productsJson = productsJson.replaceAll("\"g[0-9]{8}(..){0,1}\":", "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ProductList productList = objectMapper.readValue(productsJson, ProductList.class);
		
		return productList;
	}
	
	private WebTarget getWebTargetProductlist() throws Exception {
		Client client = getClientIgnoreCertificates();
		WebTarget webTarget = 
			client
				.target(urlDomain.replace("http:", "https:") + "services/productlist/products")
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
	
	private String getProductsFromApiRest(WebTarget webTarget) throws Exception {
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			return response.readEntity(String.class);
		}
		return null;
	}
	
	private String getProductsFromWebDriver(String urlProductList, WebDriver driver) throws Exception {
		String nameTab = "GetProducts";
		String idWindow = driver.getWindowHandle();
		SeleniumUtils.loadUrlInAnotherTabTitle(urlProductList, nameTab, driver);
		String body = driver.findElement(By.xpath("//body/pre")).getText();
		SeleniumUtils.closeTabByTitleAndReturnToWidow(nameTab, idWindow, driver);
		return body;
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
	
	public Garment getOneWithTotalLook() {
		List<Garment> listGarments = getAll();
		for (Garment garment : listGarments) {
			if (getTotalLookGarment(garment)!=null) {
				return garment;
			}
		}
		return null;
	}
	
	private GarmentDetails getTotalLookGarment(Garment product) {
		Article article = product.getArticleWithMoreStock();
		Client client = ClientBuilder.newClient();
		return ( 
			client
				.target(urlDomain.replace("http:", "https:") + "services/garments")
				.path(article.getGarmentId())
				.path("looktotal")
				.queryParam("color", article.getColor().getId())
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", getIdStockNormalized())
				.get(GarmentDetails.class));
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
	
	private String getDnsUrl(String url) throws URISyntaxException {
		URI uri = new URI(url);
		return (uri.getScheme() + "://" + uri.getHost());
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
		public GetterProducts build() throws Exception {
			return (
				new GetterProducts(url, codigoPaisAlf, app, lineaType, seccion, galeria, familia, numProducts, pagina, driver));
		}
	}
}
