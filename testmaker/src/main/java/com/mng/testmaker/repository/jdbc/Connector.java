package com.mng.testmaker.repository.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.LockingMode;

import com.mng.testmaker.conf.ConstantesTM;


public class Connector {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection(false);
    }
    
    public static Connection getConnection(boolean forReadOnly) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName("org.sqlite.JDBC");
        System.getProperty("user.dir");
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(forReadOnly); 
        config.setBusyTimeout(30000);
        config.setLockingMode(LockingMode.NORMAL);
        
        conn = DriverManager.getConnection(
        	"jdbc:sqlite:" + 
        	getSQLiteFilePathAutomaticTestingSchema(), 
        	config.toProperties());
        return conn;
    }
    
    public static String getSQLitePathDirectory() {
    	String path = 
    		System.getProperty("user.dir") + File.separator + 
    		ConstantesTM.directoryOutputTests + File.separator + 
    		"sqlite" + File.separator;
    	return path;
    }
    
    public static String getSQLiteFilePathAutomaticTestingSchema() {
    	return (
    		getSQLitePathDirectory() + 
    		ConstantesTM.SQLiteFileAutomaticTestingSchema);
    }
}
