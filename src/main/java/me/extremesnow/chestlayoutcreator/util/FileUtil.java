package me.extremesnow.chestlayoutcreator.util;

import me.extremesnow.chestlayoutcreator.ChestLayoutCreator;
import me.extremesnow.chestlayoutcreator.chest.Chest;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {


    public static void loadFile(ChestLayoutCreator plugin) {
        File file = new File(plugin.getDataFolder(), "chestdata.ser");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serializeMap(plugin);
        } else {
            plugin.setChestMap(deserializeMap(file));
        }
    }

    public static HashMap<String, Chest> deserializeMap(File file) {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, Chest> chestMap = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap) ois.readObject();
            ois.close();
            fis.close();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                DebugLogger.logDebugMessage(entry.getValue());

                String invName = entry.getValue().split(",")[0];
                String title = entry.getValue().split(",")[1];
                int i = Integer.parseInt(entry.getValue().split(",")[2]);
                chestMap.put(entry.getKey(), new Chest(invName, title, i, entry.getValue().split(",")[3]));
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }


        DebugLogger.logDebugMessage("Chest Data Deserialize Complete ");
        return chestMap;
    }

    public static void serializeMap(ChestLayoutCreator plugin) {
        File file = new File(plugin.getDataFolder(), "chestdata.ser");
        if (file.exists()) {
            file.delete();
        }
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Chest> entry : plugin.getChestMap().entrySet()) {
            map.put(entry.getKey(), entry.getValue().getName() + "," + entry.getValue().getTitle() + "," + entry.getValue().getRows() + "," + SerializeInventory.itemStackArrayToBase64(entry.getValue().getInventory().getStorageContents()));
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }




}
