package me.hunterttp.actioncooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {

    private final Map<String, Long> cooldowns = new HashMap<>();

    public void setCooldown(String playerevent, long time) {
        if(time < 1) {
            cooldowns.remove(playerevent);
        } else {
            cooldowns.put(playerevent, time);
        }
    }

    public long getCooldown(String playerevent){
        return cooldowns.getOrDefault(playerevent, (long) 0);
    }

}