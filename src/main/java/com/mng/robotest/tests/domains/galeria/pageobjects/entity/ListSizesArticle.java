package com.mng.robotest.tests.domains.galeria.pageobjects.entity;

import java.util.ArrayList;
import java.util.List;

public class ListSizesArticle {

	private final List<DataSizeArticle> listSizesArticles = new ArrayList<>();
	
	private ListSizesArticle() {}
	
	public static ListSizesArticle getInstance() {
		return (new ListSizesArticle());
	}
	
	public int size() {
		return listSizesArticles.size();
	}
	
	public void addData(String referencia, int widthSrcImg, int widthArticle) {
		var dataSizes = new DataSizeArticle();
		dataSizes.referencia = referencia;
		dataSizes.widthSrcImg = widthSrcImg;
		dataSizes.widthArticle = widthArticle;
		listSizesArticles.add(dataSizes);
	}
	
	public String getListHtml() {
		String listHtml = "";
		for (DataSizeArticle dataArticle : listSizesArticles) {
			listHtml+=
				(dataArticle.referencia + 
				" (widthAttr=" + dataArticle.widthSrcImg + 
				", realWidth=" + dataArticle.widthArticle + ")<br>");
		}
		
		return listHtml;
	}
}

class DataSizeArticle {
	public String referencia;
	public int widthSrcImg;
	public int widthArticle;
}