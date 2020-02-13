package com.mng.robotest.test80.mango.test.getdata.products;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.JaxRsClient;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.GarmentDetails;
import com.mng.robotest.test80.mango.test.getdata.products.data.ProductList;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;

public class GetterProducts extends JaxRsClient {
	
	private final String urlDomain;
	private final String codigoPaisAlf;
	private final AppEcom app;
	private final LineaType lineaType;
	private final String seccion;
	private final String galeria;
	private final String familia;
	private final Integer numProducts;
	private final Integer pagina;
	private final ProductList productList;
	
	private GetterProducts(String urlDomain, String codigoPaisAlf, AppEcom app, LineaType lineaType, String seccion, 
						   String galeria, String familia, Integer numProducts, Integer pagina) throws Exception {
		if (urlDomain.charAt(urlDomain.length()-1)=='/') {
			this.urlDomain = urlDomain;
		} else {
			this.urlDomain = urlDomain + "/";
		}
			
		this.codigoPaisAlf = codigoPaisAlf;
		this.app = app;
		this.lineaType = lineaType;
		this.seccion = seccion;
		this.galeria = galeria;
		this.familia = familia;
		this.numProducts = numProducts;
		this.pagina = pagina;
		this.productList = getProductList();
	}
	
	private ProductList getProductList() throws Exception {
		Client client = getClientIgnoreCertificates();
		Response response = 
			client
				.target(urlDomain + "services/productlist/products")
				.path(codigoPaisAlf)
				.path(getLineaPath())
				.path("sections_" + lineaType.name() + "." + seccion + "_" + lineaType.name())
				.queryParam("idSubSection", galeria + "_" + lineaType.name())
				.queryParam("menu", "familia;" + familia)
				.queryParam("pageNum", pagina)
				.queryParam("columnsPerRow", "1")
				.queryParam("rowsPerPage", numProducts)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		//Without that modification Jackson can't parse the JSON
		String body = response.readEntity(String.class);
		body = body.replace("\"garments\":{", "\"garments\":[");
		body = body.replace("}}}]", "}]}]");
		body = body.replaceAll("\"g[0-9]{8}(..){0,1}\":", "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ProductList productList = objectMapper.readValue(body, ProductList.class);
		
		return productList;
	}

	
	public List<Garment> getAll() {
		return productList.getGroups().get(0).getGarments();
	}
	public List<Garment> getWithManyColors() {
		List<Garment> listGarmentsWithManyColors = new ArrayList<>();
		List<Garment> listGarments = getAll();
		for (Garment garment : listGarments) {
			if (garment.getColors().size()>1) {
				listGarmentsWithManyColors.add(garment);
			}
		}
		return listGarmentsWithManyColors;
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
				.target(urlDomain + "services/garments")
				.path(article.getGarmentId())
				.path("looktotal")
				.queryParam("color", article.getColor().getId())
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", productList.getStockId())
				.get(GarmentDetails.class));
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
		private LineaType lineaType = LineaType.she;
		private String seccion = "prendas";
		private String galeria = "camisas";
		private String familia = "14";
		private Integer numProducts = 40;
		private Integer pagina = 1;

		public Builder(DataCtxShop dCtxSh) throws Exception {
			this.url = dCtxSh.getDnsUrlAcceso();
			this.codigoPaisAlf = dCtxSh.pais.getCodigo_alf();
			this.app = dCtxSh.appE;
		}
		
		public Builder(String url, String codigoPaisAlf, AppEcom app) {
			this.url = url;
			this.codigoPaisAlf = codigoPaisAlf;
			this.app = app;
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
				new GetterProducts(url, codigoPaisAlf, app, lineaType, seccion, galeria, familia, numProducts, pagina));
		}
	}
}
