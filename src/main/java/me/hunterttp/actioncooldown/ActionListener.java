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

        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;
        String blockName = event.getBlockPlaced().getType().toString();
        String exemptBlocks = ConfigSettings.block_place_exempt;
        String cdEnabled = ConfigSettings.block_place_cd_enabeled;
        long timeLeft = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - cooldownManager.getCooldown(playerEvent));
        long cdDuration = ConfigSettings.block_place_cd_duration;
        long actionLimit = ConfigSettings.block_place_cd_limit;
        long currentTime = System.currentTimeMillis();
        boolean blockExempt = exemptBlocks.toLowerCase().contains(blockName.toLowerCase());

        if(cdEnabled == "true" && blockExempt == false) {

            if(actionCounter.checkCount(playerEvent) < actionLimit) {

                actionCounter.addCount(playerEvent);

                cooldownManager.setCooldown(playerEvent, currentTime);

            }else{

                if(timeLeft >= cdDuration && actionLimit > 0){

                    cooldownManager.setCooldown(playerEvent, currentTime);

                    actionCounter.resetCount(playerEvent);

                }else{

                    event.setCancelled(true);

                    if (actionLimit <= 0){
                        player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");
                    }else{
                        player.sendMessage(ChatColor.DARK_RED.toString() + (timeLeft - cdDuration) + " seconds before you can do that again");
                    }

                }
            }
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;
        String blockName = event.getBlock().getType().toString();
        String exemptBlocks = ConfigSettings.block_break_exempt;
        String cdEnabled = ConfigSettings.block_break_cd_enabeled;
        long timeLeft = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - cooldownManager.getCooldown(playerEvent));
        long cdDuration = ConfigSettings.block_place_cd_duration;
        long actionLimit = ConfigSettings.block_break_cd_limit;
        long currentTime = System.currentTimeMillis();
        boolean blockExempt = exemptBlocks.toLowerCase().contains(blockName.toLowerCase());

        if(cdEnabled == "true"  && blockExempt == false) {

            if(actionCounter.checkCount(playerEvent) < actionLimit) {

                actionCounter.addCount(playerEvent);

                cooldownManager.setCooldown(playerEvent, currentTime);

            }else{

                if(timeLeft >= cdDuration && actionLimit > 0){

                    cooldownManager.setCooldown(playerEvent, currentTime);

                    actionCounter.resetCount(playerEvent);

                }else{

                    event.setCancelled(true);

                    if (actionLimit <= 0){
                        player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");
                    }else{
                        player.sendMessage(ChatColor.DARK_RED.toString() + (timeLeft - cdDuration) + " seconds before you can do that again");
                    }

                }
            }
        }
    }
}