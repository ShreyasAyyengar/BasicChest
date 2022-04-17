package me.shreyasayyengar.basicchest.objects;

import me.shreyasayyengar.basicchest.ChestPlugin;
import me.shreyasayyengar.basicchest.util.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class PlayerChest {

    private final UUID uuid;

    private Inventory pageOne;
    private Inventory pageTwo;

    public PlayerChest(Inventory pageOne, Inventory pageTwo, UUID uuid) {
        this.uuid = uuid;
        this.pageOne = pageOne;
        this.pageTwo = pageTwo;
    }

    public void openPageOne() {
        if (Bukkit.getPlayer(uuid) != null) {

            if (getSerialisedInvOne() == null) {
                Bukkit.getPlayer(uuid).openInventory(InventoryUtils.getPageOneTemplate());
                return;
            }
            Bukkit.getPlayer(uuid).openInventory(pageOne);
        }

    }

    public void openPageTwo() {
        if (Bukkit.getPlayer(uuid) != null) {
            if (getSerialisedInvTwo() == null) {
                Bukkit.getPlayer(uuid).openInventory(InventoryUtils.getPageTwoTemplate());
                return;
            }
            Bukkit.getPlayer(uuid).openInventory(pageTwo);
        }
    }

    public void writeToDatabase() {
        ChestPlugin.getInstance().getDatabase().writePlayerChest(this);
    }

    // --------------------------------------------------

    public String getSerialisedInvOne() {
        return InventoryUtils.serialise(pageOne);

    }

    public String getSerialisedInvTwo() {
        return InventoryUtils.serialise(pageTwo);
    }

    public void setPageOne(Inventory pageOne) {
        this.pageOne = pageOne;
    }

    public void setPageTwo(Inventory pageTwo) {
        this.pageTwo = pageTwo;
    }

    public UUID getUUID() {
        return uuid;
    }
}
