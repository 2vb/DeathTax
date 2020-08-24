package me.tooveebee.deathtax;

import me.tooveebee.deathtax.events.Death;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static Economy econ;

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        PluginManager pm = this.getServer().getPluginManager();
        this.getCommand("dt").setExecutor(new DeathTax(this));
        if (this.getConfig().getBoolean("death-tax-enabled")) pm.registerEvents(new Death(this), this);
        this.getServer().getPluginManager().registerEvents(this, this);
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {

    }
}