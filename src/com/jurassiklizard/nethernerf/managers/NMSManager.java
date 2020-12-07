package com.jurassiklizard.nethernerf.managers;

import org.bukkit.Bukkit;

public class NMSManager {
    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
    }
}
