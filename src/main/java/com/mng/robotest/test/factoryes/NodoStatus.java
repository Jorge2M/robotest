package com.mng.robotest.test.factoryes;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Cookie;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.utils.DataArticleGalery;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;


public class NodoStatus {

	String ip;
	Set<Cookie> cookies;
	AppEcom appE;
	String sourceDataURL;
	ListDataArticleGalery articlesNuevo = null;
	boolean tested = false;

	public NodoStatus() {}
	
	public String getIp() {
		return this.ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}	
	
	public Set<Cookie> getCookies() {
		return this.cookies;
	}
	
	public AppEcom getAppEcom() {
		return this.appE;
	}
	
	public void setCookies(Set<Cookie> cookies) {
		this.cookies = cookies;
	}
	
	public String getSourceDataURL() {
		return this.sourceDataURL;
	}
	
	public void setSourceDataURL(String sourceDataURL) {
		this.sourceDataURL = sourceDataURL;
	}
	
	public ListDataArticleGalery getArticlesNuevo() {
		return this.articlesNuevo;
	}
	
	public void setAppEcom(String app) {
		this.appE = AppEcom.valueOf(app);
	}
	
	public void setAppEcom(AppEcom appE) {
		this.appE = appE;
	}
	
	public void setArticlesNuevo(ListDataArticleGalery articlesNuevo) {
		this.articlesNuevo = articlesNuevo;
	}
	
	public boolean getTested() {
		return this.tested;
	}
	
	public void setTested(boolean tested) {
		this.tested = tested;
	}
	
	/**
	 * Busca una cookie por su nombre
	 */
	public Cookie getCookieByName(String nameCookie) {
		boolean encontrado = false; 
		Cookie cookie = null;
		Iterator<Cookie> itCookies = this.cookies.iterator();
		while (itCookies.hasNext() && !encontrado){
			Cookie cookieTmp = itCookies.next();
			if (cookieTmp.getName().compareTo(nameCookie)==0) {
				encontrado = true;
				cookie = cookieTmp;
			}
		}
				
		return cookie;
	}
	
	public DataArticleGalery getArticleNuevoThatNotFitWith(NodoStatus nodoAnt) { 
		return (this.getArticlesNuevo().getFirstArticleThatNotFitWith(nodoAnt.getArticlesNuevo()));
	}
}
