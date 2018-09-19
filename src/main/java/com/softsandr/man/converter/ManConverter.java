package com.softsandr.man.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Just open MAN HTML document and parse necessary data items to SQLite database
 */
public class ManConverter {

    public static void main(String[] args) {
        File manFolder = new File("man/man2");
        SQLiteJDBC jdbc = new SQLiteJDBC("man2");
        for (final File fileEntry : manFolder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                try {
                    Document doc = Jsoup.parse(fileEntry, "UTF-8");
                    Elements elements = doc.getElementsByAttributeValue("style", "margin-left:11%; margin-top: 1em");
//                    if (elements.isEmpty()) {
//                        System.out.println(fileEntry.getName() + ": ");
//                        System.out.println("Empty::::::");
//                        System.out.println();
//                    }
//                    else {
                    if (!elements.isEmpty()) {
                        SQLiteManRecord record = new SQLiteManRecord();
                        record.setName(elements.get(0).html().replace("'", "&#39;"));
                        record.setSynopsis(elements.get(1).html().replace("'", "&#39;"));
                        record.setFile(fileEntry.getName());
                        jdbc.insertData(record);
                    }
//                        System.out.println(fileEntry.getName() + ": ");
//                        System.out.println(elements.get(0).html());
//                        System.out.println(elements.get(1).html());
//                        System.out.println();
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
