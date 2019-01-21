package org.pruebasws.utils;

import java.util.List;
import java.io.*;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

@SuppressWarnings("javadoc")
public class fileUtils {
	
    //Función recursiva que busca un "nombrFich" recursivamente a partird de un directorio root
    public static void searchForDatFiles(File root, String nombreFich, List<File> listFiles) {
        if(root == null || listFiles == null) return; //just for safety   
        if(root.isDirectory()) {
            for(File file : root.listFiles())
                searchForDatFiles(file, nombreFich, listFiles); 
        }
        else {
            if(root.isFile() && root.getName().compareTo(nombreFich)==0) {
                listFiles.add(root);
            }
        }
    }	
	
    public static List<File> filesInFolderSortByDate(String path, boolean asc) throws Exception {
        System.out.println("Path: " + path);
        List<File> filesInFolder = Files.list(
            Paths.get(path)).
                filter(Files::isDirectory).
                    map(Path::toFile).
                        collect(Collectors.toList());
        
        Comparator<File> fileComparator = (File f1, File f2) -> {
            return Long.compare(f2.lastModified(), f1.lastModified());
        };
        
        Collections.sort(filesInFolder, fileComparator);
        
        System.out.println("Tamaño lista ficheros: " + filesInFolder.size());
        return filesInFolder;
    }
}
