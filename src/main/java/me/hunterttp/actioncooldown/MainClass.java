package me.hunterttp.actioncooldown;

import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {

    public static MainClass instance;

    @Override
    public void onEnable() {

        instance = this;

        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ActionListener(), this);

    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {


    }

    public static MainClass getInstance() {
        return instance;
    }


}