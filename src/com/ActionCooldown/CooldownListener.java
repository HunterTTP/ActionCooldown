package com.ActionCooldown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CooldownListener implements Listener{

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
    	event.setCancelled(true);
    	}
    
}