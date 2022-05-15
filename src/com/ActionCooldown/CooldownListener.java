package com.ActionCooldown;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class CooldownListener implements Listener{

	private final CooldownManager cooldownManager = new CooldownManager();
	
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
    		
			Player player = event.getPlayer();
			String uniqueid = player.getUniqueId().toString();
			String eventname = event.getEventName();
			String playerevent = uniqueid + eventname;
            
    		//Get the amount of milliseconds that have passed since the feature was last used.
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(playerevent);
            
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
            	
                player.sendMessage(ChatColor.GREEN + "A block has been placed!");
                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());
            
            }else{
            	
            	event.setCancelled(true);
                player.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN) + " seconds before " + playerevent + " can place blocks again"); 
                
            }
            
    }
    
    
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
    		
			Player player = event.getPlayer();
			String uniqueid = player.getUniqueId().toString();
			String eventname = event.getEventName();
			String playerevent = uniqueid + eventname;
            
    		//Get the amount of milliseconds that have passed since the feature was last used.
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(playerevent);
            
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
            	
                player.sendMessage(ChatColor.GREEN + "A block has been placed!");
                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());
            
            }else{
            	
            	event.setCancelled(true);
                player.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN) + " seconds before " + playerevent + " can place blocks again"); 
                
            }
            
    }
    
}