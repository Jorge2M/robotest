package com.mng.robotest.test.getproducts.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import com.mng.robotest.test.getdata.products.data.ProductList;

public class ProductListIT {

	@Test
	public void testGetStockId() {
		//Given
		ProductList productList = new ProductList();
		productList.setCacheId("v7_37_3.ts2520.001.es.0.false.false.v7.she.sections_she.prendas_she.abrigos_she.familia_15");
		
		//When
		String stockId = productList.getStockId();
		
		//Then
		assertEquals(stockId, "001.ES.0.false.false.v7");
	}
}
