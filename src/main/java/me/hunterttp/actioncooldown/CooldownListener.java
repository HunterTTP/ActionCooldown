package me.hunterttp.actioncooldown;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class CooldownListener implements Listener{

    private final CooldownManager cooldownManager = new CooldownManager();
    public long BlockPlaceEventCounter = 0;
    public long BlockBreakEventCounter = 0;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Check if enabled
        if(CooldownSettings.block_place_cd_enabeled == "true") {

            Player player = event.getPlayer();
            String uniqueid = player.getUniqueId().toString();
            String eventname = event.getEventName();
            String playerevent = uniqueid + eventname;

            if(BlockPlaceEventCounter < CooldownSettings.block_place_cd_limit) {

                BlockPlaceEventCounter++;

                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

            }else{


                long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(playerevent);

                if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownSettings.block_place_cd_duration){

                    cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

                    BlockPlaceEventCounter = 1;

                }else{

                    event.setCancelled(true);

                    player.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownSettings.block_place_cd_duration) + " seconds before you can do that again");

                }
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        //Check if enabled
        if(CooldownSettings.block_break_cd_enabeled == "true") {

            Player player = event.getPlayer();
            String uniqueid = player.getUniqueId().toString();
            String eventname = event.getEventName();
            String playerevent = uniqueid + eventname;

            if(BlockBreakEventCounter < CooldownSettings.block_break_cd_limit) {

                BlockBreakEventCounter++;

                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

            }else{

                long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(playerevent);

                if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownSettings.block_break_cd_duration){

                    cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

                    BlockBreakEventCounter = 1;

                }else{

                    event.setCancelled(true);

                    player.sendMessage(ChatColor.RED.toString() + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - CooldownSettings.block_break_cd_duration) + " seconds before you can do that again");

                }
            }
        }
    }
}