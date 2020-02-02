package com.jaylon.aqua.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Memory {


    public static double usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return usedMemory(runtime);
    }

    public static double maxMemory() {
        Runtime runtime = Runtime.getRuntime();
        return maxMemory(runtime);
    }

    static double usedMemory(Runtime runtime) {
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        return (double)(totalMemory - freeMemory) / (double)(1024 * 1024);
    }

    static double maxMemory(Runtime runtime) {
        long maxMemory = runtime.maxMemory();
        return (double)maxMemory / (double)(1024 * 1024);
    }

    public static void printMemoryInfo() {
        StringBuffer buffer = getMemoryInfo();
    }

    public static StringBuffer getMemoryInfo() {
        StringBuffer buffer = new StringBuffer();

        Runtime runtime = Runtime.getRuntime();
        double usedMemory = usedMemory(runtime);
        double maxMemory = maxMemory(runtime);

        NumberFormat f = new DecimalFormat("###,##0.0");

        String lineSeparator = System.getProperty("line.separator");
        buffer.append("Used memory: ").append(f.format(usedMemory)).append("MB").append(lineSeparator);
        buffer.append("Max available memory: ").append(f.format(maxMemory)).append("MB").append(lineSeparator);
        return buffer;
    }

    public static void freeMemory() {
        System.gc();
        System.runFinalization();
    }

}
