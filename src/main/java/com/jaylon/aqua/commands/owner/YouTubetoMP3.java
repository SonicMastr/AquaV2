package com.jaylon.aqua.commands.owner;

import com.jaylon.aqua.objects.CommandInterface;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class YouTubetoMP3 implements CommandInterface {
    @Override
    public void run(List<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage("<a:loading:649047596022759425> `Running...`").queue(message -> {
            String error = ":x: Something went wrong, and I'm not smart enough to explain what it was.";
            if(args.isEmpty()) {
                return;
            }
            ProcessBuilder processBuilder = new ProcessBuilder();
            if(System.getProperty("os.name").startsWith("Win")) {
                try {
                    System.out.println(args.get(0));
                    processBuilder.command("cmd.exe", "/c", "youtube-dl -f bestaudio --extract-audio --audio-format mp3 --audio-quality 0 --no-continue -o output.mp3 " + args.get(0));
                    startProcess(message,  processBuilder);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    message.editMessage(error).queue();
                }
            }
            else {
                try {
                    processBuilder.command("sh", "-c", "ytdl " + args + " | ffmpeg -y -i pipe:0 -b:a 128K -vn output.mp3");
                    startProcess(message,  processBuilder);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    message.editMessage(error).queue();
                }
            }
        });

    }

    @Override
    public String getName() {
        return "mp3";
    }

    @Override
    public String[] getAliases() {
        return null;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public String getDesc() {
        return "Gives you an MP3 of your YouTube link";
    }

    @Override
    public String getType() {
        return "owner";
    }

    private void startProcess(Message message, ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.inheritIO().start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String out;
        while ((out = reader.readLine()) != null) {
            output.append(out + "\n");
        }
        System.out.println(output.toString());
        int exitVal = process.waitFor();
        if (exitVal == 0) {
                message.editMessage(":white_check_mark: Here you go").queue();
                message.getChannel().sendFile(new File("output.mp3")).queue();
        } else {
            message.editMessage(":thinking: So it may not have failed, but it also may not have succeeded either. I don't know. Take it or leave it.").queue();
        }
    }
}
