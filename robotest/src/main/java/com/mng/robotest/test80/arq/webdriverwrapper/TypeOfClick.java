package com.mng.robotest.test80.arq.webdriverwrapper;

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
