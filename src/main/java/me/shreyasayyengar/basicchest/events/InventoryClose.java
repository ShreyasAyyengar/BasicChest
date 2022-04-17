package me.shreyasayyengar.basicchest.events;

import me.shreyasayyengar.basicchest.ChestPlugin;
import me.shreyasayyengar.basicchest.objects.PlayerChest;
import me.shreyasayyengar.basicchest.util.InventoryUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (!event.getView().getTitle().toLowerCase().contains("basicchest")) {
            return;
        }

        PlayerChest playerChest = ChestPlugin.getInstance().getPlayerChests().get(event.getPlayer().getUniqueId());

        if (event.getInventory().contains(InventoryUtils.getNextArrow())) {
            playerChest.setPageOne(event.getInventory());
        } else playerChest.setPageTwo(event.getInventory());

    }
}
