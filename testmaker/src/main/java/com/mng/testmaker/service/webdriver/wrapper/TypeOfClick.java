package com.mng.testmaker.service.webdriver.wrapper;

public enum TypeOfClick {
    webdriver, 
    javascript;
	
	public static TypeOfClick next(TypeOfClick typeClick) {
		switch (typeClick) {
		case webdriver:
			return javascript;
		case javascript:
		default:
			return webdriver;
		}
	}
}
