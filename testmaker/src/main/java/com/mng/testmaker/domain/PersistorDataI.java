package com.mng.testmaker.domain;

import java.sql.Connection;
import java.sql.SQLException;

public interface PersistorDataI {
	
	public void store(SuiteTM suite);
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	
}
