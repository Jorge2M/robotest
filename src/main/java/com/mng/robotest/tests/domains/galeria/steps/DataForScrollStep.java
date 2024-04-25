package com.mng.robotest.tests.domains.galeria.steps;

import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;

public class DataForScrollStep {
	private int numPageToScroll;
	private FilterOrdenacion ordenacionExpected;
	private boolean validateArticlesExpected;
	private int numArticlesExpected;
	private boolean validaImgBroken;
	
	public int getNumPageToScroll() {
		return numPageToScroll;
	}
	public void setNumPageToScroll(int numPageToScroll) {
		this.numPageToScroll = numPageToScroll;
	}
	public FilterOrdenacion getOrdenacionExpected() {
		return ordenacionExpected;
	}
	public void setOrdenacionExpected(FilterOrdenacion ordenacionExpected) {
		this.ordenacionExpected = ordenacionExpected;
	}
	public boolean isValidateArticlesExpected() {
		return validateArticlesExpected;
	}
	public void setValidateArticlesExpected(boolean validateArticlesExpected) {
		this.validateArticlesExpected = validateArticlesExpected;
	}
	public int getNumArticlesExpected() {
		return numArticlesExpected;
	}
	public void setNumArticlesExpected(int numArticlesExpected) {
		this.numArticlesExpected = numArticlesExpected;
	}
	public boolean isValidaImgBroken() {
		return validaImgBroken;
	}
	public void setValidaImgBroken(boolean validaImgBroken) {
		this.validaImgBroken = validaImgBroken;
	}
}
