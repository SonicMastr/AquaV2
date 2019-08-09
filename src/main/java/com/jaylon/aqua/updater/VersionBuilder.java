package com.jaylon.aqua.updater;

import net.dv8tion.jda.core.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class VersionBuilder {
    Logger logger = LoggerFactory.getLogger(VersionBuilder.class);

    public VersionBuilder(Message msg) throws IOException, InterruptedException {
        String basePath = new File("./").getAbsoluteFile().getParentFile().getParentFile().getParent();
        logger.info("Building New Version");
        Process process = Runtime.getRuntime().exec("gradle jar", null, new File(basePath));
        int result = process.waitFor();
        if (result != 0) {
            logger.error("Failed to Build Jar");
            msg.editMessage("Failed to Build New Version").queue();
            return;
        }
        logger.info("Build Succeeded");
        msg.editMessage("Build Succeeded. Deploying New Version...").queue();
        new VersionDeployer("./");
    }
}
