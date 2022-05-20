package me.hunterttp.actioncooldown;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class ActionTracker {

    private final HashMap<String, Integer> actionTracker = new HashMap<>();
    private final Map<String, Long> coolDowns = new HashMap<>();
    String notifyEnabled = ConfigSettings.notify_chat_cd;
    String soundEnabled = ConfigSettings.play_sound_notification;

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

    public void setCooldown(String playerEvent, long time) {
        if(time < 1) {
            coolDowns.remove(playerEvent);
        } else {
            coolDowns.put(playerEvent, time);
        }
    }

    public long getCooldown(String playerEvent){
        return coolDowns.getOrDefault(playerEvent, (long) System.currentTimeMillis());
    }

}
