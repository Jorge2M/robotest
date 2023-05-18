package com.mng.robotest.domains.ficha.pageobjects;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mng.robotest.domains.ficha.beans.DataFoto;

public class DataFotoTest {

    static final String TAG_TEMPORADA = "#temporada#";
    static final String TAG_SIZE = "#size#";
    
    @Test
    public void testFixedFotosObtainedFromFicha() {
        String srcFoto = "";
        DataFoto dataFoto = null;
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69.jpg?ts=1516296871653&imwidth=1280&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.getTemporada());
        assertEquals("20", dataFoto.getSize());
        assertEquals("21065012", dataFoto.getReferencia());
        assertEquals(TipoImagenProducto.DETALLES, dataFoto.getTypeImage());
                
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/outfit/S20/21065012_69-99999999_01.jpg?ts=1516296871653&imwidth=640&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.getTemporada());
        assertEquals("20", dataFoto.getSize());
        assertEquals("21065012", dataFoto.getReferencia());
        assertEquals(TipoImagenProducto.OUTFIT, dataFoto.getTypeImage());
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_D1.jpg?ts=1516296871653&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.getTemporada());
        assertEquals("20", dataFoto.getSize());
        assertEquals("21065012", dataFoto.getReferencia());
        assertEquals(TipoImagenProducto.DETALLES_1, dataFoto.getTypeImage());
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_D2.jpg?ts=1516296871653&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.getTemporada());
        assertEquals("20", dataFoto.getSize());
        assertEquals("21065012", dataFoto.getReferencia());
        assertEquals(TipoImagenProducto.DETALLES_2, dataFoto.getTypeImage());
        
        srcFoto = "//st.mngbcn.com/rcs/pics/static/T2/fotos/S20/21065012_69_B.jpg?ts=1513600677300&imwidth=427&imdensity=1";
        dataFoto = new DataFoto(srcFoto);
        assertEquals("2", dataFoto.getTemporada());
        assertEquals("20", dataFoto.getSize());
        assertEquals("21065012", dataFoto.getReferencia());
        assertEquals(TipoImagenProducto.BODEGON, dataFoto.getTypeImage());
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
            if (dataFoto.getSrcFoto().contains(TAG_TEMPORADA))
                assertEquals("Validation Temporada in " + tipo, temporada, dataFoto.getTemporada());
            
            if (dataFoto.getSrcFoto().contains(TAG_SIZE))
                assertEquals("Validation Size in " + tipo, size, dataFoto.getSize());
            
            assertEquals("Validation Referencia in " + tipo, referencia, dataFoto.getReferencia());
            assertEquals("Validation tipo in " + tipo, tipo, dataFoto.getTypeImage());
        }
    }
    
    private String getSrcFotoFromEnum(TipoImagenProducto tipoImagenProd, String temporada, String size, String referencia, String color) {
        String urlFoto = tipoImagenProd.getDirectorio();
        urlFoto = urlFoto.replace(TAG_TEMPORADA, temporada);
        urlFoto = urlFoto.replace(TAG_SIZE, "S" + size);
        urlFoto = urlFoto + referencia + "_" + color;
        if ("".compareTo(tipoImagenProd.getSufijo())!=0)
            urlFoto = urlFoto + "_" + tipoImagenProd.getSufijo();
            
        urlFoto = urlFoto + tipoImagenProd.getExtension();
        urlFoto = urlFoto + "?ts=1516296871653&imwidth=427&imdensity=1";
        return urlFoto;
    }
}
