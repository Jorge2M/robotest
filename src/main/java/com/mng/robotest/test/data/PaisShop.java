package com.mng.robotest.test.data;

import com.mng.robotest.test.beans.Pais;

public enum PaisShop {
	Albania("070"),
	Algeria("208", "Algérie"),
	Andorra("043"),
	Angola("330"),
	Argentina("528"),
	Armenia("077"),
	Aruba("474"),
	Australia("800"),
	Azerbaijan("078"),
	Bahrain("640"),
	Belgique("017"),
	Bermuda ("413"),
	Bolivia("516"),
	Bosnia("093", "Bosnia and Herzegovina"),
	Brazil("508"),
	Bulgaria("068"),
	Burkina("236"),
	Benin("284", "Bénin"),
	Cambodia("696"),
	Cameroun("302"),
	Canada("404"),
	Chile("512"),
	Colombia("480"),
	CostaRica("436", "Costa Rica"),
	Croatia("092"),
	Cuba("448"),
	Curacao("478", "Curaçao"),
	Cyprus("600", "Cyprus (Euros)"),
	CzechRepublic("061", "Czech Republic"),
	CoteIvori("272", "Côte d"),
	Denmark("008"),
	Deutschland("004"),
	Ecuador("500"),
	Egypt("220"),
	ElSalvador("428", "El Salvador"),
	Ceuta("022", "España (Ceuta)"),
	IslasCanarias("021", "España (Islas Canarias)"),
	Melilla("023", "España (Melilla)"),
	Espana("001", "España (Península y Baleares)"),
	Estonia("053"),
	Finland("032"),
	Guadeloupe("458", "France (Guadeloupe)"),
	GuyaneFrancaise("496", "France (Guyane Française)"),
	LaReunion("372", "France (La Réunion)"),
	Martinique("475", "France (Martinique)"),
	France("011"),
	Georgia("076"),
	Ghana("276"),
	Gibraltar("044"),
	Greece("009"),
	Guatemala("416"),
	Honduras("424"),
	HongKong("740", "Hong Kong"),
	Hungary("064"),
	Iceland("024"),
	India("664"),
	Indonesia("700"),
	Iran("616"),
	Iraq("612"),
	Ireland("007"),
	Israel("624"),
	Italia("005"),
	Jordan("628"),
	Kenya("346"),
	Kosovo("095"),
	Kuwait("636"),
	ChipreDelNorte("602", "Kuzey Kıbrıs (Türk Lirasi)"),
	Laos("684"),
	Latvia("054"),
	Lebanon("604"),
	Libya("216"),
	Liechtenstein("037"),
	Lithuania("055"),
	Luxemburg("018"),
	Macao("743"),
	Macedonia ("096"),
	Malaysia("701"),
	Maldives("667"),
	Malta("046"),
	Maroc("204"),
	Maurice("373"),
	Moldova("074"),
	Monaco("015"),
	Mongolia("716"),
	Montenegro("090"),
	Myanmar("676"),
	Mexico("412", "México"),
	Nederland("003"),
	Nicaragua("432"),
	Nigeria("288"),
	Norway("028"),
	NuevaCaledonia("809", "Nouvelle-Calédonie"),
	Oman("649"),
	Pakistan("662"),
	Panama("442", "Panamá"),
	Paraguay("520"),
	Peru("504", "Perú"),
	Philippines("708"),
	Poland("060"),
	Portugal("010"),
	Qatar("644"),
	RepublicaDominicana("456", "República Dominicana"),
	Romania("066"),
	SaudiArabia("632", "Saudi Arabia"),
	Schweiz("036"),
	Serbia("094"),
	Singapore("706"),
	Slovakia("063"),
	Slovenia("091"),
	SouthAfrica("388", "South Africa"),
	SriLanka("669", "Sri Lanka"),
	Surinam("492"),
	Sweden("030"),
	Syria ("608"),
	Senegal("248", "Sénégal"),
	Taiwan("736"),
	Thailand("680"),
	Tunisie("212"),
	Turquia("052", "Türkiye"),
	USA("400"),
	Ukraine("072"),
	UnitedArabEmirates("647", "United Arab Emirates"),
	ChannelIslands("019", "United Kingdom (Channel Islands)"),
	UnitedKingdom("006", "United Kingdom"),
	Uruguay("524"),
	Uzbekistan("081"),
	Venezuela("484"),
	Vietnam ("690"),
	Austria("038", "Österreich"),
	Bielorrusia("073", "Беларусь"),
	Kadjastan("079", "Казахстан"), 
	Kirguistan("083", "Киргизия"),
	Russia("075", "Россия (Российская Федерация)"),
	China("720", "中国"),
	Japon("732", "日本"),
	CoreaDelSur("728", "대한민국"),
	FakeCountry("999", "Country Inexistent");
	
	String codigoPais;
	String nameInPrehome;
	private PaisShop(String codigoPais) {
		this.codigoPais = codigoPais;
		this.nameInPrehome = this.name();
	}
	
	private PaisShop(String codigoPais, String nameInPrehome) {
		this.codigoPais = codigoPais;
		this.nameInPrehome = nameInPrehome;
	}
	
	public String getCodigoPais() {
		return this.codigoPais;
	}
	
	public boolean equals(Pais pais) {
		return this==getPais(pais.getCodigo_pais());
	}
	
	public static PaisShop getPais(Pais pais) {
		return getPais(pais.getCodigo_pais());
	}
	
	public static PaisShop getPais(String codigoPais) {
		for (PaisShop paisShop : PaisShop.values()) {
			if (paisShop.getCodigoPais().compareTo(codigoPais)==0) {
				return paisShop;
			}
		}
		
		return null;
	}
}
