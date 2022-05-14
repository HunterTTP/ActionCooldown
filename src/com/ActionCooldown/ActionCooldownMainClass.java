package com.ActionCooldown;

import org.bukkit.plugin.java.JavaPlugin;

public class ActionCooldownMainClass extends JavaPlugin {
    
	// Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	
    	this.getCommand("cooldown").setExecutor(new CooldownCommand());
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	

   }
}