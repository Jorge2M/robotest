package com.mng.robotest.testslegacy.pageobject.utils;

import java.util.Comparator;


public class NombreYRefComparator implements Comparator<DataArticleGalery> {
	
	@Override
	public int compare(DataArticleGalery c1, DataArticleGalery c2) {
		if (c1.equals(c2)) {
			return 0;
		}
		return 1;
	}
}
