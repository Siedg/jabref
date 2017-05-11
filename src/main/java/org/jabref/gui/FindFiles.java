package org.jabref.gui;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Siedg on 11/05/2017.
 */

public class FindFiles {
    public void findAndOpenFile(String name, File file) {
        //String[] nameWithoutType = name.split(".");
        //name = nameWithoutType[0];
        File[] list = file.listFiles();
        if(list != null) {
            for (File f : list) {
                /*
                if (f.isFile()) {
                    String filename = f.getName().substring(0, f.getName().indexOf("."));
                    System.out.println(filename);
                }
                */
                if (f.isDirectory()) {
                    findAndOpenFile(name, f);
                } else if (f.isFile()) {
                    String nameWithoutExtension = FilenameUtils.removeExtension(f.getName());
                    if (name.equalsIgnoreCase(nameWithoutExtension)) {
                        try {
                            Desktop desktop = null;
                            if (Desktop.isDesktopSupported()) {
                                desktop = Desktop.getDesktop();
                            }
                            desktop.open(f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
