package com.mng.robotest.test80.arq.utils.otras;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;

public enum LevelPais {
	top(5,3),
	conCompraNoTop(2,2),
	sinCompra(1,1);
	
	int numBannersTestShop;
	int numBannersTestOutlet;
	private LevelPais(int numBannersTestShop, int numBannersTestOutlet) {
		this.numBannersTestShop = numBannersTestShop;
		this.numBannersTestOutlet = numBannersTestOutlet;
	}
	
	public int getNumBannersTest(AppEcom app) {
		switch (app) {
		case shop:
			return numBannersTestShop;
		default:
			return numBannersTestOutlet;
		}
	}
}
