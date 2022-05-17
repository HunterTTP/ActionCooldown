package me.hunterttp.actioncooldown;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ActionListener implements Listener{

    private final CooldownManager cooldownManager = new CooldownManager();
    private final ActionCounter actionCounter = new ActionCounter();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Check if enabled
        if(ConfigSettings.block_place_cd_enabeled == "true") {

            Player player = event.getPlayer();
            String uniqueid = player.getUniqueId().toString();
            String eventname = event.getEventName();
            String playerevent = uniqueid + eventname;

            if(actionCounter.checkCount(playerevent) < ConfigSettings.block_place_cd_limit) {

                actionCounter.addCount(playerevent);

                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

            }else{

                long timeLeft = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - cooldownManager.getCooldown(playerevent));

                if(timeLeft >= ConfigSettings.block_place_cd_duration){

                    cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

                    actionCounter.resetCount(playerevent);

                }else{

                    event.setCancelled(true);

                    player.sendMessage(ChatColor.DARK_RED.toString() + (timeLeft - ConfigSettings.block_place_cd_duration) + " seconds before you can do that again");

                }
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        //Check if enabled
        if(ConfigSettings.block_break_cd_enabeled == "true") {

            Player player = event.getPlayer();
            String uniqueid = player.getUniqueId().toString();
            String eventname = event.getEventName();
            String playerevent = uniqueid + eventname;

            if(actionCounter.checkCount(playerevent) < ConfigSettings.block_break_cd_limit) {

                actionCounter.addCount(playerevent);

                cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

            }else{

                long timeLeft = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - cooldownManager.getCooldown(playerevent));

                if(timeLeft >= ConfigSettings.block_break_cd_duration){

                    cooldownManager.setCooldown(playerevent, System.currentTimeMillis());

                    actionCounter.resetCount(playerevent);

                }else{

                    event.setCancelled(true);

                    player.sendMessage(ChatColor.DARK_RED.toString() + (timeLeft - ConfigSettings.block_break_cd_duration) + " seconds before you can do that again");

                }
            }
        }
    }
}