package org.pruebasws.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

import com.mng.testmaker.data.ConstantesTestMaker;

@SuppressWarnings("javadoc")
public class Connector {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection(true/*forReadOnly*/);
    }
    
    public static Connection getConnection(boolean forReadOnly) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName("org.sqlite.JDBC");
        System.getProperty("user.dir");
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(forReadOnly); 
        config.setBusyTimeout(30000);
        config.setLockingMode(LockingMode.NORMAL);
        
        // Nos conectamos a la ubicación propia de la BD en instalaciones de la aplicación
        conn = DriverManager.getConnection(
        	"jdbc:sqlite:" + System.getProperty("user.dir") + File.separator + 
        	ConstantesTestMaker.directoryOutputTests + File.separator + 
        	"sqlite" + File.separator + 
        	"TESTSUITES_WS.sqlite", config.toProperties());
        return conn;
    }
}
