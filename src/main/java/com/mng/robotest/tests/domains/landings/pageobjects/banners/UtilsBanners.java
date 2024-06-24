package com.mng.robotest.tests.domains.landings.pageobjects.banners;

public class UtilsBanners {

	private UtilsBanners() {}
	
    public static String getFirstImageFromSrcset(String srcset) {
        if (srcset == null || srcset.isEmpty()) {
            return null;
        }
        String[] sources = srcset.split(",");
        if (sources.length > 0) {
            return sources[0].trim().split(" ")[0];
        }
        return null;
    }

}
