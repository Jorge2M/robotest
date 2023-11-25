package com.mng.robotest.tests.domains.compra.payments.eps.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageEpsSelBanco extends PageBase {

	private static final String XP_ICONO_EPS = "//div[@class='header-logo']";
	private static final String XP_ICONO_BANCO = "//div[@class='loginlogo']";

	public boolean isPresentIconoEps() {
		return state(Present, XP_ICONO_EPS).check();
	}
	
	public boolean isVisibleIconoBanco() {
		return state(Present, XP_ICONO_BANCO).check();
	}
}
