package me.hunterttp.actioncooldown;

import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {

    public static MainClass instance;
    public static MainClass getInstance() {return instance;}

    @Override
    public void onEnable() {

        instance = this;

        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ActionListener(), this);

        this.getCommand("actioncd").setExecutor(new CommandListener());

    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {}

}