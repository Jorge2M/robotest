package com.mng.robotest.test80.mango.test.getdata.productos;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class ArticlesStockFactory {

	public enum SourceArticles {
		ServiceMango(1),
		CacheBd_NotRelaxFilters(2),
		CacheBd_RelaxFilters(3);
		
		int qualityOfData;
		private SourceArticles(int qualityOfData) {
			this.qualityOfData = qualityOfData;
		}
		
		public int getLevelQualityOfData() {
			return qualityOfData;
		}
	}
	
	public static ArticlesStockGetter make(SourceArticles sourceArticles, AppEcom app, String urlTestAppMango) 
	throws Exception {
		switch (sourceArticles) {
		case ServiceMango:
			return (new ArticlesStockServiceMng(app, urlTestAppMango));
		case CacheBd_NotRelaxFilters:
		case CacheBd_RelaxFilters:
			return (new ArticlesStockCacheBD(app, urlTestAppMango, sourceArticles));
		}
		
		return null;
	}
	
}
