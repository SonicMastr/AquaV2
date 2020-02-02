package com.jaylon.aqua;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jaylon.aqua.config.Config;
import com.jaylon.aqua.events.*;
import com.jaylon.aqua.updater.VersionDeployer;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main {

    private Main() throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        new VersionDeployer("./");
        Config config = new Config(new File("config.json"));
        EventWaiter waiter = new EventWaiter();
        CommandRegister commandRegister = new CommandRegister(waiter);
        MessageReceived messageReceived = new MessageReceived(commandRegister);
        Settings.getPrefixes();

        try {
            logger.info("Starting");
            new DefaultShardManagerBuilder()
                    .setToken(config.getString("token"))
                    .setShardsTotal(2)
                    .setActivity(Activity.playing("Being Useless (In Java)"))
                    .addEventListeners(new Ready(), messageReceived, waiter)
                    .build();
            logger.info("Running");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
