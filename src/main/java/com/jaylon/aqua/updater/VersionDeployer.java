package com.jaylon.aqua.updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


public class VersionDeployer {
    private Logger logger = LoggerFactory.getLogger(VersionDeployer.class);

    public VersionDeployer(String list) throws NullPointerException, IOException {
        final List<String> versions = new VersionManager().checkDir(list);
        final Double versionCurrent = new VersionManager().getVersion();
        for (String versionTemp : versions) {
            final String[] split = versionTemp.replaceFirst("(?i)" + Pattern.quote("AquaV2-"), "")
                    .split("\\s+");
            final String versionTemp2 = split[0];
            final String[] split2 = versionTemp2.replaceFirst("(?i)" + Pattern.quote(".jar"), "")
                    .split("\\s+");
            final String versionStr = split2[0];
            final Double versionNumb = Double.parseDouble(versionStr);
            if (versionNumb < versionCurrent) {
                logger.info("Found Old Version: " + versionNumb.toString());
                logger.warn("Attempting to Delete...");
                new VersionManager().deleteJar(versionNumb);
            } else if (versionNumb.equals(versionCurrent)) {
                continue;
            } else if (versionNumb > versionCurrent) {
                logger.info("Found New Version: " + versionNumb.toString());
                logger.info("Attempting to Start Newer Version...");
                Process process = Runtime.getRuntime().exec("java -jar AquaV2-" + versionNumb.toString() + ".jar");
                if (process.isAlive()) {
                    logger.info("Started New Version. Shutting down...");
                    System.exit(1);
                }
            }
        }
        logger.info("Up to Date!");
    }
}
