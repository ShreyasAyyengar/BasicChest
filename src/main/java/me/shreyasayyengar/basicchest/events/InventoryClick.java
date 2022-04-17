package me.shreyasayyengar.basicchest.events;

import me.shreyasayyengar.basicchest.ChestPlugin;
import me.shreyasayyengar.basicchest.objects.PlayerChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class InventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        UUID uuid = event.getWhoClicked().getUniqueId();

        if (!event.getView().getTitle().toLowerCase().contains("basicchest")) {
            return;
        }
        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        }
        if (item.getItemMeta() == null) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();

        PlayerChest playerChest = ChestPlugin.getInstance().getPlayerChests().get(uuid);
        switch (itemMeta.getLocalizedName().toLowerCase()) {
            case "chest.next": {
                event.setCancelled(true);
                playerChest.openPageTwo();
                break;
            }

            case "chest.back": {
                event.setCancelled(true);
                playerChest.openPageOne();
                break;
            }
        }
    }
}
