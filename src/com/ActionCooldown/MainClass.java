package com.ActionCooldown;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
	
	public static MainClass instance;
	
    @Override
    public void onEnable() {
    	
    	instance = this;
    	
    	this.saveDefaultConfig();
    	
    	getServer().getPluginManager().registerEvents(new CooldownListener(), this);
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	

   }
    
    public static MainClass getInstance() {
        return instance;
    }
    

}