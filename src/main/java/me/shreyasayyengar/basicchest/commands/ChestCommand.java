package me.shreyasayyengar.basicchest.commands;

import me.shreyasayyengar.basicchest.ChestPlugin;
import me.shreyasayyengar.basicchest.objects.PlayerChest;
import me.shreyasayyengar.basicchest.util.InventoryUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Map<UUID, PlayerChest> chests = ChestPlugin.getInstance().getPlayerChests();
            if (chests.containsKey(player.getUniqueId())) {
                chests.get(player.getUniqueId()).openPageOne();
            } else {
                PlayerChest newChest = new PlayerChest(InventoryUtils.getPageOneTemplate(), InventoryUtils.getPageTwoTemplate(), player.getUniqueId());
                chests.put(player.getUniqueId(), newChest);
                newChest.openPageOne();
            }
        }

        return false;
    }
}
