package com.mng.robotest.test80.mango.test.pageobject.utils;

import java.util.Comparator;


public class NombreYRefComparator implements Comparator<NombreYRef> {
    
    @Override
    public int compare(NombreYRef c1, NombreYRef c2) {
        if (c1.equals(c2)) {
            return 0;
        }
        return 1;
    }
}
