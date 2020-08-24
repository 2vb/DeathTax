package me.tooveebee.deathtax.events;

import me.tooveebee.deathtax.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.tooveebee.deathtax.Main.econ;

public class Death implements Listener {
    static Main plugin;
    Economy economy = Main.getEconomy();

    public Death(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent event) {
        if (plugin.getConfig().getBoolean("death-tax-enabled")) {
            Player player = event.getEntity().getPlayer();
            EconomyResponse r = econ.withdrawPlayer(player, plugin.getConfig().getInt("tax-amount"));
            if (r.transactionSuccess()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(plugin.getConfig()
                        .getString("death-message").replace("%s", econ.format(r.amount)))));
            } else {
                if (r.errorMessage == "PlayerAccount lacking funds.") {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(plugin.getConfig()
                            .getString("no-funds-message"))));
                } else {
                    player.sendMessage(String.format("An error occurred: %s", r.errorMessage));
                }
            }
        }
    }
}

