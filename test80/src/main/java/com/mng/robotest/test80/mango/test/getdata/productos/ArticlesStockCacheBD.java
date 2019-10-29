package com.mng.robotest.test80.mango.test.getdata.productos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticlesStockFactory.SourceArticles;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.jdbc.dao.ProductCacheDAO;
import com.mng.robotest.test80.mango.test.jdbc.to.ProductCache;

public class ArticlesStockCacheBD implements ArticlesStockGetter {

	private final AppEcom app;
	private final String urlTestAppMango;
	private final SourceArticles sourceArticles;
	private final ProductCacheDAO productCacheDAO;
	
	public ArticlesStockCacheBD(AppEcom app, String urlTestAppMango, SourceArticles sourceArticles) 
	throws Exception {
		this.app = app;
		this.urlTestAppMango = urlTestAppMango;
		this.sourceArticles = sourceArticles;
		productCacheDAO = new ProductCacheDAO();
	}
	
	public ArticlesStockCacheBD(AppEcom app, String urlTestAppMango) throws Exception {
		this(app, urlTestAppMango, SourceArticles.CacheBd_NotRelaxFilters);
	}
	
	@Override
    public ArrayList<ArticleStock> getProducts(String storeId, String countryId) throws Exception {
		ArrayList<ArticleStock> listToReturn = new ArrayList<>();
        List<ProductCache> listProductsBD = getListProductsDependingTypeFilter(storeId, countryId);
        for (ProductCache productCache : listProductsBD) {
        	ArticleStock artStock = getArticleStockFromProductCache(productCache);
        	listToReturn.add(artStock);
        }
        
        return listToReturn;
	}
	
	private List<ProductCache> getListProductsDependingTypeFilter(String storeId, String countryId) {
        switch (sourceArticles) {
        case CacheBd_NotRelaxFilters:
        	return (productCacheDAO.findProductsNoCaducados(app.name(), this.urlTestAppMango, countryId, storeId, 99));
        case CacheBd_RelaxFilters: 
        	return (productCacheDAO.findProductsNoCaducadosRelaxingFilters(app.name(), storeId, 99));
        default:
        	return null;
        }
	}
	
	@Override
	public void deleteProduct(String producto) {
	    productCacheDAO.deleteProducts(producto, app.name());  
	}
	
	/**
	 * Used for Unit Testing purposes
	 */
	public void deleteProducs(TypeArticleStock typeArticle) {
		productCacheDAO.deleteProducts(typeArticle);
	}
	
	public void storeNewArticles(ArrayList<ArticleStock> listNewArticles) {
		for (ArticleStock articleStock : listNewArticles) {
			ProductCache productCache = getProductCacheFromArticleStock(articleStock);
	        productCacheDAO.insertOrReplaceProduct(productCache);
		}
	}
	
	private ArticleStock getArticleStockFromProductCache(ProductCache productCache) {
		ArticleStock articleStockReturn = new ArticleStock();
		articleStockReturn.app = AppEcom.valueOf(productCache.getAppMango());
		articleStockReturn.urlApp = productCache.getUrlEntorno();
		articleStockReturn.idAlmacen = productCache.getAlmacen();
		articleStockReturn.idCountry = productCache.getCodigoPais();
		articleStockReturn.type = TypeArticleStock.valueOf(productCache.getTipo());
		articleStockReturn.idArticle = productCache.getProducto();
		articleStockReturn.season = "";
		articleStockReturn.size = productCache.getTalla();
		articleStockReturn.colourCode = productCache.getColor();
		articleStockReturn.source = sourceArticles;
		return articleStockReturn;
	}
	
	private static ProductCache getProductCacheFromArticleStock(ArticleStock articleStock) {
		ProductCache productCacheToReturn = new ProductCache();
		productCacheToReturn.setAppMango(articleStock.app.name());
		productCacheToReturn.setUrlEntorno(articleStock.urlApp);
		productCacheToReturn.setAlmacen(articleStock.idAlmacen);
		productCacheToReturn.setCodigoPais(articleStock.idCountry);
		productCacheToReturn.setTipo(articleStock.type.name());
		productCacheToReturn.setProducto(articleStock.getReference());
		productCacheToReturn.setTalla(articleStock.size);
		productCacheToReturn.setColor(articleStock.colourCode);
		
        Date fechaGet = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Date fechaCaducidad = calculaCaducidadProductCache(24, 240, articleStock.idCountry);
		productCacheToReturn.setObtenido(fechaGet);
		productCacheToReturn.setCaducidad(fechaCaducidad);
		
		return productCacheToReturn;
	}
	
    /**
     * @return una nueva caducidad aleatoria (entre el mínimo y el máximo) para un producto
     *         se añade la aleatoriedad para evitar que todos los productos caduquen al mismo tiempo y se estresen los servicios JSON
     */
    private static Date calculaCaducidadProductCache(int minHoras, int maxHoras, String codigoPais) {
        //Calculamos un número de horas aleatorio entre el minHoras y el maxHoras
        //En el caso concreto de España aplicaremos siempre la caducidad mínima
        int intervaloHoras = 0;
        if (codigoPais.compareTo("001")==0) {
            intervaloHoras = maxHoras - minHoras;
        }

        //Calculamos las horas de caducidad y las sumamos a la fecha actual
        int calcHoras = (Double.valueOf((minHoras + (Math.random() * intervaloHoras)))).intValue();
        Calendar fechaMasHoras = Calendar.getInstance();
        fechaMasHoras.add(Calendar.HOUR, calcHoras);
        Date fechaCaducidad = new java.sql.Date(fechaMasHoras.getTime().getTime());
        return fechaCaducidad;
    }
}
