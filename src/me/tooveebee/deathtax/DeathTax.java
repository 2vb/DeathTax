package me.tooveebee.deathtax;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class DeathTax implements Listener, CommandExecutor {
    static Main plugin;

    public DeathTax(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("dt")) {
            if (args.length == 0) {
                help(sender);
            } else {
                if (args[0].equalsIgnoreCase("help")) {
                    help(sender);
                } else {
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (sender.hasPermission("tooveebee.reload")) {
                            plugin.reloadConfig();
                            plugin.saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "DeathTax has been reloaded.");
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command. You need the permission " + ChatColor.RED + "tooveebee.reload" + ChatColor.DARK_RED + " to use this command.");
                        }
                    } else if (args[0].equalsIgnoreCase("enable")) {
                        if (sender.hasPermission("tooveebee.enable")) {
                            plugin.getConfig().set("death-tax-enabled", true);
                            plugin.saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "DeathTax has been enabled.");
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command. You need the permission " + ChatColor.RED + "tooveebee.enable" + ChatColor.DARK_RED + " to use this command.");
                        }
                    } else if (args[0].equalsIgnoreCase("disable")) {
                        if (sender.hasPermission("tooveebee.disable")) {
                            plugin.getConfig().set("death-tax-enabled", false);
                            plugin.saveConfig();
                            sender.sendMessage(ChatColor.RED + "DeathTax has been disabled.");
                        } else {
                            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command. You need the permission " + ChatColor.RED + "tooveebee.disable" + ChatColor.DARK_RED + " to use this command.");
                        }
                    } else if (args[0].equalsIgnoreCase("settaxamount")) {
                        if (sender.hasPermission("tooveebee.deathtax")) {
                            if (args.length > 1) {
                                try {
                                    plugin.getConfig().set("tax-amount", Integer.parseInt(args[1]));
                                    plugin.saveConfig();
                                    sender.sendMessage("Set DeathTax amount to " + args[1]);
                                } catch (Exception e) {
                                    sender.sendMessage(ChatColor.DARK_RED + "Invalid arguments! Please make sure you provided a valid number.");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "Invalid arguments! Please make sure you provided a valid number.");
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=====================================================");
        sender.sendMessage(ChatColor.BLUE + "/dt" + ChatColor.WHITE + " Shows this help message.");
        sender.sendMessage(ChatColor.BLUE + "/dt help" + ChatColor.WHITE + " Shows this help message.");
        sender.sendMessage(ChatColor.BLUE + "/dt reload" + ChatColor.WHITE + " Reloads DeathTax.");
        sender.sendMessage(ChatColor.BLUE + "/dt enable" + ChatColor.WHITE + " Enables DeathTax.");
        sender.sendMessage(ChatColor.BLUE + "/dt disable" + ChatColor.WHITE + " Disables DeathTax.");
        sender.sendMessage(ChatColor.BLUE + "/dt settaxamount" + ChatColor.WHITE + " Sets tax amount on Death.");
        sender.sendMessage(ChatColor.GOLD + "=====================================================");
    }

}
