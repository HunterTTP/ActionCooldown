package com.ActionCooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownManager {
	
	private static MainClass mainClass = MainClass.getInstance();
	
	public static long DEFAULT_COOLDOWN = Long.parseLong(mainClass.getConfig().getString("global-cooldown-seconds"));

    private final Map<String, Long> cooldowns = new HashMap<>();

    public void setCooldown(String ue, long time) {
        if(time < 1) {
            cooldowns.remove(ue);
        } else {
            cooldowns.put(ue, time);
        }
    }

    public long getCooldown(String ue){
        return cooldowns.getOrDefault(ue, (long) 0);
    }

}