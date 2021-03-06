package me.hunterttp.actioncooldown;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ActionTracker {

    private static final MainClass mainClass = MainClass.getInstance();
    private final HashMap<String, Integer> actionTracker = new HashMap<>();
    private final Map<String, Long> coolDowns = new HashMap<>();

    public int checkCount(String playerEvent) {

            return actionTracker.getOrDefault(playerEvent, 0);

    }

    public void resetCount(String playerEvent) {

        actionTracker.put(playerEvent, 0);

    }

    public void addCount(String playerEvent) {

        actionTracker.put(playerEvent, actionTracker.getOrDefault(playerEvent, 0) + 1);

    }

    public void subtractCount(String playerEvent) {

        actionTracker.put(playerEvent, actionTracker.getOrDefault(playerEvent, 0) - 1);

    }

    public void logEventTime(String playerEvent, long time) {
        if(time < 1) {
            coolDowns.remove(playerEvent);
        } else {
            coolDowns.put(playerEvent, time);
        }
    }

    public long checkEventTime(String playerEvent){

        return coolDowns.getOrDefault(playerEvent, (long) System.currentTimeMillis());

    }

    public void notifyCooldown(Player player, String playerEvent, long cdDuration){

        Boolean notifyEnabled = mainClass.getConfig().getBoolean("notify-chat-on-cooldown-completion");
        String notifyEvent = playerEvent + "CooldownNotify";
        long currentTime = System.currentTimeMillis();
        long lastNotify = coolDowns.getOrDefault(notifyEvent, currentTime);
        long notifyFrequency = 1000;
        long millisRemaining = (checkEventTime(playerEvent) + TimeUnit.SECONDS.toMillis(cdDuration) - System.currentTimeMillis());
        long secondsRemaining = TimeUnit.MILLISECONDS.toSeconds(millisRemaining);
        long minutesRemaining = TimeUnit.MILLISECONDS.toMinutes(millisRemaining);
        long hoursRemaining = TimeUnit.MILLISECONDS.toHours(millisRemaining);

        if (currentTime == lastNotify && Objects.equals(notifyEnabled, true)){

            coolDowns.put(notifyEvent, currentTime);

            if (secondsRemaining < 60) {

                player.sendMessage(ChatColor.DARK_RED.toString() + secondsRemaining + " seconds before you can do that again");

            }else if(secondsRemaining < 3600){

                player.sendMessage(ChatColor.DARK_RED.toString() + minutesRemaining + " minutes before you can do that again");

            }else if(secondsRemaining < 7200)  {

                player.sendMessage(ChatColor.DARK_RED.toString() + hoursRemaining + " hour before you can do that again");

            }else if(secondsRemaining > 7200) {

                player.sendMessage(ChatColor.DARK_RED.toString() + hoursRemaining + " hours before you can do that again");

            }else{

                player.sendMessage(ChatColor.DARK_RED.toString() + secondsRemaining + " seconds before you can do that again");

            }

        } else if (currentTime >= (lastNotify + notifyFrequency) && Objects.equals(notifyEnabled, true)){

            coolDowns.put(notifyEvent, currentTime);

            if (secondsRemaining < 60) {

                player.sendMessage(ChatColor.DARK_RED.toString() + secondsRemaining + " seconds before you can do that again");

            }else if(secondsRemaining < 3600){

                player.sendMessage(ChatColor.DARK_RED.toString() + minutesRemaining + " minutes before you can do that again");

            }else if(secondsRemaining < 7200)  {

                player.sendMessage(ChatColor.DARK_RED.toString() + hoursRemaining + " hour before you can do that again");

            }else if(secondsRemaining > 7200) {

                player.sendMessage(ChatColor.DARK_RED.toString() + hoursRemaining + " hours before you can do that again");

            }else{

                player.sendMessage(ChatColor.DARK_RED.toString() + secondsRemaining + " seconds before you can do that again");

            }

        }




    }

    public void notifyCount(Player player, String eventName, Long actionLimit, String playerEvent){

        String friendlyEventName = eventName.replace("Event", "");
        Boolean notifyChatLimit = mainClass.getConfig().getBoolean("notify-chat-on-remaining-uses");

        if (Objects.equals(notifyChatLimit, true)) {
            player.sendMessage(friendlyEventName + " | " + checkCount(playerEvent) + "/" + actionLimit);
        }


    }

    public void notifyBlockedAction(Player player, String playerEvent){

        Boolean notifyEnabled = mainClass.getConfig().getBoolean("notify-chat-on-cooldown-completion");
        String notifyEvent = playerEvent + "BlockNotify";
        long currentTime = System.currentTimeMillis();
        long lastNotify = coolDowns.getOrDefault(notifyEvent, currentTime);
        long notifyFrequency = 1000;

        if (currentTime == lastNotify && Objects.equals(notifyEnabled, true)){

            player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");
            coolDowns.put(notifyEvent, currentTime);

        } else if (currentTime >= (lastNotify + notifyFrequency) && Objects.equals(notifyEnabled, true)){

            player.sendMessage(ChatColor.DARK_RED.toString() + "This action is not allowed");
            coolDowns.put(notifyEvent, currentTime);

        }

    }

    public void startTimer(Player player, String eventName, String playerEvent, Long cdDuration){

        Boolean notifyEnabled = mainClass.getConfig().getBoolean("notify-chat-on-cooldown-completion");
        Boolean soundEnabled = mainClass.getConfig().getBoolean("play-sound-on-cooldown-completion");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Math.toIntExact(cdDuration));
        Date date = calendar.getTime();
        Timer t = new Timer();
        String friendlyEventName = eventName.replace("Event", "");


        long currentTime = System.currentTimeMillis();

        logEventTime(playerEvent, currentTime);

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {

                resetCount(playerEvent);

                if(Objects.equals(soundEnabled, true)) {

                    int min = 0;
                    int max = 1;
                    float pitch = new Random().nextInt(max - min + 1) + min;

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, pitch);

                }

                if(Objects.equals(notifyEnabled, true)){

                    player.sendMessage(friendlyEventName + " is ready");

                }

            };

        };

        t.schedule(tt, date);

    }

    public boolean runChecks(Player player, String eventName, String playerEvent, Boolean cdEnabled, long actionLimit, long cdDuration, boolean targetExemptCheck){

        //check if this module is enabled in the settings and if this particular block is exempt
        if (Objects.equals(cdEnabled, true) && !targetExemptCheck && actionLimit > 0) {

            //add (or start) a count to represent the player successfully executing the action
            addCount(playerEvent);

            //if total number of actions is less than the limit (config.yml)
            if (checkCount(playerEvent) < actionLimit) {

                //notify the user of events remaining (config.yml)
                notifyCount(player, eventName, actionLimit, playerEvent);

            //if total number of actions are equal to the limit
            } else if (checkCount(playerEvent) == actionLimit) {

                //start the cooldown timer which will reset the number of actions when done
                startTimer(player, eventName, playerEvent, cdDuration);

                //if enabled (config.yml) notify the user of events remaining
                notifyCount(player, eventName, actionLimit, playerEvent);

            //if action limit is reached and/or cooldown is not finished
            } else {

                //remove the count as the action was cancelled
                subtractCount(playerEvent);

                //notify player of the time remaining before the cooldown is complete
                notifyCooldown(player, playerEvent, cdDuration);

                //cancel the event
                return true;

            }

            //if action limit is zero (config.yml)
        } else if (actionLimit <= 0) {

            //notify player with a special message
            notifyBlockedAction(player, playerEvent);

            //cancel the event
            return true;

        }

        //check is done
        return false;

    }

}