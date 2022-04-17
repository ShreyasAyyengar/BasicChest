package me.shreyasayyengar.basicchest.util;

import me.shreyasayyengar.basicchest.ChestPlugin;

public class Config {

    private static ChestPlugin main;

    public static void init(ChestPlugin main) {
        Config.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static String getConnectionString() {
        return main.getConfig().getString("mongodb-connection-string");
    }
}
