package com.mng.robotest.test.stpv.shop.checkout.envio;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;

public class DataSearchDeliveryPoint {
	public enum DataSearchDp {Provincia, CodigoPostal}
	
	public String data;
	public DataSearchDp typeData;
	public TipoTransporte tipoTransporte;
		
	public static DataSearchDeliveryPoint getInstance(Pago pago, AppEcom app, Pais pais) {
		DataSearchDeliveryPoint dataDp = new DataSearchDeliveryPoint();
		dataDp.tipoTransporte = pago.getTipoEnvioType(app);
		dataDp.typeData = pago.getTipoEnvioType(app).getDataSearchDp();
		switch (dataDp.typeData) {
		case CodigoPostal:
			dataDp.data = pais.getCodpos();
			break;
		case Provincia:
			dataDp.data = pago.getProvinciaEnvio();
			break;
		}
		
		return dataDp;
	}
}