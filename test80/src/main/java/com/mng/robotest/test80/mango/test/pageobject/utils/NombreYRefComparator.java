package com.mng.robotest.test80.mango.test.pageobject.utils;

import java.util.Comparator;


public class NombreYRefComparator implements Comparator<IndexArticleGalery> {
    
    @Override
    public int compare(IndexArticleGalery c1, IndexArticleGalery c2) {
        if (c1.equals(c2)) {
            return 0;
        }
        return 1;
    }
}
