package com.nutrymaco.jobsite.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class File {
    public static String loadFromFile(String fileName) throws IllegalStateException {
        StringBuilder buffer = new StringBuilder(2048);
        try {
            InputStream is = File.class.getResourceAsStream(fileName); //тут может не работать, заменил getcClass() на File.class
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
            while (reader.ready()) {
                buffer.append(reader.readLine());
                buffer.append(' ');
            }
        } catch (Exception e) {
            throw new IllegalStateException("couldn't load file " + fileName, e);
        }
        return buffer.toString();
    }
}
