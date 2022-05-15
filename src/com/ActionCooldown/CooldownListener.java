package com.ActionCooldown;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CooldownListener implements Listener{

	private final CooldownManager cooldownManager = new CooldownManager();
	
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
    		
			Player p = event.getPlayer();
			String pn = p.getName();
			//String u = p.getUniqueId().toString();
			String e = event.getEventName();
			String ue = pn + e;
            
    		//Get the amount of milliseconds that have passed since the feature was last used.
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(ue);
            
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
            	
                p.sendMessage(ChatColor.GREEN + "A block has been placed!");
                cooldownManager.setCooldown(ue, System.currentTimeMillis());
            
            }else{
            	
            	event.setCancelled(true);
                p.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownManager.DEFAULT_COOLDOWN) + " seconds before " + ue + " can place blocks again"); 
                
            }
            
    }
    
}