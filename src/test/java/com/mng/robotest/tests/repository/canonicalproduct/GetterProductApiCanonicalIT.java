package com.mng.robotest.tests.repository.canonicalproduct;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.repository.canonicalproduct.entity.EntityProduct;

class GetterProductApiCanonicalIT {

	@Test
	void testGetProduct() throws Exception {
		Optional<EntityProduct> getProduct = new GetterProductApiCanonical("ES", "ES", AppEcom.shop.name())
				.getProduct("37037734");
		
		assertTrue(getProduct.isPresent());
	}

}
