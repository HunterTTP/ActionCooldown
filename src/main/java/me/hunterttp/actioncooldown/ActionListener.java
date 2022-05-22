package me.hunterttp.actioncooldown;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;

public class ActionListener implements Listener {

    private final ActionTracker actionTracker = new ActionTracker();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Constant variables
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;

        //Custom variables
        String exemptBlocks = ConfigSettings.block_place_exempt;
        String cdEnabled = ConfigSettings.block_place_cd_enabled;
        String targetName = event.getBlockPlaced().getType().toString().toLowerCase();
        String[] exemptTargetArray = exemptBlocks.toLowerCase().split(",");
        long actionLimit = ConfigSettings.block_place_cd_limit;
        long cdDuration = ConfigSettings.block_place_cd_duration;
        boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

        //check if this module is enabled in the settings and if this particular block is exempt
        if (cdEnabled == "true" && !targetExemptCheck && actionLimit > 0) {

            //add (or start) a count to represent the player successfully executing the action
            actionTracker.addCount(playerEvent);

            //if total number of actions is less than the limit (config.yml)
            if (actionTracker.checkCount(playerEvent) < actionLimit) {

                //notify the user of events remaining (config.yml)
                actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

            //if total number of actions are equal to the limit
            } else if (actionTracker.checkCount(playerEvent) == actionLimit) {

                //start the cooldown timer which will reset the number of actions when done
                actionTracker.startTimer(player, eventName, playerEvent, cdDuration);

                //if enabled (config.yml) notify the user of events remaining
                actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

            //if action limit is reached and/or cooldown is not finished
            } else {

                //cancel the event
                event.setCancelled(true);

                //remove the count as the action was cancelled
                actionTracker.subtractCount(playerEvent);

                //notify player of the time remaining before the cooldown is complete
                actionTracker.notifyCooldown(player, playerEvent, cdDuration);

            }

        //if action limit is zero (config.yml)
        } else if (actionLimit <= 0) {

            //notify player with a special message
            player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");

        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        //Constant variables
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;

        //Custom variables
        String targetName = event.getBlock().getType().toString().toLowerCase();
        String exemptBlocks = ConfigSettings.block_break_exempt;
        String cdEnabled = ConfigSettings.block_break_cd_enabled;
        String[] exemptTargetArray = exemptBlocks.toLowerCase().split(",");
        long actionLimit = ConfigSettings.block_break_cd_limit;
        long cdDuration = ConfigSettings.block_break_cd_duration;
        boolean exemptTargetCheck = Arrays.asList(exemptTargetArray).contains(targetName);

        //check if this module is enabled in the settings and if this particular block is exempt
        if (cdEnabled == "true" && !exemptTargetCheck && actionLimit > 0) {

            //add (or start) a count to represent the player successfully executing the action
            actionTracker.addCount(playerEvent);

            //if total number of actions is less than the limit (config.yml)
            if (actionTracker.checkCount(playerEvent) < actionLimit) {

                //notify the user of events remaining (config.yml)
                actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

                //if total number of actions are equal to the limit
            } else if (actionTracker.checkCount(playerEvent) == actionLimit) {

                //start the cooldown timer which will reset the number of actions when done
                actionTracker.startTimer(player, eventName, playerEvent, cdDuration);

                //if enabled (config.yml) notify the user of events remaining
                actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

                //if action limit is reached and/or cooldown is not finished
            } else {

                //cancel the event
                event.setCancelled(true);

                //remove the count as the action was cancelled
                actionTracker.subtractCount(playerEvent);

                //notify player of the time remaining before the cooldown is complete
                actionTracker.notifyCooldown(player, playerEvent, cdDuration);

            }

            //if action limit is zero (config.yml)
        } else if (actionLimit <= 0) {

            //notify player with a special message
            player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");

        }

    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        Player player = null;

        //set player
        if (event.getDamager() instanceof Projectile) {

            Projectile projectile = (Projectile) event.getDamager();

            if (projectile.getShooter() instanceof Player) {

                player = (Player) projectile.getShooter();

            }

        } else if (event.getDamager() instanceof Player) {

            player = (Player) event.getDamager();

        }

        //send message
        if (player != null) {

            String uniqueId = player.getUniqueId().toString();
            String eventName = event.getEventName();
            String playerEvent = uniqueId + eventName;

            //Custom variables
            String exemptMobs = ConfigSettings.attack_exempt_mobs;
            String targetName = event.getEntity().toString();
            String cdEnabled = ConfigSettings.attack_cd_enabled;
            String[] exemptTargetArray = exemptMobs.toLowerCase().split(",");
            long actionLimit = ConfigSettings.attack_cd_limit;
            long cdDuration = ConfigSettings.attack_cd_duration;
            boolean exemptTargetCheck = Arrays.asList(exemptTargetArray).contains(targetName);

            //check if this module is enabled in the settings and if this particular target is exempt
            if (cdEnabled == "true" && !exemptTargetCheck && actionLimit > 0) {

                //add (or start) a count to represent the player successfully executing the action
                actionTracker.addCount(playerEvent);

                //if total number of actions is less than the limit (config.yml)
                if (actionTracker.checkCount(playerEvent) < actionLimit) {

                    //notify the user of events remaining (config.yml)
                    actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

                    //if total number of actions are equal to the limit
                } else if (actionTracker.checkCount(playerEvent) == actionLimit) {

                    //start the cooldown timer which will reset the number of actions when done
                    actionTracker.startTimer(player, eventName, playerEvent, cdDuration);

                    //if enabled (config.yml) notify the user of events remaining
                    actionTracker.notifyCount(player, eventName, actionLimit, playerEvent);

                    //if action limit is reached and/or cooldown is not finished
                } else {

                    //cancel the event
                    event.setCancelled(true);

                    //remove the count as the action was cancelled
                    actionTracker.subtractCount(playerEvent);

                    //notify player of the time remaining before the cooldown is complete
                    actionTracker.notifyCooldown(player, playerEvent, cdDuration);

                }

                //if action limit is zero (config.yml)
            } else if (actionLimit <= 0) {

                //notify player with a special message
                player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");

            }

        }

    }

}