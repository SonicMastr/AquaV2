package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class Execute implements CommandInterface {
    private int limit = 1927;
    @Override
    public void run(List<String> args, MessageReceivedEvent event) throws IOException, InterruptedException {
        event.getChannel().sendMessage("<a:loading:649047596022759425> `Running...`").queue(message -> {
            long startTime = System.currentTimeMillis();
            String error = ":x: Something went wrong, and I'm not smart enough to explain what it was.";
            if(args.isEmpty()) {
                return;
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            StringBuilder output = new StringBuilder();
            if(System.getProperty("os.name").startsWith("Win")) {
                try {
                    processBuilder.command("cmd.exe", "/c", String.join(" ", args));
                    startProcess(message, startTime, processBuilder, output);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    message.editMessage(error).queue();
                }
            }
            else {
                try {
                    processBuilder.command("sh", "-c", String.join(" ", args));
                    startProcess(message, startTime, processBuilder, output);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    message.editMessage(error).queue();
                }
            }
        });
    }

    private void startProcess(Message message, long startTime, ProcessBuilder processBuilder, StringBuilder output) throws IOException, InterruptedException {
        double elapsedTime;
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String out;
        while ((out = reader.readLine()) != null) {
            output.append(out + "\n");
        }
        int exitVal = process.waitFor();
        if (exitVal == 0) {
            elapsedTime = (new Date().getTime() - startTime) / (double) 1000;
            if(output.length() > limit) {
                Files.writeString(Path.of("output.txt"), output.toString());
                message.editMessageFormat(":white_check_mark: `Finished in %.3f seconds`%nThat was a bit big, so here you go.", elapsedTime).queue();
                message.getChannel().sendFile(new File("output.txt")).queue();
            } else {
                message.editMessageFormat(":white_check_mark: `Finished in %.3f seconds` ```java%n%s%n```", elapsedTime, clean(output.toString())).queue();
            }
        } else {
            message.editMessage(":thinking: So it may not have failed, but it also may not have succeeded either. I don't know. Take it or leave it.").queue();
        }
    }

    private String clean(String result) {
        String out;
        out = result.replace("`", "`" + "\u200b").replace("@", "@" + "\u200b");
        return out;
    }
    @Override
    public String getName() {
        return "exec";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"ex"};
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Execute Console Commands";
    }

    @Override
    public String getType() {
        return "owner";
    }
}
