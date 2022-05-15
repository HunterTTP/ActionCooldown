package com.ActionCooldown;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionCooldownMainClass extends JavaPlugin {
	
	public static ActionCooldownMainClass instance;
	
	//public static Plugin plugin = null;
	
    //public static long DEFAULT_COOLDOWN = ActionCooldownMainClass.getConfig().getLong("global-cooldown-seconds");
    
	// Fired when plugin is first enabled
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
    
    public static ActionCooldownMainClass getInstance() {
        return instance;
    }
    

}