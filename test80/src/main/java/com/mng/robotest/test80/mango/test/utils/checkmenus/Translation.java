package com.mng.robotest.test80.mango.test.utils.checkmenus;

import com.mng.robotest.test80.mango.test.data.CodIdioma;

public class Translation implements Label {

	private final CodIdioma codIdioma;
	private final String literal;
	
	private Translation(CodIdioma codIdioma, String literal) {
		this.codIdioma = codIdioma;
		this.literal = literal;
	}
	
	public static Translation getNew(CodIdioma codIdioma, String literal) {
		return (new Translation(codIdioma, literal));
	}
	
	public CodIdioma getCodIdioma() {
		return codIdioma;
	}
	
	public String getLiteral() {
		return literal;
	}
	
	@Override
	public String getLabel() {
		return getLiteral();
	}
}
