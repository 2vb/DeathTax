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
import org.bukkit.event.player.PlayerRespawnEvent;

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
                        .getString("death-message").replace("%amount", econ.format(r.amount).replace("%balance%", econ.format(r.balance))))));
            } else {
                if (r.errorMessage == "PlayerAccount lacking funds.") {
                    player.sendMessage(ChatColor.RED + plugin.getConfig().getString("no-funds-message"));
                    if (plugin.getConfig().getBoolean("no-funds-punishment")) {
                    }

                } else {
                    player.sendMessage(String.format("An error occurred: %s", r.errorMessage));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRespawn(PlayerRespawnEvent respawnevent) {
        if (plugin.getConfig().getBoolean("lower-health-punishment")) {
            Player player = respawnevent.getPlayer();
            player.setHealth(10);
        }
    }
}
