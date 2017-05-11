package org.jabref.gui;

import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Siedg on 11/05/2017.
 */

public class FindFiles {
    public void findAndOpenFile(String name, File file, ArrayList<File> files) {
        File[] list = file.listFiles();
        if(list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    findAndOpenFile(name, f, files);
                } else if (f.isFile()) {
                    String nameWithoutExtension = FilenameUtils.removeExtension(f.getName());
                    if (name.equalsIgnoreCase(nameWithoutExtension)) {
                        if (!files.contains(f)){
                            files.add(f);
                        }
                    }
                }
            }
        }
    }
}
