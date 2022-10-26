package com.mng.robotest.test.getproducts;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.Menu;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.ProductLabel;
import com.mng.robotest.getdata.productlist.sort.SortFactory;
import com.mng.robotest.getdata.productlist.sort.SortFactory.SortBy;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;

import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

public class GetterProductsIT {
	
	private static GetterProducts getterProducts;
	private final int numProducts = 20;
	
	public GetterProductsIT() throws Exception {
		if (getterProducts==null) {
			Pais espana = PaisGetter.get(PaisShop.ESPANA);
			getterProducts = new GetterProducts.Builder("https://shop.mango.com/", espana.getCodigo_alf(), AppEcom.shop, null)
					.linea(she)
					.menu(Menu.Shorts)
					.numProducts(numProducts)
					.pagina(1)
					.sortBy(SortBy.STOCK_DESCENDENT)
					.build();
		}
	}
	
	@Test
	public void testGetProducts() throws Exception {
		//When
		List<GarmentCatalog> listProducts = getterProducts.getAll();
		
		//Then
		assertEquals(listProducts.size(), numProducts);
		assertTrue(listProducts.get(0).getStock()>0);
		assertTrue(isListSortedByStock(listProducts));
	}
	
	private boolean isListSortedByStock(List<GarmentCatalog> listProducts) {
		if (listProducts.get(0).getStock()<listProducts.get(1).getStock()) {
			return false;
		}
		List<GarmentCatalog> listProductsSorted = new ArrayList<>(listProducts);
		Collections.sort(listProductsSorted, SortFactory.get(SortBy.STOCK_DESCENDENT));
		return listProducts.equals(listProductsSorted);
	}
	
	@Test
	public void testGetProductsManyColors() throws Exception {
		//When
		List<GarmentCatalog> listProducts = getterProducts.getAll(Arrays.asList(FilterType.MANY_COLORS));
		
		//Then
		assertTrue(listProducts.size()>0);
		assertTrue(listProducts.get(0).getColors().size()>0);
	}

	@Test
	public void testGetProductCompletaTuLook() throws Exception {
		//When
		Optional<GarmentCatalog> product = getterProducts.getOne(Arrays.asList(FilterType.TOTAL_LOOK));
		
		//Then
		assertTrue(product.isPresent());
	}
	
	@Test
	public void testGetProductNoOnline() throws Exception {
		//When
		List<GarmentCatalog> products = getterProducts.getAll(Arrays.asList(FilterType.NO_ONLINE));
		GarmentCatalog garmentOnline = getGarmentOnline(products);
		
		//Then
		assertNull(garmentOnline);
	}
	
	@Test
	public void testGetProductOnline() throws Exception {
		//When
		List<GarmentCatalog> products = getterProducts.getAll(Arrays.asList(FilterType.ONLINE));
		GarmentCatalog garmentNoOnline = getGarmentNoOnline(products);
		
		//Then
		assertNull(garmentNoOnline);
	}
	
	private GarmentCatalog getGarmentOnline(List<GarmentCatalog> garments) {
		for (GarmentCatalog garment: garments) {
			if (isGarmentOnline(garment)) {
				return garment;
			}
		}
		return null;
	}
	
	private GarmentCatalog getGarmentNoOnline(List<GarmentCatalog> garments) {
		for (GarmentCatalog garment: garments) {
			if (!isGarmentOnline(garment)) {
				return garment;
			}
		}
		return null;
	}
	
	private boolean isGarmentOnline(GarmentCatalog garment) {
		for (ProductLabel productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "exclusivo_online".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
}
