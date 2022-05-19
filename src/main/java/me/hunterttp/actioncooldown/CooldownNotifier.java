package me.hunterttp.actioncooldown;

import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CooldownNotifier {

    public void startTimer(Player player, String eventName, long cdDuration){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Math.toIntExact(cdDuration));
        Date date = calendar.getTime();
        Timer t = new Timer();

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {

                player.sendMessage(eventName + " is ready");

            };

        };

        t.schedule(tt, date);

    }

}
