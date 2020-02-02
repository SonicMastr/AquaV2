package com.jaylon.aqua;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Settings {

    public static final String PREFIX = "<@!557340223864832021>";
    public static final long OWNER = 294544470953689088L;
    public static Map<Long, String> PREFIXES = new HashMap<>();

    public static void getPrefixes() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("customprefix.file"));

        Map<Long, String> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String[] columns = scanner.nextLine().split(" ");
            map.put(Long.parseLong(columns[0]), columns[1]);
        }

        scanner.close();
        PREFIXES = map;
    }

    public static void setPrefixes(long id, String prefix) {
        PREFIXES.put(id, prefix);

        File file = new File("customprefix.file");
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {

            for (Map.Entry<Long, String> entry : PREFIXES.entrySet()) {
                bf.write(entry.getKey() + " " + entry.getValue());

                bf.newLine();
            }
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
