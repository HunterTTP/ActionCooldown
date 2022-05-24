package me.hunterttp.actioncooldown;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import java.util.Arrays;

public class ActionListener implements Listener {

    private static final MainClass mainClass = MainClass.getInstance();
    private final ActionTracker actionTracker = new ActionTracker();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        //Constant variables
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;

        //Config.yml variables
        String exemptTargets = mainClass.getConfig().getString("block-place-exempt-blocks");
        Boolean cdEnabled = mainClass.getConfig().getBoolean("block-place-cooldown-enabled");
        long actionLimit = mainClass.getConfig().getLong("block-place-cooldown-actionlimit");
        long cdDuration = mainClass.getConfig().getLong("block-place-cooldown-duration");

        //Custom variables
        String targetName = event.getBlockPlaced().getType().toString().toLowerCase().replace(" ","_");
        String[] exemptTargetArray = exemptTargets.toLowerCase().split(",");
        boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

        if(actionTracker.runChecks(player, eventName, playerEvent, cdEnabled, actionLimit, cdDuration, targetExemptCheck)){

            //cancel the event
            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        //Constant variables
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;

        //Config.yml variables
        String exemptTargets = mainClass.getConfig().getString("block-break-exempt-blocks");
        Boolean cdEnabled = mainClass.getConfig().getBoolean("block-break-cooldown-enabled");
        long actionLimit = mainClass.getConfig().getLong("block-break-cooldown-actionlimit");
        long cdDuration = mainClass.getConfig().getLong("block-break-cooldown-duration");

        //Custom variables
        String targetName = event.getBlock().getType().toString().toLowerCase().replace(" ","_");
        String[] exemptTargetArray = exemptTargets.toLowerCase().split(",");
        boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

        if(actionTracker.runChecks(player, eventName, playerEvent, cdEnabled, actionLimit, cdDuration, targetExemptCheck)){

            //cancel the event
            event.setCancelled(true);

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

            //Constant variables
            String uniqueId = player.getUniqueId().toString();
            String eventName = event.getEventName();
            String playerEvent = uniqueId + eventName;

            //Config.yml variables
            String exemptTargets = mainClass.getConfig().getString("attack-cooldown-exempt-mobs");
            Boolean cdEnabled = mainClass.getConfig().getBoolean("attack-cooldown-enabled");
            long actionLimit = mainClass.getConfig().getLong("attack-cooldown-actionlimit");
            long cdDuration = mainClass.getConfig().getLong("attack-cooldown-duration");

            //Custom variables
            String targetName = event.getEntity().toString().toLowerCase().replace("craft","").replace(" ","_");
            String[] exemptTargetArray = exemptTargets.toLowerCase().split(",");
            boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

            if (actionTracker.runChecks(player, eventName, playerEvent, cdEnabled, actionLimit, cdDuration, targetExemptCheck)) {

                //cancel the event
                event.setCancelled(true);

            }

        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        //Constant variables
        Player player = event.getPlayer();
        String uniqueId = player.getUniqueId().toString();
        String eventName = event.getEventName();
        String playerEvent = uniqueId + eventName;

        //Config.yml variables
        String exemptTargets = mainClass.getConfig().getString("drop-item-cooldown-exempt-items");
        Boolean cdEnabled = mainClass.getConfig().getBoolean("drop-item-cooldown-enabled");
        long actionLimit = mainClass.getConfig().getLong("drop-item-cooldown-actionlimit");
        long cdDuration = mainClass.getConfig().getLong("drop-item-cooldown-duration");

        //Custom variables
        String targetName = event.getItemDrop().getName().toLowerCase().replace(" ","_");
        String[] exemptTargetArray = exemptTargets.toLowerCase().split(",");
        boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

        if (actionTracker.runChecks(player, eventName, playerEvent, cdEnabled, actionLimit, cdDuration, targetExemptCheck)) {

            //cancel the event
            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {

        //set player
        if (event.getEntity() instanceof Player) {

            //Constant variables
            Player player = (Player) event.getEntity();
            String uniqueId = player.getUniqueId().toString();
            String eventName = event.getEventName();
            String playerEvent = uniqueId + eventName;

            //Config.yml variables
            String exemptTargets = mainClass.getConfig().getString("pickup-item-cooldown-exempt-items");
            Boolean cdEnabled = mainClass.getConfig().getBoolean("pickup-item-cooldown-enabled");
            long actionLimit = mainClass.getConfig().getLong("pickup-item-cooldown-actionlimit");
            long cdDuration = mainClass.getConfig().getLong("pickup-item-cooldown-duration");

            //Custom variables
            String targetName = event.getItem().getName().toLowerCase().replace(" ","_");
            String[] exemptTargetArray = exemptTargets.toLowerCase().split(",");
            boolean targetExemptCheck = Arrays.asList(exemptTargetArray).contains(targetName);

            if(actionTracker.runChecks(player, eventName, playerEvent, cdEnabled, actionLimit, cdDuration, targetExemptCheck)){

                //cancel the event
                event.setCancelled(true);

            }

        }

    }

}