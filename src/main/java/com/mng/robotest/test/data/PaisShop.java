package com.mng.robotest.test.data;

import com.mng.robotest.test.beans.Pais;

public enum PaisShop {
	ALBANIA("070"),
	ALGERIA("208", "Algérie"),
	ANDORRA("043"),
	ANGOLA("330"),
	ARGENTINA("528"),
	ARMENIA("077"),
	ARUBA("474"),
	AUSTRALIA("800"),
	AZERBAIJAN("078"),
	BAHRAIN("640"),
	BELGIQUE("017"),
	BERMUDA("413"),
	BOLIVIA("516"),
	BOSNIA("093", "Bosnia and Herzegovina"),
	BRAZIL("508"),
	BULGARIA("068"),
	BURKINA("236"),
	BENIN("284", "Bénin"),
	CAMBODIA("696"),
	CAMEROUN("302"),
	CANADA("404"),
	CHILE("512"),
	COLOMBIA("480"),
	COSTARICA("436", "Costa Rica"),
	CROATIA("092"),
	CUBA("448"),
	CURACAO("478", "Curaçao"),
	CYPRUS("600", "Cyprus (Euros)"),
	CZECH_REPUBLIC("061", "Czech Republic"),
	COTEIVORI("272", "Côte d"),
	DENMARK("008"),
	DEUTSCHLAND("004"),
	ECUADOR("500"),
	EGYPT("220"),
	EL_SALVADOR("428", "El Salvador"),
	CEUTA("022", "España (Ceuta)"),
	ISLAS_CANARIAS("021", "España (Islas Canarias)"),
	MELILLA("023", "España (Melilla)"),
	ESPANA("001", "España (Península y Baleares)"),
	ESTONIA("053"),
	FINLAND("032"),
	GUADELOUPE("458", "France (Guadeloupe)"),
	GUYANE_FRANCAISE("496", "France (Guyane Française)"),
	LA_REUNION("372", "France (La Réunion)"),
	MARTINIQUE("475", "France (Martinique)"),
	FRANCE("011"),
	GEORGIA("076"),
	GHANA("276"),
	GIBRALTAR("044"),
	GREECE("009"),
	GUATEMALA("416"),
	HONDURAS("424"),
	HONG_KONG("740", "Hong Kong"),
	HUNGARY("064"),
	ICELAND("024"),
	INDIA("664"),
	INDONESIA("700"),
	IRAN("616"),
	IRAQ("612"),
	IRELAND("007"),
	ISRAEL("624"),
	ITALIA("005"),
	JORDAN("628"),
	KENYA("346"),
	KOSOVO("095"),
	KUWAIT("636"),
	CHIPRE_DEL_NORTE("602", "Kuzey Kıbrıs (Türk Lirasi)"),
	LAOS("684"),
	LATVIA("054"),
	LEBANON("604"),
	LIBYA("216"),
	LIECHTENSTEIN("037"),
	LITHUANIA("055"),
	LUXEMBURG("018"),
	MACAO("743"),
	MACEDONIA("096"),
	MALAYSIA("701"),
	MALDIVES("667"),
	MALTA("046"),
	MAROC("204"),
	MAURICE("373"),
	MOLDOVA("074"),
	MONACO("015"),
	MONGOLIA("716"),
	MONTENEGRO("090"),
	MYANMAR("676"),
	MEXICO("412", "México"),
	NEDERLAND("003"),
	NICARAGUA("432"),
	NIGERIA("288"),
	NORWAY("028"),
	NUEVA_CALEDONIA("809", "Nouvelle-Calédonie"),
	OMAN("649"),
	PAKISTAN("662"),
	PANAMA("442", "Panamá"),
	PARAGUAY("520"),
	PERU("504", "Perú"),
	PHILIPPINES("708"),
	POLAND("060"),
	PORTUGAL("010"),
	QUATAR("644"),
	REPUBLICA_DOMINICANA("456", "República Dominicana"),
	ROMANIA("066"),
	SAUDI_ARABIA("632", "Saudi Arabia"),
	SCHWEIZ("036"),
	SERBIA("094"),
	SINGAPORE("706"),
	SLOVAKIA("063"),
	SLOVENIA("091"),
	SOUTH_AFRICA("388", "South Africa"),
	SRI_LANKA("669", "Sri Lanka"),
	SURINAM("492"),
	SWEDEN("030"),
	SYRIA("608"),
	SENEGAL("248", "Sénégal"),
	TAIWAN("736"),
	THAILAND("680"),
	TUNISIE("212"),
	TURQUIA("052", "Türkiye"),
	USA("400"),
	UKRAINE("072"),
	UNITED_ARAB_EMIRATES("647", "United Arab Emirates"),
	CHANNEL_ISLANDS("019", "United Kingdom (Channel Islands)"),
	UNITED_KINGDOM("006", "United Kingdom"),
	URUGUAY("524"),
	UZBEKISTAN("081"),
	VENEZUELA("484"),
	VIETNAM("690"),
	AUSTRIA("038", "Österreich"),
	BIELORUSSIA("073", "Беларусь"),
	KADJASTAN("079", "Казахстан"), 
	KIRGUISTAN("083", "Киргизия"),
	RUSSIA("075", "Россия (Российская Федерация)"),
	CHINA("720", "中国"),
	JAPON("732", "日本"),
	COREA_DEL_SUR("728", "대한민국"),
	FAKE_COUNTRY("999", "Country Inexistent");
	
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
	
	public boolean isEquals(Pais pais) {
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
