package com.jaylon.aqua.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Uptime {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    long uptime = runtimeMXBean.getUptime();
    long uptimeInSeconds = uptime / 1000;
    long uptimeHours = uptimeInSeconds / (60 * 60);
    long uptimeMinutes = (uptimeInSeconds / 60) - (uptimeHours * 60);
    long uptimeSeconds = uptimeInSeconds % 60;

    public String getUptime() {
        return String.format("Uptime: %sh %sm %ss", uptimeHours, uptimeMinutes, uptimeSeconds);
    }

    public long getSeconds() {
        return uptimeSeconds;
    }

    public long getMinutes() {
        return uptimeMinutes;
    }

    public long getHours() {
        return uptimeHours;
    }
}
