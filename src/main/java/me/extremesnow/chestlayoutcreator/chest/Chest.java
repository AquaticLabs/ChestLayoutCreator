package me.extremesnow.chestlayoutcreator.chest;

import lombok.Getter;
import me.extremesnow.chestlayoutcreator.util.SerializeInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.Serializable;

@Getter
public class Chest {

    private Inventory inventory;
    private String name;
    private String title;
    private int rows;

    public Chest(String name, int rows) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.rows = rows;
        inventory = Bukkit.createInventory(null, rows * 9, this.name);
    }

    public Chest(String name, String title, int rows) {
        this.name = name;
        this.rows = rows;
        this.title = title;
        title = ChatColor.translateAlternateColorCodes('&', title);
        inventory = Bukkit.createInventory(null, rows * 9, title);
    }

    public Chest(String name, String title, int rows, String s) {
        this.name = name;
        this.rows = rows;
        this.title = title;
        title = ChatColor.translateAlternateColorCodes('&', title);
        inventory = Bukkit.createInventory(null, rows * 9, title);
        try {
            ItemStack[] is = SerializeInventory.itemStackArrayFromBase64(s);
            int j = 0;
            for (ItemStack i : is) {
                if (i == null) {
                    inventory.setItem(j, new ItemStack(Material.AIR));
                    j++;
                    continue;
                }
                inventory.setItem(j, i);
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
