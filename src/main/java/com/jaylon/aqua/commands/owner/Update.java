package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import com.jaylon.aqua.updater.VersionBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class Update implements CommandInterface {

    Logger logger = LoggerFactory.getLogger(Update.class);

    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Updating...").queue(( message -> {
            String basePath = new File("./").getAbsoluteFile().getParentFile().getParentFile().getParent();
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("git pull origin master", null, new File(basePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            InputStream err = process.getErrorStream();
            int result = 0;
            try {
                result = process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result != 0) {
                try {
                    logger.error("Error While " + br.readLine());
                    message.editMessageFormat("Error While " + br.readLine()).queue();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (s.contentEquals("Already up to date.")) {
                logger.info(s);
                message.editMessage(s).queue();
            } else {
                logger.info("Found Update.Building...");
                message.editMessage("Found Update. Building...").queue((msg -> {
                    try {
                        new VersionBuilder(msg);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            }
        }));
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"u"};
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Updates Bot to Newer Version if Available";
    }

    @Override
    public String getType() {
        return "owner";
    }
}
