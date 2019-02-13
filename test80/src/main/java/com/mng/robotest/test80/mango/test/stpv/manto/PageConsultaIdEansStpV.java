package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.WhenSave;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaIdEans;

@SuppressWarnings("javadoc")
public class PageConsultaIdEansStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Es visible el contenido de la pestaña Busqueda Excel<br>" + 
            "2) Es visible el contenido de la pestaña Busqueda Rapida<br>" +
            "3) Es visible el título de página correcto";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaIdEans.isVisibleDivBusquedaExcel(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageConsultaIdEans.isVisibleDivBusquedaRapida(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!PageConsultaIdEans.isVisibleTituloPagina(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    
    
    
	public static void consultaDatosContacto(List<String> pedidosPrueba, DataFmwkTest dFTest) {
	    DatosStep datosStep = new DatosStep       (
	        "Introducimos datos de pedido válido y consultamos los datos de contacto", 
	        "Deben mostrar la información de contacto");
	    datosStep.setSaveErrorPage(WhenSave.Never);
	    try {
	        PageConsultaIdEans.inputPedidosAndClickBuscarDatos(pedidosPrueba, dFTest.driver);
	            
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    }
	    finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
	    int maxSecondsToWait = 2;
	    String descripValidac = 
	        "1) Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
	        "2) El número de líneas de pedido es " + pedidosPrueba.size() + "<br>" +
	        "3) Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString() + "</b>";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
                if (PageConsultaIdEans.getLineasPedido(dFTest.driver)!=pedidosPrueba.size()) {
                    listVals.add(2, State.Defect);            
                }
                if (!PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, dFTest.driver)) {
                    listVals.add(3, State.Defect);
                }
    
                datosStep.setListResultValidations(listVals);
            } 
            finally { listVals.checkAndStoreValidations(descripValidac); }
    	}

	
	
	
	public static void consultaIdentificadoresPedido(List<String> pedidosPrueba, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
	            "Introducimos datos de pedido válido y consultamos los Identificadores que tiene", 
	            "Debe mostrar los identificadores del pedido");
	    datosStep.setSaveErrorPage(WhenSave.Never);
        try {
        	PageConsultaIdEans.inputPedidosAndClickBuscarIdentificadores(pedidosPrueba, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
        int maxSecondsToWait = 2;
		String descripValidac = 
			"1) Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
	        "2) El número de líneas de pedido es " + pedidosPrueba.size() + "<br>" +
	        "3) Aparece una línea por cada uno de los pedidos <b>" + pedidosPrueba.toString() + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageConsultaIdEans.getLineasPedido(dFTest.driver)!=pedidosPrueba.size()) {
                listVals.add(2, State.Defect);            
            }
            if (!PageConsultaIdEans.isPedidosTablaCorrecto(pedidosPrueba, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
	public static void consultaTrackings(List<String> pedidosPrueba, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
			"Introducimos datos de pedido válido y consultamos el trackings", 
	        "Debe mostrar el tracking");
	    datosStep.setSaveErrorPage(WhenSave.Never);
        try {
        	PageConsultaIdEans.inputPedidosAndClickBuscarTrackings(pedidosPrueba, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
        int maxSecondsToWait = 2;
		String descripValidac = 
			"1) Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static void consultaDatosEan(List<String> articulosPrueba, DataFmwkTest dFTest) {
		DatosStep datosStep = new DatosStep       (
			"Introducimos artículos válidos y consultamos el EAN", 
	        "Debe mostrar el EAN");
	    datosStep.setSaveErrorPage(WhenSave.Never);
        try {
        	PageConsultaIdEans.inputArticulosAndClickBuscarDatosEan(articulosPrueba, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
        int maxSecondsToWait = 2;
		String descripValidac = 
			"1) Se muestra la tabla de información (la esperamos un máximo de " + maxSecondsToWait + " segundos)<br>" +
		    "2) El número de líneas de artículos es " + articulosPrueba.size() + "<br>" +
		    "3) Aparece una línea por cada uno de los artículos <b>" + articulosPrueba.toString() + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageConsultaIdEans.isVisibleTablaInformacionUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageConsultaIdEans.getLineasPedido(dFTest.driver)!=articulosPrueba.size()) {
                listVals.add(2, State.Defect);            
            }
            if (!PageConsultaIdEans.isArticulosTablaCorrecto(articulosPrueba, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
