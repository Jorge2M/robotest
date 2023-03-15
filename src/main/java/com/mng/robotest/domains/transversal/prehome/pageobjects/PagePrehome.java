package com.mng.robotest.domains.transversal.prehome.pageobjects;

import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;

public class PagePrehome implements PagePrehomeI, PageFromFooter {

	private final PagePrehomeI pagePreHome = new PagePrehomeNew();
	//private boolean preHomeLocated = false;
	
	public String getName() {
		return "Prehome";
	}
	
	public boolean isPageUntil(int seconds) {
		setPagePreHome();
		return pagePreHome.isPageUntil(seconds);
	}
	
	public boolean isPage() {
        return isPageUntil(0);		
	}
	public boolean isPageCorrectUntil(int seconds) {
		return isPageUntil(seconds);
	}	
	
	public boolean isPaisSelectedWithMarcaCompra() {
		setPagePreHome();
		return pagePreHome.isPaisSelectedWithMarcaCompra();
	}
	public void selecionPais() {
		setPagePreHome();
		pagePreHome.selecionPais();
	}

	public boolean isPaisSelected() {
		setPagePreHome();
		return pagePreHome.isPaisSelected();
	}
	
	public void selecPaisIdiomaYAccede() {
		setPagePreHome();
		pagePreHome.selecPaisIdiomaYAccede();
	}
	public void selecionIdiomaAndEnter() {
		setPagePreHome();
		pagePreHome.selecionIdiomaAndEnter();
	}
	public void accesoShopViaPrehome(boolean acceptCookies) throws Exception {
		previousAccessShopSteps(acceptCookies);
		pagePreHome.accesoShopViaPrehome(acceptCookies);
	}
	
	public void previousAccessShopSteps(boolean acceptCookies) throws Exception {
		setPagePreHome();
		pagePreHome.previousAccessShopSteps(acceptCookies);
		setPagePreHome();
	}

	private void setPagePreHome() {
//		if (!preHomeLocated || pagePreHome==null) {
//			PagePrehomeI pagePrehomeOld = new PagePrehomeOld();
//			if (pagePrehomeOld.isPage()) {
//				preHomeLocated = true;
//				pagePreHome = pagePrehomeOld;
//				return;
//			} 
//			PagePrehomeI pagePrehomeNew = new PagePrehomeNew();
//			if (pagePrehomeNew.isPageUntil(2)) {
//				preHomeLocated = true;
//				pagePreHome = pagePrehomeNew;
//				return;
//			}
//			pagePreHome = pagePrehomeOld;
//		}
	}
	
}
