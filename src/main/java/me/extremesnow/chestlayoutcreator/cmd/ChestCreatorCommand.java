package me.extremesnow.chestlayoutcreator.cmd;

import me.extremesnow.chestlayoutcreator.ChestLayoutCreator;
import me.extremesnow.chestlayoutcreator.chest.Chest;
import me.extremesnow.chestlayoutcreator.util.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChestCreatorCommand implements CommandExecutor, TabCompleter {

    private final ChestLayoutCreator plugin;

    public ChestCreatorCommand(ChestLayoutCreator plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ChestLayoutCreator] Must be ran by a player.");
            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("chestlayoutcreator")) {
            if (!player.hasPermission("chestlayoutcreator.create")) {
                player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You do not have permission for this command.");
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(ChatColor.AQUA + "---  [ " + ChatColor.DARK_AQUA + "Chest Layout Creator " + ChatColor.AQUA + "] ---");
                player.sendMessage(ChatColor.AQUA + "- " + ChatColor.DARK_AQUA + "/clc create <name> <rows>");
                player.sendMessage(ChatColor.AQUA + "- " + ChatColor.DARK_AQUA + "/clc delete <name>");
                player.sendMessage(ChatColor.AQUA + "- " + ChatColor.DARK_AQUA + "/clc open <name>");
                player.sendMessage(ChatColor.AQUA + "----------------------");
                return true;
            }

            if (args[0].equalsIgnoreCase("create")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You must specify a name for the chest.");
                    return true;
                }
                String name = args[1];
                if (plugin.getChestMap().get(name) != null) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] " + name  + " already exists.");
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You must specify how many rows for the chest.");
                    return true;
                }


                if (!Utilities.isInt(args[2])) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] " + args[2] + " is not a valid number.");
                    return true;
                }
                int i = Integer.parseInt(args[2]);

                if (i > 6 || i < 0) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] Number must be between 1-6");
                    return true;
                }

                if (args.length < 4) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You must specify a inventory title.");
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (int j = 3; j < args.length; j++){
                    sb.append(args[j]);
                    if (j == args.length - 1) {
                        break;
                    } else {
                        sb.append(" ");
                    }
                }
                String title = sb.toString();



                Chest chest = new Chest(name, title, i);
                plugin.getChestMap().put(name, chest);
                player.openInventory(chest.getInventory());
                player.sendMessage(ChatColor.AQUA + "[ChestLayoutCreator] " + ChatColor.DARK_AQUA + "Successfully created Chest: " + name);

                return true;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You must specify a name for the chest.");
                    return true;
                }
                String name = args[1];
                if (plugin.getChestMap().get(name) == null) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] " + name  + " is not a valid chest.");
                    return true;
                }
                plugin.getChestMap().remove(name);
                player.sendMessage(ChatColor.AQUA + "[ChestLayoutCreator] " + ChatColor.DARK_AQUA + "Successfully deleted Chest: " + name);
                return true;
            }

            if (args[0].equalsIgnoreCase("open")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] You must specify a name for the chest.");
                    return true;
                }
                String name = args[1];
                if (plugin.getChestMap().get(name) == null) {
                    player.sendMessage(ChatColor.RED + "[ChestLayoutCreator] " + name  + " is not a valid chest.");
                    return true;
                }
                Chest chest = plugin.getChestMap().get(name);
                player.openInventory(chest.getInventory());
                player.sendMessage(ChatColor.AQUA + "[ChestLayoutCreator] " + ChatColor.DARK_AQUA + "Opened Chest: " + name);
                return true;
            }

        }
        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("create");
            list.add("delete");
            list.add("open");
            List<String> newList = new ArrayList<>();

            StringUtil.copyPartialMatches(args[0], list, newList);
            //sort the list
            Collections.sort(newList);
            return newList;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("open")) {
            List<String> list = new ArrayList<>(plugin.getChestMap().keySet());
            List<String> newList = new ArrayList<>();
            StringUtil.copyPartialMatches(args[1], list, newList);
            //sort the list
            Collections.sort(newList);
            return newList;
        }


        return new ArrayList<>();
    }

}
