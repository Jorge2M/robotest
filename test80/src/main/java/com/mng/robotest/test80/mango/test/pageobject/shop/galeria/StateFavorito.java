package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

public enum StateFavorito {
    Marcado("remove"), 
    Desmarcado("add");
	
	String dataFav;
	private StateFavorito(String dataFav) {
		this.dataFav = dataFav;
	}
	
	public String getDataFav() {
		return this.dataFav;
	}
}
