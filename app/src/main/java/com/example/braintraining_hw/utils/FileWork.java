package com.example.braintraining_hw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileWork {

    private static SharedPreferences sharedPreferences;


    public static void initializeSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstant.NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void writeIntToSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int readIntFromSharedPreferences(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }


    public static boolean isFileExists(String filename, Context context) {
        File file = context.getFileStreamPath(filename);
        return file.exists();
    }

    public static String writeDataToFile(String filename, List<String> data, Context context) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            for (String d : data) {
                fos.write(d.getBytes());
            }
            return "";
        }
        catch(IOException ex) {
            return ex.getMessage();
        }
    }

    public static List<String> readDataFromFile(String filename, Context context) {
        List<String> data = new ArrayList<>();

        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        }
        catch (IOException ex) {
            return new ArrayList<>();
        }

        return data;
    }
}
