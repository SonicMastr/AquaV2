package com.jaylon.aqua.updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VersionManager {

    Logger logger = LoggerFactory.getLogger(VersionManager.class);
    private final List<String> versions = new ArrayList<>();

    public List<String> checkDir(String list) throws NullPointerException{
        File folder = new File(list);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".jar"));
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                versions.add(listOfFiles[i].getName());
                logger.info("Found Version: " + versions.get(i));
            } else if (listOfFiles[i].isDirectory()) {
                logger.info("Directory... Skipping");
            }
        }
        return versions;
    }

    double getVersion() {
        return 1.13;
    }

    public void deleteJar(Double version) {
        File file = new File("./AquaV2-" + version.toString() + ".jar");

        if(file.delete())
        {
            logger.info("Deleted Old Version");
        }
        else
        {
            logger.error("Failed to Delete Old Version");
        }
    }
}
