package com.mng.robotest.test80.mango.test.getdata.productos;

import java.util.ArrayList;

public interface ArticlesStockGetter {
	abstract public ArrayList<ArticleStock> getProducts(String storeId, String countryId) throws Exception;
	abstract public void deleteProduct(String producto);
}
