package com.ActionCooldown;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionCooldownMainClass extends JavaPlugin {
    
	// Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	
    	this.getCommand("cooldown").setExecutor(new CooldownCommand());
    	
    	getServer().getPluginManager().registerEvents(new CooldownListener(), this);
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	

   }
    

}