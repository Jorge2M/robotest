package com.mng.robotest.test80.mango.test.utils.awssecrets;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public class GetterLocalFileSecrets extends GetterSecretsFromInputStream {

	private final static String PATH_FILE = "C:\\mango\\ficheros\\secrets\\secrets.xml";
	
	@Override
	InputStream getInputStreamSecrets() throws Exception {
		return new FileInputStream(new File(PATH_FILE));
	}
	
}
