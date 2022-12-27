package me.extremesnow.chestlayoutcreator;

import lombok.Getter;
import lombok.Setter;
import me.extremesnow.chestlayoutcreator.chest.Chest;
import me.extremesnow.chestlayoutcreator.cmd.ChestCreatorCommand;
import me.extremesnow.chestlayoutcreator.util.DebugLogger;
import me.extremesnow.chestlayoutcreator.util.FileUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ChestLayoutCreator extends JavaPlugin {


    @Getter@Setter
    Map<String, Chest> chestMap = new HashMap<>();

    @Override
    public void onEnable() {

        FileUtil.loadFile(this);
        DebugLogger.setDebug(false);
        getCommand("chestlayoutcreator").setExecutor(new ChestCreatorCommand(this));
        getCommand("chestlayoutcreator").setTabCompleter(new ChestCreatorCommand(this));

    }

    @Override
    public void onDisable() {
        FileUtil.serializeMap(this);
        // Plugin shutdown logic
    }
}
