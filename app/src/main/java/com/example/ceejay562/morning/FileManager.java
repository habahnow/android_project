package com.example.ceejay562.morning;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceejay562 on 5/21/2015.
 */
public class FileManager {

    private List<File> songs;

    public FileManager(){


        songs = new ArrayList<>();
    }

    public void createList(String filePattern, File dir){
        //list of all teh files from the parent directory
        File fileList[] = dir.listFiles();
        //file path and file name
        if(fileList != null && fileList.length > 0){
            for(File file : fileList){
                if(file.isDirectory()) {
                    createList(filePattern, file);
                }
                else{
                    String fileName = file.getName();
                    if(fileName.endsWith("filePattern")){
                        songs.add(file);
                    }
                }
            }
        }
    }

    public List<File> getSongs(){
        return songs;
    }

}
