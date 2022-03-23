package ru.olofmart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static GameProgress progress1 = new GameProgress(100, 1, 2, 1.5);
    private static GameProgress progress2 = new GameProgress(30, 8, 10, 5.0);
    private static GameProgress progress3 = new GameProgress(1, 3, 4, 3.7);
    private static final String FILE_PATH = "/Users/olmart/Desktop/ProjectsJava/Netology/Games/savegames/save";
    private static final String ZIP_PATH = "/Users/olmart/Desktop/ProjectsJava/Netology/Games/savegames/saves.zip";
    private static List<String> saveFiles = new ArrayList<>();


    public static void main(String[] args) {

        saveGame(FILE_PATH + "1.sv", progress1);
        saveGame(FILE_PATH + "2.sv", progress2);
        saveGame(FILE_PATH + "3.sv", progress3);

        saveFiles.add(FILE_PATH + "1.sv");
        saveFiles.add(FILE_PATH + "2.sv");
        saveFiles.add(FILE_PATH + "3.sv");

        zipFiles(ZIP_PATH, saveFiles);

        new File(FILE_PATH + "1.sv").delete();
        new File(FILE_PATH + "2.sv").delete();
        new File(FILE_PATH + "3.sv").delete();


    }

    public  static void saveGame (String pathFile, GameProgress gameProgress) {
        //File save = new File(pathFile);

        try (FileOutputStream fos = new FileOutputStream(pathFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            //save.createNewFile();
            oos.writeObject(gameProgress);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles (String pathZip, List<String> saveFiles) {

        ZipOutputStream zout = null;
        FileInputStream fis = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(pathZip));
            for (int i = 0; i < saveFiles.size(); i++) {
                fis = new FileInputStream(saveFiles.get(i));
                ZipEntry entry = new ZipEntry(saveFiles.get(i));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
            }
            zout.closeEntry();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally  {
            try {
                zout.close();
                fis.close();
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
