package me.shreyasayyengar.basicchest.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class InventoryUtils {

    public static String serialise(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static Inventory deserialise(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(), Utils.colourise("&6Your BasicChest"));

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static Inventory getPageOneTemplate() {
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.colourise("&6Your BasicChest"));
        inventory.setItem(inventory.getSize() - 1, getNextArrow());
        return inventory;
    }

    public static Inventory getPageTwoTemplate() {
        Inventory inventory = Bukkit.createInventory(null, 54, Utils.colourise("&6Your BasicChest"));

        int rows = inventory.getSize() / 9;
        int bottomLeft = (rows * 9) - 9;

        inventory.setItem(bottomLeft, getBackArrow());
        return inventory;
    }

    public static ItemStack getNextArrow() {
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = stack.getItemMeta();

        itemMeta.setLocalizedName("chest.next");
        itemMeta.setDisplayName(Utils.colourise("&aNext Page"));
        itemMeta.setLore(Arrays.asList("&7Click to go to the next page."));
        stack.setItemMeta(itemMeta);

        return stack;
    }

    public static ItemStack getBackArrow() {
        ItemStack stack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = stack.getItemMeta();

        itemMeta.setLocalizedName("chest.back");
        itemMeta.setDisplayName(Utils.colourise("&aBack Page"));
        itemMeta.setLore(Arrays.asList("&7Click to go to the previous page."));
        stack.setItemMeta(itemMeta);

        return stack;
    }
}
