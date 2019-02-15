package com.mng.robotest.test80.mango.test.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.jdbc.to.ProductCache;


public class ProductCacheDAO {
    
    public static String SQLSelectProductCacheAlmacen = 
        "SELECT APP, PAIS, URL_ENTORNO, PRODUCTO, COLOR, TALLA, TIPO, ALMACEN, OBTENIDO, CADUCIDAD " + 
        "  FROM PRODUCT_CACHE " + 
        " WHERE APP= ? AND " +
        "       URL_ENTORNO = ? AND " +
        "       PAIS = ? AND " + 
        "       ALMACEN = ? AND " +
        "       CADUCIDAD > ? " +
        " ORDER BY CADUCIDAD DESC";

    public static String SQLSelectProductCacheAlmacenRelaxingFilters = 
        "SELECT APP, PAIS, URL_ENTORNO, PRODUCTO, COLOR, TALLA, TIPO, ALMACEN, OBTENIDO, CADUCIDAD " + 
        "  FROM PRODUCT_CACHE " + 
        " WHERE APP= ? AND " +
        "       ALMACEN = ? AND " +
        "       CADUCIDAD > ? " +
        " ORDER BY CADUCIDAD DESC";    
    
    
    public static String SQLInsertProductCache = 
        "INSERT OR REPLACE INTO PRODUCT_CACHE (APP, PAIS, URL_ENTORNO, PRODUCTO, COLOR, TALLA, TIPO, ALMACEN, OBTENIDO, CADUCIDAD) " + 
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";            
    
    public static String SQLDeleteProductCache = 
        "DELETE FROM PRODUCT_CACHE " + 
        " WHERE PRODUCTO = ? AND " +
        "       APP = ?";
    
    public static String SQLDeleteProductsByTypeArticle = 
        "DELETE FROM PRODUCT_CACHE " + 
        " WHERE TIPO = ?";	
    
    public static String SQLDeleteCaducados = 
        "DELETE FROM PRODUCT_CACHE " + 
        " WHERE CADUCIDAD < ? ";
            
    public ProductCacheDAO() {}
    
    public List<ProductCache> findProductsNoCaducados(String app, String urlEntorno, String codigoPais, String almacen, int max) {
    	Date caducidad = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
                PreparedStatement select = conn.prepareStatement(SQLSelectProductCacheAlmacen)) {      
                select.setString(1, app);
                select.setString(2, urlEntorno);
                select.setString(3, codigoPais);
                select.setString(4, almacen);
                select.setDate(5, caducidad);
                try (ResultSet rs = select.executeQuery()) {
                    return readListProducts(rs, max);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }        
    }
    
    public List<ProductCache> findProductsNoCaducadosRelaxingFilters(String app, String almacen, int max) {
    	Date caducidad = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try (Connection conn = Connector.getConnection(true/*forReadOnly*/);
            PreparedStatement select = conn.prepareStatement(SQLSelectProductCacheAlmacenRelaxingFilters)) {      
            select.setString(1, app);
            select.setString(2, almacen);
            select.setDate(3, caducidad);
            try (ResultSet rs = select.executeQuery()) {
                return readListProducts(rs, max);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }        
    }    
    
    /**
     * Inserta un producto en la tabla PRODUCTS_CACHE
     */
    public void insertOrReplaceProduct(ProductCache product) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement insert = conn.prepareStatement(SQLInsertProductCache)) {
            setProduct(insert, product);            
            insert.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }        
    }
    
    /**
     * Inserta un producto en la tabla PRODUCTS_CACHE a partir de un objeto de tipo selArticulo (obtenido mediante llamada a servicio JSON)
     * @param fechaGet fecha en la que se obtuvo el producto mediante el servicio JSON
     * @param caducidad fecha de caducidad
     */
    public void insertOrReplaceProduct(ArticleStock articleStock, String codigoPais, String tipoProd, String almacen, 
    								   String appMango, String bpath, Date fechaGet, Date caducidad) {
        //Creamos un ProductCache a partir del selArticulo + otros datos
        ProductCache productItem = new ProductCache();
        productItem.setAppMango(appMango);
        productItem.setCodigoPais(codigoPais);
        productItem.setUrlEntorno(bpath);
        productItem.setProducto(articleStock.getReference());
        productItem.setColor(articleStock.getColourCode());
        productItem.setTalla(articleStock.getSize());
        productItem.setTipo(tipoProd);
        productItem.setAlmacen(almacen);
        productItem.setObtenido(fechaGet);
        productItem.setCaducidad(caducidad);
        
        //Insertamos el registro en la tabla PRODUCT_CACHE
        insertOrReplaceProduct(productItem);
    }
    
    /**
     * Lee una lista de registros del resultado de una query
     */
    private List<ProductCache> readListProducts(ResultSet rs, int max) throws SQLException {
        List<ProductCache> listProduct = new ArrayList<>();
        int i=0;
        while (rs.next() && i<max) {
            ProductCache productItem = new ProductCache();
            productItem.setAppMango(rs.getString("APP"));
            productItem.setCodigoPais(rs.getString("PAIS"));
            productItem.setUrlEntorno(rs.getString("URL_ENTORNO"));
            productItem.setProducto(rs.getString("PRODUCTO"));
            productItem.setColor(rs.getString("COLOR"));
            productItem.setTalla(rs.getString("TALLA"));
            productItem.setTipo(rs.getString("TIPO"));
            productItem.setAlmacen(rs.getString("ALMACEN"));
            productItem.setObtenido(rs.getDate("OBTENIDO"));
            productItem.setCaducidad(rs.getDate("CADUCIDAD"));
            
            listProduct.add(productItem);
            i+=1;
        }
        
        return listProduct;
     }
    
    /**
     * @param tipoProducto especificado en el servicio JSON
     * @param urlEntorno URL de la aplicación a la que pertenecen los artículos
     * @caducidad fecha de caducidad máxima del artículo
     * @param max número máximo de parámetros
     * @return una lista de productos limitada por el parámetro max
     */
    public void deleteProducts(String producto, String app) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteProductCache)) {      
        	delete.setString(1, producto);
            delete.setString(2, app);
            
            delete.execute();
            
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }        
    }
    
    public void deleteProducts(TypeArticleStock typeArticle) {
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteProductsByTypeArticle)) {      
        	delete.setString(1, typeArticle.name());
            delete.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }        
    }
    
    public static void deleteProductsCaducados() {
    	Date caducidad = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try (Connection conn = Connector.getConnection();
            PreparedStatement delete = conn.prepareStatement(SQLDeleteCaducados)) {      
        	delete.setDate(1, caducidad);
            delete.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }        
    }
    
     /**
     * Setea los datos para la inserción de un registro en PRODUCT_CACHE
     */
    private void setProduct(PreparedStatement insert, ProductCache productItem) throws SQLException {
         insert.setString(1, productItem.getAppMango());
         insert.setString(2, productItem.getCodigoPais());
         insert.setString(3, productItem.getUrlEntorno());
         insert.setString(4, productItem.getProducto());
         insert.setString(5, productItem.getColor());
         insert.setString(6, productItem.getTalla());
         insert.setString(7, productItem.getTipo());
         insert.setString(8, productItem.getAlmacen());
         insert.setDate  (9, productItem.getObtenido());         
         insert.setDate  (10, productItem.getCaducidad());      
     }
}
