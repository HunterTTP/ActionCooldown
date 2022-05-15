package com.ActionCooldown;

import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CooldownListener implements Listener{

	private final CooldownManager cooldownManager = new CooldownManager();
	
    @EventHandler
    //public void onPlace(BlockPlaceEvent event, CommandSender sender, Command command, String label, String[] args) {
    public void onPlace(BlockPlaceEvent event) {
    		
			Player p = event.getPlayer();
            
    		//Get the amount of milliseconds that have passed since the feature was last used.
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
            	
                p.sendMessage(ChatColor.GREEN + "A block has been placed!");
                cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());
            
            }else{
            	
            	event.setCancelled(true);
                p.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN) + " seconds before you can place blocks again"); 
                
            }
            
    }
    
}