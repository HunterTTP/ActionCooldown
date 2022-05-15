package com.ActionCooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
	
	
	private static MainClass mainClass = MainClass.getInstance();
	
	public static long DEFAULT_COOLDOWN = Long.parseLong(mainClass.getConfig().getString("global-cooldown-seconds"));

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public void setCooldown(UUID player, long time) {
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public long getCooldown(UUID player){
        return cooldowns.getOrDefault(player, (long) 0);
    }

}