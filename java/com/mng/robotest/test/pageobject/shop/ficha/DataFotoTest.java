package com.mng.robotest.test.pageobject.shop.ficha;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mng.robotest.test.pageobject.shop.ficha.DataFoto;
import com.mng.robotest.test.pageobject.shop.ficha.TipoImagenProducto;

public class DataFotoTest {

    static final String tagTemporada = "#temporada#";
    static final String tagSize = "#size#";
    
    @Test
    public void testFixedFotosObtainedFromFicha() {
        String srcFoto = "";
        DataFoto dataFoto = null;
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69.jpg?ts=1516296871653&imwidth=1280&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals(dataFoto.temporada, "2");
        assertEquals(dataFoto.size, "20");
        assertEquals(dataFoto.referencia, "21065012");
        assertEquals(dataFoto.typeImage, TipoImagenProducto.DETALLES);
                
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/outfit/S20/21065012_69-99999999_01.jpg?ts=1516296871653&imwidth=640&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.temporada);
        assertEquals("20", dataFoto.size);
        assertEquals("21065012", dataFoto.referencia);
        assertEquals(TipoImagenProducto.OUTFIT, dataFoto.typeImage);
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_D1.jpg?ts=1516296871653&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.temporada);
        assertEquals("20", dataFoto.size);
        assertEquals("21065012", dataFoto.referencia);
        assertEquals(TipoImagenProducto.DETALLES_1, dataFoto.typeImage);
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_D2.jpg?ts=1516296871653&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.temporada);
        assertEquals("20", dataFoto.size);
        assertEquals("21065012", dataFoto.referencia);
        assertEquals(TipoImagenProducto.DETALLES_2, dataFoto.typeImage);
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_B.jpg?ts=1513600677300&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.temporada);
        assertEquals("20", dataFoto.size);
        assertEquals("21065012", dataFoto.referencia);
        assertEquals(TipoImagenProducto.BODEGON, dataFoto.typeImage);
    }
    
    @Test
    public void testFotosObtainedFromEnum() {
        String temporada = "2";
        String size = "20";
        String referencia = "21065012";
        String color = "74";
        for (TipoImagenProducto tipo : TipoImagenProducto.values()) {
            String srcFoto = getSrcFotoFromEnum(tipo, temporada, size, referencia, color);
            DataFoto dataFoto = new DataFoto(srcFoto);
            if (dataFoto.srcFoto.contains(tagTemporada))
                assertEquals("Validation Temporada in " + tipo, temporada, dataFoto.temporada);
            
            if (dataFoto.srcFoto.contains(tagSize))
                assertEquals("Validation Size in " + tipo, size, dataFoto.size);
            
            assertEquals("Validation Referencia in " + tipo, referencia, dataFoto.referencia);
            assertEquals("Validation tipo in " + tipo, tipo, dataFoto.typeImage);
        }
    }
    
    private String getSrcFotoFromEnum(TipoImagenProducto tipoImagenProd, String temporada, String size, String referencia, String color) {
        String urlFoto = tipoImagenProd.getDirectorio();
        urlFoto = urlFoto.replace(tagTemporada, temporada);
        urlFoto = urlFoto.replace(tagSize, "S" + size);
        urlFoto = urlFoto + referencia + "_" + color;
        if ("".compareTo(tipoImagenProd.getSufijo())!=0)
            urlFoto = urlFoto + "_" + tipoImagenProd.getSufijo();
            
        urlFoto = urlFoto + tipoImagenProd.getExtension();
        urlFoto = urlFoto + "?ts=1516296871653&imwidth=427&imdensity=1";
        return urlFoto;
    }
}
