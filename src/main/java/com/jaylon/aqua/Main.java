package com.jaylon.aqua;

import com.jaylon.aqua.config.Config;
import com.jaylon.aqua.events.*;
import com.jaylon.aqua.updater.VersionDeployer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main {

    private Main() throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Checking For Updated Jar...");
        new VersionDeployer("./");
        Config config = new Config(new File("config.json"));
        CommandHandler commandHandler = new CommandHandler();
        MessageReceived messageReceived = new MessageReceived(commandHandler);

        try {
            logger.info("Starting");
            new JDABuilder(AccountType.BOT)
                    .setToken(config.getString("token"))
                    .setAudioEnabled(false)
                    .setGame(Game.playing("Being Useless (In Java)"))
                    .addEventListener(new Ready(), messageReceived)
                    .build().awaitReady();
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
