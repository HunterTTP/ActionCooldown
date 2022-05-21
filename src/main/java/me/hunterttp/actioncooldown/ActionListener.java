package me.hunterttp.actioncooldown;

//import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ActionListener implements Listener {

    private final ActionTracker actionTracker = new ActionTracker();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;
        String blockName = event.getBlockPlaced().getType().toString();
        String exemptBlocks = ConfigSettings.block_place_exempt;
        String cdEnabled = ConfigSettings.block_place_cd_enabled;
        String notifyChatLimit = ConfigSettings.notify_chat_limit;
        long actionLimit = ConfigSettings.block_place_cd_limit;
        long cdDuration = ConfigSettings.block_place_cd_duration;
        boolean blockExempt = exemptBlocks.toLowerCase().contains(blockName.toLowerCase());

        //check if this module is enabled in the settings and if this paticular block is exempt
        if (cdEnabled == "true" && !blockExempt && actionLimit > 0) {

            //add (or start) a count to represent the player successfully executing the action
            actionTracker.addCount(playerEvent);

            //if total number of actions is less than the limit (config.yml)
            if (actionTracker.checkCount(playerEvent) < actionLimit) {

                //if enabled (config.yml) notify the user of events remaining
                if (notifyChatLimit == "true") {
                    player.sendMessage(eventName + " | " + actionTracker.checkCount(playerEvent) + "/" + actionLimit);
                }

                //if total number of actions are equal to the limit
            } else if (actionTracker.checkCount(playerEvent) == actionLimit) {

                //start the cooldown timer which will reset the number of actions when done
                actionTracker.startTimer(player, eventName, playerEvent, cdDuration);

                //if enabled (config.yml) notify the user of actions remaining
                if (notifyChatLimit == "true") {
                    player.sendMessage(eventName + " | " + actionTracker.checkCount(playerEvent) + "/" + actionLimit);
                }

                //if action limit is reached and cooldown is not finished
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

        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;
        String blockName = event.getBlock().getType().toString();
        String exemptBlocks = ConfigSettings.block_break_exempt;
        String cdEnabled = ConfigSettings.block_break_cd_enabled;
        String notifyChatLimit = ConfigSettings.notify_chat_limit;
        long actionLimit = ConfigSettings.block_break_cd_limit;
        long cdDuration = ConfigSettings.block_break_cd_duration;
        boolean blockExempt = exemptBlocks.toLowerCase().contains(blockName.toLowerCase());

        //check if this module is enabled in the settings and if this paticular block is exempt
        if (cdEnabled == "true" && !blockExempt && actionLimit > 0) {

            //add (or start) a count to represent the player successfully executing the action
            actionTracker.addCount(playerEvent);

            //if total number of actions is less than the limit (config.yml)
            if (actionTracker.checkCount(playerEvent) < actionLimit) {

                //if enabled (config.yml) notify the user of events remaining
                if (notifyChatLimit == "true") {
                    player.sendMessage(eventName + " | " + actionTracker.checkCount(playerEvent) + "/" + actionLimit);
                }

                //if total number of actions are equal to the limit
            } else if (actionTracker.checkCount(playerEvent) == actionLimit) {

                //start the cooldown timer which will reset the number of actions when done
                actionTracker.startTimer(player, eventName, playerEvent, cdDuration);

                //if enabled (config.yml) notify the user of actions remaining
                if (notifyChatLimit == "true") {
                    player.sendMessage(eventName + " | " + actionTracker.checkCount(playerEvent) + "/" + actionLimit);
                }

            //if action limit is reached and cooldown is not finished
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