package com.mng.robotest.test.pageobject.shop.checkout.eps;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageEpsSelBanco extends PageBase {

	private static final String XPATH_ICONO_EPS = "//div[@class='header-logo']";
	private static final String XPATH_ICONO_BANCO = "//div[@class='loginlogo']";

	public boolean isPresentIconoEps() {
		return state(Present, XPATH_ICONO_EPS).check();
	}
	
	public boolean isVisibleIconoBanco() {
		return state(Present, XPATH_ICONO_BANCO).check();
	}
}
