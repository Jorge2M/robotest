package com.mng.robotest.test80.mango.test.getdata.productos;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticlesStockFactory.SourceArticles;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;

public class ArticleStock {
	
	AppEcom app;
	String urlApp;
	String idAlmacen;
	String idCountry;
	TypeArticleStock type;
	String idArticle;
	String season;
	String size;
	String colourCode;
	SourceArticles source;
    ValePais valePais = null;
	
    public ArticleStock() {}
    
    public ArticleStock(String reference) {
    	this.idArticle = reference;
    }
    
    public ArticleStock(ArticuloScreen articulo) {
        this.idArticle = articulo.getReferencia();
        this.colourCode = articulo.getCodigoColor();
        this.size = articulo.getTallaNum();
    }
    
    public AppEcom getApp() {
    	return this.app;
    }
    
    public String idCountry() {
    	return this.idCountry;
    }
    
    public TypeArticleStock getType() {
    	return type;
    }
    
    public String getSize() {
    	return this.size;
    }
    
    public String getColourCode() {
    	return this.colourCode;
    }
    
    public SourceArticles getSource() {
    	return this.source;
    }
    
	public String getReference() {
		if (idArticle!=null && "".compareTo(idArticle)!=0) {
			return (idArticle.substring(0, 8));
		}
		return "";
	}
	
    public ValePais getValePais() {
        return this.valePais;
    }
        
    public void setType(TypeArticleStock type) {
    	this.type = type;
    }
    
    public void setSource(SourceArticles source) {
    	this.source = source;
    }
    
    public void setValePais(ValePais valePais) {
        this.valePais = valePais;
    } 
        
    public boolean isVale() {
        if (this.valePais==null) {
            return false;
        }
        return true;
    }

	
	@Override
	public boolean equals(Object object) {
		if (object instanceof ArticleStock) {
			ArticleStock otherArticle = (ArticleStock)object;
			if (app==otherArticle.app &&
				urlApp.compareTo(otherArticle.urlApp)==0 &&
				idCountry.compareTo(otherArticle.idCountry)==0 &&
				getReference().compareTo(otherArticle.getReference())==0 &&
				type==otherArticle.type)
				return true;
		}
		
		return false;
	}
}
