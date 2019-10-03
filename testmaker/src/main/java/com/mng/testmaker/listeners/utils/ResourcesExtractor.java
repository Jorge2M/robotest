package com.mng.testmaker.listeners.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.listeners.utils.GetterResources.Directory;
import com.mng.testmaker.utils.controlTest.fmwkTest;

public class ResourcesExtractor {

    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	private ResourcesExtractor() {}
	
	public static ResourcesExtractor getNew() {
		return new ResourcesExtractor();
	}
	
	public void copyDirectoryResources(String dirOriginFromResources, String dirDestination) {
		GetterResources getterResources = GetterResources.makeGetter(dirOriginFromResources);
		try {
			Directory directoryData = getterResources.getDataFromPath();
			if (directoryData!=null) {
				copyDirectoryResources(directoryData, dirDestination);
			} else {
				pLogger.warn("Not found data from path in resources " + dirOriginFromResources);
			}
		} 
		catch (Exception e) {
			pLogger.error("Problem in copy of static directory for HTML Report", e);
		}
	}

	private void copyDirectoryResources(Directory dirOriginFromResources, String dirDestination) 
	throws Exception {
		createDirectoryIfNotExists(dirDestination);
		for (String fileName : dirOriginFromResources.listFiles) {
			String pathFileNameOrigin = dirOriginFromResources.pathFromResources + fileName;
			String pathFileNameDestination = dirDestination + "/" + fileName;
			copyFileFromResources(pathFileNameOrigin, pathFileNameDestination);
		}
		
		for (Directory directory : dirOriginFromResources.listDirectories) {
			copyDirectoryResources(directory, dirDestination + "/" + directory.nameDirectory);
		}
	}
	
    private void createDirectoryIfNotExists(String directoryFromClasspathNormalized) {
    	File newDirectory = new File(directoryFromClasspathNormalized);
    	if (!newDirectory.exists()) {
    		newDirectory.mkdirs();
    	}
    }
    
    private void copyFileFromResources(
    		String pathFileNameOriginFromResources, String pathFileNameDestination) throws IOException {
    	InputStream inputStreamResources = getClass().getResourceAsStream("/" + pathFileNameOriginFromResources);
    	File fileDestination = new File(pathFileNameDestination);
    	FileUtils.copyInputStreamToFile(inputStreamResources, fileDestination);
    }
}
