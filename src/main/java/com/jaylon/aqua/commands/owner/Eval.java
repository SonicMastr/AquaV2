package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Eval implements CommandInterface {
    private final GroovyShell engine;
    private final String imports;

    public Eval() {
        this.engine = new GroovyShell();
        this.imports = "import java.io.*\n" +
                "import java.lang.*\n" +
                "import java.util.*\n" +
                "import java.util.concurrent.*\n" +
                "import net.dv8tion.jda.core.*\n" +
                "import net.dv8tion.jda.core.entities.*\n" +
                "import net.dv8tion.jda.core.entities.impl.*\n" +
                "import net.dv8tion.jda.core.managers.*\n" +
                "import net.dv8tion.jda.core.managers.impl.*\n" +
                "import net.dv8tion.jda.core.utils.*\n";
    }


    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        if (args.isEmpty()) {
            event.getChannel().sendMessage("You didn't provide arguments").queue();
            return;
        }

        try {
            long startTime = System.currentTimeMillis();
            double elapsedTime;
            engine.setProperty("args", args);
            engine.setProperty("event", event);
            engine.setProperty("message", event.getMessage());
            engine.setProperty("channel", event.getChannel());
            engine.setProperty("jda", event.getJDA());
            engine.setProperty("guild", event.getGuild());
            engine.setProperty("member", event.getMember());

            String script = imports + String.join(" ", args);
            Object out = engine.evaluate(script);
            elapsedTime = (System.currentTimeMillis() - startTime) / (double) 1000;

            if (out == null) {
                event.getChannel().sendMessageFormat(":white_check_mark: `Executed without error in %.3f seconds`", elapsedTime).queue();
            } else {
                event.getChannel().sendMessageFormat(":white_check_mark: `Finished in %.3f seconds` ```java%n%s%n```", elapsedTime, out.toString()).queue();
            }

        } catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "eval";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"ev"};
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Evaluates Java Code";
    }

    @Override
    public String getType() {
        return "owner";
    }
}
