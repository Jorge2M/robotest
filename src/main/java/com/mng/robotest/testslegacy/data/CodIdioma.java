package com.mng.robotest.testslegacy.data;

import com.mng.robotest.testslegacy.beans.IdiomaPais;

public enum CodIdioma {
	AL("Deutsch"),
	BG("български"),
	CA("Català"),
	CS("Čeština"),
	ES("Castellano"), 
	FR("Français"),
	HU("Magyar"),
	ID("Bahasa indonesia"),
	IN("English"),
	IT("Italiano"),
	HR("Hrvatski"),
	JA("日本"),
	KO("한국어"),
	EL("Ελληνικά"),
	NL("Nederlands"),
	NO("Norsk"),
	PL("Polski"),
	PO("Portugues"),
	RO("Româna"),
	RU("Русский"),
	SV("Svenska"),
	TH("ไทย"),
	TR("Türk"),
	UA("Україна"),
	US("English (USA)"),
	ZH("中文"),
	AR("العربية");
	
	private final String literalIdioma;
	
	CodIdioma(String literalIdioma) {
		this.literalIdioma = literalIdioma;
	}
	
	public String getLiteral() {
		return this.literalIdioma;
	}
	
	public static CodIdioma from(String codIdioma) {
		return CodIdioma.valueOf(codIdioma);
	}
	
	public boolean isEquals(IdiomaPais idioma) {
		return this.name().compareTo(idioma.getCodigo().name())==0;
	}
}
