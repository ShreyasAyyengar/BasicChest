package me.shreyasayyengar.basicchest;

import me.shreyasayyengar.basicchest.commands.ChestCommand;
import me.shreyasayyengar.basicchest.events.InventoryClick;
import me.shreyasayyengar.basicchest.events.InventoryClose;
import me.shreyasayyengar.basicchest.objects.MongoDB;
import me.shreyasayyengar.basicchest.objects.PlayerChest;
import me.shreyasayyengar.basicchest.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Stream;

public final class ChestPlugin extends JavaPlugin {

    private final Map<UUID, PlayerChest> playerChests = new HashMap<>();
    private MongoDB database;

    public static ChestPlugin getInstance() {
        return ChestPlugin.getPlugin(ChestPlugin.class);
    }

    @Override
    public void onEnable() {
        getLogger().info("BasicChest is now enabled!");

        Config.init(this);
        initDB();
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        playerChests.forEach((uuid, playerChest) -> playerChest.writeToDatabase());
    }

    private void initDB() {
        database = new MongoDB(Config.getConnectionString());
    }

    private void registerEvents() {
        Stream.of(
                new InventoryClick(),
                new InventoryClose()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerCommands() {
        this.getCommand("chest").setExecutor(new ChestCommand());
    }

    // ------------------------------------------------------------------------
    public MongoDB getDatabase() {
        return database;
    }

    public Map<UUID, PlayerChest> getPlayerChests() {
        return playerChests;
    }


}
