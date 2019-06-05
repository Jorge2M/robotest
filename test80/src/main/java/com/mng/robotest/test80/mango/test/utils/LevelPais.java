package com.mng.robotest.test80.mango.test.utils;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

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
