package me.hunterttp.actioncooldown;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class ActionCounter {

    private final HashMap<String, Integer> actiontracker = new HashMap<>();
    String notifyEnabled = ConfigSettings.notify_chat_cd;
    String soundEnabled = ConfigSettings.play_sound_notifcation;

    public HashMap<String, Integer> getActionTracker() {
        return actiontracker;
    }

    public int checkCount(String playerevent) {

            return actiontracker.getOrDefault(playerevent, 0);

    }

    public void resetCount(String playerevent) {

        actiontracker.put(playerevent, 0);

    }

    public void addCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.getOrDefault(playerevent, 0) + 1);

    }

    public void subtractCount(String playerevent) {

        actiontracker.put(playerevent, actiontracker.getOrDefault(playerevent, 0) - 1);

    }

    public void startTimer(Player player, String eventName, long cdDuration, String playerEvent){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Math.toIntExact(cdDuration));
        Date date = calendar.getTime();
        Timer t = new Timer();
        Random rand = new Random();
        int low = 1;
        int high = 3;
        int result = rand.nextInt(high-low) + low;
        float randomNote = (float)result;

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {

                resetCount(playerEvent);

                if(soundEnabled == "true") {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1.0f, randomNote);
                    //player.sendMessage("Note was: "+ randomNote);
                }

                if(notifyEnabled == "true"){
                    player.sendMessage(eventName + " is ready");
                }

            };

        };

        t.schedule(tt, date);

    }

}
